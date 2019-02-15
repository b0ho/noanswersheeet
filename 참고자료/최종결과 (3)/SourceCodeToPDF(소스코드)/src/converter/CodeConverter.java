package converter;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.itextpdf.text.DocumentException;

import loader.Checker;
import option.*;

/**
 * 소스코드를 PDF로 변환하기 위한 클래스 입니다.<br>
 * 이 클래스는 분석기,출력기와 UI 사이에서 동작하며 중간 작업들을 처리합니다.<p>
 * 이 클래스와 하위 Parser, Printer는 별도의 스레드로 동작하며 이벤트를 발생시킵니다.
 * @author 강승민
 *
 */
public class CodeConverter extends Thread{
	
	/**
	 * 코드 분석기
	 */
	private CodeParser parser;
	
	/**
	 * 코드 출력기
	 */
	private CodePrinter printer;
	
	/**
	 * 분석기에서 생성된 토큰 벡터
	 */
	private Vector<Vector<Vector<Token>>> codeToken = new Vector<Vector<Vector<Token>>>(); 
	
	/**
	 * 파일 정보를 가져올 Checker입니다.
	 */
	private Checker checker;
	
	/**
	 * 옵션 정보 속성
	 */
	private Option option;
	
	/**
	 * 보안 정보 속성
	 */
	private Security security;
	
	/**
	 * 변환 이벤트 처리 리스너 리스트
	 */
	private EventListenerList listenerList = new EventListenerList();
	
	/**
	 * 현재 변환 진행률
	 */
	private int currentPrograss = 0;
	
	/**
	 * 전체 변환량
	 */
	private int totalPrograss;
	
	/**
	 * CodeConverter 생성자
	 * @param chker Checker
	 * @param op Option
	 * @param sec Security
	 */
	public CodeConverter(Checker chker, Option op, Security sec){
		checker = chker;
		option = op;
		security = sec;
		parser = new CodeParser(option);
		printer = new CodePrinter(option, security);
		totalPrograss = 0;
		for(int i = 0 ; i < checker.fileList.size() ; i++){
			totalPrograss += checker.fileList.get(i).size();
		}
		if(option.getConvertFile()){
			for(int i = 0 ; i < checker.fileList.size() ; i++){
				totalPrograss += checker.fileList.get(i).size();
			}
		}
		if(option.getConvertPackage() && checker.dirList.size() > 0){
			totalPrograss += checker.dirList.size();
		}
		if(option.getConvertAll() && checker.dirList.size() > 0){
			totalPrograss++;
		}
	}
	
	/**
	 * 변환 작업을 시작합니다.
	 */
	private void convert(){
		int size = checker.fileList.size();
		if(checker.dirList.size() == 0){
			Vector<File> fVector = checker.fileList.get(0);
			for(int j = 0 ; j < fVector.size() ; j++){
				try {
					Vector<Vector<Token>> packageToken = new Vector<Vector<Token>>();
					Vector<Token> parseResult = parser.parse(fVector.get(j));
					packageToken.add(parseResult);
					codeToken.addElement(packageToken);
					currentPrograss++;
					ConvertPrograssEvent evt = new ConvertPrograssEvent(this, currentPrograss, totalPrograss);
					fireConvertPrograssEvent(evt);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			for(int i = 0 ; i < size ; i++){
				Vector<File> fVector = checker.fileList.get(i);
				Vector<Vector<Token>> packageToken = new Vector<Vector<Token>>();
				for(int j = 0 ; j < fVector.size() ; j++){
					try {
						Vector<Token> parseResult = parser.parse(fVector.get(j));
						packageToken.add(parseResult);
						currentPrograss++;
						ConvertPrograssEvent evt = new ConvertPrograssEvent(this, currentPrograss, totalPrograss);
						fireConvertPrograssEvent(evt);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				codeToken.addElement(packageToken);
			}
		}
		
		if(checker.dirList.size() == 0){
			convertFile();
		}else{
			if(option.getConvertAll()){
				convertAll();
			}
			if(option.getConvertPackage()){
				convertPackage();
			}
			if(option.getConvertFile()){
				convertFile();
			}
		}
	}
	
	/**
	 * 변환 진행 이벤트 리스너를 추가합니다.
	 * @param listener 추가 할 이벤트 리스너
	 */
	public void addConvertPrograssListener(IConvertPrograssListener listener){
		listenerList.add(IConvertPrograssListener.class, listener);
	}
	
	/**
	 * 변환 진행 이벤트 리스너를 제거합니다.
	 * @param listener 제거 할 이벤트 리스너
	 */
	public void removeConvertPrograssListener(IConvertPrograssListener listener){
		listenerList.remove(IConvertPrograssListener.class, listener);
	}
	
	/**
	 * 변환 이벤트를 발생시킵니다.
	 * @param event 변환 이벤트
	 */
	private void fireConvertPrograssEvent(ConvertPrograssEvent event){
		EventListener[] listeners = listenerList.getListeners(IConvertPrograssListener.class);
		for(int i = 0 ; i < listeners.length ; i++){
			((IConvertPrograssListener)listeners[i]).convertPrograss((ConvertPrograssEvent)event);
		}
	}
	
	
	/**
	 * 로드된 모든 파일을 한개의 PDF 문서로 만듭니다.
	 */
	private void convertAll(){
		try {
			Vector<String> subName = new Vector<String>();
			for(int i = 0 ; i < checker.fileList.size() ; i++){
				for(int j = 0 ; j < checker.fileList.get(i).size() ; j++){
					subName.add(checker.fileList.get(i).get(j).getName());
				}
			}
			if(checker.dirList.size() != 0){
				printer.printAll(codeToken, subName);
			}
			currentPrograss++;
			ConvertPrograssEvent evt = new ConvertPrograssEvent(this, currentPrograss, totalPrograss);
			fireConvertPrograssEvent(evt);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 로드된 파일을 패키지 단위로 PDF 문서로 만듭니다.
	 */
	private void convertPackage(){
		int size = codeToken.size();
		for(int i = 0 ; i < size ; i++){
			Vector<Vector<Token>> packageToken = codeToken.get(i);
			Vector<File> fVector = checker.fileList.get(i);
			Vector<String> subName = new Vector<String>();
			for(int j = 0 ; j < fVector.size() ; j++){
				subName.add(fVector.get(j).getName());
			}
			try {
				printer.printPackage(packageToken, checker.dirList.get(i).getName(), subName);
				currentPrograss++;
				ConvertPrograssEvent evt = new ConvertPrograssEvent(this, currentPrograss, totalPrograss);
				fireConvertPrograssEvent(evt);
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 로드된 파일을 각 파일 단위로 PDF 문서로 만듭니다.
	 */
	private void convertFile(){
		int size = codeToken.size();
		for(int i = 0 ; i < size ; i++){
			Vector<File> fVector = checker.fileList.get(i);
			Vector<Vector<Token>> packageToken = codeToken.get(i);
			for(int j = 0 ; j < packageToken.size() ; j++){
				try {
					if(packageToken.get(j) != null){
						String tempHeader = option.getHeaderTitle();
						option.setHeaderTitle(fVector.get(j).getName());
						printer.printSingleFile(packageToken.get(j), fVector.get(j).getName());
						option.setHeaderTitle(tempHeader);
					}
					currentPrograss++;
					ConvertPrograssEvent evt = new ConvertPrograssEvent(this, currentPrograss, totalPrograss);
					fireConvertPrograssEvent(evt);
				} catch (DocumentException | IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	/**
	 * 변환 진행 스레드를 시작합니다.
	 */
	@Override
	public void run() {
		convert();
	}
}
