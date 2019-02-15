package display;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import loader.Checker;
import option.Option;
import option.OptionDefault;
import option.Security;

/**
 * 메인 프레임과 패널
 * @author 김선일
 *
 */
public class Main extends JFrame {

	
	/**
	 * 프레임 시리얼 UID
	 */
	private static final long serialVersionUID = -320493438894721666L;
	
	/**
	 * 타이틀 정보
	 */
	private static final String label_Title = "SourceCode to PDF";
	
	/**
	 * 버전 정보
	 */
	private static final String label_Version = "Ver.Quater";

	/**
	 * 소스파일 선택시 특정 파일만 로드할 것인지 디렉토리를 로드할 것인지에 대한 값<br>
	 * 0 : 단일 파일<br>
	 * 1 : 디렉토리
	 */
	private int dialog_Result;
	
	/**
	 * 선택한 파일 (혹은 디렉토리) 정보를 표시하는 라벨
	 */
	private JLabel main_selectedfile;
	
	/**
	 * 소스코드 선택 시 파일을 선택할지 폴더를 선택할지 묻는 다이얼로그 창
	 */
	private JOptionPane main_filechoose_dialog;
	
	/**
	 * 파일 (혹은 디렉토리)을 선택하는 창
	 */
	private JFileChooser main_filechooser;
	
	/**
	 * 소스코드 로드 창 버튼<p>
	 * 이 버튼을 누를 시 main_filechoose_dialog 가 표시됩니다.
	 */
	private JButton main_load_btn;
	
	/**
	 * 변환 설정 창 버튼
	 */
	private JButton main_setting_btn;
	
	/**
	 * 서식 설정 창 버튼
	 */
	private JButton main_style_btn;
	
	/**
	 * 저작권 설정 창 버튼
	 */
	private JButton main_copyright_btn;
	
	/**
	 * 보안 설정 창 버튼
	 */
	private JButton main_secure_btn;
	
	/**
	 * 변환 창 버튼
	 */
	private JButton main_convert_btn;
	
	/**
	 * 도움말 (README) 창 표시 버튼
	 */
	private JButton main_help_btn;
	
	/**
	 * 메인 창에 표시할 로드된 소스코드 정보
	 */
	private JList<String> main_file_list;
	
	/**
	 * 메인 창에 표시할 로드된 소스코드 정보 (스크롤)
	 */
	private JScrollPane main_file_scl;
	
	/**
	 * 옵션 정보
	 */
	private Option option = OptionDefault.getDefaultOption();
	
	/**
	 * 보안 정보
	 */
	private Security security = new Security();
	
	/**
	 * 소스코드 관련 정보
	 */
	private Checker checker = new Checker();

	/**
	 * 메인 생성자
	 * @param op 로드된 옵션
	 */
	public Main(Option op){
		option = op;
	}
	

	/**
	 * 메인 프레임 설정
	 */
	public void setMainFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("LookAndFeel Setting Fail");
		}
		setTitle(label_Title + " " + label_Version);
		setResizable(false);

		setLayout(null);
		setPreferredSize(new Dimension(550, 300));
		this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getPreferredSize().getWidth() / 2), 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getPreferredSize().getHeight() / 2));

		main_selectedfile = new JLabel();
		main_load_btn = new JButton();
		main_style_btn = new JButton();
		main_setting_btn = new JButton();
		main_copyright_btn = new JButton();
		main_secure_btn = new JButton();
		main_convert_btn = new JButton();
		main_help_btn = new JButton();
		main_filechoose_dialog = new JOptionPane();
		main_filechooser = new JFileChooser("./");
		
		//16.06.05 05:18 추가된 Main 컴포넌트
		main_file_list = new JList<String>();
		main_file_scl = new JScrollPane(main_file_list);
		add(main_file_scl);
		main_file_scl.setBounds(38, 100, 345, 100);
		
		//16.06.05 05:10 추가된 Main 컴포넌트
		main_selectedfile.setText("디렉토리 경로");
		add(main_selectedfile);
		main_selectedfile.setBounds(38, 39, 500, 15);

		main_load_btn.setText("소스파일로드");
		main_load_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadBtnEvent(evt);
			}
		});
		add(main_load_btn);

		main_load_btn.setBounds(420, 35, 105, 23);

		main_style_btn.setText("서식설정");
		main_style_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				styleBtnEvent(evt);
			}
		});
		add(main_style_btn);
		main_style_btn.setBounds(38, 220, 81, 23);

		
		main_setting_btn.setText("변환설정");
		main_setting_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				settingBtnEvent(evt);
			}
		});
		add(main_setting_btn);
		main_setting_btn.setBounds(420, 220, 100, 23);

		
		
		main_copyright_btn.setText("저작권설정");
		main_copyright_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				copyrightBtnEvent(evt);
			}
		});
		add(main_copyright_btn);
		main_copyright_btn.setBounds(126, 220, 93, 23);

		main_secure_btn.setText("보안설정");
		main_secure_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				secureBtnEvent(evt);
			}
		});
		add(main_secure_btn);
		main_secure_btn.setBounds(226, 220, 81, 23);

		main_convert_btn.setText("변환하기");
		main_convert_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mainConvertBtnEvent(evt);
			}
		});
		enableConvertBtn();

		add(main_convert_btn);
		main_convert_btn.setBounds(420, 130, 100, 70);

		main_help_btn.setText("도움말");
		main_help_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				helpBtnEvent(evt);
			}
		});
		add(main_help_btn);
		main_help_btn.setBounds(314, 220, 69, 23);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	/**
	 * 소스코드 로드 버튼 이벤트 핸들러
	 * @param evt 버튼 이벤트
	 */
	private void loadBtnEvent(ActionEvent evt) {
		// 로드버튼 이벤트
		showDialog();
		FileSelect fileSelect = null;
		if (dialog_Result == 0) {
			if(option.getCodeLanguage() == Option.Language.C){
				main_filechooser.setFileFilter(new FileNameExtensionFilter("C Files OR Header Files", "c", "h"));
			}else if(option.getCodeLanguage() == Option.Language.CPP){
				main_filechooser.setFileFilter(new FileNameExtensionFilter("C++ Files OR Header Files", "cpp", "h"));
			}else if(option.getCodeLanguage() == Option.Language.JAVA){
				main_filechooser.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));
			}
			main_filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			main_filechooser.showOpenDialog(this);

			fileSelect = new FileSelect(checker, option);
			if (main_filechooser.getSelectedFile() != null) {
				fileSelect.selectFile(main_filechooser.getSelectedFile());
			}
		}

		if (dialog_Result == 1) {
			main_filechooser.setSelectedFile(null);
			
			main_filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			main_filechooser.showOpenDialog(this);
			
			fileSelect = new FileSelect(checker, option);

			if (main_filechooser.getSelectedFile() != null) {
				fileSelect.selectDirectory(main_filechooser.getSelectedFile());
			}
		}
		if(main_filechooser.getSelectedFile() != null){
			main_selectedfile.setText(main_filechooser.getSelectedFile().getPath());
		}

		enableConvertBtn();
		
		main_file_list.setListData(checker.fileListString);
	}

	/**
	 * 소스코드 로드 여부에 따라 변환버튼 활성화 지정
	 */
	public void enableConvertBtn() {
		//소스파일 로드여부에 따라 변환버튼 활성화,비활성화하는 메소드
		if (checker.fileList.isEmpty())
			main_convert_btn.setEnabled(false);
		else
			main_convert_btn.setEnabled(true);
	}
	
	/**
	 * 변환 버튼 처리 핸들러
	 * @param evt 버튼 이벤트
	 */
	private void mainConvertBtnEvent(ActionEvent evt) {
		// 변환버튼 이벤트: 변환버튼을 누르면 변환프레임이 띄워지게 됨
		Convert c = new Convert(checker, option, security);
		c.setConvertDialog();
		c.setVisible(true);
	}

	/**
	 * 서식 설정 버튼 처리 핸들러
	 * @param evt 버튼 이벤트
	 */
	private void styleBtnEvent(ActionEvent evt) { //
		// 서식설정버튼 이벤트: 서식설정버튼을 누르면 서식성정프레임이 띄워지게 됨
		Style s = new Style(option);
		s.setStylePanel();
		s.setVisible(true);
	}

	/**
	 * 변환 설정 버튼 처리 핸들러
	 * @param evt 버튼 이벤트
	 */
	private void settingBtnEvent(ActionEvent evt) { //
		// 서식설정버튼 이벤트: 서식설정버튼을 누르면 서식성정프레임이 띄워지게 됨
		Setting t = new Setting(option);
		t.setSettingPanel();
		t.setVisible(true);
	}
	
	/**
	 * 저작권 설정 버튼 처리 핸들러
	 * @param evt 버튼 이벤트
	 */
	private void copyrightBtnEvent(ActionEvent evt) {
		// 저작권설정 버튼 이벤트: 저작권설정 버튼을 누르면 저작권설정프레임이 띄워지게 됨
		Copyright c = new Copyright(option);
		c.setCopyrightPanel();
		c.setVisible(true);
	}

	/**
	 * 보안 설정 버튼 처리 핸들러
	 * @param evt 버튼 이벤트
	 */
	private void secureBtnEvent(ActionEvent evt) {
		// 보안설정 버튼 이벤트: 보안설정버튼을 누르면 보안설정프레임이 띄워지게 됨
		Secure s = new Secure(security);
		s.setSecurePanel();
		s.setVisible(true);

	}
	
	/**
	 * 도움말 버튼 처리 핸들러
	 * @param evt 버튼 이벤트
	 */
	private void helpBtnEvent(ActionEvent evt) {
		// 도움말 버튼 이벤트:도움말 버튼을 누르면 지정된 경로의 파일이 실행되게 됨
		try {
			File f = new File("README.txt");//도움말 파일. 문자열 수정으로 변경 가능
			Desktop.getDesktop().open(f);
		} catch (IOException e) {
			System.out.println("README 파일 로드에 실패했습니다");
		}
	}

	/**
	 * 다이얼로그 출력 메소드
	 */
	@SuppressWarnings("static-access")
	private void showDialog() {
		// 로드버튼 누를시 파일선택모드를 묻는 다이얼로그 (파일:0, 다랙토리 :1)
		String[] options = { "  파일  ", "  폴더  ", "  취소  " };
		dialog_Result = main_filechoose_dialog.showOptionDialog(this, "  파일을 변환 합니까? 폴더 전체를 변환합니까?  ", "소스코드 로드",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "파일");
	}

}
