
default:	build

clean:
	rm -rf Makefile objs

build:
	$(MAKE) -f objs/Makefile

install:
	$(MAKE) -f objs/Makefile install

modules:
	$(MAKE) -f objs/Makefile modules

upgrade:
	/development/project/hamster/pepper-discreteness/discreteness-gateway/ngx_zookeeper_lua/install/nginx-1.13.6/sbin/nginx -t

	kill -USR2 `cat /development/project/hamster/pepper-discreteness/discreteness-gateway/ngx_zookeeper_lua/install/nginx-1.13.6/logs/nginx.pid`
	sleep 1
	test -f /development/project/hamster/pepper-discreteness/discreteness-gateway/ngx_zookeeper_lua/install/nginx-1.13.6/logs/nginx.pid.oldbin

	kill -QUIT `cat /development/project/hamster/pepper-discreteness/discreteness-gateway/ngx_zookeeper_lua/install/nginx-1.13.6/logs/nginx.pid.oldbin`
