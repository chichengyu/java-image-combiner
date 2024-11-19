package com.image.combiner;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/** 文本
 * author xiaochi
 * date 2024/11/16
 */
public class Text {

    private int width;// 宽度,默认0(0表示竖排显示)
    private float space = 0;// 字间距,默认0
    private float lineHeight = 0;// 行高,默认0
    private boolean wrap = false;//是否换行,默认false
    private String color = "#fffff";// 颜色,默认白色
    private Font font = new Font("Arial", Font.PLAIN, 12);//字体,默认Font font = new Font("微软雅黑", Font.BOLD, 36);
    private float fontSize = 0;// 字体大小,默认0
    private float alpha = 1;// 透明度 0.0 - 1.0,默认1.0
    private float dim = 1;// 高斯模糊,值越大越模糊,默认1
    private float rotate = 0;// 旋转度数,默认0度
    private float rotateX = 0;// 旋转x坐标,默认0
    private float rotateY = 0;// 旋转Y坐标,默认0
    private float textX = 0;// 文字起始x坐标,默认0
    private float textY = 0;// 文字起始Y坐标,默认0
    private String text;// 文本内容
    private String separ = "";// 文本内容分割段落,默认空不分割
    private int indent = 0;// 首行缩进,默认0

    /**
     * 创建文本
     * @param text 文本
     */
    public static Text create(String text) {
        return new Text(text);
    }

    /**
     * 创建文本
     * @param text 文本
     */
    public Text(String text) {
        this.text = text;
    }

    /**
     * 获取文字宽度
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * 获取字间距
     * @return space
     */
    public float getSpace() {
        return space;
    }

    /**
     * 设置字间距
     * @param space
     * @return
     */
    public Text space(float space) {
        this.space = space;
        return this;
    }

    /**
     * 设置文字宽度
     * @param width 文字宽度
     */
    public Text width(int width) {
        this.width = width;
        return this;
    }

    /**
     * 获取行高
     * @return lineHeight
     */
    public float getLineHeight() {
        return lineHeight;
    }

    /**
     * 设置行高
     * @param lineHeight 行高
     * @return Text
     */
    public Text lineHeight(float lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    /**
     * 获取文字颜色
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置文字颜色
     * @param color 文字颜色
     * @return Text
     */
    public Text color(String color) {
        this.color = color;
        return this;
    }

    /**
     * 获取字体
     * @return font
     */
    public Font getFont() {
        return font;
    }

    /**
     * 设置字体
     * @param font 字体
     * 如 String fontPath = "D:\font\SmileySans2.ttf";
     *    Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
     */
    public Text font(Font font) {
        this.font = font;
        return this;
    }

    /**
     * 设置字体(外部字体)
     * @param file 字体file(如:)
     * String fontPath = "D:\font\SmileySans2.ttf";
     * Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
     */
    public Text font(File file) throws Exception {
        this.font = Font.createFont(Font.TRUETYPE_FONT, file);
        return this;
    }

    /**
     * 设置字体(外部字体)
     * @param in 字体file文件流(如:new FileInputStream(new File("D:\font\SmileySans2.ttf")))
     */
    public Text font(InputStream in) throws Exception {
        this.font = Font.createFont(Font.TRUETYPE_FONT,in);
        return this;
    }

    /**
     * 设置字体
     * @param fontName 字体名称(Text.showFonts();查看当前系统所有字体)
     */
    public Text font(String fontName) {
        this.font = new Font(fontName, Font.PLAIN, 12);
        return this;
    }

    /**
     * 获取字体大小
     * @return fontSize
     */
    public float getFontSize() {
        return fontSize;
    }

    /**
     * 设置字体大小
     * @param fontSize 字体大小
     */
    public Text fontSize(float fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    /**
     * 获取是否自动换行
     * @return boolean
     */
    public boolean isWrap() {
        return wrap;
    }

    /**
     * 设置自动换行(与切割符separ不能共存,切割符代表外部控制,不需要自动换行了)
     * @param wrap 是否自动换行,true换行,false不换行
     */
    public Text wrap(boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    /**
     * 获取选择度数
     * @return rotate
     */
    public float getRotate() {
        return rotate;
    }

    /**
     * 设置旋转度数(0-360)
     * @param rotate 度数
     */
    public Text rotate(float rotate) {
        this.rotate = rotate;
        return this;
    }

    /**
     * 获取旋转x坐标,默认0
     * @return rotateX
     */
    public float getRotateX() {
        return rotateX;
    }

    /**
     * 设置旋转x坐标
     * @param rotateX 旋转x坐标
     */
    public Text rotateX(float rotateX) {
        this.rotateX = rotateX;
        return this;
    }

    /**
     * 获取旋转Y坐标,默认0
     * @return rotateY
     */
    public float getRotateY() {
        return rotateY;
    }

    /**
     * 设置旋转Y坐标
     * @param rotateY 旋转Y坐标
     */
    public Text rotateY(float rotateY) {
        this.rotateY = rotateY;
        return this;
    }

    /**
     * 获取高斯模糊,值越大越模糊,默认1
     * @return dim
     */
    public float getDim() {
        return dim;
    }

    /**
     * 设置高斯模糊,值越大越模糊,默认1
     * @param dim 模糊度
     */
    public Text dim(float dim) {
        this.dim = dim;
        return this;
    }

    /**
     * 获取透明度 0.0 - 1.0,默认1.0
     * @return alpha
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * 设置透明度 0.0 - 1.0
     * @param alpha 透明度
     */
    public Text alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    /**
     * 获取文字起始x坐标,默认0
     * @return textX
     */
    public float getTextX() {
        return textX;
    }

    /**
     * 设置文字起始x坐标
     * @param textX 文字起始x坐标
     * @return Text
     */
    public Text textX(float textX) {
        this.textX = textX;
        return this;
    }

    /**
     * 获取文字起始y坐标
     * @return textY
     */
    public float getTextY() {
        return textY;
    }

    /**
     * 设置文字起始y坐标
     * @param textY 文字起始y坐标
     * @return Text
     */
    public Text textY(float textY) {
        this.textY = textY;
        return this;
    }

    /**
     * 获取文本
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * 获取分割符,默认空,不分割段落
     * @return separ
     */
    public String getSepar() {
        return separ;
    }

    /**
     * 设置分割符,分割段落
     * @param separ
     */
    public Text separ(String separ) {
        this.separ = separ;
        return this;
    }

    /**
     * 获取缩进
     * @return indent
     */
    public int getIndent() {
        return indent;
    }

    /**
     * 设置缩进
     * @param indent 缩进大小
     * @return Text
     */
    public Text indent(int indent) {
        this.indent = indent;
        return this;
    }

    /**
     * 切割文字
     * @param text 文本
     * @param maxWidth 最大宽度
     * @param fontMetrics fontMetrics对象
     * @param indent 缩进
     * @return string[]
     */
    public static String[] splitText(String text, int maxWidth, FontMetrics fontMetrics,int indent) {
        char[] words = text.toCharArray();
        List<String> lines = new ArrayList<>();
        StringBuilder wrappedText = new StringBuilder();
        int row = 1;
        for (char word : words){
            // 检查添加新单词后是否会超过最大宽度
            if (wrappedText.length() > 0) {
                // 检查加上新单词后的总长度
                if ((row > 1 ? fontMetrics.charWidth(word) : (fontMetrics.charWidth(word) + indent)) > maxWidth) {
                    // 如果超过最大宽度，将当前字符串添加到行列表，并开始新的一行
                    lines.add(wrappedText.toString());
                    wrappedText = new StringBuilder(String.valueOf(word));
                } else {
                    // 如果不超过最大宽度，添加新单词
                    wrappedText.append(word);
                }
            } else {
                wrappedText.append(word);
            }
            row++;
        }
        // 添加最后一行
        if (wrappedText.length() > 0) {
            lines.add(wrappedText.toString());
        }
        // 将行列表转换为数组
        return lines.toArray(new String[0]);
    }

    /**
     * 显示所有可用字体
     */
    public static void showFonts(){
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontName = e.getAvailableFontFamilyNames();
        for (int i = 0; i < fontName.length; i++) {
            System.out.println(fontName[i]);
        }
    }
}
