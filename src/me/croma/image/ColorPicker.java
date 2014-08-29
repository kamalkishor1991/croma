package me.croma.image;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Get useful colors from an Image.
 * @see me.croma.image.DBScanColorPicker
 * @see me.croma.image.KMeansColorPicker
 */
public interface ColorPicker {
    /**
     * Get Useful colors from an image
     * @param image file image
     * @param noOfColors approximate number of colors
     * @return List of colors
     * @throws IOException If error in reading Image
     */
	public List<Color> getUsefulColors(Image image, int  noOfColors) throws IOException;
}
