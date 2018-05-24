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

/** Image descriptor builder. */
public class ImageDesriptorBuilder {
    private int width;
    private int height;

    /**
     * Creates the builder for given dimensions.
     *
     * @param width Image width in pixels.
     * @param height Image height in pixels.
     */
    public ImageDesriptorBuilder(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get image descriptor bytes.
     * 
     * @return Bytes of image descriptor.
     */
    public byte[] get() {
        byte[] result = new byte[10];

        result[0] = 0x2C;

        Utils.writeWordLE(width, result, 5);
        Utils.writeWordLE(height, result, 7);

        return result;
    }
}
