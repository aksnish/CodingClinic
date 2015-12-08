package com.ezdi.xml.parsers;

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
/**
 * Use DOM Parser to display all tags in the XML document
 */
public class XMLDOMParser {

	public static ArrayList<String> parseXML(String testing) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException 
	{
		ArrayList<String> pcsCodeList = new ArrayList<String>();
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
		return pcsCodeList;
	}

	public static ArrayList<String> getCUI(String filename) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException 
	{
		ArrayList<String> pcs_array = new ArrayList<String>();
		String attrValue = null, attrName = null;
		new ArrayList<String>();
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
				String no = "NO-CUI";
				if(attrName=="cui"&&(!attrValue.contains(no))){	
					pcs_array.add(attrValue);
				}
			}
		}
		if(pcs_array.size()==0){
			pcs_array.add("");
		}		
		return pcs_array;
	}
}