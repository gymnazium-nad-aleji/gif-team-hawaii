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

/** Various utilities. */
public class Utils {
    /** Found nearest power of two greater than given value.
     *
     * @param value Value to be rounded to power of 2.
     * @return Smallest power of two greater than value.
     */
    public static int getNearestGreaterOrEqualPowerOfTwo(int value) {
        if (value <= 0) {
            return 0;
        }
        if (value == 1) {
            return 1;
        }
        int bound = 2;
        while (value > bound) {
            bound *= 2;
        }
        return bound;
    }

    /** Return index of highest set bit.
     * 
     * @param value Positive integer.
     * @return Highest bit index (starting at 0).
     */
    public static int getHighestSetBit(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Highest set bit works only for positive values.");
        }
        int bit = -1;
        while (value > 0) {
            bit++;
            value /= 2;
        }
        return bit;
    }

    /** Converts integer to binary representation.
     *
     * @param value Value to convert.
     * @param width Number of bits to display (zero padding).
     * @return Binary representation width wide.
     */
    public static String asBinary(int value, int width) {
        String fmt = String.format("%%%ds", width);
        return String.format(fmt, Integer.toBinaryString(value)).replace(' ', '0');
    }

    /**
     * Write two bytes in little-endian order.
     * 
     * @param value Value to write (unsigned word).
     * @param where Byte array where to put the value.
     * @param index Index into where.
     */
    public static void writeWordLE(int value, byte[] where, int index) {
        if ((value < 0) || (value > 0xFFFF)) {
            throw new IllegalArgumentException("Value does not fit into unsigned word");
        }
        
        where[index] = (byte) (value & 0xFF);
        where[index + 1] = (byte) ((value >> 8) & 0xFF);
    }
}
