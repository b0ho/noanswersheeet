//이미지를 3등분 할것임

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class cutImage {

	public static void main(String[] args) {
		try {
			BufferedImage originalImgage = ImageIO.read(new File("img_solusion_origin\\답안1회002.png"));
			
			System.out.println("Original Image Dimension: "+originalImgage.getWidth()+"x"+originalImgage.getHeight());

			BufferedImage SubImgage = originalImgage.getSubimage(100, 350, 2300, 950);
			System.out.println("Cropped Image Dimension: "+SubImgage.getWidth()+"x"+SubImgage.getHeight());

			File outputfile = new File("img_solusion\\답안1회002.png");
			ImageIO.write(SubImgage, "png", outputfile);

			System.out.println("Image cropped successfully: "+outputfile.getPath());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}