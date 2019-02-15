package option;

import java.io.File;
import java.util.regex.Pattern;

import com.itextpdf.text.Rectangle;

/**
 * 옵션을 저장하는 클래스입니다.
 * @author 오다솜
 *
 */
public class Option {
	
	/**
	 * 위치 정보입니다.<p>
	 * LEFT / CENTER / RIGHT
	 * @author 오다솜
	 *
	 */
	public enum Align {LEFT, CENTER, RIGHT};
	
	/**
	 * 언어 종류입니다.
	 * @author 오다솜
	 *
	 */
	public enum Language {JAVA, C, CPP};
	
	/**
	 * 지정되는 옵션의 갯수입니다.<br>
	 * 이 값은 옵션 파일의 RW 작업에 사용되며 새로운 옵션의 추가나 기존 옵션의 삭제가 있을 시 값의 변동이 필요합니다.
	 */
	public static final int optionCount = 38;
	
	/**
	 * 옵션 파일 인스턴스
	 */
	private OptionFile of = new OptionFile();
	
	/**
	 * 일반 문자를 표시하는 폰트의 옵션입니다.<p>
	 * 기본값<br>
	 * 폰트 : 맑은 고딕<br>
	 * 크기 : 11<br>
	 * 색상 : 0x000000<br>
	 * 스타일 : 없음
	 */
	private FontOption basicFont = new FontOption();
	
	/**
	 * 키워드 문자를 표시하는 폰트 옵션입니다.<p>
	 * 기본값<br>
	 * 폰트 : 맑은 고딕<br>
	 * 크기 : 11<br>
	 * 색상 : 0xC13A55<br>
	 * 스타일 : 없음
	 */
	private FontOption keywordFont = new FontOption();
	
	/**
	 * ' ' 혹은 " " 형태로 감싸진 문자열 내부를 표시하는 폰트 옵션입니다.<p>
	 * 기본값<br>
	 * 폰트 : 맑은 고딕<br>
	 * 크기 : 11<br>
	 * 색상 : 0x0000FF<br>
	 * 스타일 : 없음
	 */
	private FontOption stringFont = new FontOption();
	
	/**
	 * 주석 형태의 문자열을 표시하는 폰트 옵션입니다.<p>
	 * 기본값<br>
	 * 폰트 : 맑은 고딕<br>
	 * 크기 : 11<br>
	 * 색상 : 0x3F7F5F<br>
	 * 스타일 : 없음
	 */
	private FontOption commentFont = new FontOption();
	
	/**
	 * 라인 넘버를 표시하는 폰트 옵션입니다.<p>
	 * 기본값<br>
	 * 폰트 : 맑은 고딕<br>
	 * 크기 : 11<br>
	 * 색상 : 0xCCCCCC<br>
	 * 스타일 : 없음
	 */
	private FontOption lineNumberFont = new FontOption();
	
	/**
	 * 탭의 길이를 나타내는 옵션입니다.<p>
	 * 기본값 : 4
	 */
	private int tabSize;
	
	/**
	 * 생성되는 PDF 파일의 헤더에 들어갈 문구를 저장하는 옵션입니다.<p>
	 * 기본값 : 빈 문자열
	 */
	private String headerInfo;
	
	/**
	 * 저작자 정보를 저장하는 옵션입니다.<p>
	 * 기본값 : 빈 문자열
	 */
	private String copyrightInfo;
	
	/**
	 * PDF 문서의 크기, 여백등을 지정하는 옵션입니다.<p>
	 * 기본값<br>
	 * 문서 용지 : A4<br>
	 * 여백(위, 아래, 왼쪽, 오른쪽) : 30, 30, 40, 40
	 */
	private PageOption pageOption = new PageOption();
	
	/**
	 * 라인넘버를 표시할지 여부를 지정하는 옵션입니다.<p>
	 * 기본값 : true
	 */
	private boolean showLineNumber;
	
	/**
	 * 페이지 번호를 표시할지 여부를 지정하는 옵션입니다.<p>
	 * 기본값 : true
	 */
	private boolean showPageNumber;
	
	/**
	 * 소스코드의 언어 종류입니다.
	 */
	private Language codeLanguage;
	
	/**
	 * 헤더 문구 표시 위치입니댜.
	 */
	private Align headerAlign;
	
	/**
	 * 저작권 문구 표시 위치입니다.
	 */
	private Align copyrightAlign;
	
	/**
	 * 소스코드 변환 시 모든 소스파일을 한개의 문서로 출력할것인지에 대한 옵션입니다.
	 */
	private boolean convertAll;
	
	/**
	 * 소스코드 변환 시 각 패키지 당 한개의 문서로 출력할것인지에 대한 옵션입니다.
	 */
	private boolean convertPackage;
	
	/**
	 * 소스코드 변환 시 각 파일당 한개의 문서로 출력할 것인지에 대한 옵션입니다.
	 */
	private boolean convertFile;
	
	/**
	 * 종합 변환 시 지정되는 파일 명 입니다.
	 */
	private String file_name;
	
	/**
	 * 변환되는 PDF 문서가 저장되는 위치입니다.
	 */
	private String save_locate;
	
	/**
	 * 옵션을 파일로 저장합니다.
	 */
	public void saveOption(){
		of.writeOptionFile(this);
	}
	
	/**
	 * 일반 문자열을 표시하는 폰트 옵션을 지정합니다.
	 * @param fontName 폰트 파일의 경로
	 * @param fontSize 폰트의 크기
	 * @param fontColor 폰트의 색상
	 * @param style 폰트의 스타일
	 */
	public void setBasicFont(String fontName, int fontSize, int fontColor, FontOption.FontStyle style){
		basicFont.setFont(fontName, fontSize, fontColor, style);
	}
	
	/**
	 * 일반 문자열을 표시하는 폰트 옵션을 반환합니다.
	 * @return 일반 문자열 폰트
	 */
	public FontOption getBasicFont(){
		return basicFont;
	}

	/**
	 * 키워드 문자를 표시하는 폰트 옵션을 지정합니다.
	 * @param fontName 폰트 파일의 경로
	 * @param fontSize 폰트의 크기
	 * @param fontColor 폰트의 색상
	 * @param style 폰트의 스타일
	 */
	public void setKeywordFont(String fontName, int fontSize, int fontColor, FontOption.FontStyle style) {
		keywordFont.setFont(fontName, fontSize, fontColor, style);
	}
	/**
	 * 키워드 문자를 표시하는 폰트 옵션을 반환합니다.
	 * @return 키워드 문자열 폰트
	 */
	public FontOption getKeywordFont(){
		return keywordFont;
	}

	/**
	 * ' ' 혹은 " " 형태로 감싸진 문자열 내부를 표시하는 폰트 옵션을 지정합니다.
	 * @param fontName 폰트 파일의 경로
	 * @param fontSize 폰트의 크기
	 * @param fontColor 폰트의 색상
	 * @param style 폰트의 스타일
	 */
	public void setStringFont(String fontName, int fontSize, int fontColor, FontOption.FontStyle style) {
		stringFont.setFont(fontName, fontSize, fontColor, style);
	}
	/**
	 * ' ' 혹은 " " 형태로 감싸진 문자열 내부를 표시하는 폰트 옵션을 반환합니다.
	 * @return 문자열 관련 문자열 폰트
	 */
	public FontOption getStringFont(){
		return stringFont;
	}

	/**
	 * 주석을 표시하는 폰트 옵션을 지정합니다.
	 * @param fontName 폰트 파일의 경로
	 * @param fontSize 폰트의 크기
	 * @param fontColor 폰트의 색상
	 * @param style 폰트의 스타일
	 */
	public void setCommentFont(String fontName, int fontSize, int fontColor, FontOption.FontStyle style) {
		commentFont.setFont(fontName, fontSize, fontColor, style);
	}
	
	/**
	 * 주석을 표시하는 폰트 옵션을 반환합니다.
	 * @return 주석 문자열 폰트
	 */
	public FontOption getCommentFont(){
		return commentFont;
	}

	/**
	 * 라인 넘버를 표시하는 폰트 옵션을 지정합니다.
	 * @param fontName 폰트 파일의 경로
	 * @param fontSize 폰트의 크기
	 * @param fontColor 폰트의 색상
	 * @param style 폰트의 스타일
	 */
	public void setLineNumberFont(String fontName, int fontSize, int fontColor, FontOption.FontStyle style) {
		lineNumberFont.setFont(fontName, fontSize, fontColor, style);
	}
	
	/**
	 * 라인 넘버를 표시하는 폰트 옵션을 반환합니다.
	 * @return 줄번호 문자열 폰트
	 */
	public FontOption getLineNumberFont(){
		return lineNumberFont;
	}
	
	/**
	 * 탭 크기를 지정합니다.
	 * @param tabSize 탭 크기
	 */
	public void setTabSize(int tabSize){
		if(tabSize < 1 || tabSize > 6){
			tabSize = 4;
		}
		this.tabSize = tabSize;
	}
	/**
	 * 지정된 탭 크기를 반환합니다.
	 * @return 탭 크기
	 */
	public int getTabSize(){
		return tabSize;
	}
	
	/**
	 * 용지 크기를 설정합니다.
	 * @param type 용지의 종류
	 */
	public void setPageType(PageOption.PageType type){
		pageOption.setPageType(type);
	}
	
	/**
	 * 페이지 옵션을 반환합니다.
	 * @return 페이지 옵션
	 */
	public PageOption getPageOption(){
		return pageOption;
	}
	/**
	 * 용지 크기를 반환합니다.
	 * @return 용지 크기
	 */
	public Rectangle getPageType(){
		if(pageOption.getPageType() == PageOption.PageType.A1){
			return com.itextpdf.text.PageSize.A1;
		}else if(pageOption.getPageType() == PageOption.PageType.A2){
			return com.itextpdf.text.PageSize.A2;
		}else if(pageOption.getPageType() == PageOption.PageType.A3){
			return com.itextpdf.text.PageSize.A3;
		}else if(pageOption.getPageType() == PageOption.PageType.A4){
			return com.itextpdf.text.PageSize.A4;
		}else if(pageOption.getPageType() == PageOption.PageType.A5){
			return com.itextpdf.text.PageSize.A5;
		}else if(pageOption.getPageType() == PageOption.PageType.A6){
			return com.itextpdf.text.PageSize.A6;
		}else if(pageOption.getPageType() == PageOption.PageType.B1){
			return com.itextpdf.text.PageSize.B1;
		}else if(pageOption.getPageType() == PageOption.PageType.B2){
			return com.itextpdf.text.PageSize.B2;
		}else if(pageOption.getPageType() == PageOption.PageType.B3){
			return com.itextpdf.text.PageSize.B3;
		}else if(pageOption.getPageType() == PageOption.PageType.B4){
			return com.itextpdf.text.PageSize.B4;
		}else if(pageOption.getPageType() == PageOption.PageType.B5){
			return com.itextpdf.text.PageSize.B5;
		}else if(pageOption.getPageType() == PageOption.PageType.B6){
			return com.itextpdf.text.PageSize.B6;
		}else{
			return com.itextpdf.text.PageSize.A4;
		}
	}
	
	/**
	 * 용지 여백을 지정합니다.
	 * @param top 상단 여백
	 * @param bottom 하단 여백
	 * @param left 좌측 여백
	 * @param right 우측 여백
	 */
	public void setMargin(int top, int bottom, int left, int right){
		pageOption.setMarginTop(top);
		pageOption.setMarginBottom(bottom);
		pageOption.setMarginLeft(left);
		pageOption.setMarginRight(right);
	}
	
	/**
	 * 상단 여백을 지정합니다.
	 * @param margin 상단 여백
	 */
	public void setMarginTop(int margin){
		pageOption.setMarginTop(margin);
	}
	/**
	 * 상단 여백을 반환합니다.
	 * @return 상단 여백
	 */
	public int getMarginTop(){
		return pageOption.getMarginTop();
	}

	/**
	 * 하단 여백을 지정합니다. 
	 * @param margin 하단 여백
	 */
	public void setMarginBottom(int margin) {
		pageOption.setMarginBottom(margin);
	}
	/**
	 * 하단 여백을 반환합니다.
	 * @return 하단 여백
	 */
	public int getMarginBottom(){
		return pageOption.getMarginBottom();
	}

	/**
	 * 좌측 여백을 지정합니다.
	 * @param margin 좌측 여백
	 */
	public void setMarginLeft(int margin) {
		pageOption.setMarginLeft(margin);
	}
	/**
	 * 좌측 여백을 반환합니다.
	 * @return 좌측 여백
	 */
	public int getMarginLeft(){
		return pageOption.getMarginLeft();
	}

	/**
	 * 우측 여백을 지정합니다.
	 * @param margin 우측 여백
	 */
	public void setMarginRight(int margin) {
		pageOption.setMarginRight(margin);
	}
	/**
	 * 우측 여백을 반환합니다.
	 * @return 우측 여백
	 */
	public int getMarginRight(){
		return pageOption.getMarginRight();
	}
	
	/**
	 * 문서 헤더에 삽입될 문구를 지정합니다.
	 * @param header 헤더 문구
	 */
	public void setHeaderTitle(String header){
		headerInfo = header;
	}
	/**
	 * 헤더 문구를 반환합니다.
	 * @return 헤더 문구
	 */
	public String getHeaderTitle(){
		return headerInfo;
	}
	
	/**
	 * 저작자 정보를 지정합니다.
	 * @param copyright 저작자 정보
	 */
	public void setCopyright(String copyright){
		copyrightInfo = copyright;
	}
	/**
	 * 저작자 정보를 반환합니다.
	 * @return 저작자 정보
	 */
	public String getCopyright(){
		return copyrightInfo;
	}
	
	/**
	 * 라인 넘버 표시 여부를 지정합니다.
	 * @param arg 라인 넘버 표시 여부 / true = 표시 / false = 표시하지 않음
	 */
	public void setShowLineNumber(boolean arg){
		showLineNumber = arg;
	}
	/**
	 * 라인넘버 표시 여부를 반환합니다.
	 * @return 줄번호 표시 여부
	 */
	public boolean getShowLineNumber(){
		return showLineNumber;
	}
	
	/**
	 * 페이지 넘버 표시 여부를 지정합니다.
	 * @param arg 페이지 넘버 표시 여부 / true = 표시 / false = 표시하지 않음
	 */
	public void setShowPageNumber(boolean arg){
		showPageNumber = arg;
	}
	/**
	 * 페이지 넘버 표시 여부를 반환합니다.
	 * @return 쪽번호 표시 여부
	 */
	public boolean getShowPageNumber(){
		return showPageNumber;
	}
	
	/**
	 * 소스 언어 종류를 지정합니다<br>
	 * java, c, cpp
	 * @param lang 소스코드 언어 종류
	 */
	public void setCodeLanguage(Language lang){
		codeLanguage = lang;
	}
	
	/**
	 * 소스 언어 종류를 반환합니다.
	 * @return 언어 종류
	 */
	public Language getCodeLanguage(){
		return codeLanguage;
	}
	
	/**
	 * 헤더 문구의 위치를 지정합니다.
	 * @param align 헤더 위치 정보
	 */
	public void setHeaderAlign(Align align){
		headerAlign = align;
	}
	
	/**
	 * 헤더 문구의 위치를 반환합니다.
	 * @return 헤더 문구의 위치
	 */
	public Align getHeaderAlign(){
		return headerAlign;
	}
	
	/**
	 * 저작권 문구의 위치를 지정합니다.
	 * @param align 저작권 위치 정보
	 */
	public void setCopyrightAlign(Align align){
		copyrightAlign = align;
	}
	
	/**
	 * 저작권 문구의 위치를 반환합니다.
	 * @return 저작권 문주의 위치
	 */
	public Align getCopyrightAlign(){
		return copyrightAlign;
	}
	
	/**
	 * PDF 출력 시 모든 소스파일을 한개의 문서로 만들지 지정하는 옵션입니다.<br>
	 * 값을 false로 변경할 때 convertPackage나 convertFile 값이 false일 경우 값이 변경되지 않습니다.
	 * @param arg 옵션 설정 값
	 * @return 변경 성공시 true가, 실패시 false가 리턴됩니다.
	 */
	public boolean setConvertAll(boolean arg){
		if(!arg && !(getConvertPackage() || getConvertFile())){
			return false;
		}else{
			convertAll = arg;
			return true;
		}
	}
	
	/**
	 * PDF 출력 시 각 패키지 내의 소스파일을 한개의 문서로 만들지 지정하는 옵션입니다.<br>
	 * 값을 false로 변경할 때 convertAll이나 convertFile 값이 false일 경우 값이 변경되지 않습니다.
	 * @param arg 옵션 설정 값
	 * @return 변경 성공시 true가, 실패시 false가 리턴됩니다.
	 */
	public boolean setConvertPackage(boolean arg){
		if(!arg && !(getConvertAll() || getConvertFile())){
			return false;
		}else{
			convertPackage = arg;
			return true;
		}
	}
	
	/**
	 * PDF 출력 시 각 소스파일별로 문서로 만들지 지정하는 옵션입니다.<br>
	 * 값을 false로 변경할 때 convertAll이나 convertPackage 값이 false일 경우 값이 변경되지 않습니다.
	 * @param arg 옵션 설정 값
	 * @return 변경 성공시 true가, 실패시 false가 리턴됩니다.
	 */
	public boolean setConvertFile(boolean arg){
		if(!arg && !(getConvertPackage() || getConvertAll())){
			return false;
		}else{
			convertFile = arg;
			return true;
		}
	}
	
	/**
	 * 전체변환 출력 옵션 반환
	 * @return 출력 옵션 값
	 */
	public boolean getConvertAll(){
		return convertAll;
	}
	
	/**
	 * 패키지변환 출력 옵션 반환
	 * @return 출력 옵션 값
	 */
	public boolean getConvertPackage(){
		return convertPackage;
	}
	
	/**
	 * 개별 변환 출력 옵션 반환
	 * @return 출력 옵션 값
	 */
	public boolean getConvertFile(){
		return convertFile;
	}
	
	/**
	 * 전체 변환 시 지정할 파일 명 설정
	 * @param filename 파일 명
	 */
	public void setFileName(String filename) {
		filename = filename.trim(); //파일 이름의 앞 뒤 공백 제거
		if(filename.length() > 200){ //길이가 200자 초과인 경우의 예외 처리
			filename = filename.substring(0, 200);
		}
		if(Pattern.compile("[:\\\\/%*?:|\"<>]").matcher(filename).find()){ //유효하지 않은 키워드 발견시 기본값으로 변환
			filename = "__ConvertAllFile__";
		}
		file_name = filename;
	}
	
	/**
	 * 전체 변환 시 사용되는 파일 명 값 반환
	 * @return 파일 명
	 */
	public String getFileName() {
		return file_name;
	}
	
	/**
	 * PDF 저장 위치 설정
	 * @param locate 저장 위치
	 */
	public void setSaveLocate (String locate) {
		try{
			if(!new File(locate).isDirectory()){ //유효하지 않은 폴더일 시 기본값으로 변환
				locate = System.getProperty("user.dir") + "\\";
			}
		}catch(Exception e){
			locate = System.getProperty("user.dir") + "\\";
		}
		save_locate = locate;
	}
	
	/**
	 * PDF 저장 위치 확인
	 * @return 저장 위치
	 */
	public String getSaveLocate () {
		return save_locate;
	}
}
