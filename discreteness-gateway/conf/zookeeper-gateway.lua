zoo = require "zoo"
cjson = require "cjson"
uri = ngx.var.uri
remoteAdderss = ngx.var.remote_addr
isProEnv = false

if ngx.var.http_host == "www.test.com"  then
	if ngx.var.uri == "" or  ngx.var.uri == "/" or ngx.var.uri == "/?#/" or ngx.var.uri == "/?#" or ngx.var.uri == "/?/" or ngx.var.uri == "/?" or address == nil then
		return ngx.exec("@frontProxy",{})
	end
end

function getProxyAddress(env)
	childs, err = zoo.childrens(getPath(env))
	tempChilds = {}
	if childs ~= nil and table.maxn(childs) then
		for k, v in pairs(childs) do
			if string.match(v, '(%d+.%d+.%d+.%d+:%d+)') ~= nil then
				table.insert(tempChilds,v)	
			end
		end
	end
	return tempChilds
end

function dynamicProxy(env)
	proxyAddress = getProxyAddress(env)
	if proxyAddress == nil or table.maxn(proxyAddress) <= 0 then
		proxyAddress = getProxyAddress(true)
	end
	
	if proxyAddress ~= nil or table.maxn(proxyAddress) > 0 then
		index = math.random(1,table.maxn(proxyAddress))
		address = proxyAddress[index]
		return ngx.exec("/proxy",{proxyhost=address})
	else
		return ngx.exec("/error404Proxy",{errorUrl="404"})
	end
end

function getPath(env)
	if env == false then
		if uri == "/" then
			return "/url/" ..remoteAdderss .. "/root"
		else
			return "/url/" .. remoteAdderss .. uri
		end
	else
		if uri == "/" then
			return "/url/root"
		else
			return "/url" .. uri
		end
	end
end

dynamicProxy(isProEnv)



