package display;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import loader.Checker;
import option.Option;

/**
 * 선택된 파일 혹은 폴더에 대한 처리 클래스
 * @author 유병호
 *
 */
public class FileSelect{
	/**
	 * 소스코드 정보
	 */
	private Checker checker;
	
	/**
	 * 옵션 정보
	 */
	private Option option;

	/**
	 * 생성자
	 * @param chker 소스코드 정보
	 * @param op 옵션 정보
	 */
	public FileSelect(Checker chker, Option op) {
		checker = chker;
		option = op;
	}
	
	/**
	 * 소스코드 정보를 반환한다.
	 * @return 소스코드 정보
	 */
	public Checker getChecker(){
		return checker;
	}

	/**
	 * 선택한 파일을 처리한다. 
	 * @param f 선택 된 파일
	 */
	public void selectFile(File f) {
		//단일파일 선택 메소드 
		if(option.getCodeLanguage() == Option.Language.JAVA){
			checker.explorerFile(f.toString(), new String[] {".java"});
		}else if(option.getCodeLanguage() == Option.Language.C){
			checker.explorerFile(f.toString(), new String[] {".c", ".h"});
		}else if(option.getCodeLanguage() == Option.Language.CPP){
			checker.explorerFile(f.toString(), new String[] {".cpp", ".h"});
		}
		
		if(checker.fileList.size() == 0){//설정한 확장자와 선택한 확장자가 다를시 나타나는 경고 다이얼로그 
			JOptionPane.showMessageDialog(null, "옵션에 설정된 언어의 확장자와 일치하지 않습니다!", "오류발생", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * 선택한 디렉토리를 처리한다.
	 * @param source 선택된 디렉토리
	 */
	public void selectDirectory(File source){
		checker.clearAllList();
		//디렉토리 선택 메소드
		checker.rootPath = source.getName();
		if(option.getCodeLanguage() == Option.Language.JAVA){
			try {
				checker.explorerDirectory(source.toString(), new String[] {".java"});
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "폴더가 손상되었거나 읽기 권한이 없습니다.", "오류발생", JOptionPane.ERROR_MESSAGE);
			}
		}else if(option.getCodeLanguage() == Option.Language.C){
			try {
				checker.explorerDirectory(source.toString(), new String[] {".c", ".h"});
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "폴더가 손상되었거나 읽기 권한이 없습니다.", "오류발생", JOptionPane.ERROR_MESSAGE);
			}
		}else if(option.getCodeLanguage() == Option.Language.CPP){
			try {
				checker.explorerDirectory(source.toString(), new String[] {".cpp", ".h"});
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "폴더가 손상되었거나 읽기 권한이 없습니다.", "오류발생", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
