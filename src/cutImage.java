//이미지를 등분 하여 개별 문항으로 만듬

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class cutImage {

	public static void main(String[] args) {
		try {
			//불러올 이미지 선택
			//*추후에는 폴더 단위로 불러야함
			//*또한 문제를 자를 것인지 해설을 자를것인지 선택해야함
			BufferedImage originalImgage = ImageIO.read(new File("img_question_origin/개강대비_문제_1회_3쪽.png"));

			//잘라낼 범위 지정
			BufferedImage SubImgage = originalImgage.getSubimage(100, 350, 2300, 950);

			//저장할 위치 선택
			//*읽어온 파일명을 기반하여 네이밍해야함
			File outputfile = new File("img_question/개강대비_문제_1회_4번.png");
			ImageIO.write(SubImgage, "png", outputfile);
			//*추후에는 위의과정을 분할 개수만큼 반복해야함

			System.out.println("자르기 완료");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}