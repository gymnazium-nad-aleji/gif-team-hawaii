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

/** Immutable RGB color representation. */
public class Color {
    /** Black color constant. */
    public static final Color BLACK = new Color(0, 0, 0);

    /** Blue color constant. */
    public static final Color BLUE = new Color(0, 0, 255);

    /** Green color constant. */
    public static final Color GREEN = new Color(0, 255, 0);

    /** Red color constant. */
    public static final Color RED = new Color(255, 0, 0);

    /** White color constant. */
    public static final Color WHITE = new Color(255, 255, 255);

    /** Red component of this color. */
    private final int red;

    /** Green component of this color. */
    private final int green;

    /** Blue component of this color. */
    private final int blue;

    /**
     * Create color from RGB representation.
     * 
     * @param rgb Color where each byte represents one component.
     */
    public Color(int rgb) {
        this((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
    }

    /**
     * Create color from RGB components.
     * 
     * @param r Red component (0-255).
     * @param g Green component (0-255).
     * @param b Blue component (0-255).
     */
    public Color(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
    }

    /**
     * Get RGB representation.
     * 
     * @return RGB representation formed by merged components.
     */
    public int getRGB() {
        return (((red << 8) | green) << 8) | blue;
    }

    /**
     * Get red component value.
     * 
     * @return Component value in range 0-255.
     */
    public int getRed() {
        return red;
    }

    /**
     * Get green component value.
     * 
     * @return Component value in range 0-255.
     */
    public int getGreen() {
        return green;
    }

    /**
     * Get blue component value.
     * 
     * @return Component value in range 0-255.
     */
    public int getBlue() {
        return blue;
    }

    @Override
    public int hashCode() {
        return ((red * 31) + blue) * 31 + green;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || !(obj instanceof Color)) {
            return false;
        }

        Color other = (Color) obj;
        return (red == other.red) && (green == other.green) && (blue == other.blue);
    }

    @Override
    public String toString() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}
