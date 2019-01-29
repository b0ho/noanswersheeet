package hwplib.test;

import hwplib.object.HWPFile;
import hwplib.object.bodytext.ParagraphListInterface;
import hwplib.object.bodytext.paragraph.Paragraph;
import hwplib.reader.HWPReader;
import hwplib.tool.paragraphadder.ParagraphAdder;
import hwplib.writer.HWPWriter;

public class TestParagraphAdder {
	public static void main(String[] args) throws Exception {
		HWPFile sourceHWPFile = HWPReader.fromFile("hwp_origin\\문제1회.hwp");
		HWPFile targetHWPFile = HWPReader.fromFile("hwp_new\\오답노트2.hwp");
		
		if (sourceHWPFile != null && targetHWPFile != null) {
			// test-source.hwp의  두번째 문단을 구한다
			Paragraph sourceParagraph = sourceHWPFile.getBodyText().getSectionList().get(0).getParagraph(1);
	
			// test-target.hwp의 첫번째 섹션을 구한다. 
			ParagraphListInterface targetFirstSection = targetHWPFile.getBodyText().getSectionList().get(0);
			
			ParagraphAdder paraAdder = new ParagraphAdder(targetHWPFile, targetFirstSection);
			paraAdder.add(sourceHWPFile, sourceParagraph);
			
			HWPWriter.toFile(targetHWPFile, "hwp_new\\오답노트3.hwp");
		}
	}
}