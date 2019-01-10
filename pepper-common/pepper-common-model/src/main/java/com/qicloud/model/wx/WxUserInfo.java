package com.pepper.model.wx;

import java.util.List;
import java.util.Map;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.pepper.utils.Util;

public class WxUserInfo {

	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
	private Integer subscribe;
	/**
	 * 用户的标识，对当前公众号唯一
	 */
	private String openid;
	/**
	 * 用户的昵称
	 */
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private Integer sex;
	/**
	 * 用户所在城市
	 */
	private String city;
	/**
	 * 用户所在国家
	 */
	private String country;
	/**
	 * 用户所在省份
	 */
	private String province;
	/**
	 * 用户的语言，简体中文为zh_CN
	 */
	private String language;
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	private String headimgurl;
	/**
	 * subscribe_time 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	private Integer subscribeTime;
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	 */
	private String unionid;
	/**
	 * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 */
	private String remark;
	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）
	 */
	private Integer groupid;
	/**
	 * tagid_list 用户被打上的标签ID列表
	 */
	private List<Integer> tagidList;
	/**
	 * subscribe_scene 返回用户关注的渠道来源，ADD_SCENE_SEARCH
	 * 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD
	 * 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK
	 * 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID
	 * 支付后关注，ADD_SCENE_OTHERS 其他
	 */
	private String subscribeScene;
	/**
	 * 二维码扫码场景（开发者自定义）
	 */
	private Integer qrScene;
	/**
	 * qr_scene_str 二维码扫码场景描述（开发者自定义）
	 */
	private String qrSceneStr;
	
	public WxUserInfo() {
	}
	
	@SuppressWarnings("unchecked")
	public static WxUserInfo parse(String jsonStr) {
		WxUserInfo wxUserInfo = new WxUserInfo();
		try {
			Map<String, Object> temp = JsonUtils.parse(jsonStr, Map.class);
			Integer errcode = (Integer) temp.get("errcode");
			String errmsg = (String) temp.get("errmsg");
			if (errcode != null) {
				throw new RuntimeException("errcode:" + errcode + ",errmsg:" + errmsg);
			}
			wxUserInfo.setSubscribe((Integer) temp.get("subscribe"));
			wxUserInfo.setOpenid((String) temp.get("openid"));
			wxUserInfo.setNickname((String) temp.get("nickname"));
			wxUserInfo.setSex((Integer) temp.get("sex"));
			wxUserInfo.setLanguage((String) temp.get("language"));
			wxUserInfo.setCity((String) temp.get("city"));
			wxUserInfo.setProvince((String) temp.get("province"));
			wxUserInfo.setCountry((String) temp.get("country"));
			wxUserInfo.setHeadimgurl((String) temp.get("headimgurl"));
			wxUserInfo.setSubscribeTime((Integer) temp.get("subscribe_time"));
			wxUserInfo.setUnionid((String) temp.get("unionid"));
			wxUserInfo.setRemark((String) temp.get("remark"));
			wxUserInfo.setGroupid((Integer) temp.get("groupid"));
			wxUserInfo.setTagidList((List<Integer>) temp.get("tagid_list"));
			wxUserInfo.setSubscribeScene((String) temp.get("subscribe_scene"));
			wxUserInfo.setQrScene((Integer) temp.get("qr_scene"));
			wxUserInfo.setQrSceneStr((String) temp.get("qr_scene_str"));
		} catch (Exception e) {
			throw new RuntimeException(jsonStr + " can't convert to " + wxUserInfo + " because parse error", e);
		}
		return wxUserInfo;
	}

	public boolean isSubscribe() {
		return subscribe != 0 && Util.isNotEmpty(openid);
	}
	
	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Integer subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public List<Integer> getTagidList() {
		return tagidList;
	}

	public void setTagidList(List<Integer> tagidList) {
		this.tagidList = tagidList;
	}

	public String getSubscribeScene() {
		return subscribeScene;
	}

	public void setSubscribeScene(String subscribeScene) {
		this.subscribeScene = subscribeScene;
	}

	public Integer getQrScene() {
		return qrScene;
	}

	public void setQrScene(Integer qrScene) {
		this.qrScene = qrScene;
	}

	public String getQrSceneStr() {
		return qrSceneStr;
	}

	public void setQrSceneStr(String qrSceneStr) {
		this.qrSceneStr = qrSceneStr;
	}

}

