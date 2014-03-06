package com.taodian.mty;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class CheckAppAuth {
	public static boolean  checkIn(String shopId, String appName, String uSignKey){
		boolean flag = false;
		String appSecret = "efca4eb89432d23696458ad132d43fc8";
		
		String signKey = shopId + "," + appName + "," + appSecret;
		
		String signStr = MD5(signKey);
		
		if(signStr.equals(uSignKey)){
			flag = true;
		}else{
			System.out.printf("  signKey=%s  ", signKey);
			System.out.printf("  signStr=%s   uSignkey=%s", signStr, uSignKey);
		}
		return flag;
	}
	
	public static String MD5(String str)  
    {  
        MessageDigest md5 = null;  
        try  
        {
            md5 = MessageDigest.getInstance("MD5"); 
        }catch(Exception e)  
        {  
            e.printStackTrace();  
            return "";  
        }  
          
        byte[] byteArray = null;
		try {
			byteArray = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}  
        
        byte[] md5Bytes = md5.digest(byteArray);  
          
        StringBuffer hexValue = new StringBuffer();  
        for( int i = 0; i < md5Bytes.length; i++)  
        {  
            int val = ((int)md5Bytes[i])&0xff;  
            if(val < 16)  
            {  
                hexValue.append("0");  
            }  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    } 
}
