package me.croma.image;

import me.croma.ml.DBScanClustering;

import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Using DBScan algorithm to cluster Colors.
 */
public class DBScanColorPicker implements ColorPicker {

    public DBScanColorPicker() {

    }

	public List<Color> getUsefulColors(Image img, int noOfColors) throws IOException {
        Image image = img.getScaledInstance(Math.min(50, img.getWidth()), Math.min(img.getHeight(),50));
		int h = image.getHeight();
		int w = image.getWidth();
		double input[][] = new double[h*w][3];
		int index = 0;
		for (int i = 0;i < w;i++) {
			for (int j = 0;j < h;j++) {
				int rgb = image.getColor(i,j).getRGB();
				input[index][0] = (rgb >> 16) & (0xFF);   //red
				input[index][1] = (rgb >> 8) & (0xFF);  //green
				input[index++][2] = (rgb) & (0xFF); //blue
			}
		}
		DBScanClustering db = new DBScanClustering(input, (2.5 * Math.sqrt(3) * 255)/(noOfColors), 40);
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
		List<Color> r = new ArrayList<Color>(noOfColors);
		for (double c[] : m) {
			r.add(new Color((int)Math.round(c[0]), (int)Math.round(c[1]), (int)Math.round(c[2])));
		}
		return r;

	}

}
