## Croma #

Lib for getting useful colors from an image.

###Test
Run:

     java -jar croma.jar <Image dir. path> <noOfColors> <algo (0 or 1)> 
     
  It will generate a output.html file in given dir.

###Release

####v1.0
[Download jar](https://github.com/kamalkishor1991/croma/blob/r1.0/croma.jar?raw=true)

####v1.1
[Download jar](https://github.com/kamalkishor1991/croma/raw/r1.1/croma1.1.jar?raw=true)
  1. Bug fixes
  2. New ColorPicker implementation based on Median Cut
   
### [java docs](https://kamalkishor1991.github.com/croma/index.html)

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

[AWTImage.java](https://github.com/kamalkishor1991/croma/blob/master/src/me/croma/image/AWTImage.java)
