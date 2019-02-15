package converter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import option.Option;

/**
 * 헤더 정보, 저작권 정보, 페이지 번호 표시를 위한 문서 이벤트 처리 클래스 입니다.
 * @author 강승민
 *
 */
public class CopyRightEvent extends PdfPageEventHelper{
	/**
	 * 저작권 정보입니다.
	 */
	private Phrase footer;
	
	/**
	 * 헤더 정보입니다.
	 */
	private Phrase header;
	
	/**
	 * 옵션 정보
	 */
	private Option option;
	
	/**
	 * 생성자 : 위치를 기본값으로 정의하는 객체 생성자 입니다.
	 * @param op Option 인스턴스입니다.
	 */
	public CopyRightEvent(Option op){
		option = op;
		footer = new Phrase(new Chunk(option.getCopyright()));
		header = new Phrase(new Chunk(option.getHeaderTitle()));
	}
	
	/**
	 * 이벤트 헨들러 메소드입니다.
	 * 헤더, 저작권 정보의 처리를 진행합니다.
	 */
	public void onEndPage(PdfWriter writer, Document doc){
		if(option.getHeaderAlign() == Option.Align.LEFT){
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, header, doc.left(), doc.top() + 10, 0);
		}else if(option.getHeaderAlign() == Option.Align.CENTER){
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, header, (doc.right() - doc.left()) / 2 + doc.leftMargin(), doc.top() + 10, 0);
		}else if(option.getHeaderAlign() == Option.Align.RIGHT){
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, header, doc.right(), doc.top() + 10, 0);
		}
		
		if(option.getCopyrightAlign() == Option.Align.LEFT){
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, footer, doc.left(), doc.bottom() - 20, 0);
			if(option.getShowPageNumber()){
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(new Chunk("Page " + writer.getPageNumber())), (doc.right() - doc.left()) / 2 + doc.leftMargin(), doc.bottom() - 20, 0);
			}
		}else if(option.getCopyrightAlign() == Option.Align.CENTER){
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer, (doc.right() - doc.left()) / 2 + doc.leftMargin(), doc.bottom() - 20, 0);
			if(option.getShowPageNumber()){
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(new Chunk("Page " + writer.getPageNumber())), doc.left(), doc.bottom() - 20, 0);
			}
		}else if(option.getCopyrightAlign() == Option.Align.RIGHT){
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, footer, doc.right(), doc.bottom() - 20, 0);
			if(option.getShowPageNumber()){
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(new Chunk("Page " + writer.getPageNumber())), (doc.right() - doc.left()) / 2 + doc.leftMargin(), doc.bottom() - 20, 0);
			}
		}
	}
}
