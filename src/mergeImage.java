//표지와 이미지를 합쳐 새로운 한글 문서를 생성

import hwplib.object.HWPFile;
import hwplib.reader.HWPReader;
import hwplib.writer.HWPWriter;

public class mergeImage {
	public static void main(String[] args) throws Exception {
		String filename = "hwp_origin\\표지.hwp";
		HWPFile hwpFile = HWPReader.fromFile(filename);
		//HWPFile hwpFileNew = new HWPFile();
		//HWPWriter.toFile(hwpFileNew, filename);
		
		
		if (hwpFile != null) {
			insertImageSet tii = new insertImageSet();
			tii.insertShapeWithImage(hwpFile);

			makeTable tmt = new makeTable();
			tmt.makeTable(hwpFile, "정서영");
			
			
			
			
			String writePath = "hwp_new\\오답노트2.hwp";
			HWPWriter.toFile(hwpFile, writePath);
		}
		
		
		
	}
}
