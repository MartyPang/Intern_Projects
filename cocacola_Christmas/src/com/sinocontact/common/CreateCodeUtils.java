package com.sinocontact.common;

public class CreateCodeUtils {
	public static String createNumberCode(int num){
		String code="";
		for(int i=1;i<=num;i++){
			code+=radom(1,9);
		}
		return code;
	}
	public static int radom(int min,int max){
        return (int)(Math.random()*(max - min) + min);
    }
}
