package me.croma.image;

import me.croma.ml.KMeansClustering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMeansColorPicker implements ColorPicker {

	@Override
	public List<Color> getUsefulColors(Image img, int noOfColors) throws IOException {

        Image image = img.getScaledInstance(1000, 1000, Image.SCALE_DEFAULT);
		int h = image.getHeight();
		int w = image.getWidth();

		double input[][] = new double[h*w][3];
		int index = 0;
		for (int i = 0;i < w;i++) {
			for (int j = 0;j < h;j++) {
				int rgb = image.getColor(i,j).getRGB();

                Color c = new Color(rgb);
                float f[] = {c.getRed(), c.getGreen(), c.getBlue()};
                input[index][0] = f[0];
                input[index][1] = f[1];
                input[index++][2] = f[2];
			}
		}
		KMeansClustering km = new KMeansClustering(noOfColors, input);
		int ans[] = km.iterate(15);
		double m[][] = km.getMeans();
		List<Color> r = new ArrayList<Color>(noOfColors);
		for (double c[] : m) {
			r.add(new Color((int)c[0], (int)c[1],(int) c[2]));
		}
		return r;

		//System.out.println(Arrays.toString(ans));
	}

}
