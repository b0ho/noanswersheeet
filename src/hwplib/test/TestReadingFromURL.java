package hwplib.test;

import hwplib.object.HWPFile;
import hwplib.reader.HWPReader;

public class TestReadingFromURL {
	public static void main(String[] args) throws Exception {
		test("http://ocwork.haansoft.com/sample/sample.hwp");
	}
	
	private static void test(String url) throws Exception {
		HWPFile hwpFile = HWPReader.fromURL(url);
		if (hwpFile.getBodyText().getSectionList().size() > 0){
			System.out.println(url + "  읽기 성공 !!");
		}
	}

}
