<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 省市县联动插件 -->
<script id="regionOptionTemplate" type="text/x-jquery-tmpl">
	{{each(i,d) data}}
		<option value="{{= d.code }}" {{if d.code == pcode}}selected{{/if}} normalopt="true">{{= d.areaName }}</option>
	{{/each}}
</script>

<script id="regionSelectTemplate" type="text/x-jquery-tmpl">
	<div class="layui-input-inline provinceSelect_{{= selectId }}">
		<select id="provinceSelect_{{= selectId }}" lay-filter="provinceSelect_{{= selectId }}" lay-filter="provinceSelect_{{= selectId }}"
			name="provinceSelect_{{= selectId }}">
			<option value=""></option>
		</select>
	</div>
	<div class="layui-input-inline citySelect_{{= selectId }}">
		<select id="citySelect_{{= selectId }}" lay-filter="citySelect_{{= selectId }}" lay-filter="citySelect_{{= selectId }}"
			name="citySelect_{{= selectId }}">
			<option value=""></option>
		</select>
	</div>
	<div class="layui-input-inline townSelect_{{= selectId }}">
		<select id="townSelect_{{= selectId }}" lay-filter="townSelect_{{= selectId }}" lay-filter="townSelect_{{= selectId }}"
			name="townSelect_{{= selectId }}">
			<option value=""></option>
		</select>
	</div>
 </script>

<script type="text/javascript">
	regionUtil = {

		/**
		 * 省市县联动插件初始化。
		 * @param selectId:必填。组件唯一id，用于区分其他同页面的相同组件；
		 * @param provinceCode：用于省下拉条回显，地区编码，String类型；
		 * @param cityCode：用于市下拉条回显，地区编码，String类型； 
		 * @param townCode：用于城镇下拉条回显，地区编码，String类型；
		 * @param regionUrl：获取数据的url，默认${ctx}/console/supportArea/getAreaChilds。
		 * @param required 是否必选，可选值true|false。默认false。必选时没有"请选择"选项。
		 *
		 * 例子：
		 * <div class="layui-form-item qc-select-group" id="addrSelect">
		 *	 <label class="layui-form-label">请选择省市区<span class="qi-req">*</span></label>
		 * </div>
		 *
		 * regionUtil.initRegionSelect("addrSelect");
		 *
		 */
		initRegionSelect : function(selectId, provinceCode, cityCode, townCode, regionUrl, required, provinceSelectFn, citySelectFn, townSelectFn) {
			if (!regionUrl) {
				regionUrl = "${ctx}/area/getAreaChilds";
			}

			//添加组件框架
			baseUtil.template('regionSelectTemplate', {
				"selectId" : selectId
			}, $("#" + selectId), true);

			/**
			 * 当省下拉条被点击时，加载选定的省下的所有市。
			 */
			layui.form.on('select(provinceSelect_' + selectId + ')', function(data) {
				var code = $("#provinceSelect_" + selectId).val();
				if (code) {
					$("#citySelect_" + selectId).children("[normalopt=true]").remove();
					baseUtil.AJpost(regionUrl, {
						"code" : code
					}, function(data) {
						baseUtil.template('regionOptionTemplate', {
							"data" : data.data.list,
							"pcode" : cityCode
						}, $("#citySelect_" + selectId), !(!!required));
						layui.event("form", "select(citySelect_" + selectId + ")");
					});
				} else {
					$("#citySelect_" + selectId).children("[normalopt=true]").remove();
					$("#townSelect_" + selectId).children("[normalopt=true]").remove();
					baseUtil.template('regionOptionTemplate', {}, $("#citySelect_" + selectId), true);
					baseUtil.template('regionOptionTemplate', {}, $("#townSelect_" + selectId), true);
				}
				layui.form.render('select');
				if (provinceSelectFn) {
					provinceSelectFn(data, 'provinceSelect_' + selectId);
				}
			});

			/**
			 * 当市下拉条被点击时，加载选定的市下的所有区。
			 */
			layui.form.on('select(citySelect_' + selectId + ')', function(data) {
				var code = $("#citySelect_" + selectId).val();
				if (code) {
					$("#townSelect_" + selectId).children("[normalopt=true]").remove();
					baseUtil.AJpost(regionUrl, {
						"code" : code
					}, function(data) {
						baseUtil.template('regionOptionTemplate', {
							"data" : data.data.list,
							"pcode" : townCode
						}, $("#townSelect_" + selectId), !(!!required));
						layui.event("form", "select(townSelect_" + selectId + ")");
					});
				} else {
					$("#townSelect_" + selectId).children("[normalopt=true]").remove();
					baseUtil.template('regionOptionTemplate', {}, $("#townSelect_" + selectId), true);
				}
				layui.form.render('select');
				if (citySelectFn) {
					citySelectFn(data, "citySelect_" + selectId);
				}
			});

			/**
			 * 当区下拉条被点击时，执行回调方法。
			 */
			layui.form.on('select(townSelect_' + selectId + ')', function(data) {
				if (townSelectFn) {
					townSelectFn(data, 'townSelect_' + selectId);
				}
			});

			/**
			 * 获取省级地区数据
			 */
			baseUtil.AJpost(regionUrl, {
				"code" : 0
			}, function(data) {
				baseUtil.template('regionOptionTemplate', {
					"data" : data.data.list,
					"pcode" : provinceCode
				}, $("#provinceSelect_" + selectId), !(!!required));
				layui.event("form", "select(provinceSelect_" + selectId + ")");
			});
			layui.form.render('select');

		}
	};
</script>