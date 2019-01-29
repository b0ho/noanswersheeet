package hwplib.writer.bodytext.paragraph.control.gso;

import java.io.IOException;

import hwplib.object.bodytext.control.gso.ControlObjectLinkLine;
import hwplib.object.bodytext.control.gso.shapecomponenteach.ShapeComponentLineForObjectLinkLine;
import hwplib.object.etc.HWPTag;
import hwplib.util.compoundFile.writer.StreamWriter;

public class ForControlObjectLinkLine {
	/**
	 * 객체 연결선 컨트롤의 나머지 부분을 쓴다.
	 * 
	 * @param line
	 *            선 컨트롤
	 * @param sw
	 *            스트림 라이터
	 * @throws IOException
	 */
	public static void writeRest(ControlObjectLinkLine oll, StreamWriter sw) throws IOException {
		sw.upRecordLevel();

		shapeComponentLine(oll.getShapeComponentLine(), sw);

		sw.downRecordLevel();
	}

	/**
	 * 선 개체 속성 레코드를 쓴다.
	 * 
	 * @param scl
	 *            선 개체 속성 레코드
	 * @param sw
	 *            스트림 라이터
	 * @throws IOException
	 */
	private static void shapeComponentLine(
			ShapeComponentLineForObjectLinkLine scl, StreamWriter sw) throws IOException {
		recordHeader(sw, scl);

		sw.writeSInt4(scl.getStartX());
		sw.writeSInt4(scl.getStartY());
		sw.writeSInt4(scl.getEndX());
		sw.writeSInt4(scl.getEndY());
		if (scl.getUnknown() != null) {
			sw.writeBytes(scl.getUnknown());
		}
	}

	/**
	 * 선 개체 속성 레코드를 쓴다.
	 * 
	 * @param scl
	 *            선 개체 속성 레코드
	 * @param sw
	 *            스트림 라이터
	 * @throws IOException
	 */
	private static void recordHeader(StreamWriter sw,
			ShapeComponentLineForObjectLinkLine scl) throws IOException {
		sw.writeRecordHeader(HWPTag.SHAPE_COMPONENT_LINE, getSize(scl));
	}

	/**
	 * 선 개체 속성 레코드의 크기를 반환한다.
	 * 
	 * @param scl
	 *            선 개체 속성 레코드
	 * @return 선 개체 속성 레코드의 크기
	 */
	private static int getSize(ShapeComponentLineForObjectLinkLine scl) {
		if (scl.getUnknown() != null) {
			return 16 + scl.getUnknown().length;
		}
		return 16;
	}
}