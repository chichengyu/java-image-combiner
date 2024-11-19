package com.image.combiner;

import com.image.combiner.base.Base;
import com.image.combiner.util.ColorUtil;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/** Combiner 组合器
 * author xiaochi
 * date 2024/11/13
 */
public class Combiner extends Base {
    /**
     * 整个画布
     */
    private BufferedImage combiner;

    /**
     * 保存的格式,默认png
     */
    private String suffix = "png";

    /**
     * 压缩质量0.0-1
     */
    private float compress = 1f;

    /**
     * 设置文件后缀
     * @param suffix
     */
    public Combiner suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * 获取整个画布
     * @return
     */
    public BufferedImage getCombiner() {
        return combiner;
    }

    /**
     * 创建组合器
     * @param width
     * @param height
     * @return
     */
    public static Combiner create(int width,int height){
        return new Combiner(width,height);
    }

    /**
     * 创建组合器
     * @param width
     * @param height
     * @return
     */
    public static Combiner create(int width,int height,int TYPE_INT_RGB){
        return new Combiner(width,height,TYPE_INT_RGB);
    }

    /**
     * 创建空白组合器
     * @param width 画布宽度
     * @param height 画布高度
     */
    public Combiner(int width, int height) {
        combiner = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * 创建空白组合器
     * @param width 画布宽度
     * @param height 画布高度
     * @param TYPE_INT_RGB BufferedImage.TYPE_INT_ARGB 注：TYPE_INT_ARGB 绘制后图片会变红 RGB不会
     */
    public Combiner(int width, int height,int TYPE_INT_RGB) {
        combiner = new BufferedImage(width,height, TYPE_INT_RGB);
    }

    /**
     * 创建背景图片组合器(默认使用背景图宽高为画布宽高)
     * @param backImagePath 背景图片路径(网络图片:https://xxxxxxxx/xxxx/xxx.jpg)
     */
    public static Combiner create(String backImagePath) throws Exception {
        return new Combiner(backImagePath);
    }

    /**
     * 创建背景图片组合器
     * @param backImagePath 背景图片路径(网络图片:https://xxxxxxxx/xxxx/xxx.jpg)
     * @param width 画布宽度
     * @param height 画布高度
     */
    public static Combiner create(String backImagePath,int width, int height) throws Exception {
        return new Combiner(backImagePath,width,height);
    }

    /**
     * 创建背景图片组合器(默认使用背景图宽高为画布宽高)
     * @param backImagePath 背景图片路径(网络图片:https://xxxxxxxx/xxxx/xxx.jpg)
     */
    public Combiner(String backImagePath) throws Exception {
        combiner = ImageIO.read(new URL(backImagePath));
    }

    /**
     * 创建背景图片组合器
     * @param backImagePath 背景图片路径(网络图片:https://xxxxxxxx/xxxx/xxx.jpg)
     * @param width 画布宽度
     * @param height 画布高度
     */
    public Combiner(String backImagePath,int width, int height) throws Exception {
        BufferedImage image = ImageIO.read(new URL(backImagePath));
        combiner = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = g2d(combiner);
        g2d.drawImage(image,0,0,width,height,null);
        g2d.dispose();
    }

    /**
     * 创建背景图片组合器
     * @param backFile 背景图片file(本地图片:new File("d:/xxxxxxxx/xxxx/xxx.jpg"))
     * @return
     */
    public static Combiner create(File backFile) throws Exception {
        return new Combiner(backFile);
    }

    /**
     * 创建背景图片组合器
     * @param backFile 背景图片file(本地图片:new File("d:/xxxxxxxx/xxxx/xxx.jpg"))
     * @param width 画布宽度
     * @param height 画布高度
     * @return
     */
    public static Combiner create(File backFile,int width, int height) throws Exception {
        return new Combiner(backFile,width,height);
    }

    /**
     * 创建背景图片组合器(默认使用背景图宽高为画布宽高)
     * @param backFile 背景图片file(本地图片:new File("d:/xxxxxxxx/xxxx/xxx.jpg"))
     * @return
     */
    public Combiner(File backFile) throws Exception {
        combiner = ImageIO.read(backFile);
    }

    /**
     * 创建背景图片组合器
     * @param backFile 背景图片file(本地图片:new File("d:/xxxxxxxx/xxxx/xxx.jpg"))
     * @param width 画布宽度
     * @param height 画布高度
     * @return
     */
    public Combiner(File backFile,int width, int height) throws Exception {
        BufferedImage image = ImageIO.read(backFile);
        combiner = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = g2d(combiner);
        g2d.drawImage(image,0,0,width,height,null);
        g2d.dispose();
    }

    /**
     * 创建背景图片组合器
     * @param backImage (如:ImageIO.read(new File("d:/xxxxxxxx/xxxx/xxx.jpg")))
     * @return
     */
    public static Combiner create(BufferedImage backImage){
        return new Combiner(backImage);
    }

    /**
     * 创建背景图片组合器
     * @param backImage (如:ImageIO.read(new File("d:/xxxxxxxx/xxxx/xxx.jpg")))
     * @param width 画布宽度
     * @param height 画布高度
     * @return
     */
    public static Combiner create(BufferedImage backImage,int width, int height){
        return new Combiner(backImage,width,height);
    }

    /**
     * 创建背景图片组合器
     * @param backImage (如:ImageIO.read(new File("d:/xxxxxxxx/xxxx/xxx.jpg")))
     * @return
     */
    public Combiner (BufferedImage backImage){
        combiner = backImage;
    }

    /**
     * 创建背景图片组合器
     * @param backImage (如:ImageIO.read(new File("d:/xxxxxxxx/xxxx/xxx.jpg")))
     * @param width 画布宽度
     * @param height 画布高度
     * @return
     */
    public Combiner(BufferedImage backImage,int width, int height){
        combiner = backImage;
        Graphics2D g2d = g2d(combiner);
        g2d.drawImage(backImage,0,0,width,height,null);
        g2d.dispose();
    }

    /**
     * 创建背景图片组合器
     * @param backIn 背景图片文件流(如:file.getInputStream())
     * @return
     */
    public static Combiner create(InputStream backIn) throws Exception {
        return new Combiner(backIn);
    }

    /**
     * 创建背景图片组合器
     * @param backIn 背景图片文件流(如:file.getInputStream())
     * @param width 画布宽度
     * @param height 画布高度
     * @return
     */
    public static Combiner create(InputStream backIn,int width, int height) throws Exception {
        return new Combiner(backIn,width,height);
    }

    /**
     * 创建背景图片组合器
     * @param backIn 背景图片文件流(如:file.getInputStream())
     * @return
     */
    public Combiner(InputStream backIn) throws Exception {
        combiner = ImageIO.read(backIn);
    }

    /**
     * 创建背景图片组合器
     * @param backIn 文件流 (如:file.getInputStream())
     * @param width 画布宽度
     * @param height 画布高度
     * @return
     */
    public Combiner(InputStream backIn,int width, int height) throws Exception {
        BufferedImage backImage = ImageIO.read(backIn);
        combiner = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = g2d(combiner);
        g2d.drawImage(backImage,0,0,width,height,null);
        g2d.dispose();
    }

    /**
     * 设置压缩质量(0.0-1.0)
     * @param compress 压缩质量(0.0-1.0),PNG无效
     * @return Combiner
     */
    public Combiner compress(float compress) {
        this.compress = compress;
        return this;
    }

    /**
     * 设置背景颜色
     * @param bgColor 如: #1bdf1a
     * @return
     */
    public Combiner backColor(String bgColor){
        Graphics2D g2d = g2d(combiner);
        g2d.setBackground(ColorUtil.getColor(bgColor));
        //通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g2d.clearRect(0, 0, combiner.getWidth(),combiner.getHeight());
        g2d.dispose();
        return this;
    }

    /**
     * 旋转画布
     * @param degree 旋转度数
     * @return
     */
    public Combiner rotate(int degree){
        return rotate(degree,0,0,combiner.getWidth(),combiner.getHeight(),0,0,"");
    }

    /**
     * 旋转画布
     * @param degree 旋转度数
     * @param translocateX 旋转后相对画布所在位置的x坐标
     * @param translocateY 旋转后相对画布所在位置的y坐标
     * @return
     */
    public Combiner rotate(int degree,int translocateX,int translocateY){
        return rotate(degree,translocateX,translocateY,combiner.getWidth(),combiner.getHeight(),0,0,"");
    }

    /**
     * 旋转画布
     * @param degree 旋转度数
     * @param translocateX 旋转后相对画布所在位置的x坐标
     * @param translocateY 旋转后相对画布所在位置的y坐标
     * @param width 旋转后的宽度
     * @param height 旋转后的高度
     * @return
     */
    public Combiner rotate(int degree,int translocateX,int translocateY,int width,int height){
        return rotate(degree,translocateX,translocateY,width,height,0,0,"");
    }

    /**
     * 旋转画布
     * @param degree 旋转度数
     * @param translocateX 旋转后相对画布所在位置的x坐标
     * @param translocateY 旋转后相对画布所在位置的y坐标
     * @param width 旋转后的宽度
     * @param height 旋转后的高度
     * @param backColor 旋转后的背景色
     * @return
     */
    public Combiner rotate(int degree,int translocateX,int translocateY,int width,int height,String backColor){
        return rotate(degree,translocateX,translocateY,width,height,0,0,backColor);
    }

    /**
     * 旋转画布
     * @param degree 旋转度数
     * @param translocateX 旋转后相对画布所在位置的x坐标
     * @param translocateY 旋转后相对画布所在位置的y坐标
     * @param width 旋转后的宽度
     * @param height 旋转后的高度
     * @param x 旋转起始x坐标
     * @param y 旋转起始y坐标
     * @param backColor 旋转后的背景色
     * @return
     */
    public Combiner rotate(int degree,int translocateX,int translocateY,int width,int height,int x,int y,String backColor){
        BufferedImage image = new BufferedImage(combiner.getWidth(), combiner.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = g2d(image);
        if (!"".equals(backColor)){
            g2d.setBackground(ColorUtil.getColor(backColor));
            g2d.clearRect(0,0,image.getWidth(),image.getHeight());
        }
        g2d.rotate(Math.toRadians(degree),x,y);
        g2d.drawImage(combiner,translocateX,translocateY,width,height,null);
        g2d.dispose();
        combiner = image;
        return this;
    }

    /**
     * 裁剪画布
     * @param width 裁剪区域的宽
     * @param height 裁剪区域的高
     * @return
     */
    public Combiner crop(int width,int height){
        return crop(0,0,width,height);
    }

    /**
     * 裁剪画布
     * @param x 裁剪区域的起始x坐标
     * @param y 裁剪区域的起始y坐标
     * @param width 裁剪区域的宽
     * @param height 裁剪区域的高
     * @return
     */
    public Combiner crop(int x,int y,int width,int height){
        // 裁剪
        BufferedImage crop = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = g2d(crop);
        g2d.drawImage(combiner.getSubimage(x, y, width, height), 0, 0, null);
        g2d.dispose();
        combiner = crop;
        return this;
    }

    /**
     * 透明度(0.0~1.0)
     * @param alpha 透明度 (0.0~1.0)
     * @return
     */
    public Combiner alpha(float alpha) {
        BufferedImage image = new BufferedImage(combiner.getWidth(), combiner.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = g2d(image);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g2d.drawImage(combiner,0,0,image.getWidth(),image.getHeight(),null);
        g2d.dispose();
        combiner = image;
        return this;
    }

    /**
     * 透明度
     * @param alphaComposite 透明度 如:AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.5f)
     * @return
     */
    public Combiner alpha(AlphaComposite alphaComposite) {
        BufferedImage image = new BufferedImage(combiner.getWidth(), combiner.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = g2d(image);
        g2d.setComposite(alphaComposite);
        g2d.drawImage(combiner,0,0,image.getWidth(),image.getHeight(),null);
        g2d.dispose();
        combiner = image;
        return this;
    }

    /**
     * 高斯模糊
     * @param blur 模糊度(值越大越模糊)
     * @return
     */
    public Combiner dim(int blur){
        float[] blurKernel = new float[blur * blur];
        Arrays.fill(blurKernel, 1.f /(blur * blur));
        Kernel kernel = new Kernel(blur, blur, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        combiner = op.filter(combiner, null);
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
    public Combiner convolveOp(ConvolveOp convolveOp){
        combiner = convolveOp.filter(combiner, null);
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
    public Combiner convolveOp(BufferedImageOp convolveOp) {
        combiner = convolveOp.filter(combiner, null);
        return this;
    }

    /**
     * 离散效果类似毛玻璃(粒子效果)
     * 通过把图像中的点离散化，即将图像上的像素点随机偏移一定的距离来模仿类似毛玻璃折射后的效果
     * @param radius 离散最大半径
     * @return
     */
    public Combiner spread(int radius) {
        int x, y, quantum, x_distance, y_distance;
        BufferedImage newCombiner = new BufferedImage(this.combiner.getWidth(), this.combiner.getHeight(), BufferedImage.TYPE_INT_RGB);
        Random rand = new Random(System.currentTimeMillis());
        quantum = radius;
        for (y = 0; y < this.combiner.getHeight(); y++) {
            for (x = 0; x < this.combiner.getWidth(); x++) {
                do {
                    x_distance = (int) (((2 * (double) radius + 1) * rand.nextDouble()) - quantum);
                    y_distance = (int) (((2 * (double) radius + 1) * rand.nextDouble()) - quantum);
                } while ((x + x_distance < 0 || y + y_distance < 0) || x + x_distance > this.combiner.getWidth()
                        || y + y_distance > this.combiner.getHeight());
                try {
                    newCombiner.setRGB(x, y, this.combiner.getRGB(x + x_distance, y + y_distance));
                } catch (Exception e) {

                }
            }
        }
        this.combiner = newCombiner;
        return this;
    }

    /**
     * 按比例缩放
     * @param ratio 缩放比例
     * */
    public Combiner scale(double ratio){
        int width = (int) (this.combiner.getWidth() * ratio);
        int height = (int) (this.combiner.getHeight() * ratio);
        this.combiner = reset(this.combiner, width, height);
        return this;
    }

    /**
     * 按比例缩放至(最大边=指定pix)
     * @param pixel 最大宽/高
     * */
    public Combiner thumbnail(int pixel){
        int width = this.combiner.getWidth();
        int height = this.combiner.getHeight();
        int l = Math.max(width, height);
        double ratio = (pixel * 1.0) / l;
        int w = (int) (width * ratio);
        int h = (int) (height * ratio);
        this.combiner = reset(this.combiner, w, h);
        return this;
    }

    /**
     * 保存
     * @param savePath 保存路径(默认直接路径结尾格式)
     * @return
     */
    public Combiner save(String savePath) throws Exception {
        return save(savePath,true);
    }

    /**
     * 保存
     * @param savePath 保存路径(默认直接路径结尾格式)
     * @param isSuffix 是否覆盖suffix设置的后缀(默认不覆盖)
     * @return
     */
    public Combiner save(String savePath,boolean isSuffix) throws Exception {
        if (!isSuffix){
            String fileNotSuffix = savePath.substring(0,savePath.lastIndexOf("."));
            if (!"".equals(fileNotSuffix)){
                savePath = fileNotSuffix + "." + suffix;
            }
        }
        File file = new File(savePath);
        if (!file.getParentFile().exists()){
            file.setWritable(true);
            file.getParentFile().mkdirs();
        }
        FileOutputStream os = new FileOutputStream(file);
        os.write(saveAsStream(combiner).toByteArray());
        os.flush();
        os.close();
        return this;
    }

    /**
     * 保存为Base64
     * @return Base64
     */
    public String saveAsBase64() throws Exception {
        return saveAsBase64(this.suffix);
    }

    /**
     * 保存为Base64
     * @return Base64
     */
    public String saveAsBase64(String imageType) throws Exception {
        byte[] bytes = saveAsStream(combiner).toByteArray();
        return "data:image/"+imageType+";base64,"+ new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    /**
     * 保存为byte数组
     * @return byte[]
     */
    public byte[] saveAsByte() throws Exception {
        return saveAsStream(combiner).toByteArray();
    }

    /**
     * 保存为byte数组
     * @return byte[]
     */
    public byte[] saveAsByte(BufferedImage combiner) throws Exception {
        return saveAsStream(combiner).toByteArray();
    }

    /**
     * 保存为输出流
     * @return InputStream
     */
    public InputStream saveAsInStream() throws Exception {
        return new ByteArrayInputStream(saveAsStream().toByteArray());
    }

    /**
     * 保存为输出流
     * @return OutputStream
     */
    public OutputStream saveAsOutStream() throws Exception {
        return saveAsStream(this.combiner);
    }

    /**
     * 保存为输出流
     * @return ByteArrayOutputStream
     */
    public ByteArrayOutputStream saveAsStream() throws Exception {
        return saveAsStream(this.combiner);
    }

    /**
     * 保存为输出流
     * @return ByteArrayOutputStream
     */
    public ByteArrayOutputStream saveAsStream(BufferedImage combiner) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        if (1 > compress){
            ImageWriter writer = ImageIO.getImageWritersBySuffix(suffix).next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(compress);
            }
            //writer.setOutput(new MemoryCacheImageOutputStream(os));
            writer.setOutput(ImageIO.createImageOutputStream(os));
            writer.write(null, new IIOImage(combiner, null, null), param);
            writer.dispose();
        }else {
            ImageIO.write(combiner,suffix,os);
        }
        os.close();
        return os;
    }

    // ================================= 合并图片 =============================
    /**
     * 合并图片
     * @param image 图片对象
     * @return Combiner
     */
    public Combiner addImage(Image image){
        return addImage(image,0,0);
    }

    /**
     * 合并图片
     * @param image 待合并的图片
     * @param x 相对画布的起始x坐标
     * @param y 相对画布的起始y坐标
     * @return
     */
    public Combiner addImage(Image image,int x,int y){
        Graphics2D g2d = g2d(combiner);
        g2d.rotate(Math.toRadians(image.getRotate()),image.getRotateX(),image.getRotateY());
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image.getAlpha()));
        g2d.drawImage(image.getImage(),x,y,image.getWidth(),image.getHeight(),null);
        g2d.dispose();
        return this;
    }

    /**
     * 合并图片
     * @param image 待合并的图片
     * @param width 待合并的图片宽度
     * @param height 待合并的图片高度
     * @return
     */
    public Combiner addImage(BufferedImage image,int width,int height){
        return addImage(image,0,0,width,height,0,0,0,1);
    }

    /**
     * 合并图片
     * @param image 待合并的图片
     * @param x 相对画布的起始x坐标
     * @param y 相对画布的起始y坐标
     * @param width 待合并的图片宽度
     * @param height 待合并的图片高度
     * @return
     */
    public Combiner addImage(BufferedImage image,int x,int y,int width,int height){
        return addImage(image,x,y,width,height,0,0,0,1);
    }

    /**
     * 合并图片
     * @param image 待合并的图片
     * @param x 相对画布的起始x坐标
     * @param y 相对画布的起始y坐标
     * @param width 待合并的图片宽度
     * @param height 待合并的图片高度
     * @param rotate 待合并的图片旋转度数
     * @return
     */
    public Combiner addImage(BufferedImage image,int x,int y,int width,int height,int rotate){
        return addImage(image,x,y,width,height,rotate,0,0,1);
    }

    /**
     * 合并图片
     * @param image 待合并的图片
     * @param x 相对画布的起始x坐标
     * @param y 相对画布的起始y坐标
     * @param width 待合并的图片宽度
     * @param height 待合并的图片高度
     * @param rotate 待合并的图片旋转度数
     * @param rotateX 待合并的图片旋转起始x坐标
     * @param rotateY 待合并的图片旋转起始Y坐标
     * @param alpha 待合并的图片透明度
     * @return
     */
    public Combiner addImage(BufferedImage image,int x,int y,int width,int height,int rotate,int rotateX,int rotateY,float alpha){
        Graphics2D g2d = g2d(combiner);
        g2d.rotate(Math.toRadians(rotate),rotateX,rotateY);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(image,x,y,width,height,null);
        g2d.dispose();
        return this;
    }

    // ================================= 添加文字 =============================
    /**
     * 添加文字
     * @param text
     * @return
     */
    public Combiner addText(Text text){
        Graphics2D g2d = combiner.createGraphics();
        //设置字体
        Font font = text.getFont();
        if (0 < text.getFontSize()){
            font = font.deriveFont(text.getFontSize());
        }
        g2d.setFont(font);
        g2d.setColor(ColorUtil.getColor(text.getColor()));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, text.getAlpha()));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (0 < text.getRotate()){
            AffineTransform at = new AffineTransform();
            at.rotate(Math.toRadians(text.getRotate()),text.getRotateX(),text.getRotateY()); // 旋转45度，旋转中心为(文字x坐标,100)
            g2d.setTransform(at);
        }
        float lineHeight = g2d.getFontMetrics().getHeight() + text.getLineHeight();
        float ystart = text.getTextY() + lineHeight;
        int indent = text.getIndent();
        int textWidth = text.getWidth();
        if (text.isWrap()){
            FontMetrics fontMetrics = g2d.getFontMetrics(font);
            String[] lines = Text.splitText(text.getText(), textWidth, fontMetrics,indent); //实现文字自动换行
            int row = 1;
            for (String line : lines) {
                if (row > 1){
                    indent = 0;
                }
                if (null != line && !"".equals(line)){
                    char[] chars = line.toCharArray();
                    float x = indent;
                    for (char c : chars) {
                        if (x > textWidth){
                            x = 0;
                            ystart += lineHeight;
                        }
                        int charWidth = fontMetrics.charWidth(c);
                        g2d.drawString(String.valueOf(c), x + text.getTextX(), ystart);
                        x += charWidth + text.getSpace();
                    }
                }
                ystart += lineHeight;
                row++;
            }
        }else {
            String[] lines = {text.getText()};
            if (!"".equals(text.getSepar())){
                lines = text.getText().split(text.getSepar());
            }
            FontMetrics fontMetrics = g2d.getFontMetrics(font);
            for (String line : lines) {
                char[] chars = line.toCharArray();
                float x = indent;
                for (char c : chars) {
                    if (x > textWidth){
                        break;
                    }
                    int charWidth = fontMetrics.charWidth(c);
                    g2d.drawString(String.valueOf(c), x + text.getTextX(), ystart);
                    x += charWidth + text.getSpace();
                }
                ystart += lineHeight;
            }
        }
        g2d.dispose();
        return this;
    }
}
