local zoo = require "zoo"
local cjson = require "cjson"
local uri = ngx.var.uri
local devAdderss = ngx.var.remote_addr
local isProEnv = false
local proPath = ""
local devPath = ""
local childs, err

if ngx.var.http_host == "www.test.com"  then
	if ngx.var.uri == "" or  ngx.var.uri == "/" or ngx.var.uri == "/?#/" or ngx.var.uri == "/?#" or ngx.var.uri == "/?/" or ngx.var.uri == "/?" or address == nil then
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


