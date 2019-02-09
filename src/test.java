//한글 문서를 열고 이미지를 추가

import hwplib.object.HWPFile;
import hwplib.reader.HWPReader;
import hwplib.writer.HWPWriter;

public class test {
	public static void main(String[] args) throws Exception {
		String filename = "hwp_new\\오답노트2.hwp";
		HWPFile hwpFile = HWPReader.fromFile(filename);
		//HWPFile hwpFileNew = new HWPFile();
		//ssss

		//HWPWriter.toFile(hwpFileNew, filename);
		
		
		if (hwpFile != null) {
			
			
			insertImageSet tii = new insertImageSet();
			tii.insertShapeWithImage(hwpFile);

			
			
			String writePath = "hwp_new\\오답노트3.hwp";
			HWPWriter.toFile(hwpFile, writePath);
		}
		
		
		
	}
}