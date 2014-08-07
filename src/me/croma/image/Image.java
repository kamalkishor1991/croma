package me.croma.image;


public abstract class Image {
    /**
     * Default hint for getScaledImage
     */
    public static final int SCALE_DEFAULT = 0;
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    protected final int height, width;

    protected Image(int width, int height) {
        this.height = height;
        this.width = width;
    }

    /**
     * @param x x position
     * @param y y position
     * @return Color at (x, y)
     */
    public abstract Color getColor(int x, int y);

    /**
     * Creates a scaled version of this image. A new Image object is returned
     * @param width the width to which to scale the image.
     * @param height the height to which to scale the image.
     * @param hints  flags to indicate the type of algorithm to use for image resampling(specified by implementation).
     * @return a scaled version of the image.
     * @throws java.lang.IllegalArgumentException
     */
    public abstract Image getScaledInstance(int width, int height, int hints);
}
