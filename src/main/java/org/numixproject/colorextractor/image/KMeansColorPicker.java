package org.numixproject.colorextractor.image;

import org.numixproject.colorextractor.ml.KMeansClustering;


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
        int multiplier = 4;
        List<Color> kMeansColors = getKColors(image, noOfColors * multiplier);
        Collections.sort(kMeansColors, new Comparator<Color>() {
            @Override
            public int compare(Color o1, Color o2) {
                float d1[] = o1.getHSB();
                float d2[] = o2.getHSB();
                return d1[0] >= d2[0] ? 1 : -1;
            }
        });
        int n = multiplier / 2;
        List<Color> r = new ArrayList<>();
        for (int i = 0; i < kMeansColors.size(); i += multiplier) {
            List<Color> ll = new ArrayList<Color>();
            for (int j = 0; j < multiplier; j++) {
                if (i + j < kMeansColors.size()) ll.add(kMeansColors.get(i + j));
            }
            Collections.sort(ll, new Comparator<Color>() {
                @Override
                public int compare(Color o1, Color o2) {
                    return Double.compare(o1.getHSB()[1], o2.getHSB()[1]);
                }
            });
            for (int j = 0; j < n; j++) {
                if (ll.size() - j > 0) r.add(ll.get(ll.size() - 1 - j));//add pure colors.
            }
        }

        List<Color> finalColors = new ArrayList<>();
        for (int i = 0; i < r.size(); i += n) {
            List<Color> ll = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (i + j < r.size()) ll.add(r.get(i + j));
            }
            Collections.sort(ll, new Comparator<Color>() {
                @Override
                public int compare(Color o1, Color o2) {
                    return Double.compare(o1.getHSB()[2], o2.getHSB()[2]);
                }
            });
            if (ll.size() > 0) finalColors.add(ll.get(ll.size() - 1));
        }

        return finalColors;
    }


    private List<Color> getKColors(Image img, int noOfColors) throws IOException {
        int mx = Math.max(img.getHeight(), img.getWidth());
        int mn = Math.min(img.getHeight(), img.getWidth());
        int sh = Math.min(100, mx);
        int sw = Math.min((int) (sh * (mn * 1.0) / mx), mn);
        Image image = img.getScaledInstance(sh, sw);
        int h = image.getHeight();
        int w = image.getWidth();
        double input[][] = new double[h * w][3];

        int index = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int rgb = image.getColor(i, j).getRGB();
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
        Set<Color> setToRet = new HashSet<>();
        for (double c[] : m) {
            int ct[] = cs.LABtoRGB(c);
            Color cc = new Color(ct[0], ct[1], ct[2]);
            setToRet.add(cc);
        }
        return new ArrayList<>(setToRet);
    }


}
