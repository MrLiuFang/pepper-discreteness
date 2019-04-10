yum -y install wget 
mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
sudo yum clean all
sudo yum makecache
yum -y install zlib zlib-devel gcc-c++ libtool openssl openssl-devel readline-devel gd-devel libgd2-xpm libgd2-xpm-dev gd-devel libevent libevent-devel pcre-devel
yum -y --exclude=kernel* update

#cd ./libevent-2.1.8-stable
#gmake confclean
#make clean
#make uninstall
#./configure
#make 
#make install

#cd ../lua-5.3.5
#make clean
#make uninstall
#make linux test
#make install



cd ./LuaJIT-2.1.0-beta2
make clean
make uninstall
make && make install
export LUAJIT_LIB=/usr/local/lib
export LUAJIT_INC=/usr/local/include/luajit-2.1
make clean

cd ../automake-1.15.1
gmake confclean
make clean
make uninstall
./configure
make && make install
libtoolize 
aclocal
autoheader
make clean

cd ../lua-cjson
make clean
make uninstall
make all
make install
\cp -r -f ./cjson.so /usr/local/lib/lua/5.1/
make clean


cd ../lua-redis-parser-master
make clean
make uninstall
make && make install
make clean

cd ../zookeeper-3.5.4-beta/src/c
make clean
make uninstall
./configure --enable-shared --disable-static
make && make install
make clean

rm -rf /usr/local/nginx
cd ../../../nginx-1.14.2
make clean
make uninstall
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
make clean

\cp -r -f ../ngx_zookeeper_lua/lua/* /usr/share/lua/5.1/
\cp -r -f ../conf/* /usr/local/nginx/conf

\cp -r -f ../conf/nginx.service /lib/systemd/system/
systemctl enable nginx.service
systemctl stop nginx.service
systemctl start nginx.service