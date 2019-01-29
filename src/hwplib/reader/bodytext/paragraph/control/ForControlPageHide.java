package hwplib.reader.bodytext.paragraph.control;

import java.io.IOException;

import hwplib.object.bodytext.control.ControlPageHide;
import hwplib.object.bodytext.control.ctrlheader.CtrlHeaderPageHide;
import hwplib.util.compoundFile.reader.StreamReader;

/**
 * 감추기 컨트롤을 읽기 위한 객체
 * 
 * @author neolord
 */
public class ForControlPageHide {
	/**
	 * 감추기 컨트롤을 읽는다.
	 * 
	 * @param pghd
	 *            감추기 컨트롤
	 * @param sr
	 *            스트림 리더
	 * @throws IOException
	 */
	public static void read(ControlPageHide pghd, StreamReader sr)
			throws IOException {
		ctrlHeader(pghd.getHeader(), sr);
	}

	/**
	 * 감추기 컨트롤의 컨트롤 헤더 레코드를 읽는다.
	 * 
	 * @param header
	 *            감추기 컨트롤의 컨트롤 헤더 레코드
	 * @param sr
	 *            스트림 리더
	 * @throws IOException
	 */
	private static void ctrlHeader(CtrlHeaderPageHide header, StreamReader sr)
			throws IOException {
		header.getProperty().setValue(sr.readUInt4());
	}
}
