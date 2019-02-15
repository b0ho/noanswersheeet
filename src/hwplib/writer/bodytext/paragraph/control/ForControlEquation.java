package hwplib.writer.bodytext.paragraph.control;

import hwplib.object.bodytext.control.ControlEquation;
import hwplib.util.compoundFile.writer.StreamWriter;
import hwplib.writer.bodytext.paragraph.control.eqed.ForEQEdit;
import hwplib.writer.bodytext.paragraph.control.gso.part.ForCaption;
import hwplib.writer.bodytext.paragraph.control.gso.part.ForCtrlHeaderGso;

/**
 * 수식 컨트롤을 쓰기 위한 컨트롤
 * 
 * @author neolord
 */
public class ForControlEquation {
	/**
	 * 수식 컨트롤을 쓴다.
	 * 
	 * @param eqed
	 *            수식 컨트롤
	 * @param sw
	 *            스트림 라이터
	 * @throws Exception
	 */
	public static void write(ControlEquation eqed, StreamWriter sw)
			throws Exception {
		ForCtrlHeaderGso.write(eqed.getHeader(), sw);

		sw.upRecordLevel();

		ForCaption.write(eqed.getCaption(), sw);
		ForEQEdit.write(eqed.getEQEdit(), sw);

		sw.downRecordLevel();
	}
}
