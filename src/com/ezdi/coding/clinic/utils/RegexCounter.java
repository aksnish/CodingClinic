package com.ezdi.coding.clinic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCounter {
	public static void main(String[] args) throws IOException {
		BufferedReader buf = new BufferedReader(new FileReader(new File("data/pcs_xml/QA37.txt.xml")));
		String line,icdPcsCode;
		int i =0;
		while((line=buf.readLine())!=null){
			Pattern p = Pattern.compile("[0-9A-Z]{7}");
			Matcher m = p.matcher(line);
			if (m.find()){
				
			 icdPcsCode = m.group();
			 if((icdPcsCode.equals("SOURCET")))
					continue;
			 System.out.println(icdPcsCode);
			 i++;
			}
			
		}
		System.out.println("Total : "+ i );
	}
}
