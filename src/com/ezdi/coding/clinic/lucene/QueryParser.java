//package com.ezdi.coding.clinic.lucene;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.Iterator;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.queryParser.ParseException;
//import org.apache.lucene.queryParser.*;
//import org.apache.lucene.search.BooleanClause;
//import org.apache.lucene.search.BooleanQuery;
//import org.apache.lucene.search.*;
////import org.apache.lucene.search.Hits;
//import org.apache.lucene.search.function.*;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.store.LockObtainFailedException;
//import org.apache.lucene.util.Version;
//
//	public class QueryParser {
//
//		public static final String FILES_TO_INDEX_DIRECTORY = "filesToIndex";
//		public static final String INDEX_DIRECTORY = "indexDirectory";
//
//		public static final String FIELD_PATH = "path";
//		public static final String FIELD_CONTENTS = "contents";
//		private static final File INDEX_DIR = new File("");
//
//		public static void main(String[] args) throws Exception {
//
//			Directory directory = FSDirectory.open(INDEX_DIR);
//			IndexSearcher indexSearcher = new IndexSearcher(directory);
//
//			Query query1 = new TermQuery(new Term(FIELD_CONTENTS, "mushrooms"));
//			Query query2 = new TermQuery(new Term(FIELD_CONTENTS, "steak"));
//
//			BooleanQuery booleanQuery = new BooleanQuery();
//			booleanQuery.add(query1, BooleanClause.Occur.MUST);
//			booleanQuery.add(query2, BooleanClause.Occur.MUST);
//			displayQuery(booleanQuery);
//			Hits hits = indexSearcher.search(booleanQuery, null);
//			displayHits(hits);
//
//			booleanQuery = new BooleanQuery();
//			booleanQuery.add(query1, BooleanClause.Occur.MUST);
//			booleanQuery.add(query2, BooleanClause.Occur.MUST_NOT);
//			displayQuery(booleanQuery);
//			hits = indexSearcher.search(booleanQuery, 0);
//			displayHits(hits);
//
//			booleanQuery = new BooleanQuery();
//			booleanQuery.add(query1, BooleanClause.Occur.MUST);
//			booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
//			displayQuery(booleanQuery);
//			hits = indexSearcher.search(booleanQuery, 0);
//			displayHits(hits);
//
//			searchIndexWithQueryParser("+contents:mushrooms +contents:steak");
//			searchIndexWithQueryParser("mushrooms steak");
//			searchIndexWithQueryParser("bacon eggs");
//			searchIndexWithQueryParser("(mushrooms steak) OR (bacon eggs)");
//			searchIndexWithQueryParser("(mushrooms steak) AND (bacon eggs)");
//			searchIndexWithQueryParser("(mush*ms OR raspberries) AND (ste?k)");
//
//		}
//
//
//		public static void searchIndexWithQueryParser(String searchString) throws IOException, ParseException {
//			System.out.println("Searching for '" + searchString + "' using QueryParser");
//			Directory directory = FSDirectory.open(INDEX_DIR);
//			IndexSearcher indexSearcher = new IndexSearcher(directory);
//
//			QueryParser queryParser = new QueryParser();
//			Query query = queryParser.parse(searchString);
//			System.out.println("Type of query: " + query.getClass().getSimpleName());
//			displayQuery(query);
//			Hits hits = indexSearcher.search(query, 0);
//			displayHits(hits);
//		}
//
//		public static void displayHits(Hits hits) throws CorruptIndexException, IOException {
//			System.out.println("Number of hits: " + hits.length());
//
//			Iterator<Hit> it = hits.iterator();
//			while (it.hasNext()) {
//				Hit hit = it.next();
//				Document document = hit.getDocument();
//				String path = document.get(FIELD_PATH);
//				System.out.println("Hit: " + path);
//			}
//			System.out.println();
//		}
//
//		public static void displayQuery(Query query) {
//			System.out.println("Query: " + query.toString());
//		}
//	}
