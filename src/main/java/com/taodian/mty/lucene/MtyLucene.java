package com.taodian.mty.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MergeScheduler;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.simple.JSONArray;
import org.wltea.analyzer.lucene.IKSimilarity;


public class MtyLucene {
	private IndexWriter iw;
	private Directory fsDir;
	private Analyzer analyzer;
	
	public void setDirctory(String path) throws IOException{
		fsDir = FSDirectory.open(new File(path));
	}
	
	public void setAnalyzer(String Analyzer_name){
		if(Analyzer_name.equals("IKAnalyzer")){
			analyzer = AnalyzerUtil.getIkAnalyzer(true);
		}
	}

	public boolean createIndex() throws Exception{
    	IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_33, analyzer);
    	
    	conf.setMergeScheduler(optimizeIndex());
    	
  	
    	iw = new IndexWriter(fsDir , conf);
    	//
    	iw.addDocument(addDocument(1, "聚资库", "聚资库，是以分享知识与经验的学习交流平台，加入聚资库，学习新知识、结交新朋友、塑造个人形象。"));
    	iw.addDocument(addDocument(2, "资料", "在上面一部分经验中，我们已经为一个目录下的文本文档建立好了索引。"));
    	iw.addDocument(addDocument(3, "微知识", "这个类的目的是把用户输入的查询字符串封装成 Lucene 能够识别的 Query。"));
    	iw.close();
    	
    	System.out.println( "this is createIndex ok" );
    	return true;
    }
	
	public List<HashMap<String, String>> searchIndex(String where) throws Exception{
		IndexSearcher isearcher = null;
		Directory fsDir = null;
		IndexReader ird =null;
		List<HashMap<String, String>> data =new ArrayList<HashMap<String, String>>();
		//常见一个地址
		//fsDir = FSDirectory.open(indexdir);
		//常见一个读取器
		ird = IndexReader.open(fsDir, true);
		isearcher = new IndexSearcher(ird);
		//在索引器中使用IKSimilarity相似度评估器
		isearcher.setSimilarity( new IKSimilarity());
		String[] field = {"title", "content"};
		
		BooleanClause.Occur[] Occures  = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
		Query query = MultiFieldQueryParser.parse(Version.LUCENE_33, where, field, Occures, analyzer);
		TopDocs td = isearcher.search(query, 20);
		ScoreDoc[] sd = td.scoreDocs;
		
		for(ScoreDoc s:sd){
			Document doc = isearcher.doc(s.doc);
			String id = doc.get("id");
			String title = doc.get("title");
			String content = doc.get("content");
			
			HashMap<String, String> mp = new HashMap<String, String>();
			mp.put("id", id);
			mp.put("title",title);
			mp.put("content", content);
			data.add(mp);
		}
		isearcher.close();
		
		return data;
	}

    public Document addDocument(Integer id, String title, String content) {

        Document doc = new Document();

        //Field.Index.NO 表示不索引

        //Field.Index.ANALYZED 表示分词且索引
        //Field.Index.NOT_ANALYZED 表示不分词且索引
        doc.add(new Field("id", String.valueOf(id), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED));
         
        return doc;
    }


	private MergeScheduler optimizeIndex() {
		// TODO Auto-generated method stub
		return null;
	}
}
