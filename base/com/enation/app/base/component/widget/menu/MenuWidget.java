package com.enation.app.base.component.widget.menu;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.enation.app.base.core.service.ISiteMenuManager;
import com.enation.eop.sdk.widget.AbstractWidget;
import com.enation.framework.util.StringUtil;

/**
 * 菜单挂件
 * @author kingapex
 * 2010-1-30下午07:03:14
 */
@Component("site_menu")
@Scope("prototype")
public class MenuWidget  extends AbstractWidget{
	private ISiteMenuManager siteMenuManager;
	
	@Override
	protected void display(Map<String, String> params) {
		List menuList  =siteMenuManager.list(0);
		this.putData("menuList", menuList);
		
		String isDropDown =  params.get("isDropDown");
		isDropDown =StringUtil.isEmpty(isDropDown)?"off":"on";
		this.putData("isDropDown", isDropDown);
	}
	
	@Override
	protected void config(Map<String, String> params) {

		 this.setPageName("menu_config");	
	}
	public ISiteMenuManager getSiteMenuManager() {
		return siteMenuManager;
	}
	public void setSiteMenuManager(ISiteMenuManager siteMenuManager) {
		this.siteMenuManager = siteMenuManager;
	}
	
}
