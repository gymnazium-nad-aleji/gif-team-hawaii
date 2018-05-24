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

import cz.alisma.alej.prog.image.Color;
import cz.alisma.alej.prog.image.ColorMap;

/** Local/global color table builder. */
public class ColorTableBuilder {
    private ColorMap map;
    private int size;

    /**
     * Creates the builder from given color map.
     *
     * @param map Color map.
     */
    public ColorTableBuilder(ColorMap map) {
        this.map = map;
        this.size = map.getSize();
    }

    /**
     * Sets actual map size (for rounding).
     *
     * @param size Actual color map size.
     * @return Self.
     */
    public ColorTableBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    /**
     * Gets the map as array of bytes.
     *
     * @return Color map in GIF local/global color table format.
     */
    public byte[] get() {
        byte[] result = new byte[size * 3];

        int bound = Math.min(map.getSize(), size);
        for (int i = 0; i < bound; i++) {
            Color color = map.getColor(i);
            result[i * 3 + 0] = (byte) color.getRed();
            result[i * 3 + 1] = (byte) color.getGreen();
            result[i * 3 + 2] = (byte) color.getBlue();
        }

        return result;
    }
}
