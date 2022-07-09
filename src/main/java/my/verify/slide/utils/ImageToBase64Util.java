package my.verify.slide.utils;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageToBase64Util {

    /**
     * BufferedImage 对象转BASE64
     * @param image
     * @return
     */
    public static String bufferedImageToBase64(BufferedImage image)  {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"PNG",out);
            byte[] bytes = out.toByteArray();
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encodeBuffer(bytes);
        } catch (IOException e) {
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException ex) {

            }
        }

        return null;
    }


}
