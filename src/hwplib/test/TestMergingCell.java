package hwplib.test;

import hwplib.object.HWPFile;
import hwplib.object.bodytext.control.Control;
import hwplib.object.bodytext.control.ControlTable;
import hwplib.object.bodytext.control.ControlType;
import hwplib.reader.HWPReader;
import hwplib.tool.TableCellMerger;
import hwplib.writer.HWPWriter;

public class TestMergingCell {
	public static void main(String[] args) throws Exception {
		String filename = "sample_hwp\\test-merge-cell.hwp";

		HWPFile hwpFile = HWPReader.fromFile(filename);
		if (hwpFile != null) {
			Control control = hwpFile.getBodyText().getSectionList().get(0).getParagraph(0).getControlList().get(2);
			if (control.getType() == ControlType.Table) {
				ControlTable table = (ControlTable) control;
				TableCellMerger.mergeCell(table, 2, 2, 4, 3);
			}

			String writePath = "sample_hwp\\test-merge-cell-2.hwp";
			HWPWriter.toFile(hwpFile, writePath);
		}
	}

}
