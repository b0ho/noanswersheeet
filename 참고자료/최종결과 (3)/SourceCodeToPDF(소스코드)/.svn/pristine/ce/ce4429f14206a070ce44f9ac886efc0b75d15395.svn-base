package display;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

import option.FontOption;
import option.Option;
import option.PageOption;

/**
 * 서식 설정 창 입니다.
 * @author 김선일
 *
 */
public class Style extends JDialog {
	/**
	 * SerialUID
	 */
	private static final long serialVersionUID = 529200692386985004L; //

	/**
	 * 옵션 정보
	 */
	private Option option;

	/**
	 * 각 서식 별 정보를 가지는 탭 패널
	 */
	private JTabbedPane jTabbedPane1 = new JTabbedPane();
	
	/**
	 * 용지 설정 탭 패널
	 */
	private JPanel style_paperset_tab = new JPanel();
	
	/**
	 * 일반 문자 폰트 설정 탭 패널
	 */
	private JPanel style_general_tab = new JPanel();
	
	/**
	 * 문자열 문자 폰트 설정 탭 패널
	 */
	private JPanel style_string_tab = new JPanel();
	
	/**
	 * 키워드 문자 폰트 설정 탭 패널
	 */
	private JPanel style_keyword_tab = new JPanel();
	
	/**
	 * 주석 문자 폰트 설정 탭 패널
	 */
	private JPanel style_comment_tab = new JPanel();
	
	/**
	 * 줄번호 문자 폰트 설정 탭 패널
	 */
	private JPanel style_linenumber_tab = new JPanel();
	
	/**
	 * 서식 저장 버튼
	 */
	private JButton saveBtn = new JButton();
	
	/**
	 * 서식 설정 창 닫기 버튼
	 */
	private JButton cancelBtn = new JButton();
	
	/**
	 * 지정된 위치의 폰트 [이름,경로] 쌍으로 된 Map 데이터
	 */
	private HashMap<String,String> fontData = new HashMap<String,String>();
	
	/**
	 * 폰트 이름 벡터
	 */
	private Vector<String> fontName = new Vector<String>();
	
	/**
	 * 일반 문자 폰트의 사용중인 폰트 정보
	 */
	private String general_FontFamily;
	
	/**
	 * 문자열 문자 폰트의 사용중인 폰트 정보
	 */
	private String string_FontFamily;
	
	/**
	 * 주석 문자 폰트의 사용중인 폰트 정보
	 */
	private String comment_FontFamily;
	
	/**
	 * 키워드 문자 폰트의 사용중인 폰트 정보
	 */
	private String keyword_FontFamily;
	
	/**
	 * 줄번호 문자 폰트의 사용중인 폰트 정보
	 */
	private String linenumber_FontFamily;

	/**
	 * 생성자<p>
	 * 지정된 경로의 폰트 폴더의 폰트들을 로드합니다.
	 * @param op 옵션 정보
	 */
	public Style(Option op) { //
		option = op;
		
		File[] files = new File("fonts/").listFiles();
		for(int i = 0 ; i < files.length ; i++){
			if(files[i].getName().endsWith(".ttf") || files[i].getName().endsWith(".TTF")){
				try {
					BaseFont bf = BaseFont.createFont(files[i].getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
					Font f = new Font(bf);
					if(fontData.get(f.getFamilyname()) == null){
						fontData.put(f.getFamilyname(), files[i].getPath());
						fontName.add(f.getFamilyname());
					}
					if(option.getBasicFont().getFontName().equals(files[i].getPath())){
						general_FontFamily = f.getFamilyname();
					}
					if(option.getStringFont().getFontName().equals(files[i].getPath())){
						string_FontFamily = f.getFamilyname();
					}
					if(option.getCommentFont().getFontName().equals(files[i].getPath())){
						comment_FontFamily = f.getFamilyname();
					}
					if(option.getKeywordFont().getFontName().equals(files[i].getPath())){
						keyword_FontFamily = f.getFamilyname();
					}
					if(option.getLineNumberFont().getFontName().equals(files[i].getPath())){
						linenumber_FontFamily = f.getFamilyname();
					}
				} catch (DocumentException | IOException e) {
					System.out.println("Font Load Error");
				}
			}
		}
	}

	/**
	 * 서식 설정 창 초기화 메소드
	 */
	public void setStylePanel() {
		// 서식설정프레임의 컵ㅁ포넌트 설정

		setTitle("서식 설정");
		setModal(true);
		setPreferredSize(new java.awt.Dimension(500, 370));
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getPreferredSize().getWidth() / 2), 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getPreferredSize().getHeight() / 2));
		setLayout(null);

		style_paperset_tab.setLayout(null);
		setPapersetTab(style_paperset_tab);
		setGeneralTab(style_general_tab);
		setCommentTab(style_comment_tab);
		setStringTab(style_string_tab);
		setKeywordTab(style_keyword_tab);
		setLinenumberTab(style_linenumber_tab);
		saveBtn.setText("적용");
		add(saveBtn);
		saveBtn.setBounds(300, 300, 83, 23);
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveBtnEvent();
			}
		});
		cancelBtn.setText("취소");
		add(cancelBtn);
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeBtnEvent();
			}
		});
		cancelBtn.setBounds(400, 300, 83, 23);

		jTabbedPane1.setBounds(0, 0, 500, 270);
		jTabbedPane1.addTab("용지설정", style_paperset_tab);

		jTabbedPane1.addTab("일반", style_general_tab);

		jTabbedPane1.addTab("문자열", style_string_tab);

		jTabbedPane1.addTab("키워드", style_keyword_tab);

		jTabbedPane1.addTab("주석", style_comment_tab);

		jTabbedPane1.addTab("라인넘버", style_linenumber_tab);
		add(jTabbedPane1);
		setResizable(false);
		pack();

	}

	// PaperSetTab 컴포넌트 선언
	/**
	 * 용지 크기 설정 라벨
	 */
	private JLabel paper_size_label = new JLabel();
	
	/**
	 * 용지 상단 여백 설정 라벨
	 */
	private JLabel paper_upblank_label = new JLabel();
	
	/**
	 * 용지 하단 여백 설정 라벨
	 */
	private JLabel paper_downblank_label = new JLabel();
	
	/**
	 * 용지 좌측 여백 설정 라벨
	 */
	private JLabel paper_leftblank_label = new JLabel();
	
	/**
	 * 용지 우측 여백 설정 라벨
	 */
	private JLabel paper_rightblank_label = new JLabel();
	
	/**
	 * 용지 상단 여백 설정 입력 필드
	 */
	private JTextField paper_upblank_tf = new JTextField();
	
	/**
	 * 용지 하단 여백 설정 입력 필드
	 */
	private JTextField paper_downblank_tf = new JTextField();
	
	/**
	 * 용지 좌측 여백 설정 입력 필드
	 */
	private JTextField paper_leftblank_tf = new JTextField();
	
	/**
	 * 용지 우측 여백 설정 입력 필드
	 */
	private JTextField paper_rightblank_tf = new JTextField();
	
	/**
	 * 용지 크기 콤보박스
	 */
	private JComboBox<PageOption.PageType> paper_size_cb = new JComboBox<PageOption.PageType>();

	/**
	 * 용지 설정 탭 초기화 메소드
	 * @param j 삽입 되어질 부모 패널
	 */
	private void setPapersetTab(JPanel j) {
		// PapersetTab 컴포넌트 설정
		j.setLayout(null);

		paper_size_label.setBounds(20, 50, 76, 15);
		paper_size_label.setText("용지 크기");
		j.add(paper_size_label);

		paper_size_cb.setBounds(120, 50, 80, 20);
		paper_size_cb.setModel(new DefaultComboBoxModel<PageOption.PageType>(PageOption.PageType.values()));
		paper_size_cb.setSelectedItem(option.getPageOption().getPageType());
		j.add(paper_size_cb);

		paper_upblank_label.setBounds(20, 80, 76, 15);
		paper_upblank_label.setText("위쪽 여백");
		j.add(paper_upblank_label);

		paper_upblank_tf.setBounds(120, 75, 30, 21);
		paper_upblank_tf.setDocument(new JExtendTextField("0-9", 2));
		j.add(paper_upblank_tf);
		paper_upblank_tf.setText(String.valueOf(option.getMarginTop()));

		paper_downblank_label.setBounds(20, 110, 76, 15);
		j.add(paper_downblank_label);
		paper_downblank_label.setText("아래쪽 여백");
		paper_downblank_tf.setDocument(new JExtendTextField("0-9", 2));
		paper_downblank_tf.setText(String.valueOf(option.getMarginBottom()));
		paper_downblank_tf.setBounds(120, 105, 30, 21);
		j.add(paper_downblank_tf);

		paper_leftblank_label.setBounds(20, 140, 76, 15);
		paper_leftblank_label.setText("왼쪽 여백");
		j.add(paper_leftblank_label);

		paper_leftblank_tf.setBounds(120, 135, 30, 21);
		paper_leftblank_tf.setDocument(new JExtendTextField("0-9", 2));
		j.add(paper_leftblank_tf);
		paper_leftblank_tf.setText(String.valueOf(option.getMarginLeft()));

		paper_rightblank_label.setBounds(20, 170, 76, 15);
		paper_rightblank_label.setText("오른쪽 여백");
		j.add(paper_rightblank_label);

		paper_rightblank_tf.setDocument(new JExtendTextField("0-9", 2));
		paper_rightblank_tf.setText(String.valueOf(option.getMarginRight()));
		paper_rightblank_tf.setBounds(120, 165, 30, 21);
		j.add(paper_rightblank_tf);
	}

	// GeneralTab의 컴포넌트 선언
	/**
	 * 일반 문자에 지정할 폰트 리스트
	 */
	private JList<String> general_font_list = new JList<String>();

	/**
	 * 일반 문자에 지정할 폰트 크기 리스트
	 */
	private JList<Integer> general_size_list = new JList<Integer>();

	/**
	 * 일반 문자에 지정할 컬러 정보 지정 라벨
	 */
	private JLabel general_color_label = new JLabel();

	/**
	 * 일반 문자에 지정할 컬러 정보 입력 필드
	 */
	private JTextField general_color_tf = new JTextField();

	/**
	 * 폰트 리스트 스크롤 패널
	 */
	private JScrollPane general_font_scl = new JScrollPane();

	/**
	 * 폰트 크기 리스트 스크롤 패널
	 */
	private JScrollPane general_size_scl = new JScrollPane();

	/**
	 * 일반 문자에 지정할 폰트 스타일 
	 */
	private JComboBox<FontOption.FontStyle> general_style_cb = new JComboBox<FontOption.FontStyle>(FontOption.FontStyle.values());
	
	/**
	 * 일반 문자 폰트 설정 탭 초기화 메소드
	 * @param j 삽입 되어질 부모 패널
	 */
	private void setGeneralTab(JPanel j) {
		// GeneralTab의 컴포넌트 설정
		j.setLayout(null);
		
		//위치 설정 부분
		general_font_scl.setBounds(30, 50, 150, 190);
		general_size_scl.setBounds(210, 50, 50, 190);
		general_color_label.setBounds(290, 50, 40, 21);
		general_color_tf.setBounds(330, 50, 76, 21);
		general_style_cb.setBounds(290, 100, 100, 21);
		
		general_style_cb.setSelectedItem(option.getBasicFont().getFontStyle());
		j.add(general_style_cb);
		
		//폰트 설정 부분
		j.add(general_font_scl);
		general_font_scl.setViewportView(general_font_list);

		general_font_list.setListData(fontName);
		general_font_list.setSelectedValue(general_FontFamily, true);
		
		//폰트 크기 설정 부분
		j.add(general_size_scl);

		Integer[] sizeArray = new Integer[99];
		for (int i = 1; i < 100; i++)
			sizeArray[i - 1] = i;
		general_size_scl.setViewportView(general_size_list);
		general_size_list.setListData(sizeArray);
		general_size_list.setSelectedValue(option.getBasicFont().getFontSize(), true);

		
		general_color_label.setText("색상  #");
		general_color_tf.setDocument(new JExtendTextField("0-9a-fA-F", 6));
		general_color_tf.setText(fullColorName(option.getBasicFont().getFontColor()));
		j.add(general_color_tf);
		j.add(general_color_label);
		
		general_font_scl.setViewportView(general_font_list);
	}

	// stringTab의 컴포넌트 선언
	/**
	 * 문자열 문자에 지정할 폰트 리스트
	 */
	private JList<String> string_font_list = new JList<String>();

	/**
	 * 문자열 문자에 지정할 폰트 크기 리스트
	 */
	private JList<Integer> string_size_list = new JList<Integer>();

	/**
	 * 문자열 문자에 지정할 컬러 정보 지정 라벨
	 */
	private JLabel string_color_label = new JLabel();

	/**
	 * 문자열 문자에 지정할 컬러 정보 입력 필드
	 */
	private JTextField string_color_tf = new JTextField();

	/**
	 * 폰트 리스트 스크롤 패널
	 */
	private JScrollPane string_font_scl = new JScrollPane();

	/**
	 * 폰트 크기 리스트 스크롤 패널
	 */
	private JScrollPane string_size_scl = new JScrollPane();

	/**
	 * 문자열 문자에 지정할 폰트 스타일 
	 */
	private JComboBox<FontOption.FontStyle> string_style_cb = new JComboBox<FontOption.FontStyle>(FontOption.FontStyle.values());
	
	/**
	 * 문자열 문자 폰트 설정 탭 초기화 메소드
	 * @param j 삽입 되어질 부모 패널
	 */
	private void setStringTab(JPanel j) {
		// stringTab의 컴포넌트 설정
		j.setLayout(null);
		string_font_scl.setBounds(30, 50, 150, 190);
		string_size_scl.setBounds(210, 50, 50, 190);
		string_color_label.setBounds(290, 50, 40, 21);
		string_color_tf.setBounds(330, 50, 76, 21);
		string_style_cb.setBounds(290, 100, 100, 21);
		
		string_style_cb.setSelectedItem(option.getStringFont().getFontStyle());
		j.add(string_style_cb);
		
		//폰트 설정 부분
		j.add(string_font_scl);
		string_font_scl.setViewportView(string_font_list);
		
		string_font_list.setListData(fontName);
		string_font_list.setSelectedValue(string_FontFamily, true);
		
		//폰트 크기 설정 부분
		j.add(string_size_scl);

		Integer[] sizeArray = new Integer[99];
		for (int i = 1; i < 100; i++)
			sizeArray[i - 1] = i;
		string_size_scl.setViewportView(string_size_list);
		string_size_list.setListData(sizeArray);
		string_size_list.setSelectedValue(option.getStringFont().getFontSize(), true);

		
		string_color_label.setText("색상  #");
		string_color_tf.setDocument(new JExtendTextField("0-9a-fA-F", 6));
		string_color_tf.setText(fullColorName(option.getStringFont().getFontColor()));
		j.add(string_color_tf);
		j.add(string_color_label);
		
		string_font_scl.setViewportView(string_font_list);
	}

	// keywordTab의 컴포넌트 선언
	/**
	 * 키워드 문자에 지정할 폰트 리스트
	 */
	private JList<String> keyword_font_list = new JList<String>();

	/**
	 * 키워드 문자에 지정할 폰트 크기 리스트
	 */
	private JList<Integer> keyword_size_list = new JList<Integer>();

	/**
	 * 키워드 문자에 지정할 컬러 정보 지정 라벨
	 */
	private JLabel keyword_color_label = new JLabel();

	/**
	 * 키워드 문자에 지정할 컬러 정보 입력 필드
	 */
	private JTextField keyword_color_tf = new JTextField();

	/**
	 * 폰트 리스트 스크롤 패널
	 */
	private JScrollPane keyword_font_scl = new JScrollPane();

	/**
	 * 폰트 크기 리스트 스크롤 패널
	 */
	private JScrollPane keyword_size_scl = new JScrollPane();

	/**
	 * 키워드 문자에 지정할 폰트 스타일 
	 */
	private JComboBox<FontOption.FontStyle> keyword_style_cb = new JComboBox<FontOption.FontStyle>(FontOption.FontStyle.values());

	/**
	 * 키워드 문자 폰트 설정 탭 초기화 메소드
	 * @param j 삽입 되어질 부모 패널
	 */
	private void setKeywordTab(JPanel j) {
		// ketwordTab의 컴포넌트 설정
		j.setLayout(null);

		keyword_font_scl.setBounds(30, 50, 150, 190);
		keyword_size_scl.setBounds(210, 50, 50, 190);
		keyword_color_label.setBounds(290, 50, 40, 21);
		keyword_color_tf.setBounds(330, 50, 76, 21);
		keyword_style_cb.setBounds(290, 100, 100, 21);

		keyword_style_cb.setSelectedItem(option.getKeywordFont().getFontStyle());
		j.add(keyword_style_cb);

		// 폰트 설정 부분
		j.add(keyword_font_scl);
		keyword_font_scl.setViewportView(keyword_font_list);

		keyword_font_list.setListData(fontName);
		keyword_font_list.setSelectedValue(keyword_FontFamily, true);

		// 폰트 크기 설정 부분
		j.add(keyword_size_scl);

		Integer[] sizeArray = new Integer[99];
		for (int i = 1; i < 100; i++)
			sizeArray[i - 1] = i;
		keyword_size_scl.setViewportView(keyword_size_list);
		keyword_size_list.setListData(sizeArray);
		keyword_size_list.setSelectedValue(option.getKeywordFont().getFontSize(), true);

		keyword_color_label.setText("색상  #");
		keyword_color_tf.setDocument(new JExtendTextField("0-9a-fA-F", 6));
		keyword_color_tf.setText(fullColorName(option.getKeywordFont().getFontColor()));
		j.add(keyword_color_tf);
		j.add(keyword_color_label);

		keyword_font_scl.setViewportView(keyword_font_list);
	}

	// commentTab의 컴포넌트 선언
	/**
	 * 주석 문자에 지정할 폰트 리스트
	 */
	private JList<String> comment_font_list = new JList<String>();

	/**
	 * 주석 문자에 지정할 폰트 크기 리스트
	 */
	private JList<Integer> comment_size_list = new JList<Integer>();

	/**
	 * 주석 문자에 지정할 컬러 정보 지정 라벨
	 */
	private JLabel comment_color_label = new JLabel();

	/**
	 * 주석 문자에 지정할 컬러 정보 입력 필드
	 */
	private JTextField comment_color_tf = new JTextField();

	/**
	 * 폰트 리스트 스크롤 패널
	 */
	private JScrollPane comment_font_scl = new JScrollPane();

	/**
	 * 폰트 크기 리스트 스크롤 패널
	 */
	private JScrollPane comment_size_scl = new JScrollPane();

	/**
	 * 주석 문자에 지정할 폰트 스타일 
	 */
	private JComboBox<FontOption.FontStyle> comment_style_cb = new JComboBox<FontOption.FontStyle>(FontOption.FontStyle.values());

	/**
	 * 주석 문자 폰트 설정 탭 초기화 메소드
	 * @param j 삽입 되어질 부모 패널
	 */
	private void setCommentTab(JPanel j) {

		// commentTab의 컴포넌트 설정
		j.setLayout(null);
		comment_font_scl.setBounds(30, 50, 150, 190);
		comment_size_scl.setBounds(210, 50, 50, 190);
		comment_color_label.setBounds(290, 50, 40, 21);
		comment_color_tf.setBounds(330, 50, 76, 21);
		comment_style_cb.setBounds(290, 100, 100, 21);
		
		comment_style_cb.setSelectedItem(option.getCommentFont().getFontStyle());
		j.add(comment_style_cb);
		
		//폰트 설정 부분
		j.add(comment_font_scl);
		comment_font_scl.setViewportView(comment_font_list);
		
		comment_font_list.setListData(fontName);
		comment_font_list.setSelectedValue(comment_FontFamily, true);
		
		//폰트 크기 설정 부분
		j.add(comment_size_scl);

		Integer[] sizeArray = new Integer[99];
		for (int i = 1; i < 100; i++)
			sizeArray[i - 1] = i;
		comment_size_scl.setViewportView(comment_size_list);
		comment_size_list.setListData(sizeArray);
		comment_size_list.setSelectedValue(option.getCommentFont().getFontSize(), true);

		
		comment_color_label.setText("색상  #");
		comment_color_tf.setDocument(new JExtendTextField("0-9a-fA-F", 6));
		comment_color_tf.setText(fullColorName(option.getCommentFont().getFontColor()));
		j.add(comment_color_tf);
		j.add(comment_color_label);
		
		comment_font_scl.setViewportView(comment_font_list);
	}

	// linenumberTab의 컴포넌트 선언
	/**
	 * 줄번호 문자에 지정할 폰트 리스트
	 */
	private JList<String> linenumber_font_list = new JList<String>();
	
	/**
	 * 줄번호 문자에 지정할 폰트 크기 리스트
	 */
	private JList<Integer> linenumber_size_list = new JList<Integer>();
	
	/**
	 * 줄번호 문자에 지정할 컬러 정보 지정 라벨
	 */
	private JLabel linenumber_color_label = new JLabel();
	
	/**
	 * 줄번호 문자에 지정할 컬러 정보 입력 필드
	 */
	private JTextField linenumber_color_tf = new JTextField();
	
	/**
	 * 폰트 리스트 스크롤 패널
	 */
	private JScrollPane linenumber_font_scl = new JScrollPane();
	
	/**
	 * 폰트 크기 리스트 스크롤 패널
	 */
	private JScrollPane linenumber_size_scl = new JScrollPane();
	
	/**
	 * 줄번호 문자에 지정할 폰트 스타일 
	 */
	private JComboBox<FontOption.FontStyle> linenumber_style_cb = new JComboBox<FontOption.FontStyle>(FontOption.FontStyle.values());

	/**
	 * 줄번호 문자 폰트 설정 탭 초기화 메소드
	 * @param j 삽입 되어질 부모 패널
	 */
	private void setLinenumberTab(JPanel j) {
		// linenumverTab의 컴포넌트 설정
		j.setLayout(null);
		linenumber_font_scl.setBounds(30, 50, 150, 190);
		linenumber_size_scl.setBounds(210, 50, 50, 190);
		linenumber_color_label.setBounds(290, 50, 40, 21);
		linenumber_color_tf.setBounds(330, 50, 76, 21);
		linenumber_style_cb.setBounds(290, 100, 100, 21);
		
		linenumber_style_cb.setSelectedItem(option.getLineNumberFont().getFontStyle());
		j.add(linenumber_style_cb);
		
		//폰트 설정 부분
		j.add(linenumber_font_scl);
		linenumber_font_scl.setViewportView(linenumber_font_list);
		linenumber_font_list.setListData(fontName);
		linenumber_font_list.setSelectedValue(linenumber_FontFamily, true);
		
		//폰트 크기 설정 부분
		j.add(linenumber_size_scl);

		Integer[] sizeArray = new Integer[99];
		for (int i = 1; i < 100; i++)
			sizeArray[i - 1] = i;
		linenumber_size_scl.setViewportView(linenumber_size_list);
		linenumber_size_list.setListData(sizeArray);
		linenumber_size_list.setSelectedValue(option.getLineNumberFont().getFontSize(), true);

		
		linenumber_color_label.setText("색상  #");
		linenumber_color_tf.setDocument(new JExtendTextField("0-9a-fA-F", 6));
		linenumber_color_tf.setText(fullColorName(option.getLineNumberFont().getFontColor()));
		j.add(linenumber_color_tf);
		j.add(linenumber_color_label);
		
		linenumber_font_scl.setViewportView(linenumber_font_list);
	}

	/**
	 * 닫기 버튼 이벤트 핸들러
	 */
	private void closeBtnEvent() {
		// 닫기버튼 이벤트: 현재 창을 닫음
		dispose();
	}

	/**
	 * 저장 버튼 이벤트 핸들러
	 */
	private void saveBtnEvent() {
		if(paper_upblank_tf.getText().equals("")){
			paper_upblank_tf.setText("0");
		}
		if(paper_downblank_tf.getText().equals("")){
			paper_downblank_tf.setText("0");
		}
		if(paper_leftblank_tf.getText().equals("")){
			paper_leftblank_tf.setText("0");
		}
		if(paper_rightblank_tf.getText().equals("")){
			paper_rightblank_tf.setText("0");
		}
		if(general_color_tf.getText().equals("")){
			general_color_tf.setText("0");
		}
		if(string_color_tf.getText().equals("")){
			string_color_tf.setText("0");
		}
		if(keyword_color_tf.getText().equals("")){
			keyword_color_tf.setText("0");
		}
		if(comment_color_tf.getText().equals("")){
			comment_color_tf.setText("0");
		}
		if(linenumber_color_tf.getText().equals("")){
			linenumber_color_tf.setText("0");
		}
		
		// 적용버튼 이벤트:현재 설정값들을 저장함
		option.setMargin(Integer.valueOf(paper_upblank_tf.getText()), Integer.valueOf(paper_downblank_tf.getText()), Integer.valueOf(paper_leftblank_tf.getText()), Integer.valueOf(paper_rightblank_tf.getText()));
		option.setPageType((PageOption.PageType) paper_size_cb.getSelectedItem());
		option.setBasicFont(fontData.get(general_font_list.getSelectedValue()), general_size_list.getSelectedValue(), Integer.valueOf(general_color_tf.getText(), 16), (FontOption.FontStyle) general_style_cb.getSelectedItem());
		option.setStringFont(fontData.get(string_font_list.getSelectedValue()), string_size_list.getSelectedValue(), Integer.valueOf(string_color_tf.getText(), 16), (FontOption.FontStyle) string_style_cb.getSelectedItem());
		option.setKeywordFont(fontData.get(keyword_font_list.getSelectedValue()), keyword_size_list.getSelectedValue(), Integer.valueOf(keyword_color_tf.getText(), 16), (FontOption.FontStyle) keyword_style_cb.getSelectedItem());
		option.setCommentFont(fontData.get(comment_font_list.getSelectedValue()), comment_size_list.getSelectedValue(), Integer.valueOf(comment_color_tf.getText(), 16), (FontOption.FontStyle) comment_style_cb.getSelectedItem());
		option.setLineNumberFont(fontData.get(linenumber_font_list.getSelectedValue()), linenumber_size_list.getSelectedValue(), Integer.valueOf(linenumber_color_tf.getText(), 16), (FontOption.FontStyle) linenumber_style_cb.getSelectedItem());
		
		option.saveOption();
		dispose();
		
	}
	
	/**
	 * 정수 컬러값을 문자열로 바꾸는 메소드
	 * @param color 정수로 된 컬러값
	 * @return 16진수로 형태의 문자열로 변환된 컬러 값
	 */
	private String fullColorName(int color){
		if(color < 16){
			return "00000" + Integer.toHexString(color).toUpperCase();
		}else if(color < 256){
			return "0000" + Integer.toHexString(color).toUpperCase();
		}else if(color < 4096){
			return "000" + Integer.toHexString(color).toUpperCase();
		}else if(color < 65536){
			return "00" + Integer.toHexString(color).toUpperCase();
		}else if(color < 1048576){
			return "0" + Integer.toHexString(color).toUpperCase();
		}else{
			return Integer.toHexString(color).toUpperCase();
		}
	}
}