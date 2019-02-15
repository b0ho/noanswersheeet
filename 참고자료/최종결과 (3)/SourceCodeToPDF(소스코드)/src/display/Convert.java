package display;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import converter.CodeConverter;
import converter.ConvertPrograssEvent;
import converter.IConvertPrograssListener;
import loader.Checker;
import option.Option;
import option.Security;

/**
 * 변환 다이얼로그 창
 * @author 김선일
 *
 */
public class Convert extends JDialog {
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = -6990134427787874178L;

	/**
	 * 변환 버튼
	 */
    private JButton convert_convert_btn;
    
    /**
     * 변환 창 닫기 버튼
     */
    private JButton convert_cancel_btn;
    
    /**
     * 변환 진행 바
     */
    private JProgressBar convert_progressbar;
    
    /**
     * 변환할 소스코드 리스트 뷰 패널
     */
    private JScrollPane convert_source_scp;
    
    /**
     * 변환할 소스코드 리스트
     */
    private JList<String> convert_source_list;
	
    /**
     * 변환 소스코드 정보
     */
	private Checker checker;
	
	/**
	 * 옵션 정보
	 */
	private Option option;
	
	/**
	 * 보안 정보
	 */
	private Security security;
	
	/**
	 * 변환기
	 */
	private CodeConverter converter;
	
	/**
	 * 셍상지<p>
	 * 변수를 초기화 하고 이벤트를 등록합니다.
	 * @param chker 소스코드 정보
	 * @param op 옵션 정보
	 * @param sec 보안 정보
	 */
	public Convert(Checker chker, Option op, Security sec){
		checker = chker;
		option = op;
		security = sec;
		converter = new CodeConverter(checker, option, security);
		converter.addConvertPrograssListener(new IConvertPrograssListener() {
			@Override
			public void convertPrograss(ConvertPrograssEvent event) {
				convert_progressbar.setValue(event.getCurrentPrograss());
				convert_progressbar.setMaximum(event.getTotalPrograss());
			
				//변환완료시 완료다이얼로그 출력
				//확인버튼 클릭 후 변환창 종료
				if(event.getCurrentPrograss() == event.getTotalPrograss()){
					showFinishDialog();
					dispose();
				}
			}
		});
	}
	
	/**
	 * 디스플레이 셋업
	 */
	public void setConvertDialog(){
		setTitle("소스코드 변환");
		//변환프레임 컴포넌트 설정
		convert_progressbar = new JProgressBar();
        convert_source_scp = new JScrollPane();
        convert_convert_btn = new JButton();
        convert_cancel_btn = new JButton();

        
        convert_source_list = new JList<String>();
        
        setModal(true);
        setPreferredSize(new Dimension(500, 300));
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getPreferredSize().getWidth() / 2), 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getPreferredSize().getHeight() / 2));
        setName("변환"); 
        
        setLayout(null);

        convert_progressbar.setStringPainted(true);
        add(convert_progressbar);
        convert_progressbar.setBounds(60, 54, 290, 20);
        add(convert_source_scp);
        convert_source_scp.setBounds(60, 80, 290, 140);
        convert_source_scp.setViewportView(convert_source_list);
        convert_source_list.setListData(checker.fileListString);
        
        convert_convert_btn.setText("변환");
        convert_convert_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                convertBtnEvent(evt);
            }
        });
        add(convert_convert_btn);
        convert_convert_btn.setBounds(360, 50, 57, 23);

        convert_cancel_btn.setText("닫기");
        convert_cancel_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                convertCancleBtnEvent(evt);
            }
        });
        add(convert_cancel_btn);
        convert_cancel_btn.setBounds(360, 230, 57, 23);
        setResizable(false);
        pack();
	}
	
	/**
	 * 변환 버튼 이벤트 헨들러
	 * @param evt 버튼 이벤트
	 */
	private void convertBtnEvent(ActionEvent evt) {
		if(!converter.isAlive()){
			converter.start();
			convert_convert_btn.setEnabled(false);
		}
    }
	
	/**
	 * 닫기 버튼 이벤트 핸들러
	 * @param evt 버튼 이벤트
	 */
	private void convertCancleBtnEvent(ActionEvent evt) { 
        dispose();
    }
	
	/**
	 * 변환 완료시 처리 작업
	 */
	private void showFinishDialog(){
		convert_convert_btn.setEnabled(true);
		JOptionPane.showMessageDialog(null, "변환이 완료되었습니다!", "변환완료", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
