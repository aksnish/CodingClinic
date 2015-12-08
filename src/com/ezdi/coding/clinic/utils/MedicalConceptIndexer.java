package com.ezdi.coding.clinic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MedicalConceptIndexer {

	public static String getAttributeList(String testing) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException 
	{
		String pcs_array = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new FileInputStream(new File(testing)));
		doc.getDocumentElement();  
		NodeList allNodes = doc.getElementsByTagName("*"); 
		int num = allNodes.getLength();
		for (int i=0; i<num; i++) {
			Element node = (Element) allNodes.item(i);
			String parent = node.getParentNode().getNodeName();
			String tag = node.getTagName();
			if((parent.equals("Relation"))||(tag.equals("Relation"))||(!parent.equals("RelationSet")))
			{	
				continue;
			}
		}
		return pcs_array;
	}

	public static ArrayList<String> getMedicalConcepts(String filename) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException 
	{
		String attrValue = null, attrName = null;
		ArrayList<String> pcsCodeList = new ArrayList<String>();
		ArrayList<ArrayList<String>> testing = new ArrayList<ArrayList<String>>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new FileInputStream(new File(filename)));
		doc.getDocumentElement();  
		NodeList allNodes = doc.getElementsByTagName("*"); 
		int num = allNodes.getLength();
		for (int i=0; i<num; i++) {
			Element node = (Element) allNodes.item(i);
			String parent = node.getParentNode().getNodeName();
			String tag = node.getTagName();
			if((parent.equals("Relation"))||(tag.equals("Relation"))||(!parent.equals("RelationSet")))
			{	
				continue;
			}
			NamedNodeMap attributes = node.getAttributes();
			int numAttrs = attributes.getLength();
			for (int j = 0; j < numAttrs; j++) {
				Attr attr = (Attr) attributes.item(j);
				attrName = attr.getNodeName();
				attrValue = attr.getNodeValue();
				if(attrName=="value"){	
					pcsCodeList.add(attrValue.replaceAll("[^\\p{ASCII}]", " "));
				}
				testing.add(pcsCodeList);
			}
		}
		if(pcsCodeList.size()==0){
			pcsCodeList.add("");
		}
		return pcsCodeList;
	}
}


