package me.croma.image;

/**
 * Represent RGB image.
 */
public class Image {
    private final int image[][];
    private int height;
    private int width;

    /**
     * Bit map of Image array.
     * first 8 bit -> Blue
     * 2 ed 8 Bit -> Green
     * 3 ed 8 Bit -> Red
     * @param image array of rgb value of image.
     */
    public Image(int image[][]) {
        height = image.length;
        width = image.length == 0 ? 0 : image[0].length;
        this.image = image;//TODO validation and throw exception

    }

    public int getRGB(int x, int y) {
        return image[x][y];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
