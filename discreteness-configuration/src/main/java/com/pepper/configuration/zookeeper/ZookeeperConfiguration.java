package com.pepper.configuration.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author mrliu
 *
 */
@Configuration
@ConditionalOnClass(value={ZooKeeper.class,CuratorFramework.class})
@ConditionalOnProperty(name = "zookeeper.address")
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperConfiguration {

	private ZookeeperProperties zookeeperProperties;

	public ZookeeperConfiguration(ZookeeperProperties zookeeperProperties) {
		super();
		this.zookeeperProperties = zookeeperProperties;
	}
	
	@Bean
	public CuratorFramework curatorFramework(){
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(zookeeperProperties.getAddress())
		        .sessionTimeoutMs(1000)    // 连接超时时间
		        .connectionTimeoutMs(1000) // 会话超时时间
		        // 刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
		        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
		        .build();
		curatorFramework.start();
		return curatorFramework;
	}

//	@Bean
//	public ZooKeeper zooKeeper() throws IOException, InterruptedException {
//		ZooKeeper zookeeper = new ZooKeeper(zookeeperProperties.getAddress(), 1, new Watcher() {
//			@Override
//			public void process(WatchedEvent event) {
//				if (event.getState() == KeeperState.SyncConnected) {
//					connectedSignal.countDown();
//				}
//			}
//		});
//		connectedSignal.await();
//		return zookeeper;
//	}
}
