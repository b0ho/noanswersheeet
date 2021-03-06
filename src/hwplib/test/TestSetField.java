package hwplib.test;

import java.util.ArrayList;

import hwplib.object.HWPFile;
import hwplib.object.bodytext.control.ControlType;
import hwplib.reader.HWPReader;
import hwplib.tool.objectfinder.FieldFinder;
import hwplib.tool.objectfinder.SetFieldResult;
import hwplib.writer.HWPWriter;

public class TestSetField {

	public static void main(String[] args) throws Exception {
		HWPFile hwpFile = HWPReader.fromFile("sample_hwp\\test-필드설정.hwp");
		if (hwpFile != null) {
			{
				ArrayList<String> textList = new ArrayList<String>();
				textList.add("필드1 값1\n2줄\n3줄\n");
				textList.add("필드1 값1\n2줄\n3줄");
				textList.add("필드1 값3");
				textList.add("필드1 값4");
				SetFieldResult sfr = FieldFinder.setFieldText(hwpFile, ControlType.FIELD_CLICKHERE, "필드1", textList);
				System.out.println("필드1 설정경과  = " + sfr);
			}
			{
				ArrayList<String> textList = new ArrayList<String>();
				textList.add("필드2 값1");
				textList.add("필드2 값2");
				textList.add("필드2 값3");
				SetFieldResult sfr = FieldFinder.setFieldText(hwpFile, ControlType.FIELD_CLICKHERE, "필드2", textList);
				System.out.println("필드2 설정경과  = " + sfr);
			}

			HWPWriter.toFile(hwpFile, "sample_hwp\\test-필드설정_saved.hwp");
		}
	}

}
