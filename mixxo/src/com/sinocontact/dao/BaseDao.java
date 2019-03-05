package com.sinocontact.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

/**
 * 
 * @Description: 处理dao操作的基类
 * @CreateTime: 2016-7-18 上午9:40:03
 * @author: Martin Pang
 * @version V1.0
 */
public class BaseDao {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BaseDao.class);
	private QueryRunner queryRunner = null;
	protected QueryRunner getQueryRunner(){
		if (null == queryRunner) {
			queryRunner = new QueryRunner();
		}		
		return queryRunner;
	}	
	
}