package com.pepper.register;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.ReflectionException;
import javax.servlet.ServletContext;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CreateModable;
import org.apache.curator.framework.imps.CuratorFrameworkImpl;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.pepper.util.SpringContextUtil;

/**
 * 注册资源URL（zookeeper）
 * @author mrliu
 *
 */
@Component
@Lazy
//@ConditionalOnBean(name="curatorFrameworkImpl")
public class RegisterUrl implements ApplicationListener<ContextRefreshedEvent>{
	
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CuratorFramework curatorFramework;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private WebApplicationContext webApplicationConnect;


	private String host;

	private String port;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		init();
	}
	
	public void init() {
		registerUrl();
	}

	public void registerUrl() {
		try {
			String ctx = webApplicationConnect.getServletContext().getContextPath();
			webApplicationConnect.getServletContext().setAttribute("ctx", ctx);
			ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
			Map<String, Object> controllerBean = applicationContext.getBeansWithAnnotation(Controller.class);
			if (controllerBean == null || controllerBean.size() <= 0) {
				return;
			}
			List<String> endPoints = getEndPoints();
			logger.info(endPoints.toString());
			Set<String> resultUrl = new HashSet<String>();
			RequestMappingHandlerMapping bean = webApplicationConnect.getBean(RequestMappingHandlerMapping.class);
			Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
			for (RequestMappingInfo rmi : handlerMethods.keySet()) {
				PatternsRequestCondition pc = rmi.getPatternsCondition();
				Set<String> pSet = pc.getPatterns();
				resultUrl.addAll(pSet);
			}
			
			for (String url : resultUrl) {
				if (environment.getProperty("environment","").equals("dev")) {
					logger.info("注册url：" + host + "_" + url + "--地址--" + host + ":" + port + ctx);
					registerPath(host+"/"+url,host + ":" + port + ctx);
				} else {
					logger.info("注册url：" + url + "--地址--" + host + ":" + port + ctx);
					registerPath(url,host + ":" + port + ctx);
				}
			}
			registerAssets(webApplicationConnect.getServletContext());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("资源注册失败！");
		}

	}
	
	private void registerPath(String path,String data) throws Exception{
		if(path.equals("/")){
			path="/index";
		}
		StringBuffer sb = new StringBuffer("/url");
		sb.append(path);
		Stat stat = curatorFramework.checkExists().forPath(sb.toString());
		if(stat != null){
			curatorFramework.delete().forPath( sb.toString());
		}
		curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath( sb.toString());
	}

	/**
	 * 获取tomcat web 服务端口和地址
	 * 
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 * @throws UnknownHostException
	 * @throws AttributeNotFoundException
	 * @throws InstanceNotFoundException
	 * @throws MBeanException
	 * @throws ReflectionException
	 */
	private List<String> getEndPoints() throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"),
				Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
		ArrayList<String> endPoints = new ArrayList<String>();
		host = NetUtils.getLocalHost();
		// 获取端口
		for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
			ObjectName obj = i.next();
			String scheme = mbs.getAttribute(obj, "scheme").toString();
			port = obj.getKeyProperty("port");
			String ep = scheme + "://" + host + ":" + port;
			endPoints.add(ep);
		}
		return endPoints;
	}

	/**
	 * 注册静态资源
	 * @throws Exception 
	 */
	private void registerAssets(ServletContext servletContext) throws Exception {
		String ctx = webApplicationConnect.getServletContext().getContextPath();
		List<String> assetsList = new ArrayList<String>();
		
		/**
		 * 当前类路径，也就是base/WEB-INF/classes/
		 */
		String appDir = "";

		/**
		 * WEB-INF路径，也就是base/WEB-INF/
		 */
		String webInfDir = "";

		if (StringUtils.hasText(Thread.currentThread().getContextClassLoader().getResource("/").getPath())) {
			// tomcat和weblogic兼容
			appDir = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		}
		if (StringUtils.hasText(appDir)) {
			webInfDir = appDir.substring(0, appDir.length() - 8);
		}

		traverseFolder(assetsList, webInfDir + "lib", servletContext);
		for (String assetsPath : assetsList) {
			if (environment.getProperty("environment").equals("dev")) {
				logger.info("注册url：" + host + "_" + assetsPath + "--地址--" + host + ":" + port + ctx);
				registerPath(host+"/"+assetsPath,host + ":" + port + ctx);
			} else {
				logger.info("注册url：" + assetsPath + "--地址--" + host + ":" + port + ctx);
				registerPath(assetsPath,host + ":" + port + ctx);
			}
//			logger.info(assetsPath + "  " + host + ":" + port + ctx);
		}
	}

	@SuppressWarnings("rawtypes")
	private void traverseFolder(List<String> assetsList, String path, ServletContext servletContext)
			throws IOException {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length > 0) {
				for (File file2 : files) {
					if (file2.isDirectory() && !file2.getName().equals("WEB-INF")
							&& !file2.getName().equals("META-INF")) {
						traverseFolder(assetsList, file2.getAbsolutePath(), servletContext);
					} else if (!file2.isDirectory()) {
						JarFile jarFile = new JarFile(file2);
						Enumeration enu = jarFile.entries();
						while (enu.hasMoreElements()) {
							JarEntry element = (JarEntry) enu.nextElement();
							String name = element.getName();
							if (!element.isDirectory() && name.indexOf("META-INF/resources/assets") == 0) {
								assetsList.add(servletContext.getContextPath()
										+ name.replaceAll("META-INF/resources", "").replaceAll("\\\\", "/"));
							}
						}
						jarFile.close();
					}
				}
			}
		}
	}

}
