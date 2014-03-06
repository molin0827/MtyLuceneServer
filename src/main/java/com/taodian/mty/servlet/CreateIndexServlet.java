package com.taodian.mty.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.mortbay.util.ajax.JSONDateConvertor;
import org.mortbay.util.ajax.JSONObjectConvertor;

import com.taodian.mty.CheckAppAuth;
import com.taodian.mty.lucene.MtyLucene;

public class CreateIndexServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
    	doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		System.out.print("  this is request: createIndex  ");
    	String indexDir =null;
		String absPath = null;
		String DEBUG = null;
		String result = null;
		
		HashMap<String, Object> data =new HashMap<String, Object>();
		String shopId = request.getParameter("shop_id");
		String appName = request.getParameter("app_name");
		String uSignKey = request.getParameter("sign_key");
		
		if(CheckAppAuth.checkIn(shopId, appName, uSignKey)){
			
			try {
				indexDir = "data/index/" + shopId + "/" + appName;
				String id = request.getParameter("id");
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				
				if(!(id == null ||title == null || content == null)){
					
					MtyLucene ml = new MtyLucene();
					ml.setDirctory(indexDir);
					ml.setAnalyzer("IKAnalyzer");
					absPath = ml.getDataRoot().getAbsolutePath();
					boolean flag = false;
					flag= ml.createIndex(id, title, content);
					if(flag){
						data.put("status", "ok");
						
					}else{
						data.put("status", "err");
					}

				}else{
					data.put("status", "err");
					data.put("msg", "Parameter is missing");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			data.put("status", "err");
			data.put("errmsg", "sign faild");
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		
		DEBUG = request.getParameter("debug");
		if(DEBUG != null && DEBUG.equals("true")){
			response.getWriter().print("this is MtyLuceneServer indexDir:" + absPath);
		}
		result = JSONObject.toJSONString(data);
		response.getWriter().print(result);
    }
}
