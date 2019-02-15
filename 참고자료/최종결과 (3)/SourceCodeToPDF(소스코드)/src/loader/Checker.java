package loader;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * 소스코드 검사 클래스
 * @author 유병호
 *
 */
public class Checker {
	
	/**
	 * 변환할 디렉토리(패키지) 경로 리스트
	 */
	public Vector<String> dirListString = new Vector<String>(); // 변환할 패키지 목록 저장 (스트링 벡터)
	
	/**
	 * 변환할 파일 경로 리스트
	 */
	public Vector<String> fileListString = new Vector<String>(); // 변환할 파일 목록 저장 (스트링 벡터)
	
	/**
	 * 변환되지 않는 필터링 된 파일 경로 리스트
	 */
	public Vector<String> failListString = new Vector<String>(); // 필터링 될 파일 목록 (스트링 벡터)
	
	/**
	 * 변환할 디렉토리(패키지) 리스트
	 */
	public Vector<File> dirList = new Vector<File>(); // 변환할 패키지 목록 저장 (파일 벡터)
	
	/**
	 * 변환할 파일 리스트 [각 패키지 별 [파일 리스트]] 형태
	 */
	public Vector<Vector<File>> fileList = new Vector<Vector<File>>(); // 변환할 파일 목록 저장 (파일 벡터)
	
	/**
	 * 변환되지 않는 필터링 된 파일 리스트
	 */
	public Vector<File> failList = new Vector<File>(); // 필터링 될 파일 목록 (파일 벡터)
	
	/**
	 * 사용자가 선택한 경로
	 */
	public String rootPath;
	
	/**
	 * 필터링 용 임시 경로
	 */
	private String source = "";

	/**
	 * 파일 필터링 메소드
	 * @param source 필터링 할 파일 경로
	 * @param ext 필터링 확장자 리스트
	 */
	public void explorerFile(String source, String[] ext){
		clearAllList();
		boolean find = false;
		for(int i = 0 ; i < ext.length ; i++){
			find = source.endsWith(ext[i]);
			if(find) break;
		}
		
		if(find){
			Vector<File> newVector = new Vector<File>();
			File f = null;
			try{
				f = new File(source);
			}catch(NullPointerException e){
				return;
			}
			newVector.addElement(f);
			this.fileList.addElement(newVector);
			this.fileListString.addElement(stringReplace(f));
		}else{
			File f = new File(source);
			this.failList.addElement(f);
			this.failListString.addElement(stringReplace(f));
		}
	}
	
	/**
	 * 디렉토리 필터링 메소드
	 * @param source 필터링 할 디렉토리 경로
	 * @param ext 필터링 확장자 리스트
	 * @throws IOException 파일 읽기에 문제가 발생한 경우 발생합니다.
	 */
	public void explorerDirectory(String source, String[] ext) throws IOException { // 지정된 경로부터 탐색 하는 메소드
		File start = null;
		try{
			start = new File(source);
		}catch(NullPointerException e){
			throw new IOException();
		}
		
		File[] file_list = null;
		try{
			file_list = start.listFiles();
		}catch(SecurityException e){
			throw new IOException();
		}
		Vector<File> fileVector = new Vector<File>();
		try {
			if(file_list == null){
				throw new IOException();
			}
			for (int i = 0; i < file_list.length; i++) { // 시작 폴더부터 모든 하위 폴더 까지 검사
				File file_list_tmp = null;
				try{
					file_list_tmp = file_list[i]; // file_list_tmp에 하나씩 파일을 받음
				}catch(NullPointerException e){
					continue;
				}
				
				String file_name = file_list[i].toString().toLowerCase(); // file_name에 파일 이름을 string으로 저장
				boolean check_type = false;
				for(int typeChk = 0 ; typeChk < ext.length ; typeChk++){
					check_type = file_name.endsWith(ext[typeChk]);
					if(check_type) break;
				}
				if (file_list_tmp.isFile() && !check_type) { // 파일이지만 타입이 일치하지 않을 경우
					this.failListString.addElement(stringReplace(file_list_tmp)); // 해당하는 파일을 필터링 파일 목록에 저장
					this.failList.addElement(file_list_tmp); // 해당하는 파일을 필터링 파일 목록에 저장 (파일 벡터)
				}
				if (file_list_tmp.isFile() && check_type) { // 파일이면서 타입이 일치하는 경우
					this.fileListString.addElement(stringReplace(file_list_tmp)); // 해당하는 파일을 변환할 파일 목록에 저장
					fileVector.addElement(file_list_tmp); // 해당하는 파일을 변환할 파일 목록에 저장 (파일 벡터)
					String dir = source.replaceAll("\\\\", "/"); // 문자열 처리
					if (!dirListString.contains(dir)) { // 같은 이름의 패키지 명이 없을 경우
						dirListString.addElement(dir); // 패키지 그룹에 경로 추가
						dirList.addElement(start); // 패키지 그룹에 경로 추가 (파일 벡터)
					}
				} else if (file_list_tmp.isDirectory()) { // 폴더일 경우
					explorerDirectory(file_list_tmp.getCanonicalPath().toString(), ext); // 하위 경로로 이동
				}
			}
			if(fileVector.size() != 0){
				this.fileList.addElement(fileVector);
			}
		} catch (IOException e) {
			return;
		}
	}
	
	/**
	 * 모든 리스트를 초기화 합니다.
	 */
	public void clearAllList(){
		dirList.removeAllElements();
		fileList.removeAllElements();
		failList.removeAllElements();
		dirListString.removeAllElements();
		fileListString.removeAllElements();
		failListString.removeAllElements();
	}

	/**
	 * \ 형태의 파일 경로 구분을 / 형태로 치환
	 * @param file 경로를 치환할 파일
	 * @return 치환된 경로
	 */
	private String stringReplace(File file) { // 문자열 처리
		String rep = this.source.replaceAll("\\\\", "/");
		String path = this.source + file.getPath().replaceAll("\\\\", "/").replaceAll(rep, "");
		return path;
	}

}