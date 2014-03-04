package com.taodian.mty.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taodian.mty.CheckAppAuth;
import com.taodian.mty.lucene.MtyLucene;

public class CreateIndexServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
    	doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String msg = null;
		String indexDir =null;
		String absPath = null;
		if(CheckAppAuth.checkIn()){
			String param = request.getParameter("param");
			String shopId = request.getParameter("shop_id");
			String appName = request.getParameter("app_name");
			indexDir = "data/index/" + shopId + "/" + appName;
			MtyLucene ml = new MtyLucene();
			ml.setDirctory(indexDir);
			ml.setAnalyzer("IKAnalyzer");
			
			absPath = ml.getDataRoot().getAbsolutePath();
			try {
				boolean flag = ml.createIndex();
				if(flag){
					msg = "CreateIndex is OK";
				}else{
					msg = "CreateIndex is err";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		response.getWriter().print("this is MtyLuceneServer indexDir:" + absPath + "<br>");
		response.getWriter().print("this is MtyLuceneServer CreateIndex:" + msg);
    }
}
