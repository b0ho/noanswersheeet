package hwplib.reader.bodytext.paragraph.control;

import java.io.IOException;

import hwplib.object.bodytext.control.ControlIndexMark;
import hwplib.object.bodytext.control.ctrlheader.CtrlHeaderIndexMark;
import hwplib.util.compoundFile.reader.StreamReader;

/**
 * 찾아보기 표식 컨트롤을 읽기 위한 객체
 * 
 * @author neolord
 */
public class ForControlIndexMark {
	/**
	 * 찾아보기 표식 컨트롤을 읽는다.
	 * 
	 * @param idxm
	 *            찾아보기 표식 컨트롤
	 * @param sr
	 *            스트림 리더
	 * @throws IOException
	 */
	public static void read(ControlIndexMark idxm, StreamReader sr)
			throws IOException {
		ctrlHeader(idxm.getHeader(), sr);
	}

	/**
	 * 찾아보기 표시 컨트롤의 컨트롤 헤더 레코드를 읽는다.
	 * 
	 * @param header
	 *            찾아보기 표시 컨트롤의 컨트롤 헤더 레코드
	 * @param sr
	 *            스트림 리더
	 * @throws IOException
	 */
	private static void ctrlHeader(CtrlHeaderIndexMark header, StreamReader sr)
			throws IOException {
		header.setKeyword1(sr.readUTF16LEString());
		header.setKeyword2(sr.readUTF16LEString());
		sr.skip(4);
	}
}
