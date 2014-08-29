package me.croma.image;

/**
 * It can be extended differentially on desktop(java.awt) and android(android.graphics).
 * Classes using Image class can be used on both platforms.
 */

public abstract class Image {

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
     * @return a scaled version of the image.
     */
    public abstract Image getScaledInstance(int width, int height);
}
