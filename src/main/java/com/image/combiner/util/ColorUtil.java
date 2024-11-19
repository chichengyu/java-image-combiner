package com.image.combiner.util;

import java.awt.Color;

/**
 * author xiaochi
 * date 2024/11/13
 */
public class ColorUtil {

    /**
     * 获取颜色
     * @param color #2395439
     * @return
     */
    public static Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
