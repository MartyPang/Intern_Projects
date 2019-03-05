package com.sinocontact.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

public class BaseDao {

	private static final Logger logger = Logger.getLogger(BaseDao.class);
	private QueryRunner queryRunner = null;
	protected QueryRunner getQueryRunner(){
		if (null == queryRunner) {
			queryRunner = new QueryRunner();
		}		
		return queryRunner;
	}	
	
}
