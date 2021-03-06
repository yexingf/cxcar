package com.enation.app.shop.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.shop.core.service.IProductStoreManager;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.IntegerMapper;

/**
 * 产品库存管理
 * @author kingapex
 *2014-1-1下午4:29:41
 */
public class ProductStoreManager implements IProductStoreManager {
	private IDaoSupport daoSupport;
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void decreaseEnable(int goodsid, int productid, int depotid,int num) {
		
		this.daoSupport.execute(" update es_product_store set enable_store = enable_store - ? where depotid = ? and productid = ? ", num, depotid, productid);
		this.daoSupport.execute(" update es_product set enable_store = enable_store - ? where product_id = ? ", num, productid);
		this.daoSupport.execute(" update es_goods set enable_store = enable_store - ? where goods_id = ? ", num, goodsid);

		//int enable_store = this.daoSupport.queryForInt(" select enable_store from es_goods where goods_id = ? ", goodsid);
		//if(enable_store == 0){
			//this.daoSupport.execute("update es_goods set market_enable=? where goods_id=?", 0, goodsid);
		//}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void increaseEnable(int goodsid, int productid, int depotid,int num) {
		
		if(this.checkExists(goodsid, depotid)){
			this.daoSupport.execute("update es_product_store set enable_store =enable_store+? where goodsid=? and depotid=?", num,goodsid,depotid);
		}else{
			this.daoSupport.execute("insert into es_product_store(goodsid,productid,depotid,enable_store)values(?,?,?,?)",goodsid,productid, depotid,num);

		}
		
		this.daoSupport.execute("update es_product set enable_store=enable_store+? where product_id=?", num,productid);
		this.daoSupport.execute("update es_goods set enable_store=enable_store+? where goods_id=?", num,goodsid);
		
		this.daoSupport.execute("update es_goods set market_enable=? where goods_id=?", 1,goodsid);//自动上架
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void decreaseStroe(int goodsid, int productid, int depotid,int num) {
		this.daoSupport.execute("update es_product_store set store=store-? where depotid=? and productid=?", num,depotid,productid);
		this.daoSupport.execute("update es_product set store=store-? where product_id=?", num,productid);
		this.daoSupport.execute("update es_goods set store=store-? where goods_id=?", num,goodsid);
		
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void increaseStroe(int goodsid, int productid, int depotid,int num) {
		
		if(this.checkExists(goodsid, depotid)){
			this.daoSupport.execute("update es_product_store set enable_store=enable_store+?,store =store+? where goodsid=? and depotid=?", num,num,goodsid,depotid);
		}else{
			this.daoSupport.execute("insert into es_product_store(goodsid,productid,depotid,store,enable_store)values(?,?,?,?,?)",goodsid,productid, depotid,num,num);

		}
		
		this.daoSupport.execute("update es_product set enable_store=enable_store+?, store=store+?  where product_id=?", num, num,productid);
		this.daoSupport.execute("update es_goods set enable_store=enable_store+?,store=store+?  where goods_id=?", num, num,goodsid);
		this.daoSupport.execute("update es_goods set market_enable=? where goods_id=?", 1,goodsid);//自动上架
		
	}


	/**
	 * 查询某个仓库的商品库存是否存在
	 * @param goodsid
	 * @param depotid
	 * @return
	 */
	private boolean checkExists(int goodsid,int depotid){
		int count = this.daoSupport.queryForInt("select count(0) from es_product_store where goodsid=? and depotid=?", goodsid,depotid) ;
		return count==0?false:true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.core.service.IProductStoreManager#getStroe(int, int)
	 */
	@Override
	public int getEnableStroe(int goodsid, int depotid) {
		String sql ="select enable_store from es_product_store where goodsid=? and depotid=?";
		List<Integer> storeList  = this.daoSupport.queryForList(sql, new IntegerMapper(),goodsid,depotid);
		int store=0;
		if(!storeList.isEmpty()){
			store=storeList.get(0);
		}
		return store;
	}

	
	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	
 

}
