package my.verify.slide.controller;

import my.verify.slide.service.VerifyService;
import my.verify.slide.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/verifyService")
public class VerifyController {

    @Autowired
    private VerifyService verifyService;

    @RequestMapping(value = "/slideImage",method = RequestMethod.GET)
    public Result slideImage() throws IOException {
        return verifyService.slideImage();
    }

    @RequestMapping(value = "/verifyCodeCheck",method = RequestMethod.POST)
    public Result verifyCodeCheck(@RequestBody Map<String,Object> params){
        return verifyService.verifyCodeCheck(params);
    }

}
