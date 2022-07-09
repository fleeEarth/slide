package my.verify.slide.service.impl;

import my.verify.slide.service.VerifyService;
import my.verify.slide.utils.DrawCaptchaUtil;
import my.verify.slide.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Autowired
    private HttpServletResponse response;

    /**
     * 存放验证码
     */

    private HashMap<String,Integer> code = new HashMap<>();
    @Override
    public Result slideImage() throws IOException {

        String imageFilePath = ClassUtils.getDefaultClassLoader().getResource("static").getPath() ;
        File imageFile = new File(imageFilePath+ "/captcha/");
        File slideFile = new File(imageFilePath+ "/slide/slide.png");
        if(imageFile.isDirectory()){
            File[] files = imageFile.listFiles();
            Random random = new Random();
            int i = random.nextInt(files.length);
            imageFile =  files[i];
        }
        return Result.success(DrawCaptchaUtil.drawImage(imageFile, slideFile,code));
    }

    @Override
    public Result verifyCodeCheck(Map<String, Object> params) {
        try{
            Integer offset = code.get("offset");
            Integer commitOffset = (Integer)params.get("offset");
            Integer padding = offset - commitOffset;
            if(padding > 5 || padding < -5){
                return Result.success(false);
            }
            return Result.success(true);
        }catch (Exception e){
            return Result.success(false);
        }

    }
}
