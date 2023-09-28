package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TextConverter implements TextGraphicsConverter {
    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    private TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage bwImg;
        double ratio = width / height;
        if (ratio > maxRatio) {
            throw new BadImageSizeException(ratio, maxRatio);
        }

        int newWidth = width;
        int newHeight = height;

        double widthRatio = (double) width / maxWidth;
        double heightRatio = (double) height / maxHeight;

        if (widthRatio > 1 || heightRatio > 1) {
            double maxRatio = Math.max(widthRatio, heightRatio);
            newWidth = (int) (width / maxRatio);
            newHeight = (int) (height / maxRatio);
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        width = bwRaster.getWidth();
        height = bwRaster.getHeight();
        ImageIO.write(bwImg, "png", new File("out1.png"));

        char[][] chars = new char[width * 2][height];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                chars[w * 2][h] = c;
                chars[w * 2 + 1][h] = c;
            }
        }
        StringBuilder result = new StringBuilder();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width * 2; w++) {
                result.append(chars[w][h]);
            }
            result.append("\n");
        }

        return result.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}