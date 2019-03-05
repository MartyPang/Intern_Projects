package com.sinocontact.service;

import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import org.apache.log4j.Logger;

import com.sinocontact.util.PropertyUtils;


/**
 * 
 * @Description: 上传图片到本地 相关操作
 * @author: Alen Gu 
 * @CreateTime: 2016-7-14
 */
public class UploadImgService {
	private static final Logger logger = Logger.getLogger(UploadImgService.class);
	
	public static String upload(String access_token,String media_id,String imgname) {  
        String url = PropertyUtils.getProperty("fileAPIUrl")+access_token+"&media_id="+media_id;  
        //logger.info("第一步");
        byte[] btImg = getImageFromNetByUrl(url);  
        if(null != btImg && btImg.length > 0){  
        	logger.info("读取到：" + btImg.length + " 字节");  
            String fileName = imgname+".jpg";  
            return writeImageToDisk(btImg, fileName);  
        }else{  
        	logger.info("图片错误");  
        } 
        return null;
    }  
    /** 
     * 将图片写入到磁盘 
     * @param img 图片数据流 
     * @param fileName 文件保存时的名称 
     */  
    public static String writeImageToDisk(byte[] img, String fileName){ 
    	String name =PropertyUtils.getProperty("resource_upload_path")+fileName;
        try {  
            File file = new File(name);  
            FileOutputStream fops = new FileOutputStream(file);   
            fops.write(img);  
            fops.flush();  
            fops.close();  
            logger.info("图片上传成功");
            return name;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return null;  
    }  
    /** 
     * 根据地址获得数据的字节流 
     * @param strUrl 网络连接地址 
     * @return 
     */  
    public static byte[] getImageFromNetByUrl(String strUrl){  
        try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5 * 1000);  
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
            return btImg;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    /** 
     * 从输入流中获取数据 
     * @param inStream 输入流 
     * @return 
     * @throws Exception 
     */  
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }  

}
