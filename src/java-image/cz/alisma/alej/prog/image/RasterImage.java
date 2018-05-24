/*
 * MIT License
 * Copyright (c) 2018 Gymnazium Nad Aleji
 * Copyright (c) 2018 Vojtech Horky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package cz.alisma.alej.prog.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.PrintStream;

/**
 * Raster image representation.
 * 
 * This class wraps java.awt.image.BufferedImage that actually stores the
 * information.
 */
public class RasterImage {
    /** The underlying image. */
    private final BufferedImage image;

    /**
     * Create raster image from a BufferedImage.
     * 
     * @param im Indexed image (a copy is made).
     * @throws IllegalArgumentException When type of image is not indexed (with a palette).
     */
    public RasterImage(BufferedImage im) {
        int imType = im.getType();
        switch (imType) {
        case BufferedImage.TYPE_BYTE_BINARY:
        case BufferedImage.TYPE_BYTE_INDEXED:
            break;
        default:
            throw new IllegalArgumentException("Not an indexed image.");
        }

        image = copyBufferedImage(im);
    }

    /**
     * Get image width.
     * 
     * @return Image width in pixels.
     */
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * Get image height.
     * 
     * @return Image height in pixels.
     */
    public int getHeight() {
        return image.getHeight();
    }

    /**
     * Get color at given location.
     * 
     * @param x X coordinate (zero-based).
     * @param y Y coordinate (zero-based).
     * @return Color at given location.
     */
    public Color getColor(int x, int y) {
        Color res = new Color(image.getRGB(x, y));
        return res;
    }

    /**
     * Get copy of internal representation of the image.
     * 
     * @return Copy of the backing image.
     */
    public RenderedImage getAwtImage() {
        return copyBufferedImage(image);
    }

    /**
     * Dumps the image in text format.
     * 
     * @param out Where to print the image to.
     * @param title Image title.
     */
    public void textDump(PrintStream out, String title) {
        int width = getWidth();
        int height = getHeight();
        System.out.printf("RasterImage[%d x %d] %s\n", width, height, title);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.printf("%s%s", x > 0 ? " " : "", getColor(x, y));
            }
            System.out.println();
        }
    }

    /**
     * Makes a deep copy of given image.
     * 
     * @param original Image to be copied.
     * @return Deep copy of the original image.
     */
    private static BufferedImage copyBufferedImage(BufferedImage original) {
        ColorModel cm = original.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = original.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
