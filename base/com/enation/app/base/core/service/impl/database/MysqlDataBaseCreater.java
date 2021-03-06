package com.enation.app.base.core.service.impl.database;

import com.enation.app.base.core.service.IDataBaseCreater;
import com.enation.framework.database.ISqlFileExecutor;

/**
 *  mysql数据库创建
 * @author kingapex
 *
 */
public class MysqlDataBaseCreater implements IDataBaseCreater {
	private  ISqlFileExecutor sqlFileExecutor;
	
	@Override
	public void create() {
		sqlFileExecutor.execute("file:com/enation/eop/eop_mysql.sql");
		sqlFileExecutor.execute("file:com/enation/app/shop/javashop_mysql.sql");
		sqlFileExecutor.execute("file:com/enation/app/cms/cms_mysql.sql");
	}
	public ISqlFileExecutor getSqlFileExecutor() {
		return sqlFileExecutor;
	}
	public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
		this.sqlFileExecutor = sqlFileExecutor;
	}

}
