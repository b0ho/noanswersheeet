package option;
/**
 * 옵션의 기본값과 관련된 클래스 입니다.
 * @author 오다솜
 *
 */
public class OptionDefault {
   
   /**
    * 새롭게 옵션을 생성하고 기본값으로 지정한 뒤 반환합니다.
    * @return 기본값으로 지정된 옵션입니다.
    */
   public static Option getDefaultOption(){
      Option op = new Option();

      //폰트 파일 경로, 크기, 색상, style순 
      op.setBasicFont("fonts\\malgun.ttf", 11, 0x000000, FontOption.FontStyle.NORMAL); //일반 문자열 표시하는 폰트 옵션 기본값 지정
      op.setKeywordFont("fonts\\malgun.ttf", 11, 0xC13A55, FontOption.FontStyle.NORMAL);//키워드 문자 표시하는 폰트 옵션 기본값 지정
      op.setStringFont("fonts\\malgun.ttf", 11, 0x0000FF, FontOption.FontStyle.NORMAL); //' ' 혹은 " " 형태로 감싸진 문자열 내부를 표시하는 폰트 옵션 기본값 지정
      op.setCommentFont("fonts\\malgun.ttf", 11, 0x3F7F5F, FontOption.FontStyle.NORMAL); //주석 폰트 옵션 기본값 지정
      op.setLineNumberFont("fonts\\malgun.ttf", 11, 0xCCCCCC, FontOption.FontStyle.NORMAL);//라인 넘버 를 표시하는 폰트 옵션 기본값 지정
      
      op.setTabSize(4); //탭 길이 지정      
      
      op.setHeaderTitle("");
      op.setCopyright("");
      
      op.setPageType(PageOption.PageType.A4);
      op.setMargin(30, 30, 40, 40);
      
      op.setShowLineNumber(true);
      op.setShowPageNumber(true);
      
      op.setCodeLanguage(Option.Language.JAVA);
      
      op.setHeaderAlign(Option.Align.CENTER);
      op.setCopyrightAlign(Option.Align.RIGHT);
      
      op.setConvertAll(true);
      op.setConvertPackage(true);
      op.setConvertFile(true);
      
      op.setFileName("__ConvertAllFile__");
      op.setSaveLocate(System.getProperty("user.dir") + "\\");
      
      return op;
   }
   
   public static void getDefaultOption(Option op){
      //폰트 파일 경로, 크기, 색상, style순 
      op.setBasicFont("fonts\\malgun.ttf", 11, 0x000000, FontOption.FontStyle.NORMAL); //일반 문자열 표시하는 폰트 옵션 기본값 지정
      op.setKeywordFont("fonts\\malgun.ttf", 11, 0xC13A55, FontOption.FontStyle.NORMAL);//키워드 문자 표시하는 폰트 옵션 기본값 지정
      op.setStringFont("fonts\\malgun.ttf", 11, 0x0000FF, FontOption.FontStyle.NORMAL); //' ' 혹은 " " 형태로 감싸진 문자열 내부를 표시하는 폰트 옵션 기본값 지정
      op.setCommentFont("fonts\\malgun.ttf", 11, 0x3F7F5F, FontOption.FontStyle.NORMAL); //주석 폰트 옵션 기본값 지정
      op.setLineNumberFont("fonts\\malgun.ttf", 11, 0xCCCCCC, FontOption.FontStyle.NORMAL);//라인 넘버 를 표시하는 폰트 옵션 기본값 지정
      
      op.setTabSize(4); //탭 길이 지정      
      
      op.setHeaderTitle("");
      op.setCopyright("");
      
      op.setPageType(PageOption.PageType.A4);
      op.setMargin(30, 30, 40, 40);
      
      op.setShowLineNumber(true);
      op.setShowPageNumber(true);
      
      op.setCodeLanguage(Option.Language.JAVA);
      
      op.setHeaderAlign(Option.Align.CENTER);
      op.setCopyrightAlign(Option.Align.RIGHT);
      
      op.setConvertAll(true);
      op.setConvertPackage(true);
      op.setConvertFile(true);
      
      op.setFileName("__ConvertAllFile__");
      op.setSaveLocate(System.getProperty("user.dir") + "\\");
   }
}