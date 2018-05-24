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

public class RasterImageBuilderTest {
    @Test
    public void smallestImage() {
        RasterImage image = (new RasterImageBuilder()).addRow(new int[] { 0 })
                .setColorTable(new Color[] { Color.RED })
                .get();

        assertEquals(1, image.getWidth());
        assertEquals(1, image.getHeight());
        assertEquals(Color.RED, image.getColor(0, 0));
    }

    @Test
    public void smallImage() {
        RasterImage image = (new RasterImageBuilder())
                .addRow(new int[] { 0, 0, 0, 0 })
                .addRow(new int[] { 0, 1, 1, 0 })
                .addRow(new int[] { 0, 0, 0, 0 })
                .setColorTable(new Color[] { Color.BLUE, Color.WHITE })
                .get();

        assertEquals(4, image.getWidth());
        assertEquals(3, image.getHeight());

        assertEquals(Color.BLUE, image.getColor(0, 0));
        assertEquals(Color.BLUE, image.getColor(1, 0));
        assertEquals(Color.BLUE, image.getColor(2, 0));
        assertEquals(Color.BLUE, image.getColor(3, 0));

        assertEquals(Color.BLUE, image.getColor(0, 1));
        assertEquals(Color.WHITE, image.getColor(1, 1));
        assertEquals(Color.WHITE, image.getColor(2, 1));
        assertEquals(Color.BLUE, image.getColor(3, 1));

        assertEquals(Color.BLUE, image.getColor(0, 2));
        assertEquals(Color.BLUE, image.getColor(1, 2));
        assertEquals(Color.BLUE, image.getColor(2, 2));
        assertEquals(Color.BLUE, image.getColor(3, 2));
    }

    @Test(expected = IllegalStateException.class)
    public void imageWithoutRows() {
        RasterImageBuilder bld = new RasterImageBuilder();

        // This line shall throw
        bld.get();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void notRectangular() {
        RasterImageBuilder bld = new RasterImageBuilder();
        bld.addRow(new int[] { 0, 0, 1 });

        // This line shall throw
        bld.addRow(new int[] { 0, 1 });
    }
}
