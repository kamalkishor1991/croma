package me.croma.test;



import me.croma.image.*;
import me.croma.image.Color;
import me.croma.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class TestColorPicker {
	public static void main(String args[]) throws IOException {
		System.out.println(System.getProperty("user.dir"));
		//System.out.println(Arrays.toString(new File(System.getProperty("user.dir")).list()));
		//G:\jwork\Chroma\Data\Puppy-images.jpg
		String file = "Puppy-images.jpg";
		genColors(new File(System.getProperty("user.dir") + File.separator + "Data" + File.separator + file));

	}

	private static void genColors(File file) throws IOException {
		String s = "<div color=\"#3CA\" style=\"\n" +
				"    size: a3;\n" +
				"    width: 50px;\n" +
				"    height: 50px;margin:10px;\n" +
				"    background: %s\n" +
				"\"></div>";

        //

        Image img = new Image(getImage(file));
		ColorPicker km = new DBScanColorPicker();//new DBScanColorPicker();
		java.util.List<Color> l = km.getUsefulColors(img, 3);

		PrintStream ps = new PrintStream(new FileOutputStream(file.getParentFile().getPath() + File.separator + file.getName() + ".html"));
		ps.println("<HTML>");
		ps.println("<BODY>");

		for (Color c : l) {
			ps.println(String.format(s, "#" + String.format("%06x", c.getRGB() & 0x00FFFFFF)));
		}

		ps.println("</BODY>");
		ps.println("</HTML>");
		ps.close();
	}


    public static int[][] getImage(File image) throws IOException {
        int h = 50;
        int w = 50;
        BufferedImage bi = ImageIO.read(image);
        java.awt.Image  im = bi.getScaledInstance(h, w, BufferedImage.SCALE_DEFAULT);
        bi = toBufferedImage(im);
        int v[][] = new int[h][w];
        for (int i = 0; i < v.length;i++) {
            for (int j = 0;j < v[0].length;j++) {
                v[i][j] = bi.getRGB(i, j);
            }
        }
        return v;
    }




    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    private static BufferedImage toBufferedImage(java.awt.Image img)
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
