package com.enation.app.shop.component.bonus.tag;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.component.bonus.model.MemberBonus;
import com.enation.app.shop.component.bonus.service.BonusSession;
import com.enation.app.shop.component.bonus.service.IBonusManager;
import com.enation.app.shop.core.service.ICartManager;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import freemarker.template.TemplateModelException;

/**
 * 会员红包列表标签
 * @author kingapex
 *2013-9-18下午10:20:40
 */
@Component
@Scope("prototype")
public class MemberBonusListTag extends BaseFreeMarkerTag {
	
	private IBonusManager bonusManager;
	private ICartManager cartManager;
	/**
	 * 获取会员可用红包列表
	 * @param 无
	 * @return 红包列表，List<Map>型
	 * map内容
	 * type_name:红包名称
	 * type_money:红包金额
	 * send_type：红包类型 (0会员发放，1:按商品发放,2:按订单发放,3:线下发放的红包)
	 * used:是否已使用，0否，1是
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		try {
			Integer type= (Integer) params.get("type");
			Member member  =UserServiceFactory.getUserService().getCurrentMember();
			if(member ==null){
				return ("未登陆，不能使用此api");
			}
			Double goodsprice = cartManager.countGoodsTotal(this.getRequest().getSession().getId());
			if(type==1){
				goodsprice=99999999.0;
			}
			List<Map> bonusList  = bonusManager.getMemberBonusList(member.getMember_id(), goodsprice,type);
			MemberBonus useBonus = BonusSession.getOne();
			if(useBonus!=null){
				for (Map bonus : bonusList) {
					Integer bonusid= (Integer)bonus.get("bonus_id");
					if(bonusid== useBonus.getBonus_id()){//已经使用
						bonus.put("used",1);
					}else{
						bonus.put("used",0);
					}
				}
			}
			
			return  bonusList;
		} catch (Exception e) {
			throw new 	TemplateModelException(e);	 
		}
	}

	public IBonusManager getBonusManager() {
		return bonusManager;
	}

	public void setBonusManager(IBonusManager bonusManager) {
		this.bonusManager = bonusManager;
	}

	public ICartManager getCartManager() {
		return cartManager;
	}

	public void setCartManager(ICartManager cartManager) {
		this.cartManager = cartManager;
	}
	
}
