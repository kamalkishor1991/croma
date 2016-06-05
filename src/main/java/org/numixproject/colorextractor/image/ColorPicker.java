package org.numixproject.colorextractor.image;

import java.io.IOException;
import java.util.List;

/**
 * Get useful colors from an Image.
 * @see DBScanColorPicker
 * @see KMeansColorPicker
 */
public interface ColorPicker {
    /**
     * Get Useful colors from an image
     * @param image file image
     * @param noOfColors approximate number of colors
     * @return List of colors
     * @throws IOException If error in reading Image
     */
	public List<org.numixproject.colorextractor.image.Color> getUsefulColors(org.numixproject.colorextractor.image.Image image, int  noOfColors) throws IOException;
}
