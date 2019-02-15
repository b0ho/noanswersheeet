package display;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import option.Security;

/**
 * 보안 설정 창입니다.
 * @author 김선일
 *
 */
public class Secure extends JDialog {

	/**
	 * SerialUID
	 */
	private static final long serialVersionUID = -5059371536601380535L;

	/**
	 * 복사 가능 여부 설정 체크 박스
	 */
	private JCheckBox secure_copy_cb = new JCheckBox();
	
	/**
	 * 보안 옵션 사용 여부 설정 체크박스
	 */
	private JCheckBox secure_enable_cb = new JCheckBox();
	
	/**
	 * 비밀번호 입력 필드
	 */
	private JPasswordField jPasswordField1 = new JPasswordField();
	
	/**
	 * 입력 비밀번호 재확인 필드
	 */
	private JPasswordField jPasswordField2 = new JPasswordField();
	
	/**
	 * 비밀번호 입력 라벨
	 */
	private JLabel jLabel1 = new JLabel();
	
	/**
	 * 비밀번호 재확인 라벨
	 */
	private JLabel jLabel2 = new JLabel();
	
	/**
	 * 보안 정보 저장 버튼
	 */
	private JButton secure_save_btn = new JButton();
	
	/**
	 * 보안 창 닫기 버튼
	 */
	private JButton cancelBtn = new JButton();
	
	/**
	 * 보안 정보
	 */
	private Security security;
	
	/**
	 * 생성자
	 * @param sec 보안 정보
	 */
	public Secure(Security sec){
		security = sec;
	}
	
	/**
	 * 디스플레이 셋업
	 */
	public void setSecurePanel() {
		//보안설정 프레임의 컴포넌트 설정
		setModal(true);
		setTitle("보안 설정");

		setPreferredSize(new Dimension(360, 250));
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getPreferredSize().getWidth() / 2), 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getPreferredSize().getHeight() / 2));

		setLayout(null);

		secure_enable_cb.setBounds(35, 9, 117, 23);

		secure_enable_cb.setText("보안설정 사용");

		secure_enable_cb.setSelected(security.getUseSecurityOption());
		enableSecureOption();

		secure_enable_cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				enableSecureOption();
			}
		});
		add(secure_enable_cb);
		secure_copy_cb.setBounds(35, 39, 121, 23);
		secure_copy_cb.setText("복사 가능 여부");
		secure_copy_cb.setSelected(security.getUseCopyMode());
		add(secure_copy_cb);
		
		
		add(jPasswordField1);
		jPasswordField1.setBounds(130, 84, 186, 21);
		jPasswordField1.setDocument(new JExtendTextField(20));
		add(jPasswordField2);
		jPasswordField2.setBounds(130, 111, 186, 21);
		jPasswordField2.setDocument(new JExtendTextField(20));

		jLabel1.setText("비밀번호 입력");
		add(jLabel1);
		jLabel1.setBounds(49, 87, 76, 15);

		jLabel2.setText("비밀번호 확인");
		add(jLabel2);
		jLabel2.setBounds(49, 111, 76, 15);

		secure_save_btn.setText("적용");
		secure_save_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				saveBtnEvent();
			}
		});
		add(secure_save_btn);
		secure_save_btn.setBounds(120, 170, 77, 23);

		cancelBtn.setText("닫기");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				closeBtnEvent();
			}
		});
		add(cancelBtn);
		cancelBtn.setBounds(240, 170, 77, 23);
		setResizable(false);

		pack();
	}

	/**
	 * 보안 정보 사용 여부에 따른 하위 컴포넌트 활성화 여부 지정 메소드
	 */
	private void enableSecureOption() {
		//보안설정옵션 사용여부에 따라 하위 옵션들을 활성화, 비활성화 하는 메소드
		if (secure_enable_cb.isSelected()) {
			//secure_enable_cb.setEnabled(true);
			jPasswordField1.setEnabled(true);
			jPasswordField2.setEnabled(true);
			secure_copy_cb.setEnabled(true);
			jLabel1.setEnabled(true);
			jLabel2.setEnabled(true);
			
		} else {
			//secure_enable_cb.setEnabled(false);
			jPasswordField1.setEnabled(false);
			jPasswordField2.setEnabled(false);
			secure_copy_cb.setEnabled(false);
			jLabel1.setEnabled(false);
			jLabel2.setEnabled(false);
		}
		
	}
	
	/**
	 * 비밀번호 확인 메소드
	 * @return 비밀번호가 입력되지 않았거나 일치하지 않으면 false, 일치하면 true
	 */
	private boolean checkPassword(){
		//사용자가 입력한 암호가 동일한지여부와 비밀번호의 사용 여부
		if(String.valueOf(jPasswordField1.getPassword()).equals(String.valueOf(jPasswordField2.getPassword()))){
			if(String.valueOf(jPasswordField1.getPassword()).equals("")){
				security.setUsePassword(false);
			}else{
				security.setUsePassword(true);
			}
			return true;
		}else{//일치하지 않을 경우 경고 다이얼로그 출력.
			JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다!", "비밀번호 입력 확인", JOptionPane.WARNING_MESSAGE);
			
			return false;
		}
	}

	/**
	 * 저장 버튼 이벤트 핸들러
	 */
	private void saveBtnEvent() {
		// 적용버튼 이벤트: 설정값 저장
		boolean chk = checkPassword();
		if(!chk){
			return;
		}
		if(secure_enable_cb.isSelected()){
			security.setUseSecurityOption(secure_enable_cb.isSelected());
			security.setUseCopyMode(secure_copy_cb.isSelected());
			security.setPassword(String.valueOf(jPasswordField1.getPassword()));
			dispose();
		}else if(!secure_enable_cb.isSelected()){
			security.setUseSecurityOption(false);
			security.setUsePassword(false);
			dispose();
		}
	}

	/**
	 * 창 닫기 버튼 이벤트 핸들러
	 */
	private void closeBtnEvent() {
		//닫기버튼 이벤트: 현재 창을 닫음
		dispose();
	}
	
}
