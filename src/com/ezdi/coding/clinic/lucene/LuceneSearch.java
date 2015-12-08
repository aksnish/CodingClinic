package com.ezdi.coding.clinic.lucene;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LuceneSearch {

	/*x LuceneSearch.1 */
	public static ArrayList<String> displayResults (String primary, String secondary)
			throws ParseException, CorruptIndexException,
			IOException {

		File indexDir = new File("data/lucene-index/ezdi/");
		//        String query = "heart AND pneumonia";
		int maxHits = Integer.parseInt("10");
		ArrayList<String> display = new ArrayList<>();
		Query firstHalf;
		String [] splitText;
		/*x*/        

		String dField = "question";

		Analyzer stdAn 
		= new StandardAnalyzer(Version.LUCENE_36);
		QueryParser parser 
		= new QueryParser(Version.LUCENE_36,dField,stdAn);

		if(secondary==null){
			firstHalf = parser.parse(primary);

		}
		else
		{
			firstHalf = MultiFieldQueryParser.parse(Version.LUCENE_33,
					new String[]{primary, secondary},
					new String[]{"question", "question"},
					new BooleanClause.Occur[]{BooleanClause.Occur.MUST, BooleanClause.Occur.MUST},
					stdAn);

		}
		//		System.out.println("Index Dir=" + indexDir.getCanonicalPath());
		//		System.out.println("query=" + firstHalf);
		//		System.out.println("max hits=" + maxHits);
		//		System.out.println("Hits (rank,score,file name, text)");

		/*x LuceneSearch.2 */
		Directory fsDir = FSDirectory.open(indexDir);
		IndexReader reader = IndexReader.open(fsDir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		
		searcher = new IndexSearcher(IndexReader.open(fsDir));
		TopScoreDocCollector collector = TopScoreDocCollector.create(maxHits, true);
		searcher.search(firstHalf, collector);
		ScoreDoc [] score_array = collector.topDocs().scoreDocs;
		for(int i =0 ; i<score_array.length; i++){
			int docID = score_array[i].doc;
			Document d = searcher.doc(docID);
			String id = d.get(dField);
			Explanation explain = searcher.explain(firstHalf, docID);
		
		
			System.out.println("docID  :"+docID + "\ndoc : " + d + "\nexplain : " + explain);
		}
		
		/*x LuceneSearch.3 */
		//        Query q = parser.parse(query).combine(arg0);

		TopDocs hits = searcher.search(firstHalf,maxHits);
		ScoreDoc[] scoreDocs = hits.scoreDocs;

		display.add(String.format("query=" + firstHalf)+"\n");
		display.add(String.format("max hits=" + maxHits)+"\n");
		display.add(String.format("Hits (rank,score,file name, text)\n"));
		for (int n = 0; n < scoreDocs.length; ++n) {
			ScoreDoc sd = scoreDocs[n];
			float score = sd.score;
			int docId = sd.doc;
			Document d = searcher.doc(docId);
			String fileName = d.get("file");
			String text = d.get(dField).toString().replaceAll("[^\\x00-\\x7f]", " ");
			/*x*/
			//			System.out.printf("%3d %4.2f  %s \n%s\n",n, score, fileName, text);
			display.add(String.format("%3d \t%4.2f  \t%s\n",n, score, fileName));
			
			splitText = text.split("\n");
			for (String s : splitText){
				display.add(s);
			}
//			display.add(String.format("%s\n",text));
			
		}
		reader.close();
		return display;
	}
	public static void main(String[] args) throws CorruptIndexException, ParseException, IOException {
		LuceneSearch lus = new LuceneSearch();
		ArrayList<String> test = new ArrayList<>();
		test = lus.displayResults("pulmonary hypertension",null);
		for(String str : test){
			System.out.print(str);
		}

	}

}