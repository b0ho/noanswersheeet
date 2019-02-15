package display;
import java.awt.Toolkit;

import javax.swing.*;

import option.Option;

/**
 * 저작권 설정에 관한 디스플레이 입니다.
 * @author 김선일
 *
 */
public class Copyright extends JDialog {
	
	/**
	 * 다이얼로그 시리얼 UID
	 */
	private static final long serialVersionUID = 8905550640457174616L;
	
	/**
	 * 옵션 지정에 사용될 옵션입니다.
	 */
	private Option option;
	
	/**
	 * 저작권 사용 여부 설정 버튼
	 */
	private JCheckBox copyright_option_enable = new JCheckBox();
	
	/**
	 * 저작권 정보 표시 위치 콤보박스
	 */
	private JComboBox<String> copyright_location_cb = new JComboBox<String>();
	
	/**
	 * 저작권 정보 표시 위치 라벨
	 */
	private JLabel copyright_location_lb = new JLabel();
	
	/**
	 * 저작권 정보 라벨
	 */
	private JLabel copyright_imfo_lb = new JLabel();
	
	/**
	 * 저작권 정보 입력 필드
	 */
	private JTextField copyright_info_tf = new JTextField();
	
	/**
	 * 저작권 정보 저장 버튼
	 */
	private JButton copyright_save_btn = new JButton();
	
	/**
	 * 저작권 정보 닫기 버튼
	 */
	private JButton copyright_cancel_btn = new JButton();
	
	/**
	 * 생성자
	 * @param op 옵션 정보
	 */
	public Copyright(Option op){
		option = op;
	}
	
	/**
	 * 디스플레이 셋업
	 */
	public void setCopyrightPanel() {
		//저작권설정 프레임의 컴포넌트 설정
		setTitle("저작권 설정");
		setModal(true);
		setPreferredSize(new java.awt.Dimension(360, 250));
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getPreferredSize().getWidth() / 2), 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getPreferredSize().getHeight() / 2));
		enableCopyrightOption();
		setLayout(null);

		copyright_option_enable.setText("저작권 옵션 사용");
		copyright_option_enable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				enableCopyrightOption();
			}
		});
		if(!option.getCopyright().equals("")){
			copyright_option_enable.setSelected(true);
			enableCopyrightOption();
		}		
		add(copyright_option_enable);
		copyright_option_enable.setBounds(30, 30, 200, 23);
		
		copyright_location_cb.setModel(new DefaultComboBoxModel<>(new String[] { "왼쪽", "중앙", "오른쪽" }));
		copyright_location_cb.setSelectedIndex(convertAlignToItemIndex(option.getCopyrightAlign()));
		add(copyright_location_cb);
		copyright_location_cb.setBounds(170, 70, 80, 21);
		
		copyright_location_lb.setText("저작자 정보 표시 위치");
		add(copyright_location_lb);
		copyright_location_lb.setBounds(40, 70, 130, 15);
		
		add(copyright_info_tf);
		copyright_info_tf.setBounds(170, 110, 150, 21);
		copyright_info_tf.setText(option.getCopyright());
		copyright_info_tf.setDocument(new JExtendTextField("^ㄱ-ㅎㅏ-ㅣ가-힣",40));

		copyright_imfo_lb.setText("저작자 정보");
		add(copyright_imfo_lb);
		copyright_imfo_lb.setBounds(40, 110, 64, 15);
		setResizable(false);

		copyright_save_btn.setText("적용");
		copyright_save_btn.setBounds(150, 180, 83, 23);
		add(copyright_save_btn);
		copyright_save_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveBtnEvent();
			}
		});
		copyright_cancel_btn.setText("닫기");
		copyright_cancel_btn.setBounds(250, 180, 83, 23);
		add(copyright_cancel_btn);
		copyright_cancel_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeBtnEvent();
			}
		});

		pack();

	}

	/**
	 * 저작권 옵션 사용 여부에 따른 하위 컴포넌트들의 사용 여부 설정
	 */
	private void enableCopyrightOption() {
		//저작권설정 사용여부에 따라 하위 옵션들을 확성화, 비활성화 하는 메소드
		if (copyright_option_enable.isSelected()) {
			copyright_location_lb.setEnabled(true);
			copyright_imfo_lb.setEnabled(true);
			copyright_info_tf.setEnabled(true);
			copyright_location_cb.setEnabled(true);
		} else {
			copyright_location_lb.setEnabled(false);
			copyright_imfo_lb.setEnabled(false);
			copyright_info_tf.setEnabled(false);
			copyright_location_cb.setEnabled(false);
		}
	}

	/**
	 * 저장 버튼 이벤트 핸들러
	 */
	private void saveBtnEvent() {
		//적용버튼 이벤트: 설정값 저장
		if (copyright_option_enable.isSelected()) {
			option.setCopyright(copyright_info_tf.getText());
			option.setCopyrightAlign(convertItemToAlign(copyright_location_cb.getSelectedItem()));
			option.saveOption();
		} else {
			option.setCopyright("");
			option.setCopyrightAlign(Option.Align.RIGHT);
			option.saveOption();
		}
		dispose();
	}

	/**
	 * 닫기 버튼 이벤트 핸들러
	 */
	private void closeBtnEvent() {
		dispose();
	}
	
	/**
	 * 콤보박스 내의 값을 옵션 설정 값으로 변환하는 메소드
	 * @param obj 콤보박스 내 오브젝트
	 * @return 옵션 설정 값
	 */
	private Option.Align convertItemToAlign(Object obj){
		String s = obj.toString();
		if(s.equals("왼쪽")){
			return Option.Align.LEFT;
		}else if(s.equals("중앙")){
			return Option.Align.CENTER;
		}else if(s.equals("오른쪽")){
			return Option.Align.RIGHT;
		}else{
			return Option.Align.CENTER;
		}
	}
	
	/**
	 * 옵션 설정 값을 콤보 박스 내 인덱스로 변환하는 메소드
	 * @param align 옵션 설정 값
	 * @return 콤보 박스 내 인덱스
	 */
	private int convertAlignToItemIndex(Option.Align align){
		if(align == Option.Align.LEFT){
			return 0;
		}else if(align == Option.Align.CENTER){
			return 1;
		}else if(align == Option.Align.RIGHT){
			return 2;
		}else{
			return 0;
		}
	}

}
