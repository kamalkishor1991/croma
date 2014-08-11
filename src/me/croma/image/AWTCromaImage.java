package me.croma.image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AWTCromaImage extends AWTImage {


    public AWTCromaImage(File f) throws IOException {
        super(f);
    }
}
