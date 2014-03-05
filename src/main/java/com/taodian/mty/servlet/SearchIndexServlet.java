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

import com.taodian.mty.CheckAppAuth;
import com.taodian.mty.lucene.MtyLucene;

public class SearchIndexServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    	doPost(request, response);
		    }
		    
		    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				String result= null;
				String indexDir =null;
				if(CheckAppAuth.checkIn()){
					String searchStr = request.getParameter("search_string");
					String shopId = request.getParameter("shop_id");
					String appName = request.getParameter("app_name");
					
					indexDir = "data/index/" + shopId + "/" + appName;
					MtyLucene ml = new MtyLucene();
					ml.setDirctory(indexDir);
					ml.setAnalyzer("IKAnalyzer");
					try {
						List<HashMap<String, String>> data= ml.searchIndex(searchStr);
						result = JSONArray.toJSONString(data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				response.setStatus(HttpServletResponse.SC_OK);
				response.setCharacterEncoding("utf8");
				response.setContentType("text/plain");
				response.getWriter().print(result);
		    }
}
