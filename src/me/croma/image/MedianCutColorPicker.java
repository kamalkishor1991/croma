package me.croma.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MedianCutColorPicker implements ColorPicker {

    public MedianCutColorPicker() {

    }


    @Override
    public List<Color> getUsefulColors(Image img, int noOfColors) throws IOException {
        Image image = img.getScaledInstance(noOfColors, 1);
        List<Color> r = new ArrayList<Color>();
        Set<Color> set = new HashSet<Color>();
        for (int i = 0 ;i < image.getWidth();i++) {
            for (int j = 0;j < image.getHeight();j++) {
                Color c = image.getColor(i, j);
                if (!set.contains(c)) r.add(c);
                set.add(c);
            }
        }
        return r;
    }

}
