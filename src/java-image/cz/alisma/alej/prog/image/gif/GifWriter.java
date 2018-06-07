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
package cz.alisma.alej.prog.image.gif;

import cz.alisma.alej.prog.image.ColorMap;
import cz.alisma.alej.prog.image.FirstFitColorMap;
import cz.alisma.alej.prog.image.RasterImage;
import cz.alisma.alej.prog.image.Utils;

import java.io.IOException;
import java.io.OutputStream;

/** GIF encoder. */
public class GifWriter {
    /** GIF89a file signature bytes. */
    private static final byte[] GIF_SIGNATURE = { 0x47, 0x49, 0x46, 0x38, 0x39, 0x61 };

    /** GIF terminator bytes. */
    private static final byte[] GIF_TERMINATOR = { 0x3B };

    /**
     * Write raster image in GIF format.
     * 
     * @param image Image to be encoded.
     * @param output Initialized stream where to write the GIF image.
     * @throws IOException On I/O error of the stream.
     */
    public static void write(RasterImage image, OutputStream output) throws IOException {
        output.write(GIF_SIGNATURE);

        ColorMap colorMap = FirstFitColorMap.create(image);
        int colorCount = colorMap.getSize();
        int colorCountRounded = Utils.getNearestGreaterOrEqualPowerOfTwo(colorCount);
        if (colorCountRounded == 1) {
            colorCountRounded = 2;
        }
        
        LogicalScreenDesriptorBuilder logicalScreenDescriptor
                = new LogicalScreenDesriptorBuilder(image.getWidth(), image.getHeight());
        logicalScreenDescriptor.useGlobalColorTable(colorCountRounded);
        output.write(logicalScreenDescriptor.get());

        ColorTableBuilder globalColorTable = new ColorTableBuilder(colorMap);
        globalColorTable.setSize(colorCountRounded);
        output.write(globalColorTable.get());

        GraphicsControlExtensionBuilder graphicsControlExtension
                = new GraphicsControlExtensionBuilder();
        output.write(graphicsControlExtension.get());

        ImageDesriptorBuilder imageDescriptor
                = new ImageDesriptorBuilder(image.getWidth(), image.getHeight());
        output.write(imageDescriptor.get());
        
        // Begin our code       
        int[] colorIndexStream = createColorIndexStream(image, colorMap);
        BlockCreator blockCreator = new BlockCreator();
        BitPacker bitPacker = new BitPacker(blockCreator);
        
        LzwCompressor.compress(colorIndexStream, bitPacker);
        
        byte[] compressedIndexStream = blockCreator.get();
        // End our code
        
        output.write(compressedIndexStream);

        output.write(GIF_TERMINATOR);
    }

	private static int[] createColorIndexStream(RasterImage image, ColorMap colorMap) {
		int height = image.getHeight();
		int width = image.getWidth();
		int[] indexStream = new int[height * width];
		int colorCounter = 0;
		for (int y = 0; y < width; y += 1) {
			for (int x = 0; x < height; x += 1) {
				indexStream[colorCounter] = colorMap.getColorIndex(image.getColor(x, y));
				colorCounter += 1;
			}
		}
		return indexStream;
	}
}
