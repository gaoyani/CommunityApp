package com.huiwei.communityapp.info;

import java.util.ArrayList;
import java.util.List;

public class TZTGTypeInfo {
	public String id;
	public String name;
	
	public List<TZTGInfo> tztgList = new ArrayList<TZTGInfo>();
	
	public boolean isListVisible = false;
	public boolean isLoadingList = false;
}
