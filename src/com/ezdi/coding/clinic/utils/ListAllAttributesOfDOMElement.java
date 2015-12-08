package com.ezdi.coding.clinic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ListAllAttributesOfDOMElement {
//		public static void main(String[] args) throws Exception {
//			ListAllAttributesOfDOMElement list = new ListAllAttributesOfDOMElement();
//			getAttributeList("data/pcs_xml/QA1.txt.xml");
//		}

	public static String getAttributeList(String testing) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException 
	{
		String c_array = null, pcs_array = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();

		//		File test = new File("data/pcs_xml/");
		//		File [] folder = test.listFiles();
		//		for(File file : folder){
		//			System.out.println(file);
		Document doc = db.parse(new FileInputStream(new File(testing)));
		NodeList entries = doc.getElementsByTagName("problem");
		int num = entries.getLength();
		for (int i=0; i<num; i++) {
			Element node = (Element) entries.item(i);
						c_array = listAllAttributes(node);
			//				System.out.println(c_array);
			//			}
		}

		return c_array;
	}

	public static String listAllAttributes(Element element) {

		//System.out.println("List attributes for node: " + element.getNodeName());
		String [] cui_array = null;
		String attrValue = null;
		String noColon = null;

		// get a map containing the attributes of this node 
		NamedNodeMap attributes = element.getAttributes();

		// get the number of nodes in this map
		int numAttrs = attributes.getLength();

		for (int i = 0; i < numAttrs; i++) {
			Attr attr = (Attr) attributes.item(i);
			String attrName = attr.getNodeName();
			attrValue = attr.getNodeValue();
			String no = "NO-CUI";

			//			if(attrName=="cui"&&(!attrValue.contains(no)))
			//				System.out.println(attrName + " : "+ attrValue);

			if(attrName=="cui"&&(!attrValue.contains(no))){			
				noColon =  attrValue;
//				System.out.println(noColon);
			}
		}
		return noColon;
	}
}

