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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Mapping between index and a color, earlier added colors has lower indices. */
public class FirstFitColorMap implements ColorMap {
    private Color[] indexToColor;
    private Map<Color, Integer> colorToIndex;

    /** Build the color map from existing image.
     *
     * @param image Image for which to compute the color map.
     * @return Color map suitable for the given image.
     */
    public static ColorMap create(RasterImage image) {
        Set<Color> usedColors = new HashSet<>();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                usedColors.add(image.getColor(x, y));
            }
        }
        return new FirstFitColorMap(usedColors.toArray(new Color[0]));
    }

    /** Build the color map from list of colors.
     *
     * @param colors List of colors to build the map.
     */
    public FirstFitColorMap(Color[] colors) {
        indexToColor = Arrays.copyOf(colors, colors.length);
        colorToIndex = new HashMap<>();
        for (int i = 0; i < indexToColor.length; i++) {
            colorToIndex.put(indexToColor[i], i);
        }
    }

    @Override
    public int getSize() {
        return indexToColor.length;
    }

    @Override
    public int getColorIndex(Color color) {
        Integer result = colorToIndex.get(color);
        if (result == null) {
            return -1;
        }
        return result;
    }

    @Override
    public Color getColor(int index) {
        return indexToColor[index];
    }
}
