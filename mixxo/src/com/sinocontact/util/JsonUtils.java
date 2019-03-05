package com.sinocontact.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class JsonUtils
{
	public static String map2Json(Map<String,Object> map)
	{
		JSONObject jsonObj = new JSONObject(map);
		return jsonObj.toString();
	}
    public static String list2Json(List<Map<String,String>> list){
    	JSONArray array=new JSONArray(list);
    	return array.toString();
    }
    public static String list2OJson(List<Map<String,Object>> list){
    	JSONArray array=new JSONArray(list);
    	return array.toString();
    }

	public static Map<String,Object> parseJSON2Map(String json) throws JSONException {
		Map<String,Object> reMap = new HashMap<String,Object>();
		JSONObject jsonObject ;
		JSONObject jTemp ;

		jsonObject = new JSONObject(json);
		String k ;
		@SuppressWarnings("unchecked")
		Iterator<String> keysIt = jsonObject.keys();
		while (keysIt.hasNext()){
			k = keysIt.next();
			Object v =  jsonObject.get(k);
			//如果内层还是数组的话，继续解析
			if(v instanceof JSONArray){
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				for(int i = 0;i< ((JSONArray) v).length();i++){
					jTemp = ((JSONArray) v).getJSONObject(i);
					list.add(parseJSON2Map(jTemp.toString()));
				}
				reMap.put(k, list);
			}else if(v instanceof JSONObject){
				reMap.put(k,parseJSON2Map(v.toString()));
			}
			else {
				reMap.put(k, v);
			}
		}

		return reMap;
	}
	public static List<Map<String,Object>> parseJSON2List(String json) throws JSONException {
		List<Map<String,Object>> reList = new ArrayList<Map<String, Object>>();

		JSONArray jsonArray = new JSONArray(json);

		for(int i = 0;i< jsonArray.length();i++){
			Object v = jsonArray.get(i);
			if (v instanceof JSONObject ){
				reList.add(parseJSON2Map(v.toString()));
			}
		}

		return reList;
	}
}
