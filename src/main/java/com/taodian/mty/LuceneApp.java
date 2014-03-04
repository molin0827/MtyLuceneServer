package com.taodian.mty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHandler;

import com.taodian.mty.servlet.CreateIndexServlet;
import com.taodian.mty.servlet.IndexServlet;
import com.taodian.mty.servlet.SearchIndexServlet;




/**
 * Hello world!
 *
 */
public class LuceneApp {
	public static void main( String[] args ) throws Exception
    {
        System.out.println( "server start!" );
        int port = 8081;
        Server server = new Server(port);
        ServletHandler context = new ServletHandler();
        server.setHandler(context);
        context.addServletWithMapping(SearchIndexServlet.class, "/search");
        context.addServletWithMapping(CreateIndexServlet.class, "/createIndex");
        context.addServletWithMapping(IndexServlet.class, "/*");
        
        server.start();
        server.join();
    }
}
