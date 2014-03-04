package com.taodian.mty.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AnalyzerUtil {
	private static Analyzer analyzer;  
	  
    public static Analyzer getIkAnalyzer(boolean bool) {  
        if (analyzer == null) {  
            // 当为true时，分词器迚行最大词长切分 ；当为false时，分词器迚行最细粒度切  
            analyzer = new IKAnalyzer(bool);  
        }  
        return analyzer;  
    } 
}
