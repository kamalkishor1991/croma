package me.croma.image;

import java.io.IOException;
import java.util.List;

/**
 * Get useful colors from an Image.
 * @see me.croma.image.DBScanColorPicker
 * @see me.croma.image.KMeansColorPicker
 */
public interface ColorPicker {
    /**
     * Get prominent colors from an image
     * @param image file image
     * @param noOfColors approximate number of colors
     * @return List of colors
     * @throws IOException
     *
     */
	public List<Color> getUsefulColors(Image image, int  noOfColors) throws IOException;
}
