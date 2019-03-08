local zoo = require "zoo"
local cjson = require "cjson"
local uri = ngx.var.uri
local devAdderss = ngx.var.remote_addr
local isProEnv = false
local proPath = ""
local devPath = ""
local childs, err

if uri == "/" then
	if isProEnv == false then
		devPath = "/url/" ..devAdderss .. "/root"
	else
		proPath = "/url/root"
	end
else
	if isProEnv == false then
		devPath = "/url/" .. devAdderss .. uri
	else
		proPath = "/url" .. uri
	end
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
	if ngx.var.http_host == "www.test.com"  then
		if ngx.var.uri == "" or  ngx.var.uri == "/" or ngx.var.uri == "/?#/" or ngx.var.uri == "/?#" or ngx.var.uri == "/?/" or ngx.var.uri == "/?" or address == nil then
			return ngx.exec("@frontProxy",{})
		end
	end
	if address ~= nil then
		return ngx.exec("/proxy",{proxyhost=address})
	else
		return ngx.exec("@errorProxy",{proxyhost=address})
	end
	--[[for k, v in pairs(childs) do
		address = v
	end
	return ngx.exec("/proxy",{proxyhost=address,uri=ngx.var.uri})
	]]
else
	--[[return ngx.exec("@frontProxy",{})]]
end


