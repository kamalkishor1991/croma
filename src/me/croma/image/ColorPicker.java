package me.croma.image;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ColorPicker {
	public List<Color> getProminentColors(File image, int  noOfColors) throws IOException;
}
