package me.croma.test;



import me.croma.image.*;
import me.croma.image.Color;
import me.croma.image.Image;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class TestColorPicker {
	public static void main(String args[])  {
        System.out.println(System.getProperty("user.dir"));
		//System.out.println(Arrays.toString(new File(System.getProperty("user.dir")).list()));
        try {

            String file = args.length != 0 ? args[0] :(System.getProperty("user.dir") + File.separator + "test" + File.separator );
           // file = "/media/kamal/New Volume/Photo (10.18.1.164)/Rock Garden/";
            int algo = args.length >= 2 ? Integer.parseInt(args[2]) : 0;
            int noOfColors = args.length >= 3 ? Integer.parseInt(args[1]) : 6;

            genColors(new File(file),algo, noOfColors);
        } catch (IOException e) {
            System.out.println("Usage: <Images dir path> <noOfColors> <algo (0 or 1)>");
            e.printStackTrace();
        }

    }

	private static void genColors(File file, int algo, int noC) throws IOException {
        PrintStream ps = new PrintStream(file.getAbsolutePath() + "/output" + algo + ".html");
        for (File f : file.listFiles()) {
            if(isImage(f)) {
                System.out.println(f);
                genHtml(f, algo, noC, ps);
            }
        }

		ps.println("</BODY>");
		ps.println("</HTML>");
		ps.close();
	}

    private static void genHtml(File file, int algo, int noC, PrintStream ps) throws IOException {
        ps.println("<HTML>");
        ps.println("<BODY>");
        String s = "<div color=\"#3CA\" style=\"\n" +
				"    size: a3;\n" +
				"    width: 50px;\n" +
                "display: table-cell;\n" +
				"    height: 50px;margin:10px;\n" +
				"    background: %s\n" +
				"\"></div>";

        //
        Image img = new AWTImage(file);
        ColorPicker km = algo == 0 ? new KMeansColorPicker() : new DBScanColorPicker();//new DBScanColorPicker();
        if (algo == 2) km = new MedianCutColorPicker();
        java.util.List<Color> l = km.getUsefulColors(img, noC);
        java.util.List<Color> t = new ArrayList<Color>();
        for (Color c : l) {
            float f[] = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue());
            //f[1] = 0.75F;
            t.add(new Color(Color.HSBtoRGB(f[0], f[1], f[2])));
        }


        ps.println("<IMG src = './" + file.getName() + "' style=\"\n" +
                "    width: 500;\n" +
                "\"></IMG><div style=\"\n" +
                "    margin-top: 10px;\n" +
                "    margin-bottom: 10px;\n" +
                "\"><p>" + file.getName() + "</p>");
        for (Color c : l) {
            ps.print(String.format(s, "#" + String.format("%06x", c.getRGB() & 0x00FFFFFF)) + " ");
        }
        ps.print("</div>");
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



    private static boolean isImage(File f) {
        if(f.getName().toLowerCase().endsWith(".png")) return true;
        String mimetype= new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        return type.equalsIgnoreCase("image");
    }

}
