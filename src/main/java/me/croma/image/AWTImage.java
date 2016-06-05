package me.croma.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represent awt Image.
 */
public class AWTImage extends Image  {
    protected BufferedImage br;
    public AWTImage(BufferedImage br) {
        super(br.getWidth(), br.getHeight());
        this.br = br;
    }

    public AWTImage(File f) throws IOException {
        this(ImageIO.read(f));
    }

    @Override
    public Color getColor(int x, int y) {
        return new Color(br.getRGB(x, y));
    }


    /**
     *
     * @param width the width to which to scale the image.
     * @param height the height to which to scale the image.
     * @return new scaled Instance of AWTImage
     */
    @Override
    public Image getScaledInstance(int width, int height) {
        java.awt.Image  im = br.getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT);
        BufferedImage t = AWTImage.toBufferedImage(im);
        return new AWTImage(t);
    }



    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(java.awt.Image img)
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
