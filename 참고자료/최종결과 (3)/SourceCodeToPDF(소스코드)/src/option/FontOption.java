package option;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

/**
 * 폰트에 관련된 옵션 클래스 입니다.
 * @author 유병호
 *
 */
public class FontOption {
	
	/**
	 * 폰트 스타일 데이터입니다.<p>
	 * NORMAL : 아무런 스타일도 정의되어 있지 않은 상태입니다.<br>
	 * BOLD : 굵게 표시됩니다.<br>
	 * ITALIC : 기울임꼴로 표시됩니다.<br>
	 * BOLDITALIC : 굵은 스타일에 기울임꼴이 적용됩니다.<br>
	 * UNDERLINE : 밑줄이 그어집니다. <br>
	 * STRIKETHRE : 취소선이 그어집니다.
	 * @author 유병호
	 *
	 */
	public enum FontStyle {NORMAL, BOLD, ITALIC, BOLDITALIC, UNDERLINE, STRIKETHRU};
	
	/**
	 * 폰트의 모양, 표시 방법이 정의되는 개체입니다.
	 */
	private BaseFont baseFont;
	
	/**
	 * 폰트의 색, 스타일, 크기등이 정의되는 개체입니다.
	 */
	private Font font;
	
	/**
	 * 폰트파일의 경로입니다.
	 */
	private String fontPath;
	
	/**
	 * 빈 생성자
	 */
	public FontOption(){
		
	}
	
	/**
	 * 폰트 옵션을 생성합니다.
	 * @param fontName 폰트 파일이 있는 경로입니다.
	 * @param fontSize 폰트의 크기입니다.
	 * @param fontColor 폰트의 RGB 색상값입니다.
	 * @param style 폰트의 스타일입니다.
	 */
	public FontOption(String fontName, int fontSize, int fontColor, FontStyle style){
		setFont(fontName, fontSize, fontColor, style);
	}
	
	/**
	 * 폰트 옵션을 지정합니다.
	 * @param fontName 폰트 이름 입니다.
	 * @param fontSize 폰트의 크기입니다.
	 * @param fontColor 폰트의 RRGGBB 형태의 색상값입니다.
	 * @param style 폰트의 스타일입니다.
	 */
	public void setFont(String fontName, int fontSize, int fontColor, FontStyle style){
		//baseFont = FontFactory.getFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED).getBaseFont();
		try {
			baseFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			baseFont = OptionDefault.getDefaultOption().getBasicFont().getBaseFont();
		}
		fontPath = fontName;
		if(fontSize < 1 || fontSize >= 100){
			fontSize = 11;
		}
		font = new Font(baseFont, (float)fontSize);
		// RGB 색상값은 저장이나 관리에 유용하도록 합쳐서 저장하지만 iText 라이브러리의 파라메터는 각각 R, G, B 값이 따로 지정되므로
		// 비트 연산을 통해 각각의 RGB 값을 분리합니다.
		if(fontColor < 0 || fontColor > 0xFFFFFF){
			fontColor = 0;
		}
		int r = fontColor >> 16;
		int g = (fontColor >> 8) & (0x00FF);
		int b = fontColor & 0x0000FF;
		font.setColor(r,g,b);
		
		//폰트 스타일을 라이브러리 값으로 치환합니다.
		switch (style){
		case NORMAL:
			font.setStyle(Font.NORMAL);
			break;
		case BOLD:
			font.setStyle(Font.BOLD);
			break;
		case BOLDITALIC:
			font.setStyle(Font.BOLDITALIC);
			break;
		case ITALIC:
			font.setStyle(Font.ITALIC);
			break;
		case STRIKETHRU:
			font.setStyle(Font.STRIKETHRU);
			break;
		case UNDERLINE:
			font.setStyle(Font.UNDERLINE);
			break;
		default:
			font.setStyle(Font.NORMAL);
		}
	}
	
	/**
	 * 폰트 개체를 반환합니다.
	 * @return 폰트 개체
	 */
	public Font getFont(){
		return font;
	}
	
	/**
	 * 폰트 기본 정보가 있는 개체를 반환합니다.
	 * @return 폰트 기본 정보 개체
	 */
	public BaseFont getBaseFont(){
		return baseFont;
	}
	
	/**
	 * 폰트 파일의 경로를 반환합니다.
	 * @return 폰트 파일 경로
	 */
	public String getFontName(){
		return fontPath;
	}
	
	/**
	 * 폰트의 크기를 반환합니다.
	 * @return 폰트의 크기
	 */
	public int getFontSize(){
		return (int) font.getSize();
	}
	
	/**
	 * 폰트의 색상값을 RRGGBB 형태로 반환합니다.
	 * @return 폰트 색상
	 */
	public int getFontColor(){
		return (~font.getColor().getRGB()) ^ 0xFFFFFF;
	}
	
	/**
	 * 폰트 스타일을 반환합니다.
	 * @return 폰트 스타일
	 */
	public FontStyle getFontStyle(){
		// 라이브러리에서 지정되었던 폰트 스타일이 내부적으로 구현된 폰트 스타일로 치환됩니다.
		int s = font.getStyle();
		if(s == 0){
			return FontStyle.NORMAL;
		}else if(s == 1){
			return FontStyle.BOLD;
		}else if(s == 2){
			return FontStyle.ITALIC;
		}else if(s == 3){
			return FontStyle.BOLDITALIC;
		}else if(s == 4){
			return FontStyle.UNDERLINE;
		}else if(s == 8){
			return FontStyle.STRIKETHRU;
		}else{
			return FontStyle.NORMAL;
		}
	}
}
