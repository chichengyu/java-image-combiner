package com.image.combiner.base;

import com.image.combiner.util.ColorUtil;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/** 公共方法
 * author xiaochi
 * date 2024/11/16
 */
public class Base {

    /**
     * 重新绘制
     * @param originalImage 原图像
     * @param newWidth 新宽度
     * @param newHeight 新高度
     * @return BufferedImage
     */
    public static BufferedImage reset(BufferedImage originalImage, int newWidth, int newHeight){
        BufferedImage newImage = new BufferedImage(newWidth, newHeight,originalImage.getType());
        Graphics2D g2d = g2d(newImage);
        // 进行缩放
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return newImage;
    }

    /**
     * 边框设置
     * @param srcImage 源图像
     * @param border 边框宽度
     * @param radius 圆角半径
     * @param padding 内边距
     * @param borderColor 边框颜色
     * @param paddingColor 内边距颜色
     * @return BufferedImage
     */
    public static BufferedImage border(BufferedImage srcImage, int border,int radius, int padding,String borderColor,String paddingColor){
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int canvasWidth = width + padding * 2;
        int canvasHeight = height + padding * 2;
        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();
        gs.setComposite(AlphaComposite.Src);
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setColor(Color.WHITE);
        if (null != paddingColor && !"".equals(paddingColor)){
            gs.setColor(ColorUtil.getColor(paddingColor));
        }
        gs.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
        gs.setComposite(AlphaComposite.SrcAtop);
        gs.drawImage(srcImage, padding, padding, null);
        if(border !=0){
            gs.setColor(Color.GRAY);
            if (null != borderColor && !"".equals(borderColor)){
                gs.setColor(ColorUtil.getColor(borderColor));
            }
            gs.setStroke(new BasicStroke(border));
            gs.drawRoundRect(padding, padding, canvasWidth - 2 * padding, canvasHeight - 2 * padding, radius, radius);
        }
        gs.dispose();
        return image;
    }

    /**
     * 图片切圆角
     * @param srcImage 图像源
     * @param radius 圆角半径
     * @return BufferedImage
     */
    public static BufferedImage radius(BufferedImage srcImage, float radius){
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = g2d(image);
        gs.fill(new RoundRectangle2D.Float(0, 0, width, height, radius, radius));
        gs.setComposite(AlphaComposite.SrcAtop);
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
        gs.drawImage(srcImage, 0, 0,width,height, null);
        gs.dispose();
        return image;
    }

    /**
     * 获取画布g2d并设置消除锯齿
     * @param combiner 创建g2
     * @return BufferedImage
     */
    public static Graphics2D g2d(BufferedImage combiner){
        Graphics2D g2d = combiner.createGraphics();
        // 改善图像质量,消除锯齿
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g2d;
    }
}
