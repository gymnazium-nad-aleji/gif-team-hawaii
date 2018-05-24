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

import org.junit.Test;
import static org.junit.Assert.*;

public class FirstFitColorMapTest {
    @Test
    public void extractionFromImageWorks() {
        RasterImage image = (new RasterImageBuilder())
                .addRow(new int[] { 0, 0, 0, 0 })
                .addRow(new int[] { 1, 1, 1, 1 })
                .addRow(new int[] { 2, 3, 3, 2 })
                .setColorTable(new Color[] { Color.BLUE, Color.WHITE, Color.RED, Color.GREEN })
                .get();

        ColorMap map = FirstFitColorMap.create(image);

        assertEquals(4, map.getSize());

        assertEquals(Color.BLUE, map.getColor(0));
        assertEquals(Color.WHITE, map.getColor(1));
        assertEquals(Color.RED, map.getColor(2));
        assertEquals(Color.GREEN, map.getColor(3));

        assertEquals(0, map.getColorIndex(Color.BLUE));
        assertEquals(1, map.getColorIndex(Color.WHITE));
        assertEquals(2, map.getColorIndex(Color.RED));
        assertEquals(3, map.getColorIndex(Color.GREEN));
    }
}
