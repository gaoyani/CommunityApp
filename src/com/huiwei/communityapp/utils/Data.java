package com.huiwei.communityapp.utils;

import java.util.ArrayList;
import java.util.List;

import com.huiwei.communityapp.info.CommunityInfo;
import com.huiwei.communityapp.info.GoodsInfo;
import com.huiwei.communityapp.info.MemberInfo;

public class Data {
	
	public static MemberInfo memberInfo = new MemberInfo();
	public static List<CommunityInfo> communityList = new ArrayList<CommunityInfo>();
	public static List<GoodsInfo> selGoodsList = new ArrayList<GoodsInfo>();
	
	public static GoodsInfo findSelGoods(String id) {
		for (GoodsInfo info : selGoodsList) {
			if (info.id.equals(id)) {
				return info;
			}
		}
		
		return null;
	}
	
	public static void clearSelGoods() {
		for (GoodsInfo info : selGoodsList) {
			info = null;
		}
		
		selGoodsList.clear();
	}
	
	public static CommunityInfo findCommunityInfo(String id) {
		for (CommunityInfo info : communityList) {
			if (info.id.equals(id)) {
				return info;
			}
		}
		
		return null;
	}
}
