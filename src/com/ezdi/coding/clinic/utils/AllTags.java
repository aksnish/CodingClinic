package com.ezdi.coding.clinic.utils;

import java.io.ByteArrayInputStream;
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


public class AllTags {

	public static void main(String[] args) throws Exception {

		//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		//		dbf.setValidating(false);
		//		DocumentBuilder db = dbf.newDocumentBuilder();
		//		
		//		Document doc = db.parse(new FileInputStream(new File("data/pcs_xml/QA1.txt.xml")));
		//		
		//		NodeList entries = doc.getElementsByTagName("RelationSet");
		//		
		//		int num = entries.getLength();
		//		Element nodeOne = null;
		//		
		//		for (int i=0; i<num; i++) {
		//			Element node = (Element) entries.item(i);
		//			nodeOne = (Element) entries.item(i).getChildNodes();
		//			listAllAttributes(node);
		//		}
		//		System.out.println("Node List : "+nodeOne);


		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(new FileInputStream(new File("data/pcs_xml/QA1.txt.xml")));
		NodeList collected_objects = doc.getElementsByTagName("Sentence");
		System.out.println("Number of collected objects are "
				+ collected_objects.getLength());

		for (int i = 0; i < collected_objects.getLength(); i++) {

			Node aNode = collected_objects.item(i);
			// get children of "objects"
			NodeList refNodes = aNode.getChildNodes();

			System.out.println("# of chidren are " + refNodes.getLength());

			//
			for (int x = 0; x < refNodes.getLength(); x++) {
				Node n = refNodes.item(x);
				System.out.println(n.getNodeType() + " = " + n.getNodeName() + "/" + n.getNodeValue());


				System.out.println("Test the nesting : ");
				NodeList testNodes = n.getChildNodes();
				for (int j = 0; j < testNodes.getLength(); j++) {
					Node tryNode = testNodes.item(j);
					System.out.println(tryNode.getNodeType() + " = " + tryNode.getNodeName() + "/" + tryNode.getNodeValue());  

				}
			}

			// print attributes of "objects"

			NamedNodeMap attributes = aNode.getAttributes();
			for (int a = 0; a < attributes.getLength(); a++) {
				Node theAttribute = attributes.item(a);
				System.out.println(theAttribute.getNodeName() + "="
						+ theAttribute.getNodeValue());

			}

		}

	}

	public static void listAllAttributes(Element element) {

		System.out.println("List attributes for node: " + element.getNodeName());

		// get a map containing the attributes of this node 
		NamedNodeMap attributes = element.getAttributes();

		// get the number of nodes in this map
		int numAttrs = attributes.getLength();

		for (int i = 0; i < numAttrs; i++) {
			Attr attr = (Attr) attributes.item(i);

			String attrName = attr.getNodeName();
			String attrValue = attr.getNodeValue();

			System.out.println("Found attribute: " + attrName + " with value: " + attrValue);

		}
	}

}