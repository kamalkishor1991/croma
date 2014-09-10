package me.croma.image;

import me.croma.ml.KMeansClustering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Using KMeans algorithm for clustering colors.
 */
public class KMeansColorPicker implements ColorPicker {

	@Override
	public List<Color> getUsefulColors(Image img, int noOfColors) throws IOException {
        int mx = Math.max(img.getHeight(), img.getWidth());
        int mn = Math.min(img.getHeight(), img.getWidth());
        int sh = Math.min(500, mx);
        int sw = Math.min((int)(sh * (mn * 1.0)/mx), mn);
        Image image = img.getScaledInstance(sh, sw);
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
		km.iterate(30);
		double m[][] = km.getMeans();
		List<Color> r = new ArrayList<Color>(noOfColors);

        for (double c[] : m) {
            r.add(new Color((int)c[0], (int)c[1],(int) c[2]));
		}
        List<Color> ret = new ArrayList<Color>();
        Set<Color> set = new HashSet<Color>();
        for (Color c : r) {
            double min[] = input[0];
            double ct[] = {c.getRed(), c.getGreen(), c.getBlue()};
            double dis = Double.MAX_VALUE;
            for (int i = 0; i < input.length; i++) {
                if (distance(input[i] , ct ) < dis) {
                    dis = distance(input[i] , ct );
                    min = input[i];
                }
            }
            Color cc = new Color((int)min[0], (int)min[1], (int)min[2]);
            if (!set.contains(cc)) {
                ret.add(cc);
            }
            set.add(cc);
        }

		return ret;

		//System.out.println(Arrays.toString(ans));
	}


    /**
     * Euclidean distance
     * @param input1 input vector 1
     * @param input2 input vector 2
     * @return distance b/w two input vectors
     */
    private double distance(double input1[], double input2[]) {
        double r = 0;
        for(int i = 0;i < input1.length;i++) {
            r += (input1[i] - input2[i]) * (input1[i] - input2[i]);
        }
        return r;
    }


}
