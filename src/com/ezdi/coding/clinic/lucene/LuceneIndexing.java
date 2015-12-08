package com.ezdi.coding.clinic.lucene;

import com.aliasi.util.Files;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.xml.sax.SAXException;

import com.ezdi.coding.clinic.utils.*;
import com.ezdi.xml.parsers.XMLDOMParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

public class LuceneIndexing {

	public static void tempChange() throws CorruptIndexException, LockObtainFailedException, IOException, ParserConfigurationException, SAXException {
		BufferedReader buf;
		ArrayList<String> pcsCodeList = new ArrayList<String>();
		ArrayList<String> medicalConceptList = new ArrayList<String>();
		String line;
		File docDir = new File(Constants.DATA+Constants.TEXTFILE_INPUT_DIR);
		File indexDir = new File(Constants.DATA+Constants.LUCENE_INDEX_DIR);
		String [] cui_array = null, value_array= null;
		String xml_file;
		new XMLDOMParser();
		new MedicalConceptIndexer();

		Directory fsDir = FSDirectory.open(indexDir);
		Analyzer stdAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
		IndexWriterConfig iwConf = new IndexWriterConfig(Version.LUCENE_36,stdAnalyzer);
		iwConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter indexWriter = new IndexWriter(fsDir,iwConf);
		String icdPcsCode = null;
		HashSet<String> icdPcsCodes = readFlatFile();
		String indexICDCode = null ; String [] questions = null;

		for (File f : docDir.listFiles()) {
			String fileName = f.getName();
			String text = Files.readFromFile(f,"ASCII").replaceAll("[^\\p{ASCII}]", " ");
			if(text.contains("Answer")){
				questions = text.split("Answer");
//				System.out.println(questions[0]);
			}
			buf = new BufferedReader(new FileReader(new File(Constants.DATA+Constants.TEXTFILE_INPUT_DIR+fileName)));
			xml_file = Constants.DATA+Constants.XML_FILE_DIR+f.getName()+Constants.XML_EXTENSION;
			pcsCodeList = XMLDOMParser.getCUI(xml_file);
			medicalConceptList = MedicalConceptIndexer.getMedicalConcepts(xml_file);



			while((line=buf.readLine())!=null){
				Pattern p = Pattern.compile("[0-9A-Z]{7}");
				Matcher m = p.matcher(line);
				if (m.find()){
					icdPcsCode = m.group();
					for(String code : icdPcsCodes){
						if(code.contains(icdPcsCode)){
							indexICDCode = icdPcsCode;
						}
					}
					questions = line.split("\n");
					String question = questions[0];
				}
			}
			Document d = new Document();
			d.add(new Field("file",fileName, Store.YES,Index.NOT_ANALYZED));
			d.add(new Field("icdCode",indexICDCode, Store.YES,Index.NOT_ANALYZED));
			d.add(new Field("text",text, Store.YES,Index.ANALYZED));
			Field question = new Field("question", questions[0], Store.YES, Index.ANALYZED);
//			question.setBoost(0.8f);
			d.add(question);
			//			for(String s : pcsCodeList){
			//				if(s.contains(";")||s.contains(",")){
			//					cui_array = s.split("[;,]");
			//					d.add(new Field("CUI",cui_array[0], Store.YES,Index.ANALYZED));
			//				}
			//				else
			//				{
			//					d.add(new Field("CUI",s, Store.YES,Index.ANALYZED));
			//				}
			//			}
			for(String s : medicalConceptList){
				if(s.contains(";")||s.contains(",")){
					value_array = s.split("[;,]");
					d.add(new Field("medicalConcept",value_array[0].replaceAll("[^\\p{ASCII}]", " "), Store.YES,Index.ANALYZED));
				}
				else
				{
					d.add(new Field("medicalConcept",s, Store.YES,Index.ANALYZED));
				}
			}
						indexWriter.addDocument(d);
		}
				int numDocs = indexWriter.numDocs();
				indexWriter.forceMerge(1);
				indexWriter.commit();
				indexWriter.close();
				System.out.println("Index Directory=" + indexDir.getCanonicalPath());
				System.out.println("Doc Directory=" + docDir.getCanonicalPath());
				System.out.println("num docs=" + numDocs);

	}

	@SuppressWarnings("resource")
	public static void singleFileIndexing(String fileName) throws CorruptIndexException, LockObtainFailedException, IOException, ParserConfigurationException, SAXException {

		File indexDir = new File("data/lucene/test/");
		Directory fsDir = FSDirectory.open(indexDir);
		Analyzer stdAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
		IndexWriterConfig iwConf = new IndexWriterConfig(Version.LUCENE_36,stdAnalyzer);
		iwConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter indexWriter = new IndexWriter(fsDir,iwConf);

		BufferedReader buf;
		String line;
		String xml_file;
		ArrayList<String> pcsCodeList = new ArrayList<String>();
		ArrayList<String> medicalConceptList = new ArrayList<String>();
		String icdPcsCode = null; String [] questions;
		HashSet<String> icdPcsCodes = readFlatFile();

		File file = new File(fileName);
		String text = Files.readFromFile(file,"ASCII");
		questions = text.split("\n");
		System.out.println(questions[0]);
		buf = new BufferedReader(new FileReader(new File(fileName)));
		xml_file = Constants.DATA+Constants.XML_FILE_DIR+file.getName()+Constants.XML_EXTENSION;
		System.out.println(xml_file);
		pcsCodeList = XMLDOMParser.getCUI(xml_file);
		medicalConceptList = MedicalConceptIndexer.getMedicalConcepts(xml_file);

		while((line=buf.readLine())!=null){
			Pattern p = Pattern.compile("[0-9A-Z]{7}");
			Matcher m = p.matcher(line);
			if (m.find()){
				icdPcsCode = m.group();
				for(String code : icdPcsCodes){
					if(code.contains(icdPcsCode)){
					}
				}
				if(line.contains("Question:")){
					//				String question = line;
					//				System.out.println(question);
					System.out.println("yes");
				}
			}
		}
		Document d = new Document();
		d.add(new Field("file",fileName, Store.YES,Index.NOT_ANALYZED));
		//d.add(new Field("CUI",pcsCodeList.get(0), Store.YES,Index.ANALYZED));
		d.add(new Field("medicalConcept",medicalConceptList.get(0), Store.YES,Index.ANALYZED));
		indexWriter.addDocument(d);
		indexWriter.numDocs();
		indexWriter.forceMerge(1);
		indexWriter.commit();
		indexWriter.close();
	}

	@SuppressWarnings("resource")
	public static HashSet<String> readFlatFile () throws IOException {
		HashSet<String> icdPcsCodes =  new HashSet<String>();
		BufferedReader fileReaderfileReader;
		File[] dir = new File(Constants.DATA+Constants.ICD_10_DIR).listFiles();
		String line;
		String [] split;
		for(File f : dir){
			fileReaderfileReader = new BufferedReader(new FileReader(new File(f.toString())));
			while((line=fileReaderfileReader.readLine())!=null){
				split = line.split("\t");
				icdPcsCodes.add(split[0]);
			}
		}
		return icdPcsCodes;
	}
}