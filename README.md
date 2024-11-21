# java-image-combiner
<p align="left">
    <a href="https://github.com/chichengyu/java-image-combiner">
        <img src="https://img.shields.io/badge/last version-1.1.0-green" alt="version-1.1.0" />
    </a>
</p>

### 介绍
Java处理图片工具包,包含图文合成、毛玻璃效果、离散效果、制作海报等

### 导包
```
<dependency>
    <groupId>io.github.chichengyu</groupId>
    <artifactId>java-image-combiner</artifactId>
    <version>1.1.0</version>
</dependency>
```

### 说明
#### 1.画布
整个画布获取,然后可以进行自行扩展,不断绘制
```
BufferedImage combiner = Combiner.create(bgimgPath)
    .getCombiner();
```
空白画布
```
Combiner.create(100,100);
combiner.backColor("#000"); // 设置背景颜色
```
网络图片背景画布
```
String bgimgPath = "https://media2.hndt.com/data_01/1/1/2023/09/25/e422c57afbb8af0b0715faa70509c666.jpg";
Combiner combiner = Combiner.create(bgimgPath);
```
本地图片背景画布
```
String bgimgPath = "D:\\test.jpg";
Combiner combiner = Combiner.create(bgimgPath);
```
file对象图片背景画布
```
Combiner combiner = Combiner.create(new File(bgimgPath));
```
文件流图片背景画布
```
Combiner combiner = Combiner.create(file.getInputStream());
```
创建画布的同时指定画布大小
```
Combiner combiner = Combiner.create(bgimgPath,100,100);
```
裁剪画布
```
Combiner.create(bgimgPath).crop(400,200).save("D:\\03.jpg");
```
旋转画布
```
// 旋转画布并设置旋转后画布的背景颜色
Combiner.create(bgimgPath)
    .rotate(45,400,-400,300,300,"#fff")
    .save("D:\\03.jpg");
```
高斯模糊
```
Combiner.create(bgimgPath)
    .dim(10)
    .save("D:\\03.jpg");
```
透明度
```
Combiner.create(bgimgPath)
    .alpha(.5f)
    .save("D:\\03.jpg");
```
毛玻璃
```
// 添加毛玻璃效果
BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, new float[] {
    0.1f, 0.1f, 0.1f,
    0.1f, 0.2f, 0.1f,
    0.1f, 0.1f, 0.1f
}));
Combiner.create(bgimgPath)
    .convolveOp(op)
    .save("D:\\03.jpg");
```
卷积运算(可以做毛玻璃\高斯模糊...等等自行扩展)，用法与毛玻璃一致
```
// 添加毛玻璃效果
ConvolveOp op = new ConvolveOp(new Kernel(3, 3, new float[] {
    0.1f, 0.1f, 0.1f,
    0.1f, 0.2f, 0.1f,
    0.1f, 0.1f, 0.1f
}));
Combiner.create(bgimgPath)
    .convolveOp(op)
    .save("D:\\03.jpg");
```
离散效果(类似毛玻璃)
```
Combiner.create(bgimgPath)
    .spread(18)
    .save("D:\\03.jpg");
```
等比例缩放
```
Combiner.create(bgimgPath)
    .scale(0.5)
    .save("D:\\03.jpg");
```
按宽高最大边缩放(最大边=指定长度)
```
Combiner.create(bgimgPath)
    .thumbnail(400)
    .save("D:\\03.jpg");
```
#### 2.图像
*注:获取图像源` image.getImage(); `返回的是` BufferedImage `,可以进行自行扩展,

图片添加到画布
```
# 添加到画布,并指定图片的起始x,y坐标
Combiner.create(bgimgPath)
    .addImage(image,250,0) 
    .save("D:\\03.jpg",true);
```
图片路径支持4种方式:网络图片、本地图片、文件流、也可以是`BufferedImage对象`
```
# 网络图片
String imgPath = "https://media2.hndt.com/data_01/1/1/2023/09/25/e422c57afbb8af0b0715faa70509c666.jpg";
Image image = Image.create(imgPath);
# 本地图片
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));

# 文件流
Image image = Image.create(new FileInputStream(new File(mergeImgPath)));

# BufferedImage
BufferedImage image1 = new BufferedImage();
Image image = Image.create(ImageIO.read(new URL(bgimgPath)));
```
图片宽高度
```
Image image = Image.create(imgPath);
image.width(300) // 宽度
    .height(400); // 高度
```
图片等比例缩放
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
image.width(300)
    .height(400)
    .scale(200);
```
图片宽高最大边缩放(最大边=指定长度)
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
image.width(300)
    .height(400)
    .thumbnail(200);
```
图片裁剪
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
image.width(300)
    .height(400)
    .crop(200,200);
```
图片透明度
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
image.width(300)
    .height(400)
    .alpha(0.1f);
```
图片圆角
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
image.width(300)
    .height(400)
    .radius(80);
```
图片边框
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
image.width(300)
    .height(400)
    .border(10);
```
图片高斯模糊
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
image.width(300)
    .height(400)
    .dim(20);
```
图片毛玻璃
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
// 添加毛玻璃效果
BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, new float[] {
    0.1f, 0.1f, 0.1f,
    0.1f, 0.2f, 0.1f,
    0.1f, 0.1f, 0.1f
}));
image.convolveOp(op);
```
卷积运算(可以做毛玻璃\高斯模糊...等等自行扩展)，用法与毛玻璃一致
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
// 添加毛玻璃效果
BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, new float[] {
    0.1f, 0.1f, 0.1f,
    0.1f, 0.2f, 0.1f,
    0.1f, 0.1f, 0.1f
}));
image.convolveOp(op);
```
离散效果(类似毛玻璃)
```
String mergeImgPath = "D:\\test.jpg";
Image image = Image.create(new File(mergeImgPath));
image.spread(18);
```

#### 3.文本
首先说说字体,支持系统内部字体,也支持加载外部字体文件,方式多种,如下
```
# Text.showFonts(); 查询系统所有字体

String txt = "你好啊";
Text text = Text.create(txt)
    .font("微软雅黑") //内部字体
    //.font(new FileInputStream(new File(fontPath))) //加载外部字体文件
    .fontSize(80);
Combiner.create(bgimgPath)
    .addText(text)
    .save("D:\\03.jpg",true);
```
文本显示模式(横排Text.Mode.ROW/竖排Text.Mode.COL)
```
Text.create(txt)
    .direction(Text.Mode.ROW);
```
文本字体大小、颜色、换行、行高等等设置
```
String txt = "你好\n啊";
Text text = Text.create(txt)
    .font("微软雅黑") // 系统内部字体
    .separ("\n") // 指定分割符,分割成段落
    .fontSize(80) // 字体大小
    .lineHeight(-10) // 行高
    .wrap(true) // 自动换行
    .color("#f60f60");//颜色
Combiner.create(bgimgPath)
    .addText(text)
    .save("D:\\03.jpg",true);
```
文本换行切割(与切割符separ不能共存,切割符代表外部控制,不需要自动换行了)
```
String txt = "你好\n啊";
Text text = Text.create(txt)
    .font("微软雅黑")
    .separ("\n") // 指定分割符,分割成段落
    .fontSize(80);
Combiner.create(bgimgPath)
    .addText(text)
    .save("D:\\03.jpg",true);
```
文本首行缩进
```
String txt = "你好\n啊";
Text text = Text.create(txt)
    .font("微软雅黑")
    .indent(32) // 首行缩进
    .fontSize(80);
Combiner.create(bgimgPath)
    .addText(text)
    .save("D:\\03.jpg",true);
```
文本自动换行
```
String txt = "你好\n啊";
Text text = Text.create(txt)
    .font("微软雅黑")
    .fontSize(80)
    .width(300)
    .wrap(true);
Combiner.create(bgimgPath)
    .addText(text)
    .save("D:\\03.jpg",true);
```
文本透明度
```
String txt = "你好\n啊";
Text text = Text.create(txt)
    .font("微软雅黑")
    .fontSize(80)
    .alpha(0.5f)
    .width(300)
    .wrap(true);
Combiner.create(bgimgPath)
    .addText(text)
    .save("D:\\03.jpg",true);
```
文本旋转(与旋转x、y坐标设置)
```
String txt = "你好\n啊";
Text text = Text.create(txt)
    .font("微软雅黑")
    .fontSize(80)
    .rotate(15f) // 旋转度数
    .width(300)
    .wrap(true);
Combiner.create(bgimgPath)
    .addText(text)
    .save("D:\\03.jpg",true);

Text text = Text.create(txt)
    .font("微软雅黑")
    .fontSize(80)
    .rotate(15f) // 旋转度数
    .rotateX(100) // 旋转x坐标设置
    .rotateY(100) // 旋转Y坐标设置
    .width(300)
    .wrap(true);
```
更多功能可以提出建议,后续会进行更新。