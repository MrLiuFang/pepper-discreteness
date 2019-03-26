<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<script id="uploadImageTmpl" type="text/x-jquery-tmpl">
	{{each(i,d) data}}
	<div class="qc-per-media">
		<div class="qc-per-media-close layui-hide layui-anim ">
			<i class="layui-icon qc-per-media-close-icon" file-index="{{= d.fileindex }}" title="删除">&#x1006;</i>
		</div>
		{{if d.url!="" }}
		<img src="{{= d.url }}" layer-src="{{= d.url }}" class="layui-upload-img" id="{{= d.id }}" title="{{= d.filename }}"/>
		{{else}}
		<div class="qc-per-media-fail" file-index="{{= d.fileindex }}" qc-title="{{= d.filename }}上传失败，点击重传" >
			<i class="layui-icon" >&#xe69c;</i>
		</div>
		{{/if}}
	</div>
	{{/each}}
</script>

<script id="uploadVideoTmpl" type="text/x-jquery-tmpl">
	{{each(i,d) data}}
	<div class="qc-per-media">
		{{if d.url!="" }}
			<div class="qc-per-media-close layui-hide qc-per-video-play">
				<i class="layui-icon qc-per-media-close-icon" title="删除">&#x1006;</i>
				<i class="layui-icon layui-icon-play" title="播放" data-src="{{= d.url }}" qc-title="{{= d.filename }}"></i>
			</div>
		{{else}}
			<div class="qc-per-media-close layui-hide layui-anim ">
				<i class="layui-icon qc-per-media-close-icon" title="删除">&#x1006;</i>
			</div>
		{{/if}}
		{{if d.url!="" }}
			<video src="{{= d.url }}" class="layui-upload-img" id="{{= d.id }}" title="{{= d.filename }}"/>
		{{else}}
			<div class="qc-per-media-fail" file-index="{{= d.fileindex }}" qc-title="{{= d.filename }}上传失败，点击重传" >
				<i class="layui-icon" >&#xe69c;</i>
			</div>
		{{/if}}
	</div>
	{{/each}}
</script>

<script type="text/javascript">
	var upload = layui.upload, mylayer;
	layui.extend({
		mylayer : '{/}${ctx}/assets/layui/lay/modules/mylayer' // {/}的意思即代表采用自有路径，即不跟随 base 路径
	})
	layui.use('mylayer', function() {
		mylayer = layui.mylayer;
	});

	//命名空间
	var uploadUtil = {

		/**
		 * 开放方法。
		 * 上传图片。比如头像，身份证等。
		 * @param: btnId 上传按钮的id，如'按钮id'，必填。
		 * @param: contentId .layui-upload-list节点的id，如'id'，必填。
		 * @param: paramName 提交结果的参数名，如'headImg'，默认'images'。
		 * @param: viewList 回显的内容，修改或者查看用，传字符串类型，格式'[{"id":"e273c9b5538248749da328b5885fbddf","url":"http://dfs.test.qi-cloud.com/group1/M00/00/51/CgoVCVrJ4f6AZsEuAABpHr5Nj3s159.png"}]'。
		 *	 				要注意的是，当我们的json字符串是从JSTL表达式中获取的，那么调用这个方法的时候，参数要注意用两个单引号括起来，而不是两个双引号，比如'${obj}'而不是"${obj}"。
		 * @param: readonly 是否只读，true表示只读，false表示可编辑，默认false。 	
		 * @param: max 最大可上传数，null或者不传表示只能上传1张，指定一个大于1的数值表示最大上传改数值的图片，如果超过了，从后往前替换。 	
		 * @param: layExt layui上传控件的其他参数传入。 	自定义参数remove，删除回调方法。
		 */
		initImg : function(btnId, contentId, paramName, viewList, readonly,
				max, layExt) {
			return uploadUtil.initUpload(btnId, contentId, paramName, viewList,
					readonly, max, layExt, "uploadImageTmpl");
		},

		/**
		 * 开放方法。
		 * 上传视频。
		 * @param: btnId 上传按钮的id，如'按钮id'，必填。
		 * @param: contentId .layui-upload-list节点的id，如'id'，必填。
		 * @param: paramName 提交结果的参数名，如'myVideo'，默认'videos'。
		 * @param: viewList 回显的内容，修改或者查看用，传字符串类型，格式'[{"id":"e273c9b5538248749da328b5885fbddf","url":"http://dfs.test.qi-cloud.com/group1/M00/00/51/CgoVCVrJ4f6AZsEuAABpHr5Nj3s159.mp4"}]'。
		 *	 				要注意的是，当我们的json字符串是从JSTL表达式中获取的，那么调用这个方法的时候，参数要注意用两个单引号括起来，而不是两个双引号，比如'${obj}'而不是"${obj}"。
		 * @param: readonly 是否只读，true表示只读，false表示可编辑，默认false。 	
		 * @param: max 最大可上传数，null或者不传表示只能上传1张，指定一个大于1的数值表示最大上传改数值的图片，如果超过了，从后往前替换。 	
		 * @param: layExt layui上传控件的其他参数传入。 	自定义参数remove，删除回调方法。
		 */
		initVideo : function(btnId, contentId, paramName, viewList, readonly,
				max, layExt) {
			if (!paramName) {
				paramName = "videos";
			}
			var defaultOpt = {
				accept : "video",
				playVideoFn : function(_this) {
					layui.layer.open({
						type : 2,
						title : $(_this).attr("qc-title"),
						closeBtn : 1,
						area : [ '630px', '360px' ],
						fixed : false,
						maxmin : true,
						shade : 0.8,
						shadeClose : true,
						content : $(_this).attr("data-src")
					});
				}
			}
			if (layExt) {
				$.extend(defaultOpt, layExt);
			}
			return uploadUtil.initUpload(btnId, contentId, paramName, viewList,
					readonly, max, defaultOpt, "uploadVideoTmpl");
		},

		/**
		 * 媒体上传初始化方法，不开放。
		 * @param: tmplId 上传回显的jquery template的id。"uploadImageTmpl"表示上传图片，"uploadVideoTmpl"表示上传视频。
		 */
		initUpload : function(btnId, contentId, paramName, viewList, readonly,
				max, layExt, tmplId) {

			//构造器，创建一个闭包空间
			var Class = function() {
				var that = this, config = $.extend(Class.prototype.config, that
						.initConfig());
				if (!paramName) {
					config.paramName = "images";
				}
				config.content
						.parent()
						.append(
								'<input type="hidden" value="" id="'+config.contentId+'_file" name="'+config.paramName+'" lay-verify="'+config.paramName+'"/>');
				//回显
				if (viewList) {
					viewList = baseUtil.json(viewList);
					config.viewList = viewList;
					that.doRender();
				}
				//只读时
				if (readonly) {
					config.elem.addClass("layui-btn-disabled");
					config.content.find(".qc-per-media-close").remove();
					config.elem.attr("disabled", "disabled");
				} else {
					//上传控件初始化
					var uploadInst = upload.render(config);
					config.uploadInst = uploadInst;
				}
			};

			//组装参数
			Class.prototype.initConfig = function() {
				var that = this, config = that.config, mult;

				if (!layExt) {
					layExt = {};
				}
				that.layExt = layExt;

				//默认参数上传图片，根据模板不同，上传不同的流媒体
				if (config.tmplId && config.tmplId == "uploadVideoTmpl") {
					config.fileTag = "video";
				}

				//处理最大上传数，涉及layui upload控件的两个参数，multiple和number。
				if (max && max > 1) {
					mult = true;
					that.layExt["number"] = max;
				}

				//临时组装参数参数，处理一些可能变化的参数情况
				var tempConfig = {
					multiple : mult,
					choose : function(obj) {
						config.multiFileList = [];
						if (!config.reuploadObj) {
							config.reuploadObj = obj;
						}
						obj.preview(function(index, file, result) {
							//每次选择上传，index格式是"当前时间戳-选中的图片从0开始的序列号"
							//上传成功或失败后用于回显文件名，重传等操作，因为这里记录了用户选择的文件的上传前内容。
							config.previewList[index] = {
								"fileindex" : index,
								"filename" : file.name,
								"fileOrigin" : file
							};
						});
					},
					before : function(obj) {
						config.elem.html('正在上传...');
						config.elem.attr("disabled", "disabled");
						if (that.layExt.before) {
							that.layExt.before(obj);
						}
					},
					done : function(res, index, upload) {
						var noErr = common.handleResult(res);
						if (noErr) {
							//[{"id":"e273c9b5538248749da328b5885fbddf","url":"http://dfs.test.qi-cloud.com/group1/M00/00/51/CgoVCVrJ4f6AZsEuAABpHr5Nj3s159.png"}]
							var oneImage = res.data.list[0];
							oneImage["fileindex"] = config.previewList[index]["fileindex"];
							oneImage["filename"] = config.previewList[index]["filename"];
							if (mult) {
								config.multiFileList.push(oneImage);
							} else {
								config.viewList = [ oneImage ];
								that.doRender();
								setTimeout(function() {
									config.elem.html(config.btnText);
									config.elem.removeAttr("disabled",
											"disabled");
									if (that.layExt.done) {
										that.layExt.done(res, index, upload);
									}
								}, 500);
							}
						}
					},
					allDone : function(obj) {
						config.viewList = config.multiFileList;
						that.doRender();
						setTimeout(function() {
							config.elem.html(config.btnText);
							config.elem.removeAttr("disabled", "disabled");
							if (that.layExt.allDone) {
								that.layExt.done(obj);
							}
						}, 500);
					},
					error : function() {
						config.elem.html(config.btnText);
						config.elem.removeAttr("disabled", "disabled");
						layui.layer.closeAll('loading'); //关闭loading
						if (that.layExt.error) {
							that.layExt.error();
						} else {
							layui.layer.msg("上传失败，请检查网络！");
						}
					}
				};
				//将临时组装的参数和layExt参数合并到一个空对象中，目的是不能影响原始的layExt中的内容。
				var finalConfig = {};
				$.extend(finalConfig, that.layExt, tempConfig);
				return finalConfig;
			}

			//默认配置，对象所有
			Class.prototype.config = {
				//默认上传url
				"url" : '${ctx}/file/add',
				"contentId" : contentId,
				//.layui-upload-list节点
				"content" : $("#" + contentId),
				//默认提交参数名，默认"images"
				"paramName" : paramName,
				//是否只读
				"readonly" : readonly,
				//设置同时可上传的文件数量，一般配合 multiple 参数出现。0（即不限制）
				"number" : 0,
				//是否支持多上传，默认否
				"multiple" : false,
				//模板id
				"tmplId" : tmplId,
				//处理媒体类型，默认是上传图片
				"fileTag" : "img",
				"elem" : $("#" + btnId),
				"btnText" : $("#" + btnId).html(),
				//临时的多上传缓存列表
				"multiFileList" : null,
				//上传后回显对象
				"previewList" : [],
				//上传后回调方法的重传对象
				"reuploadObj" : null,
				//回显对象数组
				"viewList" : null,
				//layui upload组件的上传控件对象
				"uploadInst" : null,
				//上传失败tips临时用
				"tipsi" : null
			};

			//显示图片，为现有的图片重新绑定事件。
			//list：用于上传或删除图片后，图片显示刷新
			//defaultOpt：doRender时使用默认或者自定义的一些参数或方法。
			Class.prototype.doRender = function() {
				var that = this, config = that.config, viewList = config.viewList, content = config.content, tmplId = config.tmplId;
				if (!config.multiple) {
					baseUtil.template(tmplId, {
						"data" : viewList
					}, content, false);
				} else {
					//现有图片数
					var curImgs = content.find(".qc-per-media").length;
					//空档图片位
					var freeSpaces = config.number - curImgs;
					//如果上传的图片的数量大于空档位，那么删减掉原来的一些图片后，将新的图片插入。
					if (viewList.length > freeSpaces && curImgs > 0) {
						for (var i = 0; i < viewList.length - freeSpaces; i++) {
							content.find(".qc-per-media:last").remove();
						}
					}
					baseUtil.template(tmplId, {
						"data" : viewList
					}, content, true);
				}
				//更新提交数据
				var ids = [];
				content.find(config.fileTag).each(function() {
					ids.push($(this).attr("id"));
				});
				$("#" + config.contentId + "_file").val(ids.join(";"));
				//更新节点的移进移除效果。
				content.find(".qc-per-media").unbind("hover").hover(function() {
					var close = $(this).find(".qc-per-media-close");
					close.removeClass("layui-hide");
				}, function() {
					var close = $(this).find(".qc-per-media-close");
					close.addClass("layui-hide");
				});
				//点击图片或视频关闭按钮
				content.find(".qc-per-media-close-icon").unbind("click").click(
						function() {
							//删除点击关闭的图片或视频
							$(this).closest(".qc-per-media").remove();
							ids = [];
							//更新现有图片或视频的id到提交隐藏域中。
							content.find(config.fileTag).each(function() {
								ids.push($(this).attr("id"));
							});
							$("#" + config.contentId + "_file").val(
									ids.join(";"));
							//更新相册效果
							mylayer.photos({
								photos : "#" + config.contentId,
								anim : 5
							// 0-6的选择，指定弹出图片动画类型，默认随机（请注意//.0之前的版本用shift参数）
							});
							delete config.previewList[$(this)
									.attr("file-index")];
							if (config.remove
									&& typeof config.remove == 'function') {
								config.remove(); //删除图片回调函数					
							}
						});
				//如果是视频，还有点击播放按钮的处理
				if (tmplId && tmplId == "uploadVideoTmpl") {
					content.find(".layui-icon-play").unbind("click").click(
							function() {
								that.layExt["playVideoFn"](this);
							});
				}

				//更新相册效果
				mylayer.photos({
					photos : "#" + config.contentId,
					anim : 5
				// 0-6的选择，指定弹出图片动画类型，默认随机（请注意//.0之前的版本用shift参数）
				});

				//更新失败图片tips
				$(".qc-per-media-fail").unbind().hover(function() {
					var _that = this;
					var text = $(_that).attr("qc-title");
					if (text) {
						config.tipsi = layui.layer.tips(text, _that, {
							time : 0,
							tips : 1
						});
					}
				}, function() {
					layui.layer.close(config.tipsi);
				});

				//失败重传
				$(".qc-per-media-fail").unbind("click").bind(
						"click",
						function() {
							var index = $(this).attr("file-index");
							config.reuploadObj.upload(index,
									config.previewList[index]["fileOrigin"]);
							$(this).closest(".qc-per-media").remove();
							layui.layer.closeAll();
						});
			}

			//清空当前上传的内容
			Class.prototype.reset = function() {
				var that = this, config = that.config;
				config.content.find(".qc-per-media-close-icon").click();
			};

			//清动态加载内容。
			Class.prototype.reRender = function(viewList) {
				var that = this, config = that.config;
				that.reset();
				config.viewList = baseUtil.json(viewList);
				that.doRender();
			};

			//实例化自定义上传控件
			return new Class();

		},
		/**
		 * excel导入
		 * @param: btnId 上传按钮的id，如'按钮id'，必填。
		 * @param: url 导入处理url。
		 * @param: readonly 是否只读，true表示只读，false表示可编辑，默认false。 	
		 * @param: layExt layui上传控件的其他参数传入。 	
		 * @param: doneFn 导入成功后的回调方法。做一些处理，比如刷新列表操作等。
		 */
		excelImport : function(btnId, url, doneFn, readonly, layExt) {

			//避免layExt的参数覆盖掉一些初始的参数，而应该是叠加并存的。
			var extError = layExt ? layExt.error : null;
			var extBefore = layExt ? layExt.before : null;
			//default参数
			var defaultOpt = {
				elem : "#" + btnId,
				accept : "file",
				acceptMime : "application/vnd.ms-excel",
				exts : "xls",
				url : url,
				before : function(obj) {
					$("#" + btnId).text("正在导入...");
					$("#" + btnId).attr("disabled", "disabled");
					if (extBefore) {
						extBefore(res);
					}
				},
				done : function(data) {
					var noErr = common.handleResult(data);
					if (noErr) {
						layui.layer.msg("导入完成");
					}
					setTimeout(function() {
						$("#" + btnId).text("导入");
						$("#" + btnId).removeAttr("disabled", "disabled");
						if (doneFn) {
							doneFn(data);
						}
					}, 500);
				},
				error : function() {
					$("#" + btnId).text("导入");
					$("#" + btnId).removeAttr("disabled", "disabled");
					layui.layer.closeAll('loading'); //关闭loading
					if (extError) {
						extError();
					} else {
						layui.layer.msg("导入失败，请检查网络！");
					}
				}
			};
			//删除额外的参数，这样就不会覆盖掉默认参数，同时也能使额外的参数起作用。
			layExt ? delete layExt.error : "";
			layExt ? delete layExt.before : "";

			//如果有ext参数，合并到deault参数中
			if (layExt) {
				$.extend(defaultOpt, layExt);
			}

			//导入控件初始化
			var excelImportInst = upload.render(defaultOpt);

			//只读时
			if (readonly) {
				$("#" + btnId).addClass("layui-btn-disabled");
				$("#" + btnId).attr("disabled", "disabled");
			}
		}
	};
</script>