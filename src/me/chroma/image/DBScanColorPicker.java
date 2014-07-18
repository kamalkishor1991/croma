package me.chroma.image;

import me.chroma.ml.DBScanClustering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DBScanColorPicker implements ColorPicker {
	public List<Color> getProminentColors(File image, int noOfColors) throws IOException {
		BufferedImage bi = ImageIO.read(image);
		Image  im = bi.getScaledInstance(50, 50, BufferedImage.SCALE_DEFAULT);
		bi = toBufferedImage(im);
		int h = bi.getHeight();
		int w = bi.getWidth();
		double input[][] = new double[h*w][3];
		int index = 0;
		for (int i = 0;i < w;i++) {
			for (int j = 0;j < h;j++) {
				int rgb = bi.getRGB(i,j);
				input[index][0] = (rgb >> 16) & (0xFF);   //red
				input[index][1] = (rgb >> 8) & (0xFF);  //green
				input[index++][2] = (rgb) & (0xFF); //blue
			}
		}
		DBScanClustering db = new DBScanClustering(input, (Math.sqrt(3) * 255)/noOfColors, 100);
		int ans[] = db.startClustering();
		boolean isNoise[] = db.getNoiseArray();
		Map<Integer, Double[]> v = new HashMap<Integer, Double[]>();
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();
		for (int i = 0;i < ans.length;i++) {
			if(isNoise[i])continue;
			Double d[] = new Double[]{0.0, 0.0, 0.0};
			int ct = 1;
			if(v.containsKey(ans[i])) {
				d = v.get(ans[i]);
				ct = count.get(ans[i]);
				count.put(ans[i], ct + 1);
			} else {
				v.put(ans[i], d);
				count.put(ans[i], ct);
			}

			d[0] += input[i][0];
			d[1] += input[i][1];
			d[2] += input[i][2];

		}

		//double m[][] = km.getMeans();
		double m[][] = new double[v.size()][3];
		int mi = 0;
		for (int cluster: v.keySet()) {
			Double td[] = v.get(cluster);
			m[mi++] = new double[]{td[0]/count.get(cluster), td[1]/count.get(cluster), td[2]/count.get(cluster)};
		}
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


	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	private BufferedImage toBufferedImage(Image img)
	{
		if (img instanceof BufferedImage)
		{
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}


}
