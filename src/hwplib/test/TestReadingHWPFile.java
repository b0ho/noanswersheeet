package hwplib.test;

import hwplib.object.HWPFile;
import hwplib.reader.HWPReader;
import hwplib.writer.HWPWriter;

public class TestReadingHWPFile {
	public static void main(String[] args) throws Exception {
		test("hwp_origin\\문제1회.hwp");
		
		HWPFile hwpFile = new HWPFile();
		HWPWriter.toFile(hwpFile, "ss.hwp");
	}

	private static void test(String filename) throws Exception {
		HWPFile hwpFile = HWPReader.fromFile(filename);
		if (hwpFile.getBodyText().getSectionList().size() > 0){
			System.out.println(filename + "  읽기 성공 !!");
		}
	}
}
