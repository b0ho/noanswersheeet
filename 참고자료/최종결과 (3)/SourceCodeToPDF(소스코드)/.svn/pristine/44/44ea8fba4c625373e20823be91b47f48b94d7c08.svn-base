package converter.test;

import java.io.File;
import java.util.Vector;
import org.junit.Test;
import converter.*;
import loader.*;
import option.*;

/**
 * CodeConverter 클래스를 테스트하는 클래스
 * @author 강승민
 *
 */
public class CodeConverterTest {

	/**
	 * Run 메소드 (변환 메소드) 를 테스트하는 메소드
	 */
	/*@Test
	public void testRun() {
		Checker c = new Checker();
		Vector<File> v = new Vector<File>();
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\CodeParser.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\CodeConverter.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\CodePrinter.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\ConvertPrograssEvent.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\CopyRightEvent.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\IConvertPrograssListener.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\KeywordSet.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\ConvertPrograssEvent.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\Token.java"));
		
		Vector<File> v2 = new Vector<File>();
		v2.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\test\\CodeConverterTest.java"));
		c.fileList.add(v);
		c.fileList.add(v2);
		
		File dir1 = new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter");
		c.dirList.add(dir1);

		File dir2 = new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\test");
		c.dirList.add(dir2);
		
		Option op = OptionDefault.getDefaultOption();
		
		Security sec = new Security();
		sec.setUseSecurityOption(false);
		
		CodeConverter conv = new CodeConverter(c, op, sec);
		
		conv.run();
	}*/
	
	/**
	 * Run 메소드 및 이벤트 처리 테스트
	 */
	@Test
	public void testAddConvertPrograssListener_Run(){
		Checker c = new Checker();
		Vector<File> v = new Vector<File>();
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\CodeParser.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\CodeConverter.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\CodePrinter.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\ConvertPrograssEvent.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\CopyRightEvent.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\IConvertPrograssListener.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\KeywordSet.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\ConvertPrograssEvent.java"));
		v.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\Token.java"));
		
		Vector<File> v2 = new Vector<File>();
		v2.add(new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\test\\CodeConverterTest.java"));
		c.fileList.add(v);
		c.fileList.add(v2);
		
		File dir1 = new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter");
		c.dirList.add(dir1);

		File dir2 = new File("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\test");
		c.dirList.add(dir2);
		
		Option op = OptionDefault.getDefaultOption();
		
		Security sec = new Security();
		sec.setUseSecurityOption(false);
		
		CodeConverter conv = new CodeConverter(c, op, sec);
		
		conv.addConvertPrograssListener(new IConvertPrograssListener() {
			@Override
			public void convertPrograss(ConvertPrograssEvent event) {
				System.out.println(event.getCurrentPrograss() + " / " + event.getTotalPrograss());
			}
		});
		
		conv.run();
	}
}
