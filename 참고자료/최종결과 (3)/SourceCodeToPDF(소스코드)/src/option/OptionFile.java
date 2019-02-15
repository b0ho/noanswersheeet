package option;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 지정된 옵션을 파일로 저장하거나 파일로 저장된 옵션 정보를 읽어들입니다.
 * @author 유병호
 *
 */
public class OptionFile {
	
	/**
	 * 파일에서 읽어들이는 옵션 정보를 저장합니다.
	 */
	private Option option;
	
	/**
	 * 지정된 옵션 파일을 읽어들입니다.
	 * @return 읽어들인 옵션 파일입니다.
	 */
	@SuppressWarnings("resource")
	public Option readOptionFile(){
		option = new Option();
		BufferedReader br = null;
		
		try{
			br = new BufferedReader(new FileReader("option.ini")); //option.ini 파일을 읽어들입니다.
			String[] optionBuffer = new String[Option.optionCount];
			
			// 정확하게 optionCount 만큼의 반복을 함으로써 옵션 정보가 빠져있는 등의 비정상 적인 상황의 경우
			// 예외를 발생시키므로 대처가 가능합니다.
			for(int i = 0 ; i < Option.optionCount ; i++){
				optionBuffer[i] = br.readLine();
			}
			
			//optionCount 보다 저장되어있는 옵션 정보가 부족한 경우 기본값으로 초기화
			if(optionBuffer[Option.optionCount - 1] == null){
				option = OptionDefault.getDefaultOption();
				writeOptionFile(option);
				return option;
			}
			
			//읽어들인 옵션을 지정합니다.
			try{
				option.setBasicFont(optionBuffer[0], Integer.valueOf(optionBuffer[1]), Integer.valueOf(optionBuffer[2]), convertFontStyle(optionBuffer[3]));
				option.setKeywordFont(optionBuffer[4], Integer.valueOf(optionBuffer[5]), Integer.valueOf(optionBuffer[6]), convertFontStyle(optionBuffer[7]));
				option.setStringFont(optionBuffer[8], Integer.valueOf(optionBuffer[9]), Integer.valueOf(optionBuffer[10]), convertFontStyle(optionBuffer[11]));
				option.setCommentFont(optionBuffer[12], Integer.valueOf(optionBuffer[13]), Integer.valueOf(optionBuffer[14]), convertFontStyle(optionBuffer[15]));
				option.setLineNumberFont(optionBuffer[16], Integer.valueOf(optionBuffer[17]), Integer.valueOf(optionBuffer[18]), convertFontStyle(optionBuffer[19]));
				
				option.setTabSize(Integer.valueOf(optionBuffer[20]));
				
				option.setHeaderTitle(optionBuffer[21]);
				option.setCopyright(optionBuffer[22]);
				
				option.setPageType(convertPageType(optionBuffer[23]));
				option.setMargin(Integer.valueOf(optionBuffer[24]), Integer.valueOf(optionBuffer[25]), Integer.valueOf(optionBuffer[26]), Integer.valueOf(optionBuffer[27]));
				
				option.setShowLineNumber(Boolean.valueOf(optionBuffer[28]));
				option.setShowPageNumber(Boolean.valueOf(optionBuffer[29]));
				
				option.setCodeLanguage(convertLanguage(optionBuffer[30]));
				
				option.setHeaderAlign(convertAlign(optionBuffer[31]));
				option.setCopyrightAlign(convertAlign(optionBuffer[32]));
				
				option.setConvertAll(Boolean.valueOf(optionBuffer[33]));
				option.setConvertPackage(Boolean.valueOf(optionBuffer[34]));
				option.setConvertFile(Boolean.valueOf(optionBuffer[35]));
				option.setFileName(optionBuffer[36]);
				option.setSaveLocate(optionBuffer[37]);
			}catch(NumberFormatException e){
				option = OptionDefault.getDefaultOption();
				writeOptionFile(option);
				return option;
			}

			writeOptionFile(option);
			br.close();
		} catch(IOException e){
			// 만약 파일이 없거나 읽어들이는 경과값이 정상적이지 않을경우 기본값으로 지정된 옵션을 생성하고 그 정보를 파일에 기록합니다.
			option = OptionDefault.getDefaultOption();
			writeOptionFile(option);
		}
		return option;
	}
	
	/**
	 * 옵션을 파일로 저장합니다.
	 * @param op 파일로 저장할 옵션입니다.
	 */
	public void writeOptionFile(Option op){
		BufferedWriter bw = null;
		
		try{
			bw = new BufferedWriter(new FileWriter("option.ini"));
			
			// 각각의 옵션 값은 줄단위로 구별됩니다.
			bw.write(op.getBasicFont().getFontName()+ "");
			bw.newLine();
			bw.write(op.getBasicFont().getFontSize()+ "");
			bw.newLine();
			bw.write(op.getBasicFont().getFontColor()+ "");
			bw.newLine();
			bw.write(op.getBasicFont().getFontStyle()+ "");
			bw.newLine();
			
			bw.write(op.getKeywordFont().getFontName()+ "");
			bw.newLine();
			bw.write(op.getKeywordFont().getFontSize()+ "");
			bw.newLine();
			bw.write(op.getKeywordFont().getFontColor()+ "");
			bw.newLine();
			bw.write(op.getKeywordFont().getFontStyle()+ "");
			bw.newLine();
			
			bw.write(op.getStringFont().getFontName()+ "");
			bw.newLine();
			bw.write(op.getStringFont().getFontSize()+ "");
			bw.newLine();
			bw.write(op.getStringFont().getFontColor()+ "");
			bw.newLine();
			bw.write(op.getStringFont().getFontStyle()+ "");
			bw.newLine();
				
			bw.write(op.getCommentFont().getFontName()+ "");
			bw.newLine();
			bw.write(op.getCommentFont().getFontSize()+ "");
			bw.newLine();
			bw.write(op.getCommentFont().getFontColor()+ "");
			bw.newLine();
			bw.write(op.getCommentFont().getFontStyle()+ "");
			bw.newLine();
			
			bw.write(op.getLineNumberFont().getFontName()+ "");
			bw.newLine();
			bw.write(op.getLineNumberFont().getFontSize()+ "");
			bw.newLine();
			bw.write(op.getLineNumberFont().getFontColor()+ "");
			bw.newLine();
			bw.write(op.getLineNumberFont().getFontStyle()+ "");
			bw.newLine();
			
			bw.write(op.getTabSize()+ "");
			bw.newLine();
			
			bw.write(op.getHeaderTitle()+ "");
			bw.newLine();
			bw.write(op.getCopyright()+ "");
			bw.newLine();
			
			bw.write(op.getPageOption().getPageType() + "");
			bw.newLine();
			bw.write(op.getMarginTop()+ "");
			bw.newLine();
			bw.write(op.getMarginBottom()+ "");
			bw.newLine();
			bw.write(op.getMarginLeft()+ "");
			bw.newLine();
			bw.write(op.getMarginRight()+ "");
			bw.newLine();
			
			bw.write(op.getShowLineNumber()+ "");
			bw.newLine();
			bw.write(op.getShowPageNumber()+ "");
			bw.newLine();
			bw.write(op.getCodeLanguage()+ "");
			bw.newLine();
			
			bw.write(op.getHeaderAlign()+ "");
			bw.newLine();
			bw.write(op.getCopyrightAlign()+ "");
			bw.newLine();
			
			bw.write(op.getConvertAll()+ "");
			bw.newLine();
			bw.write(op.getConvertPackage()+ "");
			bw.newLine();
			bw.write(op.getConvertFile()+ "");
			bw.newLine();
			bw.write(op.getFileName()+ "");
			bw.newLine();
			bw.write(op.getSaveLocate()+ "");
			
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 파일에서 읽어들인 문자열을 폰트 스타일로 변환합니다.
	 * @param arg 파일에서 읽은 폰트 스타일과 관련된 문자열입니다.
	 * @return 치환된 폰트 스타일입니다.
	 */
	private FontOption.FontStyle convertFontStyle(String arg){
		if(arg.equals("NORMAL")){
			return FontOption.FontStyle.NORMAL;
		}else if(arg.equals("BOLD")){
			return FontOption.FontStyle.BOLD;
		}else if(arg.equals("ITALIC")){
			return FontOption.FontStyle.ITALIC;
		}else if(arg.equals("BOLDITALIC")){
			return FontOption.FontStyle.BOLDITALIC;
		}else if(arg.equals("UNDERLINE")){
			return FontOption.FontStyle.UNDERLINE;
		}else if(arg.equals("STRIKETHRU")){
			return FontOption.FontStyle.STRIKETHRU;
		}else{
			return FontOption.FontStyle.NORMAL;
		}
	}
	
	/**
	 * 파일에서 읽어들인 문자열을 용지 크기로 변환합니다.
	 * @param arg 파일에서 읽은 용지 크기와 관련된 문자열입니다.
	 * @return 치환된 용지 크기입니다.
	 */
	private PageOption.PageType convertPageType(String arg){
		if(arg.equals("A1")){
			return PageOption.PageType.A1;
		}else if(arg.equals("A2")){
			return PageOption.PageType.A2;
		}else if(arg.equals("A3")){
			return PageOption.PageType.A3;
		}else if(arg.equals("A4")){
			return PageOption.PageType.A4;
		}else if(arg.equals("A5")){
			return PageOption.PageType.A5;
		}else if(arg.equals("A6")){
			return PageOption.PageType.A6;
		}else if(arg.equals("B1")){
			return PageOption.PageType.B1;
		}else if(arg.equals("B2")){
			return PageOption.PageType.B2;
		}else if(arg.equals("B3")){
			return PageOption.PageType.B3;
		}else if(arg.equals("B4")){
			return PageOption.PageType.B4;
		}else if(arg.equals("B5")){
			return PageOption.PageType.B5;
		}else if(arg.equals("B6")){
			return PageOption.PageType.B6;
		}else{
			return PageOption.PageType.A4;
		}
	}
	
	/**
	 * 파일에서 읽어들인 문자열을 언어 종류 타입으로 변환합니다.
	 * @param arg 읽어들인 언어 종류와 관련된 문자열입니다.
	 * @return 치환된 언어 종류 타입입니다.
	 */
	private Option.Language convertLanguage(String arg){
		if(arg.equals("JAVA")){
			return Option.Language.JAVA;
		}else if(arg.equals("C")){
			return Option.Language.C;
		}else if(arg.equals("CPP")){
			return Option.Language.CPP;
		}else{
			return Option.Language.JAVA;
		}
	}
	
	/**
	 * 파일에서 읽어들인 문자열을 정렬 위치 타입으로 변환합니다.
	 * @param arg 읽어들인 위치와 관련된 문자열입니다.
	 * @return 치환된 정렬 위치 정보 타입입니다.
	 */
	private Option.Align convertAlign(String arg){
		if(arg.equals("LEFT")){
			return Option.Align.LEFT;
		}else if(arg.equals("RIGHT")){
			return Option.Align.RIGHT;
		}else if(arg.equals("CENTER")){
			return Option.Align.CENTER;
		}else{
			return Option.Align.CENTER;
		}
	}
}
