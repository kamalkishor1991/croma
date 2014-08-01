package me.croma.image;


public class Color {
    final private int color;

    public Color(int color) {
        this.color = color;
    }

    public Color(int red, int green, int blue) {
        this.color  = ((red << 16)) + (green << 8) +  (blue);
    }


    public int getRGB() {
        return color;
    }
}
