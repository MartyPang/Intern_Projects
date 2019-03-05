package com.sinocontact.common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;

/**
 * 重写toMap方法，getColumnLabel可以获取数据库列名的别名
 * @author SinoContact
 *
 */
public class BasicRowProcessorFix extends BasicRowProcessor implements RowProcessor {
	@Override
    public Map<String, Object> toMap(ResultSet rs) throws SQLException {
        Map<String, Object> result = new CaseInsensitiveHashMap();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();
        for (int i = 1; i <= cols; i++) {
        	result.put(rsmd.getColumnLabel(i), rs.getObject(i));
        }
        return result;
    }
}
