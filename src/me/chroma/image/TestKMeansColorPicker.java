package me.chroma.image;




import org.junit.Test;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class TestKMeansColorPicker {
	@Test
	public void test1() throws IOException {
		KMeansColorPicker km = new KMeansColorPicker();
		List<Color> l = km.getProminentColors(new File("G:\\\\Photo (10.18.1.164)\\b.jpg"), 1);
		PrintStream ps = new PrintStream(new FileOutputStream("G:\\output.html"));

	}

}
