
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import loader.*;

/**
 * 프로그램의 메인 윈도우입니다.
 * 로고 윈도우가 종료되면 실행됩니다.
 * 메인 윈도우가 종료되면 프로그램이 종료되게 됩니다.
 */

public class windowMain extends JFrame {

    private static final long serialVersionUID = -5214565881742533040L;

    /** 비디오 리스트 */
    private VideoList videoList;
    /** 타이틀 정보 */
    private static final String label_Title = "Zoom Encoder";
    /** 불러오기 버튼 */
    private JButton videoLoadButton;
    /** 도움말 버튼 */
    private JButton helpButton;
    /** 변환 버튼 */
    private JButton convertButton;
    /** 목록 위로 올리기 버튼 */
    private JButton listupButton;
    /** 목록 아래로 내리기 버튼 */
    private JButton listdownButton;
    /** 목록에서 제거 버튼 */
    private JButton selecteddelButton;
    /** 세부 정보 표시 라벨 */
    private JLabel detailLabel;
    /** 비디오 목록 표시 컴포넌트 */
    private JList<Video> videoListComponent;
    /** 비디오 목록 컴포넌트 스크롤 패널 */
    private JScrollPane videoListScroll;
    /** 파일 및 디렉토리 선택 창 */
    private JFileChooser videoFileChooser;
    /** 버튼 사이 구별 수평선 */
    private JSeparator horizonLine;

    /**
     * 프로그램 메인 윈도우 생성자 입니다.
     */
    public windowMain() {
        initComponents();
    }

    /**
     * 컴포넌트 초기화 메서드 입니다.</br>
     * 컴포넌트 레이아웃 설정 및 버튼 이벤트 등록용 메서드 입니다.
     */
    private void initComponents() {
        //데이터 초기화
        videoList = new VideoList();
        //컴포넌트 초기화
        videoListScroll = new JScrollPane();
        videoListComponent = new JList< Video>();
        videoLoadButton = new JButton();
        detailLabel = new JLabel();
        helpButton = new JButton();
        convertButton = new JButton();
        selecteddelButton = new JButton();
        horizonLine = new JSeparator();

        videoFileChooser = new JFileChooser("./");
        videoFileChooser.setFileFilter(new FileNameExtensionFilter("Select Video File OR Directory", "mp4", "MP4"));
        videoFileChooser.setMultiSelectionEnabled(true);
        videoFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //레이아웃 및 위치 설정
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(910, 610));
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getPreferredSize().getWidth() / 2),
                (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getPreferredSize().getHeight() / 2));
        setTitle(label_Title);
        setResizable(false);
        getContentPane().setLayout(null);

        videoListComponent.setFont(new Font("맑은 고딕", 0, 14));
        videoListComponent.setModel(new DefaultListModel<Video>());
        videoListComponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        videoListComponent.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                videoListSelectEvent(evt);
            }
        });

        videoListScroll.setViewportView(videoListComponent);
        getContentPane().add(videoListScroll);
        videoListScroll.setBounds(12, 10, 600, 410);

        videoLoadButton.setText("불러오기");
        getContentPane().add(videoLoadButton);
        videoLoadButton.setBounds(12, 426, 150, 45);
        videoLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                videoLoadButtonEvent(evt);
            }
        });

        detailLabel.setBackground(new Color(102, 102, 102));
        detailLabel.setText("불러오기를 클릭하여 영상을 로드해 주세요.");
        getContentPane().add(detailLabel);
        detailLabel.setBounds(619, 10, 269, 474);

        helpButton.setText("도움말");
        getContentPane().add(helpButton);
        helpButton.setBounds(512, 490, 100, 70);
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                helpButtonEvent(evt);
            }
        });

        convertButton.setText("변환하기");
        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                convertButtonEvent(evt);
            }
        });
        getContentPane().add(convertButton);
        convertButton.setBounds(619, 490, 269, 70);

        selecteddelButton.setText("선택제거");
        selecteddelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                videoListDelButtonEvent(evt);
            }
        });
        getContentPane().add(selecteddelButton);
        selecteddelButton.setBounds(512, 426, 100, 45);

        getContentPane().add(horizonLine);
        horizonLine.setBounds(12, 482, 600, 2);

        pack();
    }


    /**
     * "변환하기"버튼을 클릭시 진행하는 이벤트 입니다.
     * @param evt 이벤트 객체
     */
    private void convertButtonEvent(ActionEvent evt) {
        if(!videoList.isEmpty()){
            //new ConvertWindow(videoList).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "로드된 영상이 없습니다." + '\n' + "영상 불러오기 버튼을 클릭하여 영상을 로드해 주세요", "로드된 영상없음", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * "불러오기"버튼 클릭시 진행하는 이벤트 입니다.
     * @param evt 이벤트 객체
     */
    private void videoLoadButtonEvent(ActionEvent evt){
        videoLoadButton.setIcon(new ImageIcon("res/loading.gif"));
        videoLoadButton.setText("");

        VideoLoader loader = new VideoLoader();
        int result = videoFileChooser.showOpenDialog(this);
        if (videoFileChooser.getSelectedFiles() != null && result == JFileChooser.APPROVE_OPTION) {
            loader.selectVideo(videoFileChooser.getSelectedFiles());
        }
        if(loader.getVideoList().size() > 0){
            for(Video v : loader.getVideoList()){
                if(this.videoList.size() >= 50) break;
                this.videoList.add(v);
            }
        }
        videoListComponent.setListData(this.videoList.toArray(new Video[] {}));

        videoLoadButton.setIcon(null);
        videoLoadButton.setText("불러오기");
        if(videoListComponent.getSelectedIndex() == -1){
            videoListComponent.setSelectedIndex(0);
        }
    }

    /**
     * 비디오 목록 선택 시 진행되는 이벤트 입니다.
     * @param evt 이벤트 객체
     */
    private void videoListSelectEvent(ListSelectionEvent evt){
        if(videoListComponent.getSelectedIndex() < 0) return;
        StringBuffer str = new StringBuffer();
        Video v = this.videoList.get(videoListComponent.getSelectedIndex());
        str.append("<html>");
        str.append("<b>&emsp;[파일 정보]</b><br>");
        str.append("&emsp;파일 명 : " + v.toString() + "<br>");
        str.append("&emsp;파일 경로 : " + v.getVideoFilePath() + "<br>");
        if(v.getVideoSize(Video.VolUnit.MB) < 1000.0){
            str.append("&emsp;파일 용량 : " + v.getVideoSize(Video.VolUnit.MB) + "MB<br>");
        }else{
            str.append("&emsp;파일 용량 : " + v.getVideoSize(Video.VolUnit.GB) + "GB<br>");
        }
        str.append("<br><b>&emsp;[영상 정보]</b><br>");
        str.append("&emsp;해상도 : " + v.getWidth() + "x" + v.getHeight() + "<br>");
        str.append("&emsp;프레임레이트 : " + String.format("%.3f", v.getFramerate()) + "fps<br>");
        str.append("&emsp;코덱 : " + v.getCodec() + "<br>");
        str.append("&emsp;비트레이트 : " + v.getBitrate() + "Kbps<br>");
        str.append("</html>");
        detailLabel.setText(str.toString());
    }

    /**
     * "선택제거"버튼 클릭시 진행하는 이벤트 입니다.
     * @param evt 이벤트 객체
     */
    private void videoListDelButtonEvent(ActionEvent evt){
        if(this.videoList.size() == 0) return;
        int currentIndex = videoListComponent.getSelectedIndex();
        if(currentIndex >= 0){
            this.videoList.remove(currentIndex);
            videoListComponent.setListData(this.videoList.toArray(new Video[] {}));
            detailLabel.setText("");
        }
    }
    /**
     * "도움말"버튼 클릭시 진행하는 이벤트 입니다.
     * @param evt 이벤트 객체
     */
    private void helpButtonEvent(ActionEvent evt){
        new windowHelp().setVisible(true);
    }
}
