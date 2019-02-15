package option;

/**
 * 보안 옵션 클래스 입니다.
 * @author 유병호
 *
 */
public class Security {
	/**
	 * 보안 옵션의 사용 여부입니다.
	 */
	private boolean useSecurityOption = false;
	
	/**
	 * 복사 가능 여부입니다.
	 */
	private boolean useCopyMode = true;
	
	/**
	 * 비밀번호 사용 여부입니다.
	 */
	private boolean usePassword = false;
	
	/**
	 * 비밀번호 입니다.
	 */
	private String password = "";
	
	
	/**
	 * 보안 옵션 사용 여부를 설정합니다.
	 * @param arg 사용여부<br>
	 * true : 사용<br>
	 * false : 사용하지 않음
	 */
	public void setUseSecurityOption(boolean arg){
		useSecurityOption = arg;
	}
	
	/**
	 * PDF에 기록된 소스코드의 복사 여부를 지정합니다.
	 * @param arg 복사 여부<br>
	 * true : 복사 가능 <br>
	 * false: 복사 불가능
	 */
	public void setUseCopyMode(boolean arg){
		useCopyMode = arg;
	}
	
	/**
	 * 문서의 패스워드 사용 여부를 지정합니다.
	 * @param arg 패스워드 사용 여부<br>
	 * true : 패스워드 사용<br>
	 * false: 패스워드를 사용하지 않음
	 */
	public void setUsePassword(boolean arg){
		usePassword = arg;
	}
	
	/**
	 * 문서의 패스워드를 지정합니다.
	 * @param pwd 패스워드<br>
	 * 패스워드를 사용하지 않음으로 설정되어 있을 경우 지정된 값은 사용되지 않습니다.
	 */
	public void setPassword(String pwd){
		password = pwd;
	}
	
	/**
	 * 보안 기능 사용 여부를 반환합니다.
	 * @return 보안 기능 사용 여부
	 */
	public boolean getUseSecurityOption(){
		return useSecurityOption;
	}
	
	/**
	 * 복사 가능 여부를 반환합니다.
	 * @return 복사 가능 여부
	 */
	public boolean getUseCopyMode(){
		return useCopyMode;
	}
	
	/**
	 * 비밀번호 사용 여부를 반환합니다.
	 * @return 비밀번호 사용 여부
	 */
	public boolean getUsePassword(){
		return usePassword;
	}
	
	/**
	 * 비밀번호를 반환합니다.
	 * @return 비밀번호
	 */
	public String getPassword(){
		return password;
	}
}
