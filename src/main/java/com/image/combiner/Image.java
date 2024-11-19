package com.image.combiner;

import com.image.combiner.base.Base;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

/** 图像
 * author xiaochi
 * date 2024/11/15
 */
public class Image extends Base {

    private BufferedImage image;
    private int width;// 图片宽度,默认图片宽度
    private int height;// 图片高度,默认图片高度
    private float rotate = 0;// 旋转度数,默认0度
    private float rotateX = 0;// 旋转x坐标,默认0
    private float rotateY = 0;// 旋转Y坐标,默认0
    private float alpha = 1;// 透明度 0.0 - 1.0,默认1.0
    private float radius = 0;// 圆角半径,默认0
    private int dim = 1;// 高斯模糊,值越大越模糊,默认1
    private int border = 0;// 边框,默认0

    /**
     * 创建图片
     * @param path 图片路径 (如:网络图片:https://xxxxxxxx/xxxx/xxx.jpg)
     * @return Image
     */
    public static Image create(String path) throws Exception {
        return new Image(path);
    }

    /**
     * 创建图片
     * @param file 图片file(本地图片:new File("d:/xxxxxxxx/xxxx/xxx.jpg"))
     * @return Image
     */
    public static Image create(File file) throws Exception {
        return new Image(file);
    }

    /**
     * 创建图片
     * @param in 文件流 (如:file.getInputStream())
     * @return Image
     */
    public static Image create(InputStream in) throws Exception {
        return new Image(in);
    }

    /**
     * 创建图片
     * @param image (如:BufferedImage backImage = ImageIO.read(new File("d:/xxxxxxxx/xxxx/xxx.jpg")))
     * @return Image
     */
    public static Image create(BufferedImage image){
        return new Image(image);
    }

    /**
     * 创建图片
     * @param path 图片路径 (如:网络图片:https://xxxxxxxx/xxxx/xxx.jpg)
     */
    public Image(String path) throws Exception {
        this.image = ImageIO.read(new URL(path));
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    /**
     * 创建图片
     * @param file 图片file(本地图片:new File("d:/xxxxxxxx/xxxx/xxx.jpg"))
     */
    public Image(File file) throws Exception {
        this.image = ImageIO.read(file);
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    /**
     * 创建图片
     * @param in 文件流 (如:file.getInputStream())
     */
    public Image(InputStream in) throws Exception {
        this.image = ImageIO.read(in);
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    /**
     * 创建图片
     * @param image (如:BufferedImage backImage = ImageIO.read(new File("d:/xxxxxxxx/xxxx/xxx.jpg")))
     */
    public Image(BufferedImage image){
        this.image = image;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    /**
     * 裁剪
     * @param width 裁剪区域的宽
     * @param height 裁剪区域的高
     * @return Image
     */
    public Image crop(int width,int height){
        return crop(0,0,width,height);
    }

    /**
     * 裁剪
     * @param x 裁剪区域的起始x坐标
     * @param y 裁剪区域的起始y坐标
     * @param width 裁剪区域的宽
     * @param height 裁剪区域的高
     * @return Image
     */
    public Image crop(int x,int y,int width,int height){
        BufferedImage crop = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = g2d(crop);
        g2d.drawImage(image.getSubimage(x, y, width, height), 0, 0, null);
        g2d.dispose();
        image = crop;
        return this;
    }

    /**
     * 按比例缩放
     * @param ratio 缩放比例
     * */
    public Image scale(double ratio){
        int width = this.width = (int) (this.image.getWidth() * ratio);
        int height = this.height =(int) (this.image.getHeight() * ratio);
        this.image = reset(this.image, width, height);
        return this;
    }

    /**
     * 按比例缩放至(最大边=指定pix)
     * @param pixel 最大宽/高
     * */
    public Image thumbnail(int pixel){
        int width = this.image.getWidth();
        int height = this.image.getHeight();
        int l = Math.max(width, height);
        double ratio = (pixel * 1.0) / l;
        int w = this.width = (int) (width * ratio);
        int h = this.height = (int) (height * ratio);
        this.image = reset(this.image, w, h);
        return this;
    }

    /**
     * 获取宽度
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     * 设置宽度
     * @param width 宽度
     * @return Image
     */
    public Image width(int width) {
        this.width = width;
        return this;
    }

    /**
     * 获取高度
     * @return int
     */
    public int getHeight() {
        return height;
    }

    /**
     * 设置高度
     * @param height 高度
     * @return Image
     */
    public Image height(int height) {
        this.height = height;
        return this;
    }

    /**
     * 获取旋转度数
     * @return float
     */
    public float getRotate() {
        return rotate;
    }

    /**
     * 设置旋转度数(0~360)
     * @param rotate 度数
     * @return Image
     */
    public Image rotate(float rotate) {
        this.rotate = rotate;
        return this;
    }

    /**
     * 获取旋转起始x坐标
     * @return float
     */
    public float getRotateX() {
        return rotateX;
    }

    /**
     * 设置旋转起始x坐标
     * @param rotateX x坐标
     * @return Image
     */
    public Image rotateX(float rotateX) {
        this.rotateX = rotateX;
        return this;
    }

    /**
     * 获取旋转起始Y坐标
     * @return float
     */
    public float getRotateY() {
        return rotateY;
    }

    /**
     * 设置旋转起始Y坐标
     * @param rotateY y坐标
     * @return Image
     */
    public Image rotateY(float rotateY) {
        this.rotateY = rotateY;
        return this;
    }

    /**
     * 获取透明度
     * @return float
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * 设置透明度(0.0~1.0)
     * @param alpha 透明度(0.0~1.0)
     * @return Image
     */
    public Image alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    /**
     * 获取圆角
     * @return float
     */
    public float getRadius() {
        return radius;
    }

    /**
     * 设置圆角
     * @param radius 圆角半径
     * @return Image
     */
    public Image radius(float radius) {
        this.radius = radius;
        this.image = Base.radius(this.image,radius);
        return this;
    }

    /**
     * 获取边框
     * @return int
     */
    public int getBorder() {
        return border;
    }

    /**
     * 设置边框
     * @param border 边框宽度
     * @return Image
     */
    public Image border(int border) {
        this.border = border;
        this.image = Base.border(this.image,border,(int) this.radius,1,"","");
        return this;
    }

    /**
     * 设置边框
     * @param border 边框宽度
     * @param padding 内边距
     * @return Image
     */
    public Image border(int border,int padding) {
        this.border = border;
        this.image = Base.border(this.image,border,(int) this.radius,padding,"","");
        return this;
    }

    /**
     * 设置边框
     * @param border 边框宽度
     * @param padding 内边距
     * @param color 边框颜色
     * @return Image
     */
    public Image border(int border,int padding,String color) {
        this.border = border;
        this.image = Base.border(this.image,border,(int) this.radius,padding,color,"");
        return this;
    }

    /**
     * 设置边框
     * @param border 边框宽度
     * @param padding 内边距
     * @param color 边框颜色
     * @param paddingColor 内边距颜色
     * @return Image
     */
    public Image border(int border,int padding,String color,String paddingColor) {
        this.border = border;
        this.image = Base.border(this.image,border,(int) this.radius,padding,color,paddingColor);
        return this;
    }

    /**
     * 获取模糊度数
     * @return int
     */
    public int getDim() {
        return dim;
    }

    /**
     * 设置模糊度,值越大越模糊
     * @param blur 模糊度,值越大越模糊
     * @return Image
     */
    public Image dim(int blur) {
        if (blur > 0){
            this.dim = blur;
        }
        float[] blurKernel = new float[this.dim * this.dim];
        Arrays.fill(blurKernel, 1.f /(this.dim * this.dim));
        Kernel kernel = new Kernel(this.dim, this.dim, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        this.image = op.filter(this.image, null);
        return this;
    }

    /**
     * 卷积运算(可以做毛玻璃\高斯模糊...等等自行扩展)
     * @param convolveOp
     * @return
     * 如: float[] matrix = {
     *      0.1f, 0.1f, 0.1f, 0.1f, 0.1f,
     *      0.1f, 0.2f, 0.1f, 0.1f, 0.1f,
     *      0.1f, 0.1f, 0.1f, 0.1f, 0.1f,
     *      0.1f, 0.1f, 0.1f, 0.1f, 0.1f,
     *      0.1f, 0.1f, 0.1f, 0.1f, 0.1f
     *  };
     *  Kernel kernel = new Kernel(5, 5, matrix);
     *  ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
     */
    public Image convolveOp(ConvolveOp convolveOp) {
        this.image = convolveOp.filter(this.image, null);
        return this;
    }

    /**
     * 卷积运算(可以做毛玻璃\高斯模糊...等等自行扩展)
     * @param convolveOp
     * @return
     * 如: float[] matrix = {
     *      0.1f, 0.1f, 0.1f, 0.1f, 0.1f,
     *      0.1f, 0.2f, 0.1f, 0.1f, 0.1f,
     *      0.1f, 0.1f, 0.1f, 0.1f, 0.1f,
     *      0.1f, 0.1f, 0.1f, 0.1f, 0.1f,
     *      0.1f, 0.1f, 0.1f, 0.1f, 0.1f
     *  };
     *  Kernel kernel = new Kernel(5, 5, matrix);
     *  BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
     */
    public Image convolveOp(BufferedImageOp convolveOp) {
        this.image = convolveOp.filter(this.image, null);
        return this;
    }

    /**
     * 离散效果类似毛玻璃(粒子效果)
     * 通过把图像中的点离散化，即将图像上的像素点随机偏移一定的距离来模仿类似毛玻璃折射后的效果
     * @param radius 离散最大半径
     * @return Image
     */
    public Image spread(int radius) {
        int x, y, quantum, x_distance, y_distance;
        BufferedImage newImage = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Random rand = new Random(System.currentTimeMillis());
        quantum = radius;
        for (y = 0; y < this.image.getHeight(); y++) {
            for (x = 0; x < this.image.getWidth(); x++) {
                do {
                    x_distance = (int) (((2 * (double) radius + 1) * rand.nextDouble()) - quantum);
                    y_distance = (int) (((2 * (double) radius + 1) * rand.nextDouble()) - quantum);
                } while ((x + x_distance < 0 || y + y_distance < 0) || x + x_distance > this.image.getWidth()
                        || y + y_distance > this.image.getHeight());
                try {
                    newImage.setRGB(x, y, this.image.getRGB(x + x_distance, y + y_distance));
                } catch (Exception e) {

                }
            }
        }
        this.image = newImage;
        return this;
    }

    /**
     * 获取图片
     * @return BufferedImage
     */
    public BufferedImage getImage() {
        return image;
    }
}
