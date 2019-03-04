local parser = require("redis.parser")

local res = ngx.location.capture("/getProxyHostToDev", { args = {} })

local reply
if res.status == 200 then
        reply = parser.parse_reply(res.body)
end
if reply == nil then
        res = ngx.location.capture("/getProxyHostToPro", { args = {} })
end
if res.status == 200 then
        reply = parser.parse_reply(res.body)
        if ngx.var.http_host == "qw.demo.qi-cloud.com"  then
                if ngx.var.uri == "" or  ngx.var.uri == "/" or ngx.var.uri == "/?#/" or ngx.var.uri == "/?#" or ngx.var.uri == "/?/" or ngx.var.uri == "/?" or reply == nil then
                        return ngx.exec("@frontProxy",{})
                end
        end
        if ngx.var.http_host == "qwwx.demo.qi-cloud.com" then
                if ngx.var.uri == "" or  ngx.var.uri == "/" or ngx.var.uri == "/?#/" or ngx.var.uri == "/?#" or ngx.var.uri == "/?/" or ngx.var.uri == "/?" or reply == nil then
                        return ngx.exec("@frontProxyWx",{})
                end
        end
        if ngx.var.http_host == "qw.demo.qi-cloud.com" or ngx.var.http_host == "qwwx.demo.qi-cloud.com" then
                if reply ~= nil then
                        return ngx.exec("/proxy",{proxyhost=reply})
                end
        end
        if reply ~= nil then
                return ngx.exec("/proxy",{proxyhost=reply})
        end
end

