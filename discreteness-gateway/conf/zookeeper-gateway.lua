zoo = require "zoo"
cjson = require "cjson"
uri = ngx.var.uri
devAdderss = ngx.var.remote_addr
isProEnv = false
proPath = ""
devPath = ""
childs, err = nil

if ngx.var.http_host == "www.test.com" then
	if uri == "" or uri == "/" or uri == "/?#/" or uri == "/?#" or uri == "/?/" or uri == "/?" then
		return ngx.exec("@frontProxy",{})
	end
end

if uri == "/" then
	devPath = "/url/" ..devAdderss .. "/root"
	proPath = "/url/root"
else
	devPath = "/url/" .. devAdderss .. uri
	proPath = "/url" .. uri
end
if isProEnv == false then
	childs, err = zoo.childrens(devPath)
	if childs == nil or table.maxn(childs) == 0 then
		childs, err = zoo.childrens(proPath)
	end
else
	childs, err = zoo.childrens(proPath)
end

if childs ~= nil and table.maxn(childs) > 0 then
	index = math.random(1,table.maxn(childs))
	address = childs[index]
	count = 0
	while(string.match(address, '(%d+.%d+.%d+.%d+:%d+)') == nil and count < 10 )
	do
		count = 1+count
		index = math.random(1,table.maxn(childs))
		address = childs[index]
	end
	if address ~= nil then
		return ngx.exec("/proxy",{proxyhost=address})
	else
		return ngx.exec("/error404Proxy",{errorUrl="404"})
	end
else
	return ngx.exec("/error404Proxy",{errorUrl="404"})
end


