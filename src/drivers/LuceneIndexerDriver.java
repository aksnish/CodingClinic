package drivers;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import com.ezdi.coding.clinic.lucene.LuceneIndexing;

public class LuceneIndexerDriver {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		LuceneIndexing.tempChange();
//		LuceneIndexing.singleFileIndexing("data/TXT/QA47.txt");
	}
}
