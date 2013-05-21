import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

public class IMGRead {

    private static final int IMG_WIDTH = 255;
    private static final int IMG_HEIGHT = 255;

    public Map<String, Object[]> read(String fileName) {
        File file = new File(fileName);
        BufferedImage source, image;//source and resized images
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        try {
            source = ImageIO.read(file);//read picture from file
            int type = source.getType() == 0? BufferedImage.TYPE_INT_ARGB : source.getType();//get type
            image = resizeImage(source, type);//resize
            source = convert8(image);
            image = source; // :)

            // Getting pixel color for every pixel
            for (Integer y = 0; y < image.getHeight(); y++) {
                Object[] line = new Object[image.getWidth()];
                for (int x = 0; x < image.getWidth(); x++) {
                    int clr = image.getRGB(x, y);
                    int red = (clr & 0x00ff0000) >> 16;
                    int green = (clr & 0x0000ff00) >> 8;
                    int blue = clr & 0x000000ff;
                    line[x] = new RGBColor(red, green, blue);

                }
                data.put(String.format("%03d", y), line);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return data;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
    
    /**
     * Converts the source image to 8-bit colour using the default 256-colour
     * palette. No transparency.
     * 
     * @param src
     *            the source image to convert
     * @return a copy of the source image with an 8-bit colour depth
     * 
     * @see http://www.java2s.com/Code/Java/2D-Graphics-GUI/Providesusefulmethodsforconvertingimagesfromonecolourdepthtoanother.htm
     */
    public static BufferedImage convert8(BufferedImage src) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
            BufferedImage.TYPE_BYTE_INDEXED);
        ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
            .getColorSpace(), dest.getColorModel().getColorSpace(), null);
        cco.filter(src, dest);
        return dest;
      }
}
