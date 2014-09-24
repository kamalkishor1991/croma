package me.croma.image;

import me.croma.ml.KMeansClustering;


import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Using KMeans algorithm for clustering colors.
 */
public class KMeansColorPicker implements ColorPicker {
    private final ColorSpaceConverter cs = new ColorSpaceConverter();
    public KMeansColorPicker() {
        //default constructor
    }

    @Override
    public List<Color> getUsefulColors(Image image, int noOfColors) throws IOException {
        int m = 4;
        List<Color> l = getKColors(image, noOfColors * m);
        Collections.sort(l, new Comparator<Color>() {
            @Override
            public int compare(Color o1, Color o2) {
                float d1[] = o1.getHSB();
                float d2[] = o2.getHSB();
                return d1[0] >= d2[0] ? 1 : -1;
            }
        });
        int n = m/2;
        List<Color> r = new ArrayList<Color>();
        for (int i = 0;i < l.size();i += m) {
            List<Color> ll = new ArrayList<Color>();
            for (int j = 0;j < m;j++) {
                if (i + j < l.size()) ll.add(l.get(i + j));
            }
            Collections.sort(ll, new Comparator<Color>() {
                @Override
                public int compare(Color o1, Color o2) {
                    return Double.compare(o1.getHSB()[1], o2.getHSB()[1]);
                }
            });
            for (int j = 0;j < n;j++) {
                if (ll.size() - j > 0) r.add(ll.get(ll.size() - 1 - j));//add pure colors.
            }
        }

        List<Color> rr = new ArrayList<Color>();
        for (int i = 0;i < r.size();i += n) {
            List<Color> ll = new ArrayList<Color>();
            for (int j = 0;j < n;j++) {
                if (i + j < r.size()) ll.add(r.get(i + j));
            }
            Collections.sort(ll, new Comparator<Color>() {
                @Override
                public int compare(Color o1, Color o2) {
                    return Double.compare(o1.getHSB()[2], o2.getHSB()[2]);
                }
            });
            if (ll.size() > 0) rr.add(ll.get(ll.size() - 1));
        }
       /* //List<Color> r = l.subList(0, noOfColors);
        for (int i = 0 ;i < l.size();i++) {
            System.out.print(l.get(i).getHSB()[0] + ",");
        }*/
        return rr;
    }


	private List<Color> getKColors(Image img, int noOfColors) throws IOException {
        int mx = Math.max(img.getHeight(), img.getWidth());
        int mn = Math.min(img.getHeight(), img.getWidth());
        int sh = Math.min(100, mx);
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
                double f[] = cs.RGBtoLAB(c.getRed(), c.getGreen(), c.getBlue());
                input[index][0] = f[0];
                input[index][1] = f[1];
                input[index++][2] = f[2];
			}
		}
		KMeansClustering km = new KMeansClustering(noOfColors, input);
		km.iterate(20);
		double m[][] = km.getMeans();
		List<Color> r = new ArrayList<Color>(noOfColors);
        Set<Color> set = new HashSet<Color>();
        for (double c[] : m) {
            int ct[] = cs.LABtoRGB(c);
            Color cc = new Color(ct[0], ct[1], ct[2]);
            if (!set.contains(cc)) r.add(cc);
            set.add(cc);
		}
		return r;
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
