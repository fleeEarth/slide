package my.verify.slide.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.awt.Color.BLACK;
import static java.awt.Transparency.TRANSLUCENT;

/**
 * 验证码绘制类
 */
public class DrawCaptchaUtil {
    /**
     * 画布宽度
     */
    private static int canvasWidth = 300;

    /**
     * 画布高度
     */
    private static int canvasHeight = 150;
    /**
     * 图片宽度
     */
    private static int imageWidth = 300;

    /**
     * 图片高度
     */
    private static int imageHeight = 150;
    /**
     * 滑块画布宽度
     */
    private static int slideCanvasWidth = 50;
    /**
     * 滑块画布高度
     */
    private static int slideCanvasHeight = 150;
    /**
     * 滑块图片宽度
     */
    private static int slideImageWidth = 50;
    /**
     * 滑块图片高度
     */
    private static int slideImageHeight = 50;

    /**
     * 绘制背景图片
     *
     * @param imagePath      图片路径
     * @param slideImagePath 滑块图片路径
     * @param code 存放验证码信息
     * @return
     * @throws IOException
     */
    public static Map<String, String> drawImage(File imagePath, File slideImagePath,HashMap<String,Integer> code) throws IOException {
        BufferedImage canvas = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) canvas.getGraphics();
        g.fillRect(0, 0, imageWidth, imageHeight);
        BufferedImage read = ImageIO.read(Files.newInputStream(Paths.get(imagePath.getAbsolutePath())));
        g.drawImage(read, 0, 0, null, null);
        int[] point = randomAnchorPoint();
        //绘制滑块图片
        BufferedImage slideImage = drawSlideImage(slideImagePath, canvas, point[0], point[1]);
        //设置为透明覆盖 很重要
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9f));
        //覆盖阴影
        g.drawImage(drawSlideImageShadow(slideImagePath), point[0], point[1], null, null);
        g.dispose();
        HashMap<String, String> images = new HashMap<>();
        images.put("bkImage", ImageToBase64Util.bufferedImageToBase64(canvas));
        images.put("slideImage", ImageToBase64Util.bufferedImageToBase64(slideImage));
        //存放验证码
        code.put("offset",point[0]);
        return images;
    }

    /**
     * 绘制滑块
     *
     * @param slideImagePath 滑块图片路径
     * @param bkImg          验证码背景图
     * @param x              随机坐标X
     * @param y              随机坐标Y
     * @return
     * @throws IOException
     */
    private static BufferedImage drawSlideImage(File slideImagePath, BufferedImage bkImg, int x, int y) throws IOException {
        BufferedImage canvas = new BufferedImage(slideCanvasWidth, slideCanvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();
        g.getDeviceConfiguration().createCompatibleImage(slideCanvasWidth, slideCanvasHeight, Transparency.TRANSLUCENT);
        g = canvas.createGraphics();
        BufferedImage slideImage = bkImg.getSubimage(x, y, slideImageWidth, slideImageHeight);
        BufferedImage slide = ImageIO.read(Files.newInputStream(Paths.get(slideImagePath.getAbsolutePath())));
        Graphics2D g2 = slide.createGraphics();
        //设置为透明覆盖 很重要
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        g2.drawImage(slideImage, 0, 0, null);
        g2.dispose();
        g.drawImage(slide, 0, y, null);
        g.dispose();
        return canvas;
    }

    /**
     * 绘制背景图上的阴影
     *
     * @param slideImagePath
     * @return
     * @throws IOException
     */
    private static BufferedImage drawSlideImageShadow(File slideImagePath) throws IOException {
        BufferedImage slide = ImageIO.read(Files.newInputStream(Paths.get(slideImagePath.getAbsolutePath())));
        return slide;

    }


    /**
     * 生成随机坐标点 x > 滑块画布宽度 y < 滑块画布高度-滑块图片高度
     *
     * @return
     */
    private static int[] randomAnchorPoint() {
        Random random = new Random();
        //设置x坐标从 slideCanvasWidth 开始至 canvasWidth - slideCanvasWidth 范围随机
        int x = random.nextInt(canvasWidth - slideImageWidth);
        if (x < slideImageWidth) {
            //随机生成x点小于图片宽度+上图片宽度
            x += slideCanvasWidth;
        }
        //设置y坐标
        int y = random.nextInt(canvasHeight - slideImageHeight);
        int[] point = {x, y};
        return point;
    }


}
