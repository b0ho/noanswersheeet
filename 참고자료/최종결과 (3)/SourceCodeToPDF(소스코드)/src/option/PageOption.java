package option;
/**
 * 페이지와 관련된 옵션 클래스 입니다.
 * @author 오다솜
 *
 */
public class PageOption {
	/**
	 * 용지의 크기(타입) 데이터 입니다.
	 * @author 오다솜
	 *
	 */
	public enum PageType {A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5, B6};
	
	/**
	 * 용지의 위쪽 여백입니다.
	 */
	private int marginTop;
	
	/**
	 * 용지의 아래쪽 여백입니다.
	 */
	private int marginBottom;
	
	/**
	 * 용지의 왼쪽 여백입니다.
	 */
	private int marginLeft;
	
	/**
	 * 용지의 오른쪽 여백입니다.
	 */
	private int marginRight;
	
	/**
	 * 용지의 크기 입니다.
	 */
	private PageType type;
	
	/**
	 * 용지의 크기를 설정합니다.
	 * @param type 용지의 크기
	 */
	public void setPageType(PageType type){
		this.type = type;
	}
	
	/**
	 * 용지의 크기를 반환합니다.
	 * @return 용지의 크기
	 */
	public PageType getPageType(){
		return this.type;
	}
	
	/**
	 * 용지의 위쪽 여백을 지정합니다.
	 * @param margin 위쪽 여백
	 */
	public void setMarginTop(int margin){
		if(margin < 0 || margin >= 100){
			margin = 30;
		}
		marginTop = margin;
	}
	/**
	 * 용지의 위쪽 여백값을 반환합니다.
	 * @return 위쪽 여백
	 */
	public int getMarginTop(){
		return marginTop;
	}

	/**
	 * 용지의 아랫쪽 여백을 지정합니다.
	 * @param margin 아래쪽 여백
	 */
	public void setMarginBottom(int margin) {
		if(margin < 0 || margin >= 100){
			margin = 30;
		}
		marginBottom = margin;
	}
	/**
	 * 용지의 아래쪽 여백을 반환합니다.
	 * @return 아래쪽 여백
	 */
	public int getMarginBottom(){
		return marginBottom;
	}

	/**
	 * 용지의 왼쪽 여백을 지정합니다.
	 * @param margin 왼쪽 여백
	 */
	public void setMarginLeft(int margin) {
		if(margin < 0 || margin >= 100){
			margin = 40;
		}
		marginLeft = margin;
	}
	/**
	 * 용지의 왼쪽 여백을 반환합니다.
	 * @return 왼쪽 여백
	 */
	public int getMarginLeft(){
		return marginLeft;
	}

	/**
	 * 용지의 오른쪽 여백을 지정합니다.
	 * @param margin 오른쪽 여백
	 */
	public void setMarginRight(int margin) {
		if(margin < 0 || margin >= 100){
			margin = 40;
		}
		marginRight = margin;
	}
	/**
	 * 용지의 오른쪽 여백을 반환합니다.
	 * @return 오른쪽 여백
	 */
	public int getMarginRight(){
		return marginRight;
	}
}
