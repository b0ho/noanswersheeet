package loader.test;

import java.io.IOException;

import org.junit.Test;
import loader.Checker;

/**
 * Checker를 테스트하는 클래스
 * @author 강승민
 *
 */
public class CheckerTest {

	/**
	 * 파일 탐색 테스트
	 */
	@Test
	public void testExplorerFile() {
		Checker c = new Checker();
		String[] ext = {".java"};
		c.explorerFile("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter\\ConvertPrograssEvent.java", ext);
		
		System.out.println("File List : " + c.fileList);
		System.out.println("File Path List : " + c.fileListString);
		System.out.println();
	}

	/**
	 * 폴더 탐색 테스트
	 */
	@Test
	public void testExplorerDirectory() {
		Checker c = new Checker();
		String[] ext = {".java"};
		try {
			c.explorerDirectory("C:\\eclipse\\workspace\\SourceCodeToPDF\\src\\converter", ext);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		System.out.println("Directory List : " + c.dirList);
		System.out.println("Directory Path List : " + c.dirListString);
		System.out.println("File List : " + c.fileList);
		System.out.println("File Path List : " + c.fileListString);
	}

}
