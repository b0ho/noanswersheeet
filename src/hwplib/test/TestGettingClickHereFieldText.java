package hwplib.test;

import hwplib.object.HWPFile;
import hwplib.reader.HWPReader;
import hwplib.tool.objectfinder.FieldFinder;
import hwplib.tool.textextractor.TextExtractMethod;

public class TestGettingClickHereFieldText {
	public static void main(String[] args) throws Exception {
		HWPFile hwpFile = HWPReader.fromFile("sample_hwp\\test-필드_누름틀.hwp");
		String text1 = FieldFinder.getClickHereText(hwpFile, "필드1", TextExtractMethod.OnlyMainParagraph);
		String text2 = FieldFinder.getClickHereText(hwpFile, "필드2", TextExtractMethod.OnlyMainParagraph);
		String text3 = FieldFinder.getClickHereText(hwpFile, "Table필드1", TextExtractMethod.OnlyMainParagraph);
		String text4 = FieldFinder.getClickHereText(hwpFile, "멀티라인누름틀", TextExtractMethod.OnlyMainParagraph);
		String text5 = FieldFinder.getClickHereText(hwpFile, "xxx", TextExtractMethod.OnlyMainParagraph);
		String longText = FieldFinder.getClickHereText(hwpFile, "long", TextExtractMethod.OnlyMainParagraph);
		System.out.println("필드1 ==> " + text1);
		System.out.println("필드2 ==> " + text2);
		System.out.println("Table필드1 ==> " + text3);
		System.out.println("멀티라인누름틀 ==> " + text4);
		System.out.println("xxx ==> " + text5);
		System.out.println("long ==> " + longText);
	}
}
