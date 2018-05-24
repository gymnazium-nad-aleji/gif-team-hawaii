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

import cz.alisma.alej.prog.image.Utils;

/** Logical screen descriptor builder. */
public class LogicalScreenDesriptorBuilder {
    private int width;
    private int height;
    private int globalTableConvertedSize = -1;

    /**
     * Creates the builder for given dimensions.
     *
     * @param width Image width in pixels.
     * @param height Image height in pixels.
     */
    public LogicalScreenDesriptorBuilder(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Turns on global color table.
     *
     * @param colorCount Image color count.
     * @return Self.
     */
    public LogicalScreenDesriptorBuilder useGlobalColorTable(int colorCount) {
        switch (colorCount) {
        case 2:
            globalTableConvertedSize = 0;
            break;
        case 4:
            globalTableConvertedSize = 1;
            break;
        case 8:
            globalTableConvertedSize = 2;
            break;
        case 16:
            globalTableConvertedSize = 3;
            break;
        case 32:
            globalTableConvertedSize = 4;
            break;
        case 64:
            globalTableConvertedSize = 5;
            break;
        case 128:
            globalTableConvertedSize = 6;
            break;
        case 256:
            globalTableConvertedSize = 7;
            break;
        default:
            throw new IllegalArgumentException(
                    String.format("Invalid color table size (%d)\n", colorCount));
        }

        return this;
    }

    /**
     * Get screen descriptor bytes.
     * 
     * @return Bytes of logical screen descriptor.
     */
    public byte[] get() {
        byte[] result = new byte[7];

        Utils.writeWordLE(width, result, 0);
        Utils.writeWordLE(height, result, 2);

        // Color resolution bit
        result[4] = 16;

        if (globalTableConvertedSize >= 0) {
            result[4] |= (byte) (128 | globalTableConvertedSize);
        }

        return result;
    }
}
