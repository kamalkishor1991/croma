package me.chroma.test;



import me.chroma.image.ColorPicker;
import me.chroma.image.DBScanColorPicker;
import me.chroma.image.KMeansColorPicker;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

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
		ColorPicker km = new KMeansColorPicker();//new DBScanColorPicker();
		java.util.List<Color> l = km.getProminentColors(file, 3);

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
}
