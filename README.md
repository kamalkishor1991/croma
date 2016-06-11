## Croma #

Lib for getting useful colors from an image.

###Release

Croma is now avialable at jcenter.
####Gradle
``` compile 'org.numixproject.colorextractor:colorextractor:1.4' ```
####Maven
```
<dependency>
  <groupId>org.numixproject.colorextractor</groupId>
  <artifactId>colorextractor</artifactId>
  <version>1.4</version>
  <type>pom</type>
</dependency>
```

####v1.0
[Download jar](https://github.com/kamalkishor1991/croma/blob/r1.0/croma.jar?raw=true)
####v1.1
[Download jar](https://github.com/kamalkishor1991/croma/raw/r1.1/croma1.1.jar?raw=true)
  1. Bug fixes
  2. New ColorPicker implementation based on Median Cut

####v1.2
[Download jar](https://github.com/kamalkishor1991/croma/raw/r1.2/croma1.2.jar?raw=true)
  1. New distance calculation based on LAB color space.
  
####v1.3
[Download jar](https://github.com/kamalkishor1991/croma/raw/r1.3/croma1.3.jar?raw=true)
  1. Final filtering based on HSL color space.  
  
####v1.4
[Download jar](https://jcenter.bintray.com/org/numixproject/colorextractor/colorextractor/1.4/:colorextractor-1.4.jar)
  1. Fixed a bug in LAB to RGB conversion
  2. Added new methods to convert RGB to LAB in Color class
  3. DBScanColorPicker is now deprecated 




###Usage
This library can be used for both android and desktop systems.
You just need to extend Image differently in both.

Here is one example of how It can be Implemented in android:

```java

public class BitmapImage extends Image {
    private Bitmap image;

    public BitmapImage(Bitmap b) {
        super(b.getWidth(), b.getHeight());
        this.image = b;
    }
    public BitmapImage(File f) {
        this(BitMapImage.create(f));
    }

    private static Bitmap create(File f) {
        Bitmap image;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        image = BitmapFactory.decodeFile(f.getAbsolutePath(), options);

        return image;
    }


    @Override
    public Color getColor(int x, int y) {
        return new Color(image.getPixel(x, y));
    }

    @Override
    public Image getScaledInstance(int width, int height) {
        Bitmap resized = Bitmap.createScaledBitmap(this.image, width , height, true);
        return new BitMapImage(resized);
    }
}

```
Desktop Implementation using awt package

[AWTImage.java](https://github.com/kamalkishor1991/croma/blob/master/src/test/java/me/croma/test/AWTImage.java)
