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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import cz.alisma.alej.prog.image.Color;
import cz.alisma.alej.prog.image.RasterImage;
import cz.alisma.alej.prog.image.RasterImageBuilder;
import cz.alisma.alej.prog.image.RasterImageReader;
import cz.alisma.alej.prog.image.gif.GifWriter;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class GifWriterTest {
    @Parameters(name = "{0}")
    public static Collection<Object[]> generateTestParameters() {
        return Arrays.asList(new Object[][] {
            {
                "one-pixel image",
                (new RasterImageBuilder())
                    .setColorTable(new Color[] { Color.RED, Color.BLACK })
                    .addRow(new int[] { 0 })
                    .get()
            },
            {
                "small image",
                (new RasterImageBuilder())
                    .addRow(new int[] { 0, 0, 0, 0 })
                    .addRow(new int[] { 0, 1, 1, 0 })
                    .addRow(new int[] { 0, 0, 0, 0 })
                    .setColorTable(new Color[] { Color.BLUE, Color.WHITE })
                    .get()
            },
        });
    }

    private final RasterImage originalImage;
    private RasterImage outputImage;
    private byte[] outputFileContents;

    public GifWriterTest(String testName, RasterImage image) throws IOException {
        this.originalImage = image;
    }

    @Before
    public void prepareConversion() throws IOException {
        Assume.assumeTrue("Implementation finished.", false);
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        GifWriter.write(originalImage, output);
        outputFileContents = output.toByteArray();

        try {
            outputImage = RasterImageReader.read(new ByteArrayInputStream(outputFileContents));
        } catch (Exception e) {
            outputImage = null;
            assertTrue(e.getMessage(), false);
        }
        
        assertNotNull("written valid image", outputImage);
        output = new ByteArrayOutputStream();
        ImageIO.write(originalImage.getAwtImage(), "GIF", output);
    }

    @Test
    public void checkDimensions() {
        assertEquals("image width", originalImage.getWidth(), outputImage.getWidth());
        assertEquals("image height", originalImage.getHeight(), outputImage.getHeight());
    }

    @Test
    public void checkPixels() {
        Assume.assumeTrue("width okay", originalImage.getWidth() == outputImage.getWidth());
        Assume.assumeTrue("height okay", originalImage.getHeight() == outputImage.getHeight());

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String msg = String.format("color at [%d, %d]", x, y);

                Color expectedColor = originalImage.getColor(x, y);
                Color actualColor = outputImage.getColor(x, y);

                assertEquals(msg, expectedColor, actualColor);
            }
        }
    }
}
