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
	public static void main(String args[])  {
		System.out.println(System.getProperty("user.dir"));
		//System.out.println(Arrays.toString(new File(System.getProperty("user.dir")).list()));
        try {
            String file = args.length != 0 ? args[0] :(System.getProperty("user.dir") + File.separator + "Data" + File.separator ) + "Puppy-images.jpg";
            int algo = args.length >= 2 ? Integer.parseInt(args[2]) : 0;
            int noOfColors = args.length >= 3 ? Integer.parseInt(args[1]) : 4;

            genColors(new File(file),algo, 4);
        } catch (IOException e) {
            System.out.println("Usage: <Image path> <noOfColors> <algo (0 or 1)>");
            e.printStackTrace();
        }

    }

	private static void genColors(File file, int algo, int noC) throws IOException {
		String s = "<div color=\"#3CA\" style=\"\n" +
				"    size: a3;\n" +
				"    width: 50px;\n" +
				"    height: 50px;margin:10px;\n" +
				"    background: %s\n" +
				"\"></div>";

        //
        int h = algo == 0 ? 1000 : 70;
        int w = algo == 0 ? 1000 : 70;
        Image img = new Image(getImage(file, w, h));

		ColorPicker km = algo == 0 ? new KMeansColorPicker() : new DBScanColorPicker();//new DBScanColorPicker();
		java.util.List<Color> l = km.getUsefulColors(img, noC);

		PrintStream ps = new PrintStream(file.getAbsolutePath() + ".html");
		ps.println("<HTML>");
		ps.println("<BODY>");
        ps.println("<IMG src = '" + file.getAbsolutePath() + "' style=\"\n" +
                "    width: 500;\n" +
                "\"></IMG>");
		for (Color c : l) {
			ps.print(String.format(s, "#" + String.format("%06x", c.getRGB() & 0x00FFFFFF)) + " ");
		}

		ps.println("</BODY>");
		ps.println("</HTML>");
		ps.close();
	}


    public static int[][] getImage(File image, int w, int h) throws IOException {

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
