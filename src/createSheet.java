//표지와 이미지를 합쳐 새로운 한글 문서를 생성
//최종적으로 오답노트를 생성해야함

import hwplib.object.HWPFile;
import hwplib.reader.HWPReader;
import hwplib.writer.HWPWriter;

public class createSheet {
    public static void main(String[] args) throws Exception {
        //표지 로드
        //*로드된 파일/폴더 정보로 부터 표지를 로드해야함
        String filename = "hwp_origin/표지_개강대비.hwp";
        HWPFile hwpFile = HWPReader.fromFile(filename);

        //빈 hwp 파일 생성
        //HWPFile hwpFileNew = new HWPFile();
        //HWPWriter.toFile(hwpFileNew, filename);

        if (hwpFile != null) {
            //이름 테이블 추가
            makeTableSet tmt = new makeTableSet();
            tmt.makeTable(hwpFile, "정서영");

            //문제 이미지 추가
            insertImageSet tii = new insertImageSet();
            tii.insertShapeWithImage(hwpFile, "img_question/개강대비_문제_1회_1번.png");

            //새로운 오답노트 생성
            //*로드 된 파일을 기반으로 네이밍해야함
            String writePath = "hwp_output/개강대비_1회_정서영.hwp";
            HWPWriter.toFile(hwpFile, writePath);

            //*최종적으로 학생답안 리스트를 받은 뒤 그만큼 반복해야함
        }
    }
}
