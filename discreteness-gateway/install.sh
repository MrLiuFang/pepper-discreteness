
yum -y install zlib zlib-devel gcc-c++ libtool openssl openssl-devel gd-devel libgd2-xpm libgd2-xpm-dev gd-devel libevent-devel pcre-devel

cd ./libevent-2.1.8-stable
gmake confclean
make clean
make uninstall
./configure
make 
make install

cd ../LuaJIT-2.1.0-beta2
make clean
make uninstall
make && make install

export LUAJIT_LIB=/usr/local/lib
export LUAJIT_INC=/usr/local/include/luajit-2.1

cd ../lua-redis-parser-master
make clean
make uninstall
make && make install

cd ../zookeeper-3.5.4-beta/src/c
gmake confclean
make clean
make uninstall
./configure --enable-shared --disable-static
make && make install

cd ../../../nginx-1.14.2
gmake confclean
make clean
./configure --prefix=/usr/local/nginx \
		--with-ld-opt="-Wl,-rpath,/usr/local/lib" \
		--with-http_image_filter_module \
		--with-http_stub_status_module \
		--with-http_ssl_module \
		--with-http_realip_module \
		--add-module=../redis2-nginx-module-master \
		--add-module=../ngx_devel_kit-master \
		--add-module=../set-misc-nginx-module-master \
		--add-module=../ngx_http_redis-0.3.8 \
		--add-module=../lua-nginx-module-master \
		--add-module=../echo-nginx-module-master \
		--add-module=../ngx_zookeeper_lua \
		--with-debug
make && make install