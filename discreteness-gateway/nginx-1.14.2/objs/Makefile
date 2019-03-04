
CC =	cc
CFLAGS = -I/usr/local/include/luajit-2.1  -pipe  -O -W -Wall -Wpointer-arith -Wno-unused-parameter -Werror -g  -DNDK_SET_VAR -DNDK_UPSTREAM_LIST -DNDK_SET_VAR
CPP =	cc -E
LINK =	$(CC)


ALL_INCS = -I src/core \
	-I src/event \
	-I src/event/modules \
	-I src/os/unix \
	-I ../ngx_devel_kit-master/objs \
	-I objs/addon/ndk \
	-I ../lua-nginx-module-master/src/api \
	-I ../ngx_zookeeper_lua \
	-I objs \
	-I src/http \
	-I src/http/modules \
	-I ../ngx_devel_kit-master/src \
	-I ../ngx_devel_kit-master/src \
	-I ../ngx_devel_kit-master/objs \
	-I objs/addon/ndk \
	-I /usr/local/include/luajit-2.1 \
	-I /include


CORE_DEPS = src/core/nginx.h \
	src/core/ngx_config.h \
	src/core/ngx_core.h \
	src/core/ngx_log.h \
	src/core/ngx_palloc.h \
	src/core/ngx_array.h \
	src/core/ngx_list.h \
	src/core/ngx_hash.h \
	src/core/ngx_buf.h \
	src/core/ngx_queue.h \
	src/core/ngx_string.h \
	src/core/ngx_parse.h \
	src/core/ngx_parse_time.h \
	src/core/ngx_inet.h \
	src/core/ngx_file.h \
	src/core/ngx_crc.h \
	src/core/ngx_crc32.h \
	src/core/ngx_murmurhash.h \
	src/core/ngx_md5.h \
	src/core/ngx_sha1.h \
	src/core/ngx_rbtree.h \
	src/core/ngx_radix_tree.h \
	src/core/ngx_rwlock.h \
	src/core/ngx_slab.h \
	src/core/ngx_times.h \
	src/core/ngx_shmtx.h \
	src/core/ngx_connection.h \
	src/core/ngx_cycle.h \
	src/core/ngx_conf_file.h \
	src/core/ngx_module.h \
	src/core/ngx_resolver.h \
	src/core/ngx_open_file_cache.h \
	src/core/ngx_crypt.h \
	src/core/ngx_proxy_protocol.h \
	src/core/ngx_syslog.h \
	src/event/ngx_event.h \
	src/event/ngx_event_timer.h \
	src/event/ngx_event_posted.h \
	src/event/ngx_event_connect.h \
	src/event/ngx_event_pipe.h \
	src/os/unix/ngx_time.h \
	src/os/unix/ngx_errno.h \
	src/os/unix/ngx_alloc.h \
	src/os/unix/ngx_files.h \
	src/os/unix/ngx_channel.h \
	src/os/unix/ngx_shmem.h \
	src/os/unix/ngx_process.h \
	src/os/unix/ngx_setaffinity.h \
	src/os/unix/ngx_setproctitle.h \
	src/os/unix/ngx_atomic.h \
	src/os/unix/ngx_gcc_atomic_x86.h \
	src/os/unix/ngx_thread.h \
	src/os/unix/ngx_socket.h \
	src/os/unix/ngx_os.h \
	src/os/unix/ngx_user.h \
	src/os/unix/ngx_dlopen.h \
	src/os/unix/ngx_process_cycle.h \
	src/os/unix/ngx_linux_config.h \
	src/os/unix/ngx_linux.h \
	src/event/ngx_event_openssl.h \
	src/core/ngx_regex.h \
	objs/ngx_auto_config.h


CORE_INCS = -I src/core \
	-I src/event \
	-I src/event/modules \
	-I src/os/unix \
	-I ../ngx_devel_kit-master/objs \
	-I objs/addon/ndk \
	-I ../lua-nginx-module-master/src/api \
	-I ../ngx_zookeeper_lua \
	-I objs


HTTP_DEPS = src/http/ngx_http.h \
	src/http/ngx_http_request.h \
	src/http/ngx_http_config.h \
	src/http/ngx_http_core_module.h \
	src/http/ngx_http_cache.h \
	src/http/ngx_http_variables.h \
	src/http/ngx_http_script.h \
	src/http/ngx_http_upstream.h \
	src/http/ngx_http_upstream_round_robin.h \
	src/http/modules/ngx_http_ssi_filter_module.h \
	src/http/modules/ngx_http_ssl_module.h


HTTP_INCS = -I src/http \
	-I src/http/modules \
	-I ../ngx_devel_kit-master/src \
	-I ../ngx_devel_kit-master/src \
	-I ../ngx_devel_kit-master/objs \
	-I objs/addon/ndk \
	-I /usr/local/include/luajit-2.1 \
	-I /include


ADDON_DEPS = $(CORE_DEPS)                                                                             ../redis2-nginx-module-master/src/ddebug.h                                                 ../redis2-nginx-module-master/src/ngx_http_redis2_module.h                                 ../redis2-nginx-module-master/src/ngx_http_redis2_handler.h                                ../redis2-nginx-module-master/src/ngx_http_redis2_reply.h                                  ../redis2-nginx-module-master/src/ngx_http_redis2_util.h                                    ../ngx_devel_kit-master/src/ndk.h                                                                          ../set-misc-nginx-module-master/src/ddebug.h                                                 ../set-misc-nginx-module-master/src/ngx_http_set_default_value.h                             ../set-misc-nginx-module-master/src/ngx_http_set_hashed_upstream.h                           ../set-misc-nginx-module-master/src/ngx_http_set_quote_sql.h                                 ../set-misc-nginx-module-master/src/ngx_http_set_quote_json.h                                ../set-misc-nginx-module-master/src/ngx_http_set_unescape_uri.h                              ../set-misc-nginx-module-master/src/ngx_http_set_escape_uri.h                                ../set-misc-nginx-module-master/src/ngx_http_set_hash.h                                      ../set-misc-nginx-module-master/src/ngx_http_set_local_today.h                               ../set-misc-nginx-module-master/src/ngx_http_set_hex.h                                       ../set-misc-nginx-module-master/src/ngx_http_set_base64.h                                    ../set-misc-nginx-module-master/src/ngx_http_set_random.h                                    ../set-misc-nginx-module-master/src/ngx_http_set_rotate.h                                    ../set-misc-nginx-module-master/src/ngx_http_set_secure_random.h                             ../set-misc-nginx-module-master/src/ngx_http_set_misc_module.h                                ../set-misc-nginx-module-master/src/ngx_http_set_hmac.h              ../lua-nginx-module-master/src/ddebug.h             ../lua-nginx-module-master/src/ngx_http_lua_script.h             ../lua-nginx-module-master/src/ngx_http_lua_log.h             ../lua-nginx-module-master/src/ngx_http_lua_subrequest.h             ../lua-nginx-module-master/src/ngx_http_lua_ndk.h             ../lua-nginx-module-master/src/ngx_http_lua_control.h             ../lua-nginx-module-master/src/ngx_http_lua_time.h             ../lua-nginx-module-master/src/ngx_http_lua_string.h             ../lua-nginx-module-master/src/ngx_http_lua_misc.h             ../lua-nginx-module-master/src/ngx_http_lua_variable.h             ../lua-nginx-module-master/src/ngx_http_lua_output.h             ../lua-nginx-module-master/src/ngx_http_lua_headers.h             ../lua-nginx-module-master/src/ngx_http_lua_uri.h             ../lua-nginx-module-master/src/ngx_http_lua_req_body.h             ../lua-nginx-module-master/src/ngx_http_lua_args.h             ../lua-nginx-module-master/src/ngx_http_lua_ctx.h             ../lua-nginx-module-master/src/ngx_http_lua_regex.h             ../lua-nginx-module-master/src/ngx_http_lua_common.h             ../lua-nginx-module-master/src/ngx_http_lua_directive.h             ../lua-nginx-module-master/src/ngx_http_lua_headers_out.h             ../lua-nginx-module-master/src/ngx_http_lua_headers_in.h             ../lua-nginx-module-master/src/ngx_http_lua_consts.h             ../lua-nginx-module-master/src/ngx_http_lua_exception.h             ../lua-nginx-module-master/src/ngx_http_lua_util.h             ../lua-nginx-module-master/src/ngx_http_lua_cache.h             ../lua-nginx-module-master/src/ngx_http_lua_contentby.h             ../lua-nginx-module-master/src/ngx_http_lua_rewriteby.h             ../lua-nginx-module-master/src/ngx_http_lua_accessby.h             ../lua-nginx-module-master/src/ngx_http_lua_setby.h             ../lua-nginx-module-master/src/ngx_http_lua_capturefilter.h             ../lua-nginx-module-master/src/ngx_http_lua_clfactory.h             ../lua-nginx-module-master/src/ngx_http_lua_pcrefix.h             ../lua-nginx-module-master/src/ngx_http_lua_headerfilterby.h             ../lua-nginx-module-master/src/ngx_http_lua_shdict.h             ../lua-nginx-module-master/src/ngx_http_lua_socket_tcp.h             ../lua-nginx-module-master/src/api/ngx_http_lua_api.h             ../lua-nginx-module-master/src/ngx_http_lua_logby.h             ../lua-nginx-module-master/src/ngx_http_lua_sleep.h             ../lua-nginx-module-master/src/ngx_http_lua_semaphore.h            ../lua-nginx-module-master/src/ngx_http_lua_coroutine.h             ../lua-nginx-module-master/src/ngx_http_lua_bodyfilterby.h             ../lua-nginx-module-master/src/ngx_http_lua_initby.h             ../lua-nginx-module-master/src/ngx_http_lua_initworkerby.h             ../lua-nginx-module-master/src/ngx_http_lua_socket_udp.h             ../lua-nginx-module-master/src/ngx_http_lua_req_method.h             ../lua-nginx-module-master/src/ngx_http_lua_phase.h             ../lua-nginx-module-master/src/ngx_http_lua_probe.h             ../lua-nginx-module-master/src/ngx_http_lua_uthread.h             ../lua-nginx-module-master/src/ngx_http_lua_timer.h             ../lua-nginx-module-master/src/ngx_http_lua_config.h             ../lua-nginx-module-master/src/ngx_http_lua_worker.h             ../lua-nginx-module-master/src/ngx_http_lua_ssl_certby.h             ../lua-nginx-module-master/src/ngx_http_lua_lex.h             ../lua-nginx-module-master/src/ngx_http_lua_balancer.h             ../lua-nginx-module-master/src/ngx_http_lua_ssl_session_storeby.h             ../lua-nginx-module-master/src/ngx_http_lua_ssl_session_fetchby.h             ../lua-nginx-module-master/src/ngx_http_lua_ssl.h             ../lua-nginx-module-master/src/ngx_http_lua_log_ringbuf.h                                                                                       ../echo-nginx-module-master/src/ddebug.h                                                 ../echo-nginx-module-master/src/ngx_http_echo_module.h                                   ../echo-nginx-module-master/src/ngx_http_echo_handler.h                                  ../echo-nginx-module-master/src/ngx_http_echo_util.h                                     ../echo-nginx-module-master/src/ngx_http_echo_sleep.h                                    ../echo-nginx-module-master/src/ngx_http_echo_filter.h                                   ../echo-nginx-module-master/src/ngx_http_echo_var.h                                      ../echo-nginx-module-master/src/ngx_http_echo_location.h                                 ../echo-nginx-module-master/src/ngx_http_echo_echo.h                                     ../echo-nginx-module-master/src/ngx_http_echo_request_info.h                             ../echo-nginx-module-master/src/ngx_http_echo_subrequest.h                               ../echo-nginx-module-master/src/ngx_http_echo_foreach.h                                   $(HTTP_DEPS)


build:	binary modules manpage

binary:	objs/nginx

objs/nginx:	objs/src/core/nginx.o \
	objs/src/core/ngx_log.o \
	objs/src/core/ngx_palloc.o \
	objs/src/core/ngx_array.o \
	objs/src/core/ngx_list.o \
	objs/src/core/ngx_hash.o \
	objs/src/core/ngx_buf.o \
	objs/src/core/ngx_queue.o \
	objs/src/core/ngx_output_chain.o \
	objs/src/core/ngx_string.o \
	objs/src/core/ngx_parse.o \
	objs/src/core/ngx_parse_time.o \
	objs/src/core/ngx_inet.o \
	objs/src/core/ngx_file.o \
	objs/src/core/ngx_crc32.o \
	objs/src/core/ngx_murmurhash.o \
	objs/src/core/ngx_md5.o \
	objs/src/core/ngx_sha1.o \
	objs/src/core/ngx_rbtree.o \
	objs/src/core/ngx_radix_tree.o \
	objs/src/core/ngx_slab.o \
	objs/src/core/ngx_times.o \
	objs/src/core/ngx_shmtx.o \
	objs/src/core/ngx_connection.o \
	objs/src/core/ngx_cycle.o \
	objs/src/core/ngx_spinlock.o \
	objs/src/core/ngx_rwlock.o \
	objs/src/core/ngx_cpuinfo.o \
	objs/src/core/ngx_conf_file.o \
	objs/src/core/ngx_module.o \
	objs/src/core/ngx_resolver.o \
	objs/src/core/ngx_open_file_cache.o \
	objs/src/core/ngx_crypt.o \
	objs/src/core/ngx_proxy_protocol.o \
	objs/src/core/ngx_syslog.o \
	objs/src/event/ngx_event.o \
	objs/src/event/ngx_event_timer.o \
	objs/src/event/ngx_event_posted.o \
	objs/src/event/ngx_event_accept.o \
	objs/src/event/ngx_event_connect.o \
	objs/src/event/ngx_event_pipe.o \
	objs/src/os/unix/ngx_time.o \
	objs/src/os/unix/ngx_errno.o \
	objs/src/os/unix/ngx_alloc.o \
	objs/src/os/unix/ngx_files.o \
	objs/src/os/unix/ngx_socket.o \
	objs/src/os/unix/ngx_recv.o \
	objs/src/os/unix/ngx_readv_chain.o \
	objs/src/os/unix/ngx_udp_recv.o \
	objs/src/os/unix/ngx_send.o \
	objs/src/os/unix/ngx_writev_chain.o \
	objs/src/os/unix/ngx_udp_send.o \
	objs/src/os/unix/ngx_udp_sendmsg_chain.o \
	objs/src/os/unix/ngx_channel.o \
	objs/src/os/unix/ngx_shmem.o \
	objs/src/os/unix/ngx_process.o \
	objs/src/os/unix/ngx_daemon.o \
	objs/src/os/unix/ngx_setaffinity.o \
	objs/src/os/unix/ngx_setproctitle.o \
	objs/src/os/unix/ngx_posix_init.o \
	objs/src/os/unix/ngx_user.o \
	objs/src/os/unix/ngx_dlopen.o \
	objs/src/os/unix/ngx_process_cycle.o \
	objs/src/os/unix/ngx_linux_init.o \
	objs/src/event/modules/ngx_epoll_module.o \
	objs/src/os/unix/ngx_linux_sendfile_chain.o \
	objs/src/event/ngx_event_openssl.o \
	objs/src/event/ngx_event_openssl_stapling.o \
	objs/src/core/ngx_regex.o \
	objs/src/http/ngx_http.o \
	objs/src/http/ngx_http_core_module.o \
	objs/src/http/ngx_http_special_response.o \
	objs/src/http/ngx_http_request.o \
	objs/src/http/ngx_http_parse.o \
	objs/src/http/modules/ngx_http_log_module.o \
	objs/src/http/ngx_http_request_body.o \
	objs/src/http/ngx_http_variables.o \
	objs/src/http/ngx_http_script.o \
	objs/src/http/ngx_http_upstream.o \
	objs/src/http/ngx_http_upstream_round_robin.o \
	objs/src/http/ngx_http_file_cache.o \
	objs/src/http/ngx_http_write_filter_module.o \
	objs/src/http/ngx_http_header_filter_module.o \
	objs/src/http/modules/ngx_http_chunked_filter_module.o \
	objs/src/http/modules/ngx_http_range_filter_module.o \
	objs/src/http/modules/ngx_http_gzip_filter_module.o \
	objs/src/http/ngx_http_postpone_filter_module.o \
	objs/src/http/modules/ngx_http_ssi_filter_module.o \
	objs/src/http/modules/ngx_http_charset_filter_module.o \
	objs/src/http/modules/ngx_http_image_filter_module.o \
	objs/src/http/modules/ngx_http_userid_filter_module.o \
	objs/src/http/modules/ngx_http_headers_filter_module.o \
	objs/src/http/ngx_http_copy_filter_module.o \
	objs/src/http/modules/ngx_http_not_modified_filter_module.o \
	objs/src/http/modules/ngx_http_static_module.o \
	objs/src/http/modules/ngx_http_autoindex_module.o \
	objs/src/http/modules/ngx_http_index_module.o \
	objs/src/http/modules/ngx_http_mirror_module.o \
	objs/src/http/modules/ngx_http_try_files_module.o \
	objs/src/http/modules/ngx_http_auth_basic_module.o \
	objs/src/http/modules/ngx_http_access_module.o \
	objs/src/http/modules/ngx_http_limit_conn_module.o \
	objs/src/http/modules/ngx_http_limit_req_module.o \
	objs/src/http/modules/ngx_http_realip_module.o \
	objs/src/http/modules/ngx_http_geo_module.o \
	objs/src/http/modules/ngx_http_map_module.o \
	objs/src/http/modules/ngx_http_split_clients_module.o \
	objs/src/http/modules/ngx_http_referer_module.o \
	objs/src/http/modules/ngx_http_rewrite_module.o \
	objs/src/http/modules/ngx_http_ssl_module.o \
	objs/src/http/modules/ngx_http_proxy_module.o \
	objs/src/http/modules/ngx_http_fastcgi_module.o \
	objs/src/http/modules/ngx_http_uwsgi_module.o \
	objs/src/http/modules/ngx_http_scgi_module.o \
	objs/src/http/modules/ngx_http_memcached_module.o \
	objs/src/http/modules/ngx_http_empty_gif_module.o \
	objs/src/http/modules/ngx_http_browser_module.o \
	objs/src/http/modules/ngx_http_upstream_hash_module.o \
	objs/src/http/modules/ngx_http_upstream_ip_hash_module.o \
	objs/src/http/modules/ngx_http_upstream_least_conn_module.o \
	objs/src/http/modules/ngx_http_upstream_keepalive_module.o \
	objs/src/http/modules/ngx_http_upstream_zone_module.o \
	objs/src/http/modules/ngx_http_stub_status_module.o \
	objs/addon/src/ngx_http_redis2_module.o \
	objs/addon/src/ngx_http_redis2_handler.o \
	objs/addon/src/ngx_http_redis2_reply.o \
	objs/addon/src/ngx_http_redis2_util.o \
	objs/addon/src/ndk.o \
	objs/addon/src/ngx_http_set_base32.o \
	objs/addon/src/ngx_http_set_default_value.o \
	objs/addon/src/ngx_http_set_hashed_upstream.o \
	objs/addon/src/ngx_http_set_quote_sql.o \
	objs/addon/src/ngx_http_set_quote_json.o \
	objs/addon/src/ngx_http_set_unescape_uri.o \
	objs/addon/src/ngx_http_set_misc_module.o \
	objs/addon/src/ngx_http_set_escape_uri.o \
	objs/addon/src/ngx_http_set_hash.o \
	objs/addon/src/ngx_http_set_local_today.o \
	objs/addon/src/ngx_http_set_hex.o \
	objs/addon/src/ngx_http_set_base64.o \
	objs/addon/src/ngx_http_set_random.o \
	objs/addon/src/ngx_http_set_secure_random.o \
	objs/addon/src/ngx_http_set_rotate.o \
	objs/addon/src/ngx_http_set_hmac.o \
	objs/addon/ngx_http_redis-0.3.8/ngx_http_redis_module.o \
	objs/addon/src/ngx_http_lua_script.o \
	objs/addon/src/ngx_http_lua_log.o \
	objs/addon/src/ngx_http_lua_subrequest.o \
	objs/addon/src/ngx_http_lua_ndk.o \
	objs/addon/src/ngx_http_lua_control.o \
	objs/addon/src/ngx_http_lua_time.o \
	objs/addon/src/ngx_http_lua_misc.o \
	objs/addon/src/ngx_http_lua_variable.o \
	objs/addon/src/ngx_http_lua_string.o \
	objs/addon/src/ngx_http_lua_output.o \
	objs/addon/src/ngx_http_lua_headers.o \
	objs/addon/src/ngx_http_lua_req_body.o \
	objs/addon/src/ngx_http_lua_uri.o \
	objs/addon/src/ngx_http_lua_args.o \
	objs/addon/src/ngx_http_lua_ctx.o \
	objs/addon/src/ngx_http_lua_regex.o \
	objs/addon/src/ngx_http_lua_module.o \
	objs/addon/src/ngx_http_lua_headers_out.o \
	objs/addon/src/ngx_http_lua_headers_in.o \
	objs/addon/src/ngx_http_lua_directive.o \
	objs/addon/src/ngx_http_lua_consts.o \
	objs/addon/src/ngx_http_lua_exception.o \
	objs/addon/src/ngx_http_lua_util.o \
	objs/addon/src/ngx_http_lua_cache.o \
	objs/addon/src/ngx_http_lua_contentby.o \
	objs/addon/src/ngx_http_lua_rewriteby.o \
	objs/addon/src/ngx_http_lua_accessby.o \
	objs/addon/src/ngx_http_lua_setby.o \
	objs/addon/src/ngx_http_lua_capturefilter.o \
	objs/addon/src/ngx_http_lua_clfactory.o \
	objs/addon/src/ngx_http_lua_pcrefix.o \
	objs/addon/src/ngx_http_lua_headerfilterby.o \
	objs/addon/src/ngx_http_lua_shdict.o \
	objs/addon/src/ngx_http_lua_socket_tcp.o \
	objs/addon/src/ngx_http_lua_api.o \
	objs/addon/src/ngx_http_lua_logby.o \
	objs/addon/src/ngx_http_lua_sleep.o \
	objs/addon/src/ngx_http_lua_semaphore.o \
	objs/addon/src/ngx_http_lua_coroutine.o \
	objs/addon/src/ngx_http_lua_bodyfilterby.o \
	objs/addon/src/ngx_http_lua_initby.o \
	objs/addon/src/ngx_http_lua_initworkerby.o \
	objs/addon/src/ngx_http_lua_socket_udp.o \
	objs/addon/src/ngx_http_lua_req_method.o \
	objs/addon/src/ngx_http_lua_phase.o \
	objs/addon/src/ngx_http_lua_uthread.o \
	objs/addon/src/ngx_http_lua_timer.o \
	objs/addon/src/ngx_http_lua_config.o \
	objs/addon/src/ngx_http_lua_worker.o \
	objs/addon/src/ngx_http_lua_ssl_certby.o \
	objs/addon/src/ngx_http_lua_ssl_ocsp.o \
	objs/addon/src/ngx_http_lua_lex.o \
	objs/addon/src/ngx_http_lua_balancer.o \
	objs/addon/src/ngx_http_lua_ssl_session_storeby.o \
	objs/addon/src/ngx_http_lua_ssl_session_fetchby.o \
	objs/addon/src/ngx_http_lua_ssl.o \
	objs/addon/src/ngx_http_lua_log_ringbuf.o \
	objs/addon/src/ngx_http_echo_module.o \
	objs/addon/src/ngx_http_echo_util.o \
	objs/addon/src/ngx_http_echo_timer.o \
	objs/addon/src/ngx_http_echo_var.o \
	objs/addon/src/ngx_http_echo_handler.o \
	objs/addon/src/ngx_http_echo_filter.o \
	objs/addon/src/ngx_http_echo_sleep.o \
	objs/addon/src/ngx_http_echo_location.o \
	objs/addon/src/ngx_http_echo_echo.o \
	objs/addon/src/ngx_http_echo_request_info.o \
	objs/addon/src/ngx_http_echo_subrequest.o \
	objs/addon/src/ngx_http_echo_foreach.o \
	objs/addon/ngx_zookeeper_lua/ngx_zookeeper_lua_module.o \
	objs/ngx_modules.o

	$(LINK) -o objs/nginx \
	objs/src/core/nginx.o \
	objs/src/core/ngx_log.o \
	objs/src/core/ngx_palloc.o \
	objs/src/core/ngx_array.o \
	objs/src/core/ngx_list.o \
	objs/src/core/ngx_hash.o \
	objs/src/core/ngx_buf.o \
	objs/src/core/ngx_queue.o \
	objs/src/core/ngx_output_chain.o \
	objs/src/core/ngx_string.o \
	objs/src/core/ngx_parse.o \
	objs/src/core/ngx_parse_time.o \
	objs/src/core/ngx_inet.o \
	objs/src/core/ngx_file.o \
	objs/src/core/ngx_crc32.o \
	objs/src/core/ngx_murmurhash.o \
	objs/src/core/ngx_md5.o \
	objs/src/core/ngx_sha1.o \
	objs/src/core/ngx_rbtree.o \
	objs/src/core/ngx_radix_tree.o \
	objs/src/core/ngx_slab.o \
	objs/src/core/ngx_times.o \
	objs/src/core/ngx_shmtx.o \
	objs/src/core/ngx_connection.o \
	objs/src/core/ngx_cycle.o \
	objs/src/core/ngx_spinlock.o \
	objs/src/core/ngx_rwlock.o \
	objs/src/core/ngx_cpuinfo.o \
	objs/src/core/ngx_conf_file.o \
	objs/src/core/ngx_module.o \
	objs/src/core/ngx_resolver.o \
	objs/src/core/ngx_open_file_cache.o \
	objs/src/core/ngx_crypt.o \
	objs/src/core/ngx_proxy_protocol.o \
	objs/src/core/ngx_syslog.o \
	objs/src/event/ngx_event.o \
	objs/src/event/ngx_event_timer.o \
	objs/src/event/ngx_event_posted.o \
	objs/src/event/ngx_event_accept.o \
	objs/src/event/ngx_event_connect.o \
	objs/src/event/ngx_event_pipe.o \
	objs/src/os/unix/ngx_time.o \
	objs/src/os/unix/ngx_errno.o \
	objs/src/os/unix/ngx_alloc.o \
	objs/src/os/unix/ngx_files.o \
	objs/src/os/unix/ngx_socket.o \
	objs/src/os/unix/ngx_recv.o \
	objs/src/os/unix/ngx_readv_chain.o \
	objs/src/os/unix/ngx_udp_recv.o \
	objs/src/os/unix/ngx_send.o \
	objs/src/os/unix/ngx_writev_chain.o \
	objs/src/os/unix/ngx_udp_send.o \
	objs/src/os/unix/ngx_udp_sendmsg_chain.o \
	objs/src/os/unix/ngx_channel.o \
	objs/src/os/unix/ngx_shmem.o \
	objs/src/os/unix/ngx_process.o \
	objs/src/os/unix/ngx_daemon.o \
	objs/src/os/unix/ngx_setaffinity.o \
	objs/src/os/unix/ngx_setproctitle.o \
	objs/src/os/unix/ngx_posix_init.o \
	objs/src/os/unix/ngx_user.o \
	objs/src/os/unix/ngx_dlopen.o \
	objs/src/os/unix/ngx_process_cycle.o \
	objs/src/os/unix/ngx_linux_init.o \
	objs/src/event/modules/ngx_epoll_module.o \
	objs/src/os/unix/ngx_linux_sendfile_chain.o \
	objs/src/event/ngx_event_openssl.o \
	objs/src/event/ngx_event_openssl_stapling.o \
	objs/src/core/ngx_regex.o \
	objs/src/http/ngx_http.o \
	objs/src/http/ngx_http_core_module.o \
	objs/src/http/ngx_http_special_response.o \
	objs/src/http/ngx_http_request.o \
	objs/src/http/ngx_http_parse.o \
	objs/src/http/modules/ngx_http_log_module.o \
	objs/src/http/ngx_http_request_body.o \
	objs/src/http/ngx_http_variables.o \
	objs/src/http/ngx_http_script.o \
	objs/src/http/ngx_http_upstream.o \
	objs/src/http/ngx_http_upstream_round_robin.o \
	objs/src/http/ngx_http_file_cache.o \
	objs/src/http/ngx_http_write_filter_module.o \
	objs/src/http/ngx_http_header_filter_module.o \
	objs/src/http/modules/ngx_http_chunked_filter_module.o \
	objs/src/http/modules/ngx_http_range_filter_module.o \
	objs/src/http/modules/ngx_http_gzip_filter_module.o \
	objs/src/http/ngx_http_postpone_filter_module.o \
	objs/src/http/modules/ngx_http_ssi_filter_module.o \
	objs/src/http/modules/ngx_http_charset_filter_module.o \
	objs/src/http/modules/ngx_http_image_filter_module.o \
	objs/src/http/modules/ngx_http_userid_filter_module.o \
	objs/src/http/modules/ngx_http_headers_filter_module.o \
	objs/src/http/ngx_http_copy_filter_module.o \
	objs/src/http/modules/ngx_http_not_modified_filter_module.o \
	objs/src/http/modules/ngx_http_static_module.o \
	objs/src/http/modules/ngx_http_autoindex_module.o \
	objs/src/http/modules/ngx_http_index_module.o \
	objs/src/http/modules/ngx_http_mirror_module.o \
	objs/src/http/modules/ngx_http_try_files_module.o \
	objs/src/http/modules/ngx_http_auth_basic_module.o \
	objs/src/http/modules/ngx_http_access_module.o \
	objs/src/http/modules/ngx_http_limit_conn_module.o \
	objs/src/http/modules/ngx_http_limit_req_module.o \
	objs/src/http/modules/ngx_http_realip_module.o \
	objs/src/http/modules/ngx_http_geo_module.o \
	objs/src/http/modules/ngx_http_map_module.o \
	objs/src/http/modules/ngx_http_split_clients_module.o \
	objs/src/http/modules/ngx_http_referer_module.o \
	objs/src/http/modules/ngx_http_rewrite_module.o \
	objs/src/http/modules/ngx_http_ssl_module.o \
	objs/src/http/modules/ngx_http_proxy_module.o \
	objs/src/http/modules/ngx_http_fastcgi_module.o \
	objs/src/http/modules/ngx_http_uwsgi_module.o \
	objs/src/http/modules/ngx_http_scgi_module.o \
	objs/src/http/modules/ngx_http_memcached_module.o \
	objs/src/http/modules/ngx_http_empty_gif_module.o \
	objs/src/http/modules/ngx_http_browser_module.o \
	objs/src/http/modules/ngx_http_upstream_hash_module.o \
	objs/src/http/modules/ngx_http_upstream_ip_hash_module.o \
	objs/src/http/modules/ngx_http_upstream_least_conn_module.o \
	objs/src/http/modules/ngx_http_upstream_keepalive_module.o \
	objs/src/http/modules/ngx_http_upstream_zone_module.o \
	objs/src/http/modules/ngx_http_stub_status_module.o \
	objs/addon/src/ngx_http_redis2_module.o \
	objs/addon/src/ngx_http_redis2_handler.o \
	objs/addon/src/ngx_http_redis2_reply.o \
	objs/addon/src/ngx_http_redis2_util.o \
	objs/addon/src/ndk.o \
	objs/addon/src/ngx_http_set_base32.o \
	objs/addon/src/ngx_http_set_default_value.o \
	objs/addon/src/ngx_http_set_hashed_upstream.o \
	objs/addon/src/ngx_http_set_quote_sql.o \
	objs/addon/src/ngx_http_set_quote_json.o \
	objs/addon/src/ngx_http_set_unescape_uri.o \
	objs/addon/src/ngx_http_set_misc_module.o \
	objs/addon/src/ngx_http_set_escape_uri.o \
	objs/addon/src/ngx_http_set_hash.o \
	objs/addon/src/ngx_http_set_local_today.o \
	objs/addon/src/ngx_http_set_hex.o \
	objs/addon/src/ngx_http_set_base64.o \
	objs/addon/src/ngx_http_set_random.o \
	objs/addon/src/ngx_http_set_secure_random.o \
	objs/addon/src/ngx_http_set_rotate.o \
	objs/addon/src/ngx_http_set_hmac.o \
	objs/addon/ngx_http_redis-0.3.8/ngx_http_redis_module.o \
	objs/addon/src/ngx_http_lua_script.o \
	objs/addon/src/ngx_http_lua_log.o \
	objs/addon/src/ngx_http_lua_subrequest.o \
	objs/addon/src/ngx_http_lua_ndk.o \
	objs/addon/src/ngx_http_lua_control.o \
	objs/addon/src/ngx_http_lua_time.o \
	objs/addon/src/ngx_http_lua_misc.o \
	objs/addon/src/ngx_http_lua_variable.o \
	objs/addon/src/ngx_http_lua_string.o \
	objs/addon/src/ngx_http_lua_output.o \
	objs/addon/src/ngx_http_lua_headers.o \
	objs/addon/src/ngx_http_lua_req_body.o \
	objs/addon/src/ngx_http_lua_uri.o \
	objs/addon/src/ngx_http_lua_args.o \
	objs/addon/src/ngx_http_lua_ctx.o \
	objs/addon/src/ngx_http_lua_regex.o \
	objs/addon/src/ngx_http_lua_module.o \
	objs/addon/src/ngx_http_lua_headers_out.o \
	objs/addon/src/ngx_http_lua_headers_in.o \
	objs/addon/src/ngx_http_lua_directive.o \
	objs/addon/src/ngx_http_lua_consts.o \
	objs/addon/src/ngx_http_lua_exception.o \
	objs/addon/src/ngx_http_lua_util.o \
	objs/addon/src/ngx_http_lua_cache.o \
	objs/addon/src/ngx_http_lua_contentby.o \
	objs/addon/src/ngx_http_lua_rewriteby.o \
	objs/addon/src/ngx_http_lua_accessby.o \
	objs/addon/src/ngx_http_lua_setby.o \
	objs/addon/src/ngx_http_lua_capturefilter.o \
	objs/addon/src/ngx_http_lua_clfactory.o \
	objs/addon/src/ngx_http_lua_pcrefix.o \
	objs/addon/src/ngx_http_lua_headerfilterby.o \
	objs/addon/src/ngx_http_lua_shdict.o \
	objs/addon/src/ngx_http_lua_socket_tcp.o \
	objs/addon/src/ngx_http_lua_api.o \
	objs/addon/src/ngx_http_lua_logby.o \
	objs/addon/src/ngx_http_lua_sleep.o \
	objs/addon/src/ngx_http_lua_semaphore.o \
	objs/addon/src/ngx_http_lua_coroutine.o \
	objs/addon/src/ngx_http_lua_bodyfilterby.o \
	objs/addon/src/ngx_http_lua_initby.o \
	objs/addon/src/ngx_http_lua_initworkerby.o \
	objs/addon/src/ngx_http_lua_socket_udp.o \
	objs/addon/src/ngx_http_lua_req_method.o \
	objs/addon/src/ngx_http_lua_phase.o \
	objs/addon/src/ngx_http_lua_uthread.o \
	objs/addon/src/ngx_http_lua_timer.o \
	objs/addon/src/ngx_http_lua_config.o \
	objs/addon/src/ngx_http_lua_worker.o \
	objs/addon/src/ngx_http_lua_ssl_certby.o \
	objs/addon/src/ngx_http_lua_ssl_ocsp.o \
	objs/addon/src/ngx_http_lua_lex.o \
	objs/addon/src/ngx_http_lua_balancer.o \
	objs/addon/src/ngx_http_lua_ssl_session_storeby.o \
	objs/addon/src/ngx_http_lua_ssl_session_fetchby.o \
	objs/addon/src/ngx_http_lua_ssl.o \
	objs/addon/src/ngx_http_lua_log_ringbuf.o \
	objs/addon/src/ngx_http_echo_module.o \
	objs/addon/src/ngx_http_echo_util.o \
	objs/addon/src/ngx_http_echo_timer.o \
	objs/addon/src/ngx_http_echo_var.o \
	objs/addon/src/ngx_http_echo_handler.o \
	objs/addon/src/ngx_http_echo_filter.o \
	objs/addon/src/ngx_http_echo_sleep.o \
	objs/addon/src/ngx_http_echo_location.o \
	objs/addon/src/ngx_http_echo_echo.o \
	objs/addon/src/ngx_http_echo_request_info.o \
	objs/addon/src/ngx_http_echo_subrequest.o \
	objs/addon/src/ngx_http_echo_foreach.o \
	objs/addon/ngx_zookeeper_lua/ngx_zookeeper_lua_module.o \
	objs/ngx_modules.o \
	-L/usr/local/lib -Wl,-rpath,/usr/local/lib -Wl,-E -ldl -lpthread -lcrypt -L/usr/local/lib -lluajit-5.1 -lm -ldl -L/lib -lzookeeper_mt -lpcre -lssl -lcrypto -ldl -lpthread -lz -lgd \
	-Wl,-E
	


modules:

objs/ngx_modules.o:	$(CORE_DEPS) \
	objs/ngx_modules.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/ngx_modules.o \
		objs/ngx_modules.c


objs/src/core/nginx.o:	$(CORE_DEPS) \
	src/core/nginx.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/nginx.o \
		src/core/nginx.c


objs/src/core/ngx_log.o:	$(CORE_DEPS) \
	src/core/ngx_log.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_log.o \
		src/core/ngx_log.c


objs/src/core/ngx_palloc.o:	$(CORE_DEPS) \
	src/core/ngx_palloc.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_palloc.o \
		src/core/ngx_palloc.c


objs/src/core/ngx_array.o:	$(CORE_DEPS) \
	src/core/ngx_array.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_array.o \
		src/core/ngx_array.c


objs/src/core/ngx_list.o:	$(CORE_DEPS) \
	src/core/ngx_list.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_list.o \
		src/core/ngx_list.c


objs/src/core/ngx_hash.o:	$(CORE_DEPS) \
	src/core/ngx_hash.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_hash.o \
		src/core/ngx_hash.c


objs/src/core/ngx_buf.o:	$(CORE_DEPS) \
	src/core/ngx_buf.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_buf.o \
		src/core/ngx_buf.c


objs/src/core/ngx_queue.o:	$(CORE_DEPS) \
	src/core/ngx_queue.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_queue.o \
		src/core/ngx_queue.c


objs/src/core/ngx_output_chain.o:	$(CORE_DEPS) \
	src/core/ngx_output_chain.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_output_chain.o \
		src/core/ngx_output_chain.c


objs/src/core/ngx_string.o:	$(CORE_DEPS) \
	src/core/ngx_string.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_string.o \
		src/core/ngx_string.c


objs/src/core/ngx_parse.o:	$(CORE_DEPS) \
	src/core/ngx_parse.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_parse.o \
		src/core/ngx_parse.c


objs/src/core/ngx_parse_time.o:	$(CORE_DEPS) \
	src/core/ngx_parse_time.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_parse_time.o \
		src/core/ngx_parse_time.c


objs/src/core/ngx_inet.o:	$(CORE_DEPS) \
	src/core/ngx_inet.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_inet.o \
		src/core/ngx_inet.c


objs/src/core/ngx_file.o:	$(CORE_DEPS) \
	src/core/ngx_file.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_file.o \
		src/core/ngx_file.c


objs/src/core/ngx_crc32.o:	$(CORE_DEPS) \
	src/core/ngx_crc32.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_crc32.o \
		src/core/ngx_crc32.c


objs/src/core/ngx_murmurhash.o:	$(CORE_DEPS) \
	src/core/ngx_murmurhash.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_murmurhash.o \
		src/core/ngx_murmurhash.c


objs/src/core/ngx_md5.o:	$(CORE_DEPS) \
	src/core/ngx_md5.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_md5.o \
		src/core/ngx_md5.c


objs/src/core/ngx_sha1.o:	$(CORE_DEPS) \
	src/core/ngx_sha1.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_sha1.o \
		src/core/ngx_sha1.c


objs/src/core/ngx_rbtree.o:	$(CORE_DEPS) \
	src/core/ngx_rbtree.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_rbtree.o \
		src/core/ngx_rbtree.c


objs/src/core/ngx_radix_tree.o:	$(CORE_DEPS) \
	src/core/ngx_radix_tree.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_radix_tree.o \
		src/core/ngx_radix_tree.c


objs/src/core/ngx_slab.o:	$(CORE_DEPS) \
	src/core/ngx_slab.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_slab.o \
		src/core/ngx_slab.c


objs/src/core/ngx_times.o:	$(CORE_DEPS) \
	src/core/ngx_times.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_times.o \
		src/core/ngx_times.c


objs/src/core/ngx_shmtx.o:	$(CORE_DEPS) \
	src/core/ngx_shmtx.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_shmtx.o \
		src/core/ngx_shmtx.c


objs/src/core/ngx_connection.o:	$(CORE_DEPS) \
	src/core/ngx_connection.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_connection.o \
		src/core/ngx_connection.c


objs/src/core/ngx_cycle.o:	$(CORE_DEPS) \
	src/core/ngx_cycle.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_cycle.o \
		src/core/ngx_cycle.c


objs/src/core/ngx_spinlock.o:	$(CORE_DEPS) \
	src/core/ngx_spinlock.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_spinlock.o \
		src/core/ngx_spinlock.c


objs/src/core/ngx_rwlock.o:	$(CORE_DEPS) \
	src/core/ngx_rwlock.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_rwlock.o \
		src/core/ngx_rwlock.c


objs/src/core/ngx_cpuinfo.o:	$(CORE_DEPS) \
	src/core/ngx_cpuinfo.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_cpuinfo.o \
		src/core/ngx_cpuinfo.c


objs/src/core/ngx_conf_file.o:	$(CORE_DEPS) \
	src/core/ngx_conf_file.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_conf_file.o \
		src/core/ngx_conf_file.c


objs/src/core/ngx_module.o:	$(CORE_DEPS) \
	src/core/ngx_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_module.o \
		src/core/ngx_module.c


objs/src/core/ngx_resolver.o:	$(CORE_DEPS) \
	src/core/ngx_resolver.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_resolver.o \
		src/core/ngx_resolver.c


objs/src/core/ngx_open_file_cache.o:	$(CORE_DEPS) \
	src/core/ngx_open_file_cache.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_open_file_cache.o \
		src/core/ngx_open_file_cache.c


objs/src/core/ngx_crypt.o:	$(CORE_DEPS) \
	src/core/ngx_crypt.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_crypt.o \
		src/core/ngx_crypt.c


objs/src/core/ngx_proxy_protocol.o:	$(CORE_DEPS) \
	src/core/ngx_proxy_protocol.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_proxy_protocol.o \
		src/core/ngx_proxy_protocol.c


objs/src/core/ngx_syslog.o:	$(CORE_DEPS) \
	src/core/ngx_syslog.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_syslog.o \
		src/core/ngx_syslog.c


objs/src/event/ngx_event.o:	$(CORE_DEPS) \
	src/event/ngx_event.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/ngx_event.o \
		src/event/ngx_event.c


objs/src/event/ngx_event_timer.o:	$(CORE_DEPS) \
	src/event/ngx_event_timer.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/ngx_event_timer.o \
		src/event/ngx_event_timer.c


objs/src/event/ngx_event_posted.o:	$(CORE_DEPS) \
	src/event/ngx_event_posted.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/ngx_event_posted.o \
		src/event/ngx_event_posted.c


objs/src/event/ngx_event_accept.o:	$(CORE_DEPS) \
	src/event/ngx_event_accept.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/ngx_event_accept.o \
		src/event/ngx_event_accept.c


objs/src/event/ngx_event_connect.o:	$(CORE_DEPS) \
	src/event/ngx_event_connect.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/ngx_event_connect.o \
		src/event/ngx_event_connect.c


objs/src/event/ngx_event_pipe.o:	$(CORE_DEPS) \
	src/event/ngx_event_pipe.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/ngx_event_pipe.o \
		src/event/ngx_event_pipe.c


objs/src/os/unix/ngx_time.o:	$(CORE_DEPS) \
	src/os/unix/ngx_time.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_time.o \
		src/os/unix/ngx_time.c


objs/src/os/unix/ngx_errno.o:	$(CORE_DEPS) \
	src/os/unix/ngx_errno.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_errno.o \
		src/os/unix/ngx_errno.c


objs/src/os/unix/ngx_alloc.o:	$(CORE_DEPS) \
	src/os/unix/ngx_alloc.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_alloc.o \
		src/os/unix/ngx_alloc.c


objs/src/os/unix/ngx_files.o:	$(CORE_DEPS) \
	src/os/unix/ngx_files.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_files.o \
		src/os/unix/ngx_files.c


objs/src/os/unix/ngx_socket.o:	$(CORE_DEPS) \
	src/os/unix/ngx_socket.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_socket.o \
		src/os/unix/ngx_socket.c


objs/src/os/unix/ngx_recv.o:	$(CORE_DEPS) \
	src/os/unix/ngx_recv.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_recv.o \
		src/os/unix/ngx_recv.c


objs/src/os/unix/ngx_readv_chain.o:	$(CORE_DEPS) \
	src/os/unix/ngx_readv_chain.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_readv_chain.o \
		src/os/unix/ngx_readv_chain.c


objs/src/os/unix/ngx_udp_recv.o:	$(CORE_DEPS) \
	src/os/unix/ngx_udp_recv.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_udp_recv.o \
		src/os/unix/ngx_udp_recv.c


objs/src/os/unix/ngx_send.o:	$(CORE_DEPS) \
	src/os/unix/ngx_send.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_send.o \
		src/os/unix/ngx_send.c


objs/src/os/unix/ngx_writev_chain.o:	$(CORE_DEPS) \
	src/os/unix/ngx_writev_chain.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_writev_chain.o \
		src/os/unix/ngx_writev_chain.c


objs/src/os/unix/ngx_udp_send.o:	$(CORE_DEPS) \
	src/os/unix/ngx_udp_send.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_udp_send.o \
		src/os/unix/ngx_udp_send.c


objs/src/os/unix/ngx_udp_sendmsg_chain.o:	$(CORE_DEPS) \
	src/os/unix/ngx_udp_sendmsg_chain.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_udp_sendmsg_chain.o \
		src/os/unix/ngx_udp_sendmsg_chain.c


objs/src/os/unix/ngx_channel.o:	$(CORE_DEPS) \
	src/os/unix/ngx_channel.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_channel.o \
		src/os/unix/ngx_channel.c


objs/src/os/unix/ngx_shmem.o:	$(CORE_DEPS) \
	src/os/unix/ngx_shmem.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_shmem.o \
		src/os/unix/ngx_shmem.c


objs/src/os/unix/ngx_process.o:	$(CORE_DEPS) \
	src/os/unix/ngx_process.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_process.o \
		src/os/unix/ngx_process.c


objs/src/os/unix/ngx_daemon.o:	$(CORE_DEPS) \
	src/os/unix/ngx_daemon.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_daemon.o \
		src/os/unix/ngx_daemon.c


objs/src/os/unix/ngx_setaffinity.o:	$(CORE_DEPS) \
	src/os/unix/ngx_setaffinity.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_setaffinity.o \
		src/os/unix/ngx_setaffinity.c


objs/src/os/unix/ngx_setproctitle.o:	$(CORE_DEPS) \
	src/os/unix/ngx_setproctitle.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_setproctitle.o \
		src/os/unix/ngx_setproctitle.c


objs/src/os/unix/ngx_posix_init.o:	$(CORE_DEPS) \
	src/os/unix/ngx_posix_init.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_posix_init.o \
		src/os/unix/ngx_posix_init.c


objs/src/os/unix/ngx_user.o:	$(CORE_DEPS) \
	src/os/unix/ngx_user.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_user.o \
		src/os/unix/ngx_user.c


objs/src/os/unix/ngx_dlopen.o:	$(CORE_DEPS) \
	src/os/unix/ngx_dlopen.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_dlopen.o \
		src/os/unix/ngx_dlopen.c


objs/src/os/unix/ngx_process_cycle.o:	$(CORE_DEPS) \
	src/os/unix/ngx_process_cycle.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_process_cycle.o \
		src/os/unix/ngx_process_cycle.c


objs/src/os/unix/ngx_linux_init.o:	$(CORE_DEPS) \
	src/os/unix/ngx_linux_init.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_linux_init.o \
		src/os/unix/ngx_linux_init.c


objs/src/event/modules/ngx_epoll_module.o:	$(CORE_DEPS) \
	src/event/modules/ngx_epoll_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/modules/ngx_epoll_module.o \
		src/event/modules/ngx_epoll_module.c


objs/src/os/unix/ngx_linux_sendfile_chain.o:	$(CORE_DEPS) \
	src/os/unix/ngx_linux_sendfile_chain.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/os/unix/ngx_linux_sendfile_chain.o \
		src/os/unix/ngx_linux_sendfile_chain.c


objs/src/event/ngx_event_openssl.o:	$(CORE_DEPS) \
	src/event/ngx_event_openssl.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/ngx_event_openssl.o \
		src/event/ngx_event_openssl.c


objs/src/event/ngx_event_openssl_stapling.o:	$(CORE_DEPS) \
	src/event/ngx_event_openssl_stapling.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/event/ngx_event_openssl_stapling.o \
		src/event/ngx_event_openssl_stapling.c


objs/src/core/ngx_regex.o:	$(CORE_DEPS) \
	src/core/ngx_regex.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) \
		-o objs/src/core/ngx_regex.o \
		src/core/ngx_regex.c


objs/src/http/ngx_http.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http.o \
		src/http/ngx_http.c


objs/src/http/ngx_http_core_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_core_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_core_module.o \
		src/http/ngx_http_core_module.c


objs/src/http/ngx_http_special_response.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_special_response.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_special_response.o \
		src/http/ngx_http_special_response.c


objs/src/http/ngx_http_request.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_request.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_request.o \
		src/http/ngx_http_request.c


objs/src/http/ngx_http_parse.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_parse.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_parse.o \
		src/http/ngx_http_parse.c


objs/src/http/modules/ngx_http_log_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_log_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_log_module.o \
		src/http/modules/ngx_http_log_module.c


objs/src/http/ngx_http_request_body.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_request_body.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_request_body.o \
		src/http/ngx_http_request_body.c


objs/src/http/ngx_http_variables.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_variables.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_variables.o \
		src/http/ngx_http_variables.c


objs/src/http/ngx_http_script.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_script.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_script.o \
		src/http/ngx_http_script.c


objs/src/http/ngx_http_upstream.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_upstream.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_upstream.o \
		src/http/ngx_http_upstream.c


objs/src/http/ngx_http_upstream_round_robin.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_upstream_round_robin.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_upstream_round_robin.o \
		src/http/ngx_http_upstream_round_robin.c


objs/src/http/ngx_http_file_cache.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_file_cache.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_file_cache.o \
		src/http/ngx_http_file_cache.c


objs/src/http/ngx_http_write_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_write_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_write_filter_module.o \
		src/http/ngx_http_write_filter_module.c


objs/src/http/ngx_http_header_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_header_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_header_filter_module.o \
		src/http/ngx_http_header_filter_module.c


objs/src/http/modules/ngx_http_chunked_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_chunked_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_chunked_filter_module.o \
		src/http/modules/ngx_http_chunked_filter_module.c


objs/src/http/modules/ngx_http_range_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_range_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_range_filter_module.o \
		src/http/modules/ngx_http_range_filter_module.c


objs/src/http/modules/ngx_http_gzip_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_gzip_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_gzip_filter_module.o \
		src/http/modules/ngx_http_gzip_filter_module.c


objs/src/http/ngx_http_postpone_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_postpone_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_postpone_filter_module.o \
		src/http/ngx_http_postpone_filter_module.c


objs/src/http/modules/ngx_http_ssi_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_ssi_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_ssi_filter_module.o \
		src/http/modules/ngx_http_ssi_filter_module.c


objs/src/http/modules/ngx_http_charset_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_charset_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_charset_filter_module.o \
		src/http/modules/ngx_http_charset_filter_module.c


objs/src/http/modules/ngx_http_image_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_image_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_image_filter_module.o \
		src/http/modules/ngx_http_image_filter_module.c


objs/src/http/modules/ngx_http_userid_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_userid_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_userid_filter_module.o \
		src/http/modules/ngx_http_userid_filter_module.c


objs/src/http/modules/ngx_http_headers_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_headers_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_headers_filter_module.o \
		src/http/modules/ngx_http_headers_filter_module.c


objs/src/http/ngx_http_copy_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/ngx_http_copy_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/ngx_http_copy_filter_module.o \
		src/http/ngx_http_copy_filter_module.c


objs/src/http/modules/ngx_http_not_modified_filter_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_not_modified_filter_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_not_modified_filter_module.o \
		src/http/modules/ngx_http_not_modified_filter_module.c


objs/src/http/modules/ngx_http_static_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_static_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_static_module.o \
		src/http/modules/ngx_http_static_module.c


objs/src/http/modules/ngx_http_autoindex_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_autoindex_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_autoindex_module.o \
		src/http/modules/ngx_http_autoindex_module.c


objs/src/http/modules/ngx_http_index_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_index_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_index_module.o \
		src/http/modules/ngx_http_index_module.c


objs/src/http/modules/ngx_http_mirror_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_mirror_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_mirror_module.o \
		src/http/modules/ngx_http_mirror_module.c


objs/src/http/modules/ngx_http_try_files_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_try_files_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_try_files_module.o \
		src/http/modules/ngx_http_try_files_module.c


objs/src/http/modules/ngx_http_auth_basic_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_auth_basic_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_auth_basic_module.o \
		src/http/modules/ngx_http_auth_basic_module.c


objs/src/http/modules/ngx_http_access_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_access_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_access_module.o \
		src/http/modules/ngx_http_access_module.c


objs/src/http/modules/ngx_http_limit_conn_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_limit_conn_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_limit_conn_module.o \
		src/http/modules/ngx_http_limit_conn_module.c


objs/src/http/modules/ngx_http_limit_req_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_limit_req_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_limit_req_module.o \
		src/http/modules/ngx_http_limit_req_module.c


objs/src/http/modules/ngx_http_realip_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_realip_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_realip_module.o \
		src/http/modules/ngx_http_realip_module.c


objs/src/http/modules/ngx_http_geo_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_geo_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_geo_module.o \
		src/http/modules/ngx_http_geo_module.c


objs/src/http/modules/ngx_http_map_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_map_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_map_module.o \
		src/http/modules/ngx_http_map_module.c


objs/src/http/modules/ngx_http_split_clients_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_split_clients_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_split_clients_module.o \
		src/http/modules/ngx_http_split_clients_module.c


objs/src/http/modules/ngx_http_referer_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_referer_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_referer_module.o \
		src/http/modules/ngx_http_referer_module.c


objs/src/http/modules/ngx_http_rewrite_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_rewrite_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_rewrite_module.o \
		src/http/modules/ngx_http_rewrite_module.c


objs/src/http/modules/ngx_http_ssl_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_ssl_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_ssl_module.o \
		src/http/modules/ngx_http_ssl_module.c


objs/src/http/modules/ngx_http_proxy_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_proxy_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_proxy_module.o \
		src/http/modules/ngx_http_proxy_module.c


objs/src/http/modules/ngx_http_fastcgi_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_fastcgi_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_fastcgi_module.o \
		src/http/modules/ngx_http_fastcgi_module.c


objs/src/http/modules/ngx_http_uwsgi_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_uwsgi_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_uwsgi_module.o \
		src/http/modules/ngx_http_uwsgi_module.c


objs/src/http/modules/ngx_http_scgi_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_scgi_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_scgi_module.o \
		src/http/modules/ngx_http_scgi_module.c


objs/src/http/modules/ngx_http_memcached_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_memcached_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_memcached_module.o \
		src/http/modules/ngx_http_memcached_module.c


objs/src/http/modules/ngx_http_empty_gif_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_empty_gif_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_empty_gif_module.o \
		src/http/modules/ngx_http_empty_gif_module.c


objs/src/http/modules/ngx_http_browser_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_browser_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_browser_module.o \
		src/http/modules/ngx_http_browser_module.c


objs/src/http/modules/ngx_http_upstream_hash_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_upstream_hash_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_upstream_hash_module.o \
		src/http/modules/ngx_http_upstream_hash_module.c


objs/src/http/modules/ngx_http_upstream_ip_hash_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_upstream_ip_hash_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_upstream_ip_hash_module.o \
		src/http/modules/ngx_http_upstream_ip_hash_module.c


objs/src/http/modules/ngx_http_upstream_least_conn_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_upstream_least_conn_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_upstream_least_conn_module.o \
		src/http/modules/ngx_http_upstream_least_conn_module.c


objs/src/http/modules/ngx_http_upstream_keepalive_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_upstream_keepalive_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_upstream_keepalive_module.o \
		src/http/modules/ngx_http_upstream_keepalive_module.c


objs/src/http/modules/ngx_http_upstream_zone_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_upstream_zone_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_upstream_zone_module.o \
		src/http/modules/ngx_http_upstream_zone_module.c


objs/src/http/modules/ngx_http_stub_status_module.o:	$(CORE_DEPS) $(HTTP_DEPS) \
	src/http/modules/ngx_http_stub_status_module.c
	$(CC) -c $(CFLAGS) $(CORE_INCS) $(HTTP_INCS) \
		-o objs/src/http/modules/ngx_http_stub_status_module.o \
		src/http/modules/ngx_http_stub_status_module.c


objs/addon/src/ngx_http_redis2_module.o:	$(ADDON_DEPS) \
	../redis2-nginx-module-master/src/ngx_http_redis2_module.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_redis2_module.o \
		../redis2-nginx-module-master/src/ngx_http_redis2_module.c


objs/addon/src/ngx_http_redis2_handler.o:	$(ADDON_DEPS) \
	../redis2-nginx-module-master/src/ngx_http_redis2_handler.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_redis2_handler.o \
		../redis2-nginx-module-master/src/ngx_http_redis2_handler.c


objs/addon/src/ngx_http_redis2_reply.o:	$(ADDON_DEPS) \
	../redis2-nginx-module-master/src/ngx_http_redis2_reply.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_redis2_reply.o \
		../redis2-nginx-module-master/src/ngx_http_redis2_reply.c


objs/addon/src/ngx_http_redis2_util.o:	$(ADDON_DEPS) \
	../redis2-nginx-module-master/src/ngx_http_redis2_util.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_redis2_util.o \
		../redis2-nginx-module-master/src/ngx_http_redis2_util.c


objs/addon/src/ndk.o:	$(ADDON_DEPS) \
	../ngx_devel_kit-master/src/ndk.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ndk.o \
		../ngx_devel_kit-master/src/ndk.c


objs/addon/src/ngx_http_set_base32.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_base32.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_base32.o \
		../set-misc-nginx-module-master/src/ngx_http_set_base32.c


objs/addon/src/ngx_http_set_default_value.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_default_value.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_default_value.o \
		../set-misc-nginx-module-master/src/ngx_http_set_default_value.c


objs/addon/src/ngx_http_set_hashed_upstream.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_hashed_upstream.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_hashed_upstream.o \
		../set-misc-nginx-module-master/src/ngx_http_set_hashed_upstream.c


objs/addon/src/ngx_http_set_quote_sql.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_quote_sql.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_quote_sql.o \
		../set-misc-nginx-module-master/src/ngx_http_set_quote_sql.c


objs/addon/src/ngx_http_set_quote_json.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_quote_json.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_quote_json.o \
		../set-misc-nginx-module-master/src/ngx_http_set_quote_json.c


objs/addon/src/ngx_http_set_unescape_uri.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_unescape_uri.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_unescape_uri.o \
		../set-misc-nginx-module-master/src/ngx_http_set_unescape_uri.c


objs/addon/src/ngx_http_set_misc_module.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_misc_module.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_misc_module.o \
		../set-misc-nginx-module-master/src/ngx_http_set_misc_module.c


objs/addon/src/ngx_http_set_escape_uri.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_escape_uri.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_escape_uri.o \
		../set-misc-nginx-module-master/src/ngx_http_set_escape_uri.c


objs/addon/src/ngx_http_set_hash.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_hash.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_hash.o \
		../set-misc-nginx-module-master/src/ngx_http_set_hash.c


objs/addon/src/ngx_http_set_local_today.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_local_today.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_local_today.o \
		../set-misc-nginx-module-master/src/ngx_http_set_local_today.c


objs/addon/src/ngx_http_set_hex.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_hex.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_hex.o \
		../set-misc-nginx-module-master/src/ngx_http_set_hex.c


objs/addon/src/ngx_http_set_base64.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_base64.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_base64.o \
		../set-misc-nginx-module-master/src/ngx_http_set_base64.c


objs/addon/src/ngx_http_set_random.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_random.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_random.o \
		../set-misc-nginx-module-master/src/ngx_http_set_random.c


objs/addon/src/ngx_http_set_secure_random.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_secure_random.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_secure_random.o \
		../set-misc-nginx-module-master/src/ngx_http_set_secure_random.c


objs/addon/src/ngx_http_set_rotate.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_rotate.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_rotate.o \
		../set-misc-nginx-module-master/src/ngx_http_set_rotate.c


objs/addon/src/ngx_http_set_hmac.o:	$(ADDON_DEPS) \
	../set-misc-nginx-module-master/src/ngx_http_set_hmac.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_set_hmac.o \
		../set-misc-nginx-module-master/src/ngx_http_set_hmac.c


objs/addon/ngx_http_redis-0.3.8/ngx_http_redis_module.o:	$(ADDON_DEPS) \
	../ngx_http_redis-0.3.8/ngx_http_redis_module.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/ngx_http_redis-0.3.8/ngx_http_redis_module.o \
		../ngx_http_redis-0.3.8/ngx_http_redis_module.c


objs/addon/src/ngx_http_lua_script.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_script.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_script.o \
		../lua-nginx-module-master/src/ngx_http_lua_script.c


objs/addon/src/ngx_http_lua_log.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_log.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_log.o \
		../lua-nginx-module-master/src/ngx_http_lua_log.c


objs/addon/src/ngx_http_lua_subrequest.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_subrequest.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_subrequest.o \
		../lua-nginx-module-master/src/ngx_http_lua_subrequest.c


objs/addon/src/ngx_http_lua_ndk.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_ndk.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_ndk.o \
		../lua-nginx-module-master/src/ngx_http_lua_ndk.c


objs/addon/src/ngx_http_lua_control.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_control.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_control.o \
		../lua-nginx-module-master/src/ngx_http_lua_control.c


objs/addon/src/ngx_http_lua_time.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_time.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_time.o \
		../lua-nginx-module-master/src/ngx_http_lua_time.c


objs/addon/src/ngx_http_lua_misc.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_misc.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_misc.o \
		../lua-nginx-module-master/src/ngx_http_lua_misc.c


objs/addon/src/ngx_http_lua_variable.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_variable.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_variable.o \
		../lua-nginx-module-master/src/ngx_http_lua_variable.c


objs/addon/src/ngx_http_lua_string.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_string.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_string.o \
		../lua-nginx-module-master/src/ngx_http_lua_string.c


objs/addon/src/ngx_http_lua_output.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_output.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_output.o \
		../lua-nginx-module-master/src/ngx_http_lua_output.c


objs/addon/src/ngx_http_lua_headers.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_headers.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_headers.o \
		../lua-nginx-module-master/src/ngx_http_lua_headers.c


objs/addon/src/ngx_http_lua_req_body.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_req_body.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_req_body.o \
		../lua-nginx-module-master/src/ngx_http_lua_req_body.c


objs/addon/src/ngx_http_lua_uri.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_uri.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_uri.o \
		../lua-nginx-module-master/src/ngx_http_lua_uri.c


objs/addon/src/ngx_http_lua_args.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_args.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_args.o \
		../lua-nginx-module-master/src/ngx_http_lua_args.c


objs/addon/src/ngx_http_lua_ctx.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_ctx.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_ctx.o \
		../lua-nginx-module-master/src/ngx_http_lua_ctx.c


objs/addon/src/ngx_http_lua_regex.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_regex.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_regex.o \
		../lua-nginx-module-master/src/ngx_http_lua_regex.c


objs/addon/src/ngx_http_lua_module.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_module.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_module.o \
		../lua-nginx-module-master/src/ngx_http_lua_module.c


objs/addon/src/ngx_http_lua_headers_out.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_headers_out.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_headers_out.o \
		../lua-nginx-module-master/src/ngx_http_lua_headers_out.c


objs/addon/src/ngx_http_lua_headers_in.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_headers_in.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_headers_in.o \
		../lua-nginx-module-master/src/ngx_http_lua_headers_in.c


objs/addon/src/ngx_http_lua_directive.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_directive.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_directive.o \
		../lua-nginx-module-master/src/ngx_http_lua_directive.c


objs/addon/src/ngx_http_lua_consts.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_consts.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_consts.o \
		../lua-nginx-module-master/src/ngx_http_lua_consts.c


objs/addon/src/ngx_http_lua_exception.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_exception.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_exception.o \
		../lua-nginx-module-master/src/ngx_http_lua_exception.c


objs/addon/src/ngx_http_lua_util.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_util.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_util.o \
		../lua-nginx-module-master/src/ngx_http_lua_util.c


objs/addon/src/ngx_http_lua_cache.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_cache.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_cache.o \
		../lua-nginx-module-master/src/ngx_http_lua_cache.c


objs/addon/src/ngx_http_lua_contentby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_contentby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_contentby.o \
		../lua-nginx-module-master/src/ngx_http_lua_contentby.c


objs/addon/src/ngx_http_lua_rewriteby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_rewriteby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_rewriteby.o \
		../lua-nginx-module-master/src/ngx_http_lua_rewriteby.c


objs/addon/src/ngx_http_lua_accessby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_accessby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_accessby.o \
		../lua-nginx-module-master/src/ngx_http_lua_accessby.c


objs/addon/src/ngx_http_lua_setby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_setby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_setby.o \
		../lua-nginx-module-master/src/ngx_http_lua_setby.c


objs/addon/src/ngx_http_lua_capturefilter.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_capturefilter.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_capturefilter.o \
		../lua-nginx-module-master/src/ngx_http_lua_capturefilter.c


objs/addon/src/ngx_http_lua_clfactory.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_clfactory.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_clfactory.o \
		../lua-nginx-module-master/src/ngx_http_lua_clfactory.c


objs/addon/src/ngx_http_lua_pcrefix.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_pcrefix.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_pcrefix.o \
		../lua-nginx-module-master/src/ngx_http_lua_pcrefix.c


objs/addon/src/ngx_http_lua_headerfilterby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_headerfilterby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_headerfilterby.o \
		../lua-nginx-module-master/src/ngx_http_lua_headerfilterby.c


objs/addon/src/ngx_http_lua_shdict.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_shdict.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_shdict.o \
		../lua-nginx-module-master/src/ngx_http_lua_shdict.c


objs/addon/src/ngx_http_lua_socket_tcp.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_socket_tcp.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_socket_tcp.o \
		../lua-nginx-module-master/src/ngx_http_lua_socket_tcp.c


objs/addon/src/ngx_http_lua_api.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_api.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_api.o \
		../lua-nginx-module-master/src/ngx_http_lua_api.c


objs/addon/src/ngx_http_lua_logby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_logby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_logby.o \
		../lua-nginx-module-master/src/ngx_http_lua_logby.c


objs/addon/src/ngx_http_lua_sleep.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_sleep.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_sleep.o \
		../lua-nginx-module-master/src/ngx_http_lua_sleep.c


objs/addon/src/ngx_http_lua_semaphore.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_semaphore.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_semaphore.o \
		../lua-nginx-module-master/src/ngx_http_lua_semaphore.c


objs/addon/src/ngx_http_lua_coroutine.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_coroutine.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_coroutine.o \
		../lua-nginx-module-master/src/ngx_http_lua_coroutine.c


objs/addon/src/ngx_http_lua_bodyfilterby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_bodyfilterby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_bodyfilterby.o \
		../lua-nginx-module-master/src/ngx_http_lua_bodyfilterby.c


objs/addon/src/ngx_http_lua_initby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_initby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_initby.o \
		../lua-nginx-module-master/src/ngx_http_lua_initby.c


objs/addon/src/ngx_http_lua_initworkerby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_initworkerby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_initworkerby.o \
		../lua-nginx-module-master/src/ngx_http_lua_initworkerby.c


objs/addon/src/ngx_http_lua_socket_udp.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_socket_udp.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_socket_udp.o \
		../lua-nginx-module-master/src/ngx_http_lua_socket_udp.c


objs/addon/src/ngx_http_lua_req_method.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_req_method.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_req_method.o \
		../lua-nginx-module-master/src/ngx_http_lua_req_method.c


objs/addon/src/ngx_http_lua_phase.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_phase.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_phase.o \
		../lua-nginx-module-master/src/ngx_http_lua_phase.c


objs/addon/src/ngx_http_lua_uthread.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_uthread.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_uthread.o \
		../lua-nginx-module-master/src/ngx_http_lua_uthread.c


objs/addon/src/ngx_http_lua_timer.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_timer.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_timer.o \
		../lua-nginx-module-master/src/ngx_http_lua_timer.c


objs/addon/src/ngx_http_lua_config.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_config.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_config.o \
		../lua-nginx-module-master/src/ngx_http_lua_config.c


objs/addon/src/ngx_http_lua_worker.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_worker.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_worker.o \
		../lua-nginx-module-master/src/ngx_http_lua_worker.c


objs/addon/src/ngx_http_lua_ssl_certby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_ssl_certby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_ssl_certby.o \
		../lua-nginx-module-master/src/ngx_http_lua_ssl_certby.c


objs/addon/src/ngx_http_lua_ssl_ocsp.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_ssl_ocsp.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_ssl_ocsp.o \
		../lua-nginx-module-master/src/ngx_http_lua_ssl_ocsp.c


objs/addon/src/ngx_http_lua_lex.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_lex.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_lex.o \
		../lua-nginx-module-master/src/ngx_http_lua_lex.c


objs/addon/src/ngx_http_lua_balancer.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_balancer.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_balancer.o \
		../lua-nginx-module-master/src/ngx_http_lua_balancer.c


objs/addon/src/ngx_http_lua_ssl_session_storeby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_ssl_session_storeby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_ssl_session_storeby.o \
		../lua-nginx-module-master/src/ngx_http_lua_ssl_session_storeby.c


objs/addon/src/ngx_http_lua_ssl_session_fetchby.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_ssl_session_fetchby.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_ssl_session_fetchby.o \
		../lua-nginx-module-master/src/ngx_http_lua_ssl_session_fetchby.c


objs/addon/src/ngx_http_lua_ssl.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_ssl.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_ssl.o \
		../lua-nginx-module-master/src/ngx_http_lua_ssl.c


objs/addon/src/ngx_http_lua_log_ringbuf.o:	$(ADDON_DEPS) \
	../lua-nginx-module-master/src/ngx_http_lua_log_ringbuf.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_lua_log_ringbuf.o \
		../lua-nginx-module-master/src/ngx_http_lua_log_ringbuf.c


objs/addon/src/ngx_http_echo_module.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_module.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_module.o \
		../echo-nginx-module-master/src/ngx_http_echo_module.c


objs/addon/src/ngx_http_echo_util.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_util.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_util.o \
		../echo-nginx-module-master/src/ngx_http_echo_util.c


objs/addon/src/ngx_http_echo_timer.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_timer.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_timer.o \
		../echo-nginx-module-master/src/ngx_http_echo_timer.c


objs/addon/src/ngx_http_echo_var.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_var.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_var.o \
		../echo-nginx-module-master/src/ngx_http_echo_var.c


objs/addon/src/ngx_http_echo_handler.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_handler.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_handler.o \
		../echo-nginx-module-master/src/ngx_http_echo_handler.c


objs/addon/src/ngx_http_echo_filter.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_filter.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_filter.o \
		../echo-nginx-module-master/src/ngx_http_echo_filter.c


objs/addon/src/ngx_http_echo_sleep.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_sleep.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_sleep.o \
		../echo-nginx-module-master/src/ngx_http_echo_sleep.c


objs/addon/src/ngx_http_echo_location.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_location.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_location.o \
		../echo-nginx-module-master/src/ngx_http_echo_location.c


objs/addon/src/ngx_http_echo_echo.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_echo.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_echo.o \
		../echo-nginx-module-master/src/ngx_http_echo_echo.c


objs/addon/src/ngx_http_echo_request_info.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_request_info.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_request_info.o \
		../echo-nginx-module-master/src/ngx_http_echo_request_info.c


objs/addon/src/ngx_http_echo_subrequest.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_subrequest.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_subrequest.o \
		../echo-nginx-module-master/src/ngx_http_echo_subrequest.c


objs/addon/src/ngx_http_echo_foreach.o:	$(ADDON_DEPS) \
	../echo-nginx-module-master/src/ngx_http_echo_foreach.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/src/ngx_http_echo_foreach.o \
		../echo-nginx-module-master/src/ngx_http_echo_foreach.c


objs/addon/ngx_zookeeper_lua/ngx_zookeeper_lua_module.o:	$(ADDON_DEPS) \
	../ngx_zookeeper_lua/ngx_zookeeper_lua_module.c
	$(CC) -c $(CFLAGS)  $(ALL_INCS) \
		-o objs/addon/ngx_zookeeper_lua/ngx_zookeeper_lua_module.o \
		../ngx_zookeeper_lua/ngx_zookeeper_lua_module.c


manpage:	objs/nginx.8

objs/nginx.8:	man/nginx.8 objs/ngx_auto_config.h
	sed -e "s|%%PREFIX%%|/usr/local/nginx|" \
		-e "s|%%PID_PATH%%|/usr/local/nginx/logs/nginx.pid|" \
		-e "s|%%CONF_PATH%%|/usr/local/nginx/conf/nginx.conf|" \
		-e "s|%%ERROR_LOG_PATH%%|/usr/local/nginx/logs/error.log|" \
		< man/nginx.8 > $@

install:	build 
	test -d '$(DESTDIR)/usr/local/nginx' || mkdir -p '$(DESTDIR)/usr/local/nginx'

	test -d '$(DESTDIR)/usr/local/nginx/sbin' \
		|| mkdir -p '$(DESTDIR)/usr/local/nginx/sbin'
	test ! -f '$(DESTDIR)/usr/local/nginx/sbin/nginx' \
		|| mv '$(DESTDIR)/usr/local/nginx/sbin/nginx' \
			'$(DESTDIR)/usr/local/nginx/sbin/nginx.old'
	cp objs/nginx '$(DESTDIR)/usr/local/nginx/sbin/nginx'

	test -d '$(DESTDIR)/usr/local/nginx/conf' \
		|| mkdir -p '$(DESTDIR)/usr/local/nginx/conf'

	cp conf/koi-win '$(DESTDIR)/usr/local/nginx/conf'
	cp conf/koi-utf '$(DESTDIR)/usr/local/nginx/conf'
	cp conf/win-utf '$(DESTDIR)/usr/local/nginx/conf'

	test -f '$(DESTDIR)/usr/local/nginx/conf/mime.types' \
		|| cp conf/mime.types '$(DESTDIR)/usr/local/nginx/conf'
	cp conf/mime.types '$(DESTDIR)/usr/local/nginx/conf/mime.types.default'

	test -f '$(DESTDIR)/usr/local/nginx/conf/fastcgi_params' \
		|| cp conf/fastcgi_params '$(DESTDIR)/usr/local/nginx/conf'
	cp conf/fastcgi_params \
		'$(DESTDIR)/usr/local/nginx/conf/fastcgi_params.default'

	test -f '$(DESTDIR)/usr/local/nginx/conf/fastcgi.conf' \
		|| cp conf/fastcgi.conf '$(DESTDIR)/usr/local/nginx/conf'
	cp conf/fastcgi.conf '$(DESTDIR)/usr/local/nginx/conf/fastcgi.conf.default'

	test -f '$(DESTDIR)/usr/local/nginx/conf/uwsgi_params' \
		|| cp conf/uwsgi_params '$(DESTDIR)/usr/local/nginx/conf'
	cp conf/uwsgi_params \
		'$(DESTDIR)/usr/local/nginx/conf/uwsgi_params.default'

	test -f '$(DESTDIR)/usr/local/nginx/conf/scgi_params' \
		|| cp conf/scgi_params '$(DESTDIR)/usr/local/nginx/conf'
	cp conf/scgi_params \
		'$(DESTDIR)/usr/local/nginx/conf/scgi_params.default'

	test -f '$(DESTDIR)/usr/local/nginx/conf/nginx.conf' \
		|| cp conf/nginx.conf '$(DESTDIR)/usr/local/nginx/conf/nginx.conf'
	cp conf/nginx.conf '$(DESTDIR)/usr/local/nginx/conf/nginx.conf.default'

	test -d '$(DESTDIR)/usr/local/nginx/logs' \
		|| mkdir -p '$(DESTDIR)/usr/local/nginx/logs'

	test -d '$(DESTDIR)/usr/local/nginx/logs' \
		|| mkdir -p '$(DESTDIR)/usr/local/nginx/logs'

	test -d '$(DESTDIR)/usr/local/nginx/html' \
		|| cp -R html '$(DESTDIR)/usr/local/nginx'

	test -d '$(DESTDIR)/usr/local/nginx/logs' \
		|| mkdir -p '$(DESTDIR)/usr/local/nginx/logs'
