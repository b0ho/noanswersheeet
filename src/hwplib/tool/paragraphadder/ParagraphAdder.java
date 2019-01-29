package hwplib.tool.paragraphadder;

import java.util.ArrayList;

import hwplib.object.HWPFile;
import hwplib.object.bodytext.ParagraphListInterface;
import hwplib.object.bodytext.paragraph.Paragraph;
import hwplib.tool.paragraphadder.docinfo.DocInfoAdder;

public class ParagraphAdder {
	private HWPFile targetHWPFile;
	private ParagraphListInterface targetParaList;

	public ParagraphAdder(HWPFile targetHWPFile, ParagraphListInterface targetSection) {
		this.targetHWPFile = targetHWPFile;
		this.targetParaList = targetSection;
	}

	public void add(HWPFile hwpFile, Paragraph p) throws Exception {
		ParapraphCopyer paraCopyer = new ParapraphCopyer(new DocInfoAdder(hwpFile, targetHWPFile));

		Paragraph targetParagraph = targetParaList.addNewParagraph();
		paraCopyer.copy(p, targetParagraph);
	}

	public void add(HWPFile hwpFile, ArrayList<Paragraph> list) throws Exception {
		ParapraphCopyer copyer = new ParapraphCopyer(new DocInfoAdder(hwpFile, targetHWPFile));
		for (Paragraph p : list) {
			Paragraph targetParagraph = targetParaList.addNewParagraph();
			copyer.copy(p, targetParagraph);
		}
	}
}
