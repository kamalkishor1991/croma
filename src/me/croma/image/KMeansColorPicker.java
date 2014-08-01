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
	public List<Color> getUsefulColors(Image image, int noOfColors) throws IOException {


		int h = image.getHeight();
		int w = image.getWidth();
		double input[][] = new double[h*w][3];
		int index = 0;
		for (int i = 0;i < w;i++) {
			for (int j = 0;j < h;j++) {
				int rgb = image.getRGB(i,j);
				input[index][0] = (rgb >> 16) & (0xFF);   //red
				input[index][1] = (rgb >> 8) & (0xFF);  //green
				input[index++][2] = (rgb) & (0xFF); //blue
			}
		}
		KMeansClustering km = new KMeansClustering(noOfColors, input);
		int ans[] = km.iterate(20);
		double m[][] = km.getMeans();
		for (int i = 0;i < m.length;i++) {
			System.out.println(Arrays.toString(m[i]));
		}

		List<Color> r = new ArrayList<Color>(noOfColors);
		for (double c[] : m) {
			r.add(new Color((int)Math.round(c[0]), (int)Math.round(c[1]), (int)Math.round(c[2])));
		}
		return r;

		//System.out.println(Arrays.toString(ans));
	}

}
