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

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class LzwCompressionTest {
    @Parameters(name="{0}")
    public static Collection<Object[]> generateTestParameters() {
        return Arrays.asList(new Object[][] {
            {
                "www.matthewflickinger.com/lab/whatsinagif/lzw_image_data.asp",
                Arrays.asList(
                    1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                    1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                    1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                    1, 1, 1, 0, 0, 0, 0, 2, 2, 2,
                    1, 1, 1, 0, 0, 0, 0, 2, 2, 2,
                    2, 2, 2, 0, 0, 0, 0, 1, 1, 1,
                    2, 2, 2, 0, 0, 0, 0, 1, 1, 1,
                    2, 2, 2, 2, 2, 1, 1, 1, 1, 1,
                    2, 2, 2, 2, 2, 1, 1, 1, 1, 1,
                    2, 2, 2, 2, 2, 1, 1, 1, 1, 1
                ),
                4,
                new String[] {
                    "100", "001", "110", "110",
                    "0010", "1001", "1001", "0111", "1000", "1010",
                    "0010", "1100",
                    "00001", "01110", "01111", "00110", "00000",
                    "10101", "00000", "01010", "00111", "10110",
                    "10111", "10010", "11010", "00111", "01010",
                    "11101",
                    "001101", "011000", "001100", "010010", "010000",
                    "100100", "001100", "000101"
                }
            },
            {
                "stackoverflow.com/q/27789351",
                Arrays.asList(2, 1, 2, 1, 2, 0, 0, 2, 1, 2),
                4,
                new String[] {
                    "100", "010", "001", "110",
                    "0010", "0000", "0000", "1000", "0101"
                }
            },
        });
    }

    private final Collection<Integer> input;
    private final int firstUnusedCode;
    private final String[] expectedOutput;
    private String[] actualOutput;

    public LzwCompressionTest(String name, Collection<Integer> input, int firstUnusedCode, String[] output) {
        this.input = input;
        this.firstUnusedCode = firstUnusedCode;
        this.expectedOutput = output;
    }

    @Before
    public void doTheCompression() {
        // replace with call that actually compresses this.input
        // do not forget to uncomment assertion in outputOkay 
        actualOutput = null;
    }

    @Test
    public void outputOkay() {
        // assertArrayEquals(expectedOutput, actualOutput);
    }
}
