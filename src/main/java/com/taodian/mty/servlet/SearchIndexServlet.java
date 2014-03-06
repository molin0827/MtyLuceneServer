package com.taodian.mty.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.taodian.mty.CheckAppAuth;
import com.taodian.mty.lucene.MtyLucene;

public class SearchIndexServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    	doPost(request, response);
		    }
		    
		    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    	System.out.print("  this is request: Search  ");
		    	String result= null;
				String indexDir =null;
				HashMap<String, Object> data =new HashMap<String, Object>();
				String shopId = request.getParameter("shop_id");
				String appName = request.getParameter("app_name");
				String uSignKey = request.getParameter("sign_key");
				
				if(CheckAppAuth.checkIn(shopId, appName, uSignKey)){
					String searchStr = request.getParameter("search_string");
					
					
					indexDir = "data/index/" + shopId + "/" + appName;
					MtyLucene ml = new MtyLucene();
					ml.setDirctory(indexDir);
					ml.setAnalyzer("IKAnalyzer");
					try {
						List<HashMap<String, String>> rs= ml.searchIndex(searchStr);
						data.put("data", rs);
						data.put("status", "ok");
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
				result = JSONObject.toJSONString(data);
				response.getWriter().print(result);
		    }
}
