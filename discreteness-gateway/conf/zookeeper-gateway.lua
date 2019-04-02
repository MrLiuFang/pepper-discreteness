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

if childs ~= nil then
	index = math.random(1,table.maxn(childs))
	address = childs[index]
	while(string.match(address, '(%d+.%d+.%d+.%d+:%d+)') == nil )
	do
		index = math.random(1,table.maxn(childs))
		address = childs[index]
	end
	if address ~= nil then
		return ngx.exec("/proxy",{proxyhost=address})
	else
		return ngx.exec("@errorProxy",{proxyhost=address})
	end
else
	return ngx.exec("@frontProxy",{})
end


