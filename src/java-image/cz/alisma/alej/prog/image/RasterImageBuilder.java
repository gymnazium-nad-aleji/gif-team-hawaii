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
import java.awt.image.IndexColorModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Helper class to create raster images on the fly. */
public class RasterImageBuilder {
    /** Rows of the image, pixel is color index. */
    private List<int[]> rows = new ArrayList<>();

    /** Color model for the image. */
    private IndexColorModel colorModel = null;

    /**
     * Creates the builder with empty image.
     * 
     * Image size is deduced from calls to addRow().
     */
    public RasterImageBuilder() {
    }

    /**
     * Add another row to the image.
     * 
     * @param colors Color indexes of this row.
     * @return This builder.
     * @throws IllegalArgumentException When colors is null or empty.
     * @throws IndexOutOfBoundsException When the row does not respect width of the image.
     */
    public RasterImageBuilder addRow(int[] colors) {
        if ((colors == null) || (colors.length == 0)) {
            throw new IllegalArgumentException("Row colors cannot be null or empty.");
        }
        if (!rows.isEmpty() && (colors.length != rows.get(0).length)) {
            throw new IndexOutOfBoundsException("Rows are not of the same length.");
        }

        rows.add(Arrays.copyOf(colors, colors.length));

        return this;
    }

    /**
     * Sets a color table, giving meaning to the color indexes.
     * 
     * @param table Color table.
     * @return This builder.
     * @throws IllegalArgumentException When table is null, empty or bigger than 256.
     */
    public RasterImageBuilder setColorTable(Color[] table) {
        if (table == null) {
            throw new IllegalArgumentException("Colors cannot be null.");
        }
        if (table.length >= 256) {
            throw new IllegalArgumentException("Too many colors specified.");
        }
        if (table.length == 0) {
            throw new IllegalArgumentException("At least one color has to be specified.");
        }

        byte[] reds = new byte[table.length];
        byte[] greens = new byte[table.length];
        byte[] blues = new byte[table.length];

        for (int i = 0; i < table.length; i++) {
            reds[i] = (byte) table[i].getRed();
            greens[i] = (byte) table[i].getGreen();
            blues[i] = (byte) table[i].getBlue();
        }

        colorModel = new IndexColorModel(4, table.length, reds, greens, blues);

        return this;
    }

    /**
     * Get the actual image built so far.
     * 
     * @return Built raster image.
     */
    public RasterImage get() {
        if (rows.isEmpty()) {
            throw new IllegalStateException("Cannot create image without any rows added.");
        }

        if (colorModel == null) {
            throw new IllegalStateException("Cannot create image without any color table.");
        }

        int width = rows.get(0).length;
        int height = rows.size();

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_INDEXED, colorModel);

        for (int y = 0; y < height; y++) {
            int[] row = rows.get(y);
            for (int x = 0; x < width; x++) {
                int col = colorModel.getRGB(row[x]);
                image.setRGB(x, y, col);
            }
        }

        return new RasterImage(image);
    }
}
