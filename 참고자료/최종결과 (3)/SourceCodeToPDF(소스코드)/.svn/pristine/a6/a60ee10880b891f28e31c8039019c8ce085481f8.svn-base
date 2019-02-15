package converter;

import java.util.EventObject;

/**
 * 변환 진행률에 관련된 이벤트 입니다.
 * @author 강승민
 *
 */
public class ConvertPrograssEvent extends EventObject {

	private static final long serialVersionUID = 7231650191710081939L;
	
	/**
	 * 현재 진행률
	 */
	private int currentPrograss;
	
	/**
	 * 전체 변환량
	 */
	private int totalPrograss;
	
	/**
	 * 이벤트 생성자
	 * @param source 이벤트 발생 주체입니다.
	 * @param currentPrograss 현재 진행률 입니다.
	 * @param totalPrograss 전체 변환량 입니다.
	 */
	public ConvertPrograssEvent(Object source, int currentPrograss, int totalPrograss) {
		super(source);
		this.currentPrograss = currentPrograss;
		this.totalPrograss = totalPrograss;
	}

	/**
	 * 현재 변환 진행률을 확인합니다.
	 * @return 현재 변환 진행률
	 */
	public int getCurrentPrograss(){
		return this.currentPrograss;
	}
	
	/**
	 * 전체 변환 량을 확인합니다.
	 * @return 전체 변환량
	 */
	public int getTotalPrograss(){
		return this.totalPrograss;
	}
}
