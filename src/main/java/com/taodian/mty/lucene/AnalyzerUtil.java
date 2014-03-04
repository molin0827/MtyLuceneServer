package com.taodian.mty.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AnalyzerUtil {
	private static Analyzer analyzer;  
	  
    public static Analyzer getIkAnalyzer(boolean bool) {  
        if (analyzer == null) {  
            // ��Ϊtrueʱ���ִ���މ�����ʳ��з� ����Ϊfalseʱ���ִ���މ����ϸ������  
            analyzer = new IKAnalyzer(bool);  
        }  
        return analyzer;  
    } 
}
