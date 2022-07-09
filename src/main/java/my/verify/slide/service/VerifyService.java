package my.verify.slide.service;

import my.verify.slide.utils.Result;

import java.io.IOException;
import java.util.Map;

public interface VerifyService {
    Result slideImage() throws IOException;

    Result verifyCodeCheck(Map<String, Object> params);
}
