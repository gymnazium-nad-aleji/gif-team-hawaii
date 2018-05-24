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

import cz.alisma.alej.prog.image.RasterImage;
import cz.alisma.alej.prog.image.RasterImageReader;
import cz.alisma.alej.prog.image.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;


/** Shows byte-by-byte comparison of original and re-encoded GIF file. */
public class Comparator {
    private static final int BYTES_PER_LINE = 4;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.printf("Usage: input-image.gif\n");
            System.exit(1);
        }

        byte[] inputBytes = Files.readAllBytes(Paths.get(args[0]));
        InputStream inputStream = new ByteArrayInputStream(inputBytes);
        RasterImage input = RasterImageReader.read(inputStream);
        inputStream.close();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GifWriter.write(input, outputStream);
        outputStream.close();

        byte[] outputBytes = outputStream.toByteArray();

        printSideBySide(inputBytes, outputBytes, System.out);
    }

    private static void printSideBySide(byte[] a, byte[] b, PrintStream out) {
        int maxLength = Math.max(a.length, b.length);

        int index = 0;
        while (index < maxLength) {
            String aa = formatSubarray(a, index, BYTES_PER_LINE);
            String bb = formatSubarray(b, index, BYTES_PER_LINE);

            String marker = aa.equals(bb) ? "=" : "!";

            out.printf("%s  %s   %s\n", marker, aa, bb);

            index += BYTES_PER_LINE;
        }
    }

    private static String formatSubarray(byte[] array, int start, int len) {
        StringBuilder result = new StringBuilder();
        for (int i = start; i < start + len; i++) {
            if (i < array.length) {
                result.append(String.format("%02X ", array[i] & 0xFF));
            } else {
                result.append("   ");
            }
        }
        for (int i = start; i < start + len; i++) {
            result.append(" ");
            if (i < array.length) {
                result.append(Utils.asBinary(array[i] & 0xFF, 8));
            } else {
                result.append("        ");
            }
        }

        return result.toString();
    }
}
