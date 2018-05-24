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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

public class RasterImageReaderTest {
    private static final byte[] SMALLEST_GIF_BYTES = {
            0x47, 0x49, 0x46, 0x38, 0x39, 0x61, 0x01, 0x00,
            0x01, 0x00, (byte) 0xf0, 0x00, 0x00, 0x00, 0x00, (byte) 0xff,
            0x00, 0x00, 0x00, 0x21, (byte) 0xf9, 0x04, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x2c, 0x00, 0x00, 0x00, 0x00,
            0x01, 0x00, 0x01, 0x00, 0x00, 0x02, 0x02, 0x44,
            0x01, 0x00, 0x3b
    };

    @Test
    public void smoke() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(SMALLEST_GIF_BYTES);
        RasterImage image = RasterImageReader.read(input);

        assertEquals(1, image.getWidth());
        assertEquals(1, image.getHeight());
        assertEquals(Color.BLUE, image.getColor(0, 0));
    }
}
