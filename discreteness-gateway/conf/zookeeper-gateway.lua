local zoo = require "zoo"
local cjson = require "cjson"
local uri = ngx.var.uri;

if uri == "/" then
	uri = "/url/root"
else
	uri = "/url" .. uri
end
local childs, err = zoo.childrens(uri)
--[[ngx.say(cjson.encode(childs and childs or { error = err }))]]
if childs ~= nil then
	address = ""
	for k, v in pairs(childs) do
		address = v
	end
	return ngx.exec("/proxy",{proxyhost=address,uri=ngx.var.uri})
else
	return ngx.exec("/proxy",{proxyhost=reply})
end

