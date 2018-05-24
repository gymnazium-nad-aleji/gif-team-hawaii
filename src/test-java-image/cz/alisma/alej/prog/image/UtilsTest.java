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

public class UtilsTest {
    @Test
    public void powerOfTwoNegative() {
        assertEquals(0, Utils.getNearestGreaterOrEqualPowerOfTwo(-5));
    }

    @Test
    public void powerOfTwoZero() {
        assertEquals(0, Utils.getNearestGreaterOrEqualPowerOfTwo(0));
    }

    @Test
    public void powerOfTwoOne() {
        assertEquals(1, Utils.getNearestGreaterOrEqualPowerOfTwo(1));
    }

    @Test
    public void powerOfTwoTwo() {
        assertEquals(2, Utils.getNearestGreaterOrEqualPowerOfTwo(2));
    }

    @Test
    public void powerOfTwoFive() {
        assertEquals(8, Utils.getNearestGreaterOrEqualPowerOfTwo(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void highestBitNegative() {
        Utils.getHighestSetBit(-5);
    }

    @Test
    public void highestBitOne() {
        assertEquals(0, Utils.getHighestSetBit(1));
    }

    @Test
    public void highestBitThree() {
        assertEquals(1, Utils.getHighestSetBit(3));
    }

    @Test
    public void highestBitTwentyOne() {
        assertEquals(4, Utils.getHighestSetBit(21));
    }
    
    @Test
    public void writeWordLEStart() {
        byte[] target = new byte[] { 1, 2, 3 };
        Utils.writeWordLE(10, target, 0);

        assertArrayEquals(new byte[] { 10, 0, 3 }, target);
    }

    @Test
    public void writeWordLEMiddle() {
        byte[] target = new byte[] { 1, 2, 3, 4 };
        Utils.writeWordLE(10, target, 1);

        assertArrayEquals(new byte[] { 1, 10, 0, 4 }, target);
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeWordLETooBigValue() {
        byte[] target = new byte[] { 1, 2, 3, 4 };
        Utils.writeWordLE(65536, target, 0);
    }
}
