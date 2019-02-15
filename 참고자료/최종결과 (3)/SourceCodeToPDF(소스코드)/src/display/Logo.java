package display;

import java.awt.*;
import javax.swing.*;

import option.OptionFile;
import option.Option;

/**
 * 최초 실행 시 보이는 로고 클래스입니다.
 * 
 * @author 김선일
 *
 */
public class Logo implements Runnable {

	/**
	 * 사용자 모니터 해상도를 가져오기 위한 속성
	 */
	private Toolkit tk = Toolkit.getDefaultToolkit();

	/**
	 * 사용자 모니터의 가로 해상도
	 */
	private int screensize_width = (int) tk.getScreenSize().getWidth();

	/**
	 * 사용자 모니터의 세로 해상도
	 */
	private int screensize_height = (int) tk.getScreenSize().getHeight();// 화면 해상도의 세로

	/**
	 * 프레임 가로 크기
	 */
	private int framesize_width;

	/**
	 * 프레임 세로 크기
	 */
	private int framesize_height;

	/**
	 * 로고 표시 프레임
	 */
	private JDialog logo_frame;

	/**
	 * 옵션 파일 처리
	 */
	private OptionFile of = new OptionFile();

	/**
	 * 옵션 파일
	 */
	private Option option;

	/**
	 * 초기화를 위한 생성자
	 */
	public Logo() {
		init();
	}

	/**
	 * 초기화 메서드 옵션 파일 로드와 프레임 위치 지정 작업 수행
	 */
	public void init() {
		option = of.readOptionFile();

		ImageIcon logo_img_icon = new ImageIcon("logo.png");// 로고에 쓰일 이미지 변수 선언

		logo_frame = new JDialog(); // 로고 프레임 생성
		logo_frame.setPreferredSize(new Dimension(300, 300)); // 로고 프레임의 크기 설정
		setFrameLocation(logo_frame.getPreferredSize(), logo_frame); // 로고를 화면 정 가운데 위치하게 함
		JLabel logo_img_label = new JLabel(logo_img_icon); // 로고이미지를 라벨로 선언
		logo_frame.add(logo_img_label); // 로고이미지 라벨을 프레임에 부착
	}

	/**
	 * 프레임 위치 지정 메서드
	 * 
	 * @param d 프레임의 위치
	 * @param c 프레임
	 */
	public void setFrameLocation(Dimension d, Component c) {
		framesize_width = (int) d.getWidth(); // 프레임의 가로깅이
		framesize_height = (int) d.getHeight(); // 프레임의 세로길이

		c.setLocation(screensize_width / 2 - framesize_width / 2, screensize_height / 2 - framesize_height / 2);
	}

	/**
	 * 로고 표시 작업을 위한 스레드 실행 메소드
	 */
	public void run() {
		// 로고 애니메이션 실행 후 메인프레임 실행
		logo_frame.setUndecorated(true); // 로고프레임의 테두리를 제거
		logo_frame.setOpacity(0); // 로고 프레임의 불투명도를 0으로 설정
		logo_frame.pack();
		logo_frame.setVisible(true);
		try {
			// 로고 프레임이 서서히 나타나도록 표시함.
			logo_frame.setAlwaysOnTop(true);
			for (float i = 0; i <= 50; i++) {
				logo_frame.setOpacity((float) (0.02 * i));
				Thread.sleep(5);
			}
			Main P = new Main(option);
			P.setMainFrame(); // 메인 프레임을 화면에 띄움
			P.setVisible(true);

			logo_frame.dispose();

		} catch (InterruptedException e) {
			// 스레드 인터럽트 에러
			System.exit(0);
		}

	}

	/**
	 * main 메소드
	 * 
	 * @param args 이 값은 사용하지 않습니다.
	 */
	public static void main(String[] args) {
		Logo logo = new Logo();
		new Thread(logo).start();
	}

}
