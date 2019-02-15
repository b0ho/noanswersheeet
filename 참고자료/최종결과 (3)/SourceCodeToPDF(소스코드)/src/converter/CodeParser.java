package converter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import option.Option;

/**
 * 주어진 소스코드 파일을 분석합니다.
 * @author 강승민
 */
public class CodeParser {
	/**
	 * 임시 저장용 토큰
	 */
	private Vector<Token> tmpTokens = new Vector<Token>();
	/**
	 * 최종 변환된 토큰
	 */
	private Vector<Token> tokens = new Vector<Token>();
	
	/**
	 * 옵션 값
	 */
	private Option option;
	
	/**
	 * 생성자
	 * @param op 옵션 정보입니다.
	 */
	public CodeParser(Option op){
		option = op;
	}
	
	/**
	 * 소스코드 파일을 분석합니다.
	 * @param code 분석할 소스코드 파일
	 * @return 분석이 끝난 소스코드 파일의 토큰
	 * @throws IOException 파일 읽기에 실패 했을 때 발생합니다.
	 */
	@SuppressWarnings("resource")
	public Vector<Token> parse(File code) throws IOException{
		// 토큰 리스트를 초기화
		tmpTokens = new Vector<Token>();
		tokens = new Vector<Token>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(code));
		} catch (IOException e) {
			Token t = new Token();
			t.setType(Token.tokenType.NORMAL);
			t.setData("파일이 손상되었거나 접근 권한이 없어 변환에 실패햐였습니다.");
			tokens.add(t);
			return tokens;
		}
		
		Token result = new Token();
		String s = "";
		String tabCharactor = "";
		for(int i = 0 ; i < option.getTabSize() ; i++){
			tabCharactor += " ";
		}
		
		while((s = br.readLine()) != null){
			s += "\n";
			// PDF에서 \t 문자가 지원되지 않으므로 적절한 길이의 공백으로 치환합니다.
			s = s.replaceAll("\t", tabCharactor);
			int index = 0;
			if(result.getType() == Token.tokenType.MULTIPLE_LINE_STRING || result.getType() == Token.tokenType.MULTIPLE_LINE_COMMENT){ //이전 토큰이 문자열인 경우 계속해서 문자열 토큰으로 처리하도록 지정
				result = getIndent(s,index,result.getType()); //토큰 분할
			}else{
				result = getIndent(s,index,Token.tokenType.NORMAL); //토큰 분할
			}
			while(result.getData().length() > 0 && result != null){ //불할 된 문자열 길이가 1 이상이고 null이 아닌 경우 계속 분할 시도
				tmpTokens.add(result); //분할된 문자열을 리스트에 저장
				index += result.getData().length(); //문자열의 인덱스를 증가시켜 최초 분할 이후의 계속 분할
				if(result.getType() == Token.tokenType.MULTIPLE_LINE_STRING){ //이전 토큰이 문자열인 경우 계속해서 문자열 토큰으로 처리하도록 지정
					result = new Token(); //새롭게 분할된 토큰을 생성
					result = getIndent(s,index,Token.tokenType.MULTIPLE_LINE_STRING); //토큰 분할
				}else if(result.getType() == Token.tokenType.MULTIPLE_LINE_COMMENT){ //이전 토큰이 문자열인 경우 계속해서 문자열 토큰으로 처리하도록 지정
					result = new Token(); //새롭게 분할된 토큰을 생성
					result = getIndent(s,index,Token.tokenType.MULTIPLE_LINE_COMMENT); //토큰 분할
				}else{
					result = new Token(); //새롭게 분할된 토큰을 생성
					result = getIndent(s,index,Token.tokenType.NORMAL); //토큰 분할
				}
			}
		}
		
		for(int i = 0 ; i < tmpTokens.size() ; i++){ //분할된 토큰 크기만큼 반복
			Token filter = tmpTokens.get(i); //토큰을 가져옴
			while(filter.getData().length() > 0){ //토큰의 텍스트 길이가 1 이상인 경우 키워드 분할 시도
				int tokenIndex = 0; //키워드 위치를 지정할 인덱스 지정
				if(filter.getType() == Token.tokenType.NORMAL || filter.getType() == Token.tokenType.KEYWORD){ //분할된 토큰 타입 값이 2,3(문자열)이 아닌경우에만 키워드 분할 시도 (이후 주석 관련된 부분 추가해야함)
					int[] hasKeyword = keywordMatch(filter); //키워드 분할을 시도하여 인덱스를 구함
					if(hasKeyword[0] == -1){ //분할되지 않은 경우 (-1) 의 처리
						if(filter.getData().length() > 0){ //분할 되지 않은 토큰의 길이가 1 이상인 경우에만 처리
							Token tmp1 = new Token(); //새로운 토큰 생성
							tmp1.setData(filter.getData().substring(tokenIndex)); //분할되지 않은 토큰을 저장
							tokens.add(tmp1); //분할되지 않은 토큰을 저장
							break; //다음 토큰 처리를 위해 While 반복문 탈출
						}
					}else{ //토큰이 분할 된 경우의 처리
						Token tmp1 = new Token(); //키워드의 앞 부분을 저장할 토큰
						tmp1.setData(filter.getData().substring(tokenIndex, hasKeyword[0])); //토큰의 인덱스부터 키워드의 시작부분 -1까지의 문자열 저장
						Token tmp2 = new Token(); //키워드를 저장할 토큰
						tmp2.setData(filter.getData().substring(hasKeyword[0], hasKeyword[0]+hasKeyword[1])); //키워드의 시작부분부터 끝부분 -1까지의 문자열 저장
						tmp2.setType(Token.tokenType.KEYWORD); //키워드 타입인 1을 지정
						tokenIndex += hasKeyword[0]; //새로운 키워드 탐색 위치 지정
						filter.setData(filter.getData().substring(hasKeyword[0]+hasKeyword[1])); //키워드 까지 분할 되고 남은 토큰을 저장. 이후 다시 키워드 처리 반복
						if(tmp1.getData().length() > 0){ //키워드 앞 부분의 잘린 문자열의 길이가 1 이상인 경우에만
							tokens.add(tmp1); //키워드 앞 부분 토큰을 리스트에 저장
						}
						tokens.add(tmp2); //키워드 토큰을 저장
					}
				}else{
					tokens.add(filter); //토큰 타입 값이 문자열,주석인 경우에는 별도 처리 없이 저장
					break; //더 이상 분할할 필요가 없으므로 While 반복문 탈출
				}
			}
		}
		return tokens;
	}
	
	/**
	 * 주어진 문자열을 공백, 문자열, 주석을 기준으로 토큰화 합니다.
	 * @param line 토큰으로 변환할 문자열입니다.
	 * @param startIndex 분석을 시작할 문자열의 위치입니다.
	 * @param type 분석되는 문자열 성질입니다. 여러줄 문자열, 여러줄 주석에서 사용되어집니다. 그 외엔 NORMAL 타입입니다.
	 * @return 공백, 문자열, 주석을 기준으로 분석된 토큰입니다.
	 */
	private Token getIndent(String line, int startIndex, Token.tokenType type){
		Token t = new Token(); //분할할 토큰을 지정
		if(type == Token.tokenType.MULTIPLE_LINE_STRING || type == Token.tokenType.MULTIPLE_LINE_COMMENT){
			t.setType(type);
		}
		//문자열이 null인경우, 길이가 0인경우, 문자 인덱스가 문자열의 끝인경우 처리하지 않음.
		if (line == null || line.length() == 0 || startIndex == line.length()) {
	           return t;
		}
		int i = startIndex; //시작 인덱스 값을 복사하여 저장
		while (line.charAt(i) == ' ') { //문자열이 스페이스바인 경우 계속 인덱스를 증가시킴
			i++;
		}
		//인덱스가 문자열을 초과하지 않고 스페이스바가 아닌 경우의 처리
		while (i < line.length() && line.charAt(i) != ' ') {
			if(t.getType() == Token.tokenType.STRING || t.getType() == Token.tokenType.MULTIPLE_LINE_STRING){
				//해당 라인의 문자열 끝까지 조사했으나 닫는 " 가 없는 경우의 처리를 위한 try..catch문
				try{
					//문자열 인덱스를 계속 증가시키며 ', "가 나올 때 까지 반복
					while((line.charAt(i) != '\"' && line.charAt(i) != '\'') && (i>0 ? line.charAt(i-1) == '\\' : true)){
						i++;
						System.out.println(line.charAt(i-1));
					}
	           		//"..." 구성의 문자열 데이터를 토큰에 저장 후 반환
					t.setType(Token.tokenType.STRING);
	           	    t.setData(line.substring(startIndex, (i+1)));
	           	    return t;
				}catch(StringIndexOutOfBoundsException e){
					//"..." 구성의 문자열 데이터를 토큰에 저장 후 반환
					t.setType(Token.tokenType.MULTIPLE_LINE_STRING); //다중열을 가진 문자열로 지정
					t.setData(line.substring(startIndex, i));
					return t;
				}
			}else if(t.getType() == Token.tokenType.MULTIPLE_LINE_COMMENT){
				//해당 라인의 문자열 끝까지 조사했으나 닫는 */ 가 없는 경우의 처리를 위한 try..catch문
				try{
					//문자열 인덱스를 계속 증가시키며 */가 나올 때 까지 반복
					i++;
					while(line.charAt(i-1) != '*' || line.charAt(i) != '/'){
						i++;
					}
					t.setType(Token.tokenType.COMMENT);
					t.setData(line.substring(startIndex, (i+1)));
					return t;
				}catch(StringIndexOutOfBoundsException e){
					t.setType(Token.tokenType.MULTIPLE_LINE_COMMENT);
					t.setData(line.substring(startIndex));
					return t;
				}
			}
			//스페이스 바 이후의 문자열이 문자열을 나타내는 ' 혹은 " 인 경우의 처리
			if((line.charAt(i) == '\"' || line.charAt(i) == '\'') && (i>0 ? line.charAt(i-1) != '\\' : true)){
				//증가 된 인덱스가 최초 시작 인덱스 보다 큰 경우, 즉 최초 분할 시작 된 문자열부터 ', " 직전 까지의 문자열에 대한 처리
				if(i > startIndex){
					t.setData(line.substring(startIndex, i)); //', " 앞 까지의 문자열을 토큰에 담아 반환
					return t;
				}
				t.setType(Token.tokenType.STRING); //', " 이후의 경우 토큰의 타입을 문자열로 지정
				if(line.charAt(i) == '\"'){ //발견된 문자가 " 인 경우 그 뒤에 나오는 " 까지의 반복
					i++;
					//해당 라인의 문자열 끝까지 조사했으나 닫는 " 가 없는 경우의 처리를 위한 try..catch문
					try{
						//문자열 인덱스를 계속 증가시키며 "가 나올 때 까지 반복
						while(line.charAt(i) != '\"' || (i>0 ? (line.charAt(i-1) == '\\' && (i > 1 ? line.charAt(i-2) != '\\' : true)) : true)){
							i++;
						}
					}catch(StringIndexOutOfBoundsException e){
						//"..." 구성의 문자열 데이터를 토큰에 저장 후 반환
						t.setType(Token.tokenType.MULTIPLE_LINE_STRING); //다중열을 가진 문자열로 지정
						t.setData(line.substring(startIndex, i));
						return t;
					}
					//"..." 구성의 문자열 데이터를 토큰에 저장 후 반환
					t.setData(line.substring(startIndex, (i+1)));
					return t;
				}else if(line.charAt(i) == '\''){ //발견된 문자가 '인 경우 그 뒤에 나오는 '까지의 반복
					i++;
					//해당 라인의 문자열 끝까지 조사했으나 닫는 ' 가 없는 경우의 처리를 위한 try..catch문
					try{
						//문자열 인덱스를 계속 증가시키며 '가 나올 때 까지 반복
						while(line.charAt(i) != '\'' || (i>0 ? (line.charAt(i-1) == '\\' && (i > 1 ? line.charAt(i-2) != '\\' : true)) : true)){
							i++;
						}
					}catch(StringIndexOutOfBoundsException e){
						//'...' 구성의 문자열 데이터를 토큰에 저장 후 반환
						t.setType(Token.tokenType.MULTIPLE_LINE_STRING); //다중열을 가진 문자열로 지정
						t.setData(line.substring(startIndex, i));
						return t;
					}
					//'...' 구성의 문자열 데이터를 토큰에 저장 후 반환
					t.setData(line.substring(startIndex, (i+1)));
					return t;
				}
			}
			//싱글라인 주석에 관한 처리
			if(i > 0){
				if(line.charAt(i-1) == '/' && line.charAt(i) == '/'){ // 싱글라인 주석에 관한 처리
					if(i-1 > startIndex){ //i-1이 시작 인덱스보다 크면 (최초 시작된 인덱스가 주석과 관련된 키워드가 아니면)
						t.setData(line.substring(startIndex, i-1)); // // 앞 까지의 문자열을 토큰에 담아 반환
						return t;
					}
					t.setType(Token.tokenType.COMMENT); // 타입을 싱글라인 주석으로 지정
					i++;
					while(line.charAt(i) != '\n'){ // 라인피드가 나오기 전 까지 반복하여 i를 증가
						i++;
					}
					t.setData(line.substring(startIndex, i+1));
					return t; // 최종적으로 확정된 라인을 반환
				}
				if(line.charAt(i-1) == '/' && line.charAt(i) == '*'){ //다중라인 주석에 관한 처리
					if(i-1 > startIndex){ //i-1이 시작 인덱스보다 크면 (최초 시작된 인덱스가 주석과 관련된 키워드가 아니면)
						t.setData(line.substring(startIndex, i-1)); // // 앞 까지의 문자열을 토큰에 담아 반환
						return t;
					}
					t.setType(Token.tokenType.MULTIPLE_LINE_COMMENT); // 타입을 다중라인 주석으로 지정
					i++;
					//해당 라인의 문자열 끝까지 조사했으나 닫는 */ 가 없는 경우의 처리를 위한 try..catch문
					try{
						//문자열 인덱스를 계속 증가시키며 */가 나올 때 까지 반복
						while(line.charAt(i-1) != '*' || line.charAt(i) != '/'){
							i++;
						}
					}catch(StringIndexOutOfBoundsException e){
						///*...*/ 구성의 문자열 데이터를 토큰에 저장 후 반환
						t.setType(Token.tokenType.MULTIPLE_LINE_COMMENT); //다중라인 주석을 위한 처리
						t.setData(line.substring(startIndex));
						return t;
					}
					///*...*/ 구성의 문자열 데이터를 토큰에 저장 후 반환
					t.setType(Token.tokenType.COMMENT); //다중라인 주석을 위한 처리
					t.setData(line.substring(startIndex, (i+1)));
					return t;
				}
			}
			i++; //문자열 인덱스를 계속 증가시킴.
		}
		t.setData(line.substring(startIndex, i)); //문자열을 토큰에 담아 반환
		return t;
	}
	
	/**
	 * 문자열과 주석으로 구분되어진 토큰 이외의 일반 토큰에서 키워드가 있는 토큰을 찾습니다.
	 * @param inp 키워드가 있는지의 여부를 탐색할 토큰입니다.
	 * @return 키워드를 기준으로 새롭게 분할된 토큰입니다.
	 */
	private int[] keywordMatch(Token inp){
		//키워드 리스트
		String[] key;
		switch (option.getCodeLanguage()){
		case JAVA:
			key = KeywordSet.keyword_JAVA;
			break;
		case C:
			key = KeywordSet.keyword_C;
			break;
		case CPP:
			key = KeywordSet.keyword_CPP;
			break;
		default:
			key = KeywordSet.keyword_JAVA;
			break;
		}
		int[] ret = {-1,-1}; //최초 문자 인덱스 설정. -1은 분할되지 않음을 뜻함
		if(inp == null || inp.getData().length() == 0) { //입력값이 null이 거나 길이가 0인경우 분할되지 않은 상태로 반환
			return ret;
		}
		int startIndex = 0; //시작 인덱스를 0으로 초기화
		int keyLength = key[0].length(); //키워드의 문자 길이를 저장
		
		while(startIndex <= inp.getData().length()){ //인덱스를 문자열 끝까지 증가시키며 반복
			for(int i = 0 ; i < key.length ; i++){ //각 키에 따른 반복
				keyLength = key[i].length(); //키워드의 길이를 확인
				try{ //인덱스 초과에 대한 에러 처리가 있음.
					//시작 인덱스부터 시작인덱스+키인덱스 까지의 문자열이 키워드 값과 같은 경우의 처리
					if(inp.getData().substring(startIndex, startIndex+keyLength).equals(key[i])){
						//만약 시작 인덱스가 0이고 전체 문자의 길이가 키워드의 길이와 같은 경우
						if(startIndex == 0 && startIndex+keyLength == inp.getData().length()){
							ret[0] = startIndex; //시작 인덱스 값을 지정
							ret[1] = keyLength; //끝 인덱스 값을 지정
							return ret; //토큰 반환
						}
						//만약 시작 인덱스가 1 이상이고 인덱스+키 길이가 전체 문자열 길이와 같은 경우
						//즉 키워드 앞 쪽에는 문자가 있으나 키워드 뒤에는 문자가 없는 경우
						if(startIndex > 0 && startIndex+keyLength == inp.getData().length()){
							//키워드 앞쪽의 문자 1개가 알파벳,숫자,한글인 경우 해당 키워드는 실질적인 키워드가 아니므로 처리하지 않음
							if(!String.valueOf(inp.getData().charAt(startIndex - 1)).matches("[A-Za-z0-9가-힣ㄱ-ㅎ]")){
								//키워드 앞쪽의 문자 1개가 알파벳,숫자,한글이 아닌 경우 해당 키워드의 위치를 저장하여 반환
								ret[0] = startIndex;
								ret[1] = keyLength;
								return ret;
							}
						}
						//만약 시작인덱스 + 키워드 길이가 전체 문자열 길이보다 작은 경우
						//즉 키워드 뒤 쪽에 문자가 있는 경우
						if(startIndex+keyLength < inp.getData().length()){
							//해당 키워드 뒤쪽의 문자가 알파벳,숫자,한글인 경우 해당 키워드는 실질적인 키워드가 아니므로 처리하지 않음
							if(!String.valueOf(inp.getData().charAt(startIndex+keyLength)).matches("[A-Za-z0-9가-힣ㄱ-ㅎ]") && !String.valueOf(inp.getData().charAt(startIndex - 1)).matches("[A-Za-z0-9가-힣ㄱ-ㅎ]")){
								//키워드 뒤쪽의 문자 1개가 알파벳,숫자,한문이 아닌 경우 해당 키워드의 위치를 저장하여 반환
								ret[0] = startIndex;
								ret[1] = keyLength;
								return ret;
							}
						}
					}
				}catch(StringIndexOutOfBoundsException e){
					continue; //인덱스를 초과한 경우 다음 키워드 검사 시작
				}
			}
			startIndex++; //인덱스에 대한 모든 키 검사가 끝난 경우 다음 인덱스에서부터 새롭게 조사
		}
		return ret; //모든 조사가 끝난 토큰을 반환
	}
}
