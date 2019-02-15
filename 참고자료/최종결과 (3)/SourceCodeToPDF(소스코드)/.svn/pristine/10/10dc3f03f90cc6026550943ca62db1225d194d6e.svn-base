package converter;
/**
 * 처리기의 규칙을 통해 생성되는 문자열의 최소 단위입니다.
 * @author 강승민
 *
 */
public class Token {
	/**
	 * 문자열 값입니다.
	 */
	private String data = "";
	/**
	 * 문자열의 타입입니다.
	 */
	private tokenType type;
	
	/**
	 * 토큰이 가지는 문자열 타입입니다.<p>
	 * 타입 종류<br>
	 * NORMAL : 특정한 성질이 없는 기본 토큰입니다.<br>
	 * KEYWORD : if, for등과 같은 언어에서 지정된 키워드 성질입니다.<br>
	 * STRING : ' ' 혹은 " " 형태로 감싸진 문자열 성질입니다.<br>
	 * MULTIPLE_LINE_STRING : ' ' 혹은 " " 형태가 여러줄에 걸처 나타나는 문자열 성질입니다.<br>
	 * COMMENT : 주석 성질입니다.<br>
	 * MULTIPLE_LINE_COMMENT : 여러줄 주석 성질입니다.<br>
	 * @author 강승민
	 *
	 */
	public enum tokenType {NORMAL, KEYWORD, STRING, MULTIPLE_LINE_STRING, COMMENT, MULTIPLE_LINE_COMMENT};
	
	/**
	 * 토큰을 생성합니다.
	 * 생성된 토큰은 빈 문자열에 NORMAL 성질을 가집니다.
	 */
	public Token(){
		setType(Token.tokenType.NORMAL);
	}
	
	/**
	 * 토큰의 문자열 데이터를 반환합니다.
	 */
	@Override
	public String toString(){
		return data;
	}

	/**
	 * 토큰의 타입을 반환합니다.
	 * @return 토큰의 타입
	 */
	public tokenType getType() {
		return type;
	}

	/**
	 * 토큰의 타입을 지정합니다.
	 * @param type 토큰의 타입
	 */
	public void setType(tokenType type) {
		this.type = type;
	}
	
	/**
	 * 토큰의 문자열 데이터를 지정합니다.
	 * @param data 토큰 문자열
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * 토큰의 문자열 데이터를 반환합니다.
	 * @return 토큰 문자열
	 */
	public String getData() {
		return data;
	}
}
