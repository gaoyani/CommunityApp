package com.huiwei.communityapp.utils;

public class UrlConstants {
	
	
//	public static final String URL_DOMAIN_NAME = "http://192.168.22.233/bmzw";
	public static final String URL_DOMAIN_NAME = "http://220.181.77.44/bmzw";
	
	public static final String BMXX_LIST_URL =  URL_DOMAIN_NAME + "/bianmin.php";
	public static final String ZWXX_LIST_URL =  URL_DOMAIN_NAME + "/gover.php";
	public static final String ZWXX_DETAIL_URL =  URL_DOMAIN_NAME + "/detail.php";
	public static final String TZTG_LIST_URL =  URL_DOMAIN_NAME + "/notice_list.php";
	public static final String TZTG_TYPE_URL =  URL_DOMAIN_NAME + "/notice.php";
	public static final String DCWJ_LIST_URL =  URL_DOMAIN_NAME + "/vote.php";
	public static final String DCWJ_DETAIL_URL =  URL_DOMAIN_NAME + "/wj.php";
	
	
	public static final String URL_DOMAIN_NAME_API = URL_DOMAIN_NAME + "/api.php";
	
	
	public static final String LOGIN_URL =  URL_DOMAIN_NAME_API + "/User/login";
	public static final String REGIST_URL =  URL_DOMAIN_NAME_API + "/User/register";
	public static final String LOGOUT_URL =  URL_DOMAIN_NAME_API + "/User/loginout";
	
	public static final String GET_AUTH_CODE = URL_DOMAIN_NAME_API + "/User/verifycode";
	
	public static final String COMMUNITY_GOODS_URL =  URL_DOMAIN_NAME_API + "/Shop/lists";
	public static final String COMMUNITY_LIST_URL =  URL_DOMAIN_NAME_API + "/User/groupList";
	public static final String COMMUNITY_ORDER_LIST_URL =  URL_DOMAIN_NAME_API + "/Shop/orderList";
	public static final String ORDER_DETAIL_URL =  URL_DOMAIN_NAME_API + "/Shop/orderDetail";
	
	public static final String ADD_ORDER_URL = URL_DOMAIN_NAME_API + "/Shop/submitOrder";
	public static final String CANCEL_ORDER_URL = URL_DOMAIN_NAME_API + "/Shop/cancelOrder";
	public static final String FINISH_ORDER_URL = URL_DOMAIN_NAME_API + "/Shop/findishOrder";
	 
	
	public static final String PAYMENT_URL = URL_DOMAIN_NAME_API + "/Pay/shopPay";
	public static final String GET_PAY_RESULT_URL = URL_DOMAIN_NAME_API + "/Pay/getPayStatus";
	
	public static final String PICTURE_URL = URL_DOMAIN_NAME + "/Public/Uploads/tongbu/gouwu/";
}
