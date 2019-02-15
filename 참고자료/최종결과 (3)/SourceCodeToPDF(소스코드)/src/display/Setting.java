package display;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import option.Option;
import option.OptionDefault;

/**
 * 변환 설정 창
 * @author 유병호
 *
 */
public class Setting extends JDialog {
   /**
    * SerialUID
    */
   private static final long serialVersionUID = 8905550640457174616L;
   
   /**
    * 옵션 정보
    */
   private Option option;
   
   /**
    * 저장 위치 지정을 위한 폴더 선택기
    */
   private JFileChooser locate_chooser = new JFileChooser();
   
   /**
    * 줄번호 표시 여부 체크박스
    */
   private JCheckBox linenumber_show_jb = new JCheckBox();
   
   /**
    * 변환 방식 표시 라벨
    */
   private JLabel conver_form_lb = new JLabel();
   
   /**
    * 변환 언어 표시 라벨
    */
   private JLabel type_form_lb = new JLabel();
   
   /**
    * 변환 언어 관련 콤보박스
    */
   private JComboBox<String> type_form_cb = new JComboBox<String>();
   
   /**
    * 탭 길이 지정 라벨
    */
   private JLabel tab_size_lb = new JLabel();
   
   /**
    * 탭 길이 지정 콤보박스
    */
   private JComboBox<Integer> tab_size_cb = new JComboBox<Integer>();
   
   /**
    * 헤더 내용 지정 라벨
    */
   private JLabel header_content_lb = new JLabel();
   
   /**
    * 헤더 내용 입력 필드
    */
   private JTextField header_content_tf = new JTextField();
   
   /**
    * 헤더 위치 지정 라벨
    */
   private JLabel header_location_lb = new JLabel();
   
   /**
    * 헤더 위치 지정 콤보박스
    */
   private JComboBox<String> header_location_cb = new JComboBox<String>();
   
   /**
    * 저장 위치 지정 라벨
    */
   private JLabel save_locate_lb = new JLabel();
   
   /**
    * 저장 위치 변경 버튼
    */
   private JButton save_locate_btn = new JButton();
   
   /**
    * 저장 위치 표시 텍스트 필드
    */
   private JTextField save_locate_tf = new JTextField();
   
   /**
    * 전체 변환 옵션 시 출력될 PDF 파일의 파일명 지정 라벨
    */
   private JLabel PDF_name_lb = new JLabel();
   
   /**
    * 전체 변환 옵션 시 출력될 PDF 파일명 지정 텍스트 필드
    */
   private JTextField PDF_name_tf = new JTextField();
   
   /**
    * 변환 정보 저장 버튼
    */
   private JButton setting_reset_btn = new JButton();
   
   /**
    * 변환 정보 저장 버튼
    */
   private JButton setting_save_btn = new JButton();
   
   /**
    * 변환 설정 창 닫기 버튼
    */
   private JButton setting_cancel_btn = new JButton();
   
   /**
    * 종합 변환 옵션 체크박스
    */
   private JCheckBox convert_all_rb = new JCheckBox();
   
   /**
    * 패키지 변환 옵션 체크박스
    */
   private JCheckBox convert_package_rb = new JCheckBox();
   
   /**
    * 파일 변환 옵션 체크박스
    */
   private JCheckBox convert_file_rb = new JCheckBox();

   /**
    * 생성자
    * @param op 옵션 정보
    */
   public Setting(Option op) {
      option = op;
   }

   /**
    * 디스플레이 셋업
    */
   public void setSettingPanel() {
      setTitle("변환 설정");
      setModal(true);
      setPreferredSize(new java.awt.Dimension(500, 350));
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getPreferredSize().getWidth() / 2), 
            (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getPreferredSize().getHeight() / 2));
      setLayout(null);
      
      linenumber_show_jb.setText("라인 넘버 표시");
      add(linenumber_show_jb);
      linenumber_show_jb.setBounds(40, 20, 200, 23);
      linenumber_show_jb.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            enableLinenumberOptoin();
         }
      });
      if (option.getShowLineNumber()==true) {
         linenumber_show_jb.setSelected(true);
         enableLinenumberOptoin();
      }

      conver_form_lb.setText("변환 방식");
      add(conver_form_lb);
      conver_form_lb.setBounds(40, 50, 130, 20);

      convert_all_rb.setText("종합 변환");
      add(convert_all_rb);
      convert_all_rb.setBounds(170, 50, 100, 20);
      convert_all_rb.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            converAllOption();
         }
      });
      if (option.getConvertAll()==true) {
         convert_all_rb.setSelected(true);
         converAllOption();
      }
      
      convert_package_rb.setText("패키지 변환");
      add(convert_package_rb);
      convert_package_rb.setBounds(270, 50, 100, 20);
      convert_package_rb.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            converPackageOption();
         }
      });
      if (option.getConvertPackage()==true) {
         convert_package_rb.setSelected(true);
         converPackageOption();
      }
      
      convert_file_rb.setText("개별 변환");
      add(convert_file_rb);
      convert_file_rb.setBounds(370, 50, 100, 20);
      convert_file_rb.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            converFileOption();
         }
      });
      if (option.getConvertFile()==true) {
         convert_file_rb.setSelected(true);
         converFileOption();
      }
      
      type_form_lb.setText("언어 선택");
      add(type_form_lb);
      type_form_lb.setBounds(40, 80, 130, 20);
      
      type_form_cb.setModel(new DefaultComboBoxModel<>(new String[] { "자바", "C", "C++" }));
      type_form_cb.setSelectedIndex(converLanguageToItemIndex(option.getCodeLanguage()));
      add(type_form_cb);
      type_form_cb.setBounds(170, 80, 100, 20);

      tab_size_lb.setText("탭 길이");
      add(tab_size_lb);
      tab_size_lb.setBounds(40, 110, 130, 20);
      
      tab_size_cb.setModel(new DefaultComboBoxModel<>(new Integer[] {1, 2, 3, 4, 5, 6}));
      tab_size_cb.setSelectedIndex(converNumToItemIndex(option.getTabSize()));
      add(tab_size_cb);
      tab_size_cb.setBounds(170, 110, 100, 20);
         
      header_content_lb.setText("헤더 내용");
      add(header_content_lb);
      header_content_lb.setBounds(40, 140, 100, 20);
      
      add(header_content_tf);
      header_content_tf.setBounds(170, 140, 100, 20);
      header_content_tf.setDocument(new JExtendTextField("^ㄱ-ㅎㅏ-ㅣ가-힣", 40));
      header_content_tf.setText(option.getHeaderTitle());
      
      header_location_lb.setText("헤더 위치");
      add(header_location_lb);
      header_location_lb.setBounds(40, 170, 100, 20);
      
      header_location_cb.setModel(new DefaultComboBoxModel<>(new String[] { "왼쪽", "중앙", "오른쪽" }));
      header_location_cb.setSelectedIndex(convertAlignToItemIndex(option.getHeaderAlign()));
      add(header_location_cb);
      header_location_cb.setBounds(170, 170, 100, 20);
            
      save_locate_lb.setText("저장 경로");
      add(save_locate_lb);
      save_locate_lb.setBounds(40, 200, 130, 20);
      
      save_locate_btn.setText("변경하기");
      save_locate_btn.setBounds(170, 200, 100, 20);
      add(save_locate_btn);
      
      save_locate_tf.setText(option.getSaveLocate());
      save_locate_tf.setBounds(290, 200, 170, 20);
      save_locate_tf.setEditable(false);
      add(save_locate_tf);
      
      save_locate_btn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            locateBtnEvent(evt);
         }
      });

      PDF_name_lb.setText("파일 이름 설정");
      add(PDF_name_lb);
      PDF_name_lb.setBounds(40, 230, 100, 20);
      
      add(PDF_name_tf);
      PDF_name_tf.setBounds(170, 230, 100, 20);
      PDF_name_tf.setDocument(new JExtendTextField("^/\\:*?\"<>|.",200));
      PDF_name_tf.setText(option.getFileName());

      setResizable(false);

      
      setting_reset_btn.setText("기본값 복원");
      setting_reset_btn.setBounds(40, 270, 150, 23);
      add(setting_reset_btn);
      setting_reset_btn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            OptionDefault.getDefaultOption(option);
            option.saveOption();
            dispose();
         }
      });
      setting_save_btn.setText("적용");
      setting_save_btn.setBounds(250, 270, 83, 23);
      add(setting_save_btn);
      setting_save_btn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            saveBtnEvent();
         }
      });
      setting_cancel_btn.setText("닫기");
      setting_cancel_btn.setBounds(350, 270, 83, 23);
      add(setting_cancel_btn);
      setting_cancel_btn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            closeBtnEvent();
         }
      });
      pack();
   }

   /**
    * 종합 변환 체크박스 변화 이벤트 핸들러
    */
   private void converAllOption() {
      if (convert_all_rb.isSelected()) {
         option.setConvertAll(true);
      }
      else {
         if(!(option.getConvertPackage() || option.getConvertFile())){
            JOptionPane.showMessageDialog(null, "변환 옵션중 한 가지는 선택해야 합니다.", "에러", JOptionPane.ERROR_MESSAGE);
            convert_all_rb.setSelected(true);
         }else{
            option.setConvertAll(false);
         }
      }
   }
   
   /**
    * 패키지 변환 체크박스 변화 이벤트 핸들러
    */
   private void converPackageOption() {
      if (convert_package_rb.isSelected()) {
         option.setConvertPackage(true);
      }
      else {
         if(!(option.getConvertAll() || option.getConvertFile())){
            JOptionPane.showMessageDialog(null, "변환 옵션중 한 가지는 선택해야 합니다.", "에러", JOptionPane.ERROR_MESSAGE);
            convert_package_rb.setSelected(true);
         }else{
            option.setConvertPackage(false);
         }
      }
   }
   
   /**
    * 개별 변환 체크박스 변화 이벤트 핸들러
    */
   private void converFileOption() {
      if (convert_file_rb.isSelected()) {
         option.setConvertFile(true);
      }
      else {
         if(!(option.getConvertAll() || option.getConvertPackage())){
            JOptionPane.showMessageDialog(null, "변환 옵션중 한 가지는 선택해야 합니다.", "에러", JOptionPane.ERROR_MESSAGE);
            convert_file_rb.setSelected(true);
         }else{
            option.setConvertFile(false);
         }
      }
   }
   
   /**
    * 줄번호 표시 체크박스 변화 이벤트 핸들러
    */
   private void enableLinenumberOptoin() {
      if (linenumber_show_jb.isSelected()) {
         option.setShowLineNumber(true);
      }
      else {
         option.setShowLineNumber(false);
      }
   }

   /**
    * 저장버튼 이벤트 핸들러
    */
   private void saveBtnEvent() {
      enableLinenumberOptoin();
      converAllOption();
      converPackageOption();
      converFileOption();
      option.setCodeLanguage(convertItemToLanguage(type_form_cb.getSelectedItem()));
      option.setTabSize((int) tab_size_cb.getSelectedItem());
      option.setHeaderTitle(header_content_tf.getText());
      option.setHeaderAlign(convertItemToAlign(header_location_cb.getSelectedItem()));
      option.setFileName(PDF_name_tf.getText());
      option.setSaveLocate(save_locate_tf.getText());
      option.saveOption();
      dispose();
   }

   /**
    * 창 닫기 버튼 이벤트 핸들러
    */
   private void closeBtnEvent() {
      dispose();
   }

   /**
    * 탭 길이 변환 메소드
    * @param ts 탭 길이
    * @return 변환된 탭 길이
    */
   private int converNumToItemIndex(int ts){
      if (ts == 1) {
         return 0;
      } else if (ts == 2) {
         return 1;
      }else if (ts == 3) {
         return 2;
      }else if (ts == 4) {
         return 3;
      }else if (ts == 5) {
         return 4;
      }else if (ts == 6) {
         return 5;
      }else {
         return 3;
      }
   }
   
   /**
    * 콤보박스의 언어 내용을 옵션 타입으로 변경하는 메소드
    * @param obj 콤보박스에 지정된 언어 명칭
    * @return 변환된 언어 타입
    */
   private Option.Language convertItemToLanguage(Object obj) {
      String s = obj.toString();
      if (s.equals("자바")) {
         return Option.Language.JAVA;
      } else if (s.equals("C")) {
         return Option.Language.C;
      } else if (s.equals("C++")) {
         return Option.Language.CPP;
      } else {
         return Option.Language.JAVA;
      }
   }

   /**
    * 옵션의 언어 타입을 콤보박스의 인덱스로 변경하는 메소드
    * @param lang 옵션 정보의 언어 타입
    * @return 콤보박스의 인덱스
    */
   private int converLanguageToItemIndex(Option.Language lang) {
      if (lang == Option.Language.JAVA) {
         return 0;
      } else if (lang == Option.Language.C) {
         return 1;
      } else if (lang == Option.Language.CPP) {
         return 2;
      } else {
         return 0;
      }
   }

   /**
    * 콤보박스의 헤더 위치 내용을 옵션 설정 값으로 바꾸는 메소드
    * @param obj 콤보박스 값
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
    * 옵션 설정 값을 콤보박스의 인덱스로 바꾸는 메소드
    * @param align 옵션 설정 값
    * @return 콤보박스 인덱스
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
   
   /**
    * 저장 위치 변경 버튼 이벤트 핸들러
    * @param evt 버튼 이벤트
    */
   private void locateBtnEvent(ActionEvent evt) {
      locate_chooser.setSelectedFile(null);
      locate_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      locate_chooser.showOpenDialog(this);

      if (locate_chooser.getSelectedFile() != null) {
         save_locate_tf.setText(locate_chooser.getSelectedFile().toString().replaceAll("\\\\", "/")+"/");
      }
   }

}