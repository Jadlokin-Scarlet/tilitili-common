package com.tilitili.common.manager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.tilitili.common.entity.UploadFile;
import com.tilitili.common.entity.view.TransResult;
import com.tilitili.common.entity.view.baidu.TranslateBaseView;
import com.tilitili.common.entity.view.baidu.TranslateView;
import com.tilitili.common.utils.FileUtil;
import com.tilitili.common.utils.HttpClientUtil;
import com.tilitili.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.tilitili.common.utils.FileUtil.md5Image;
import static com.tilitili.common.utils.StringUtils.md5;
import static org.apache.http.util.TextUtils.isBlank;

@Slf4j
@Component
public class BaiduManager {
    @Value("${baidu.app-id}")
    private String appId;
    @Value("${baidu.app-key}")
    private String appKey;

    public String translate(String source) {
        TranslateView translateView = reqTranslate("auto", "en", source);
        if (translateView == null) {
            return "";
        }
        String from = translateView.getFrom();
        String to;
        if ("zh".equals(from)) {
            to = "en";
        } else {
            to = "zh";
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("?", e);
            return "";
        }
        return translate(from, to, source);
    }

    public String translate(String to, String source) {
        TranslateView translateView = reqTranslate("auto", "en", source);
        if (translateView == null) {
            return "";
        }
        String from = translateView.getFrom();
        return translate(from, to, source);
    }

    public String translate(String from, String to, String source) {
        TranslateView translateView = reqTranslate(from, to, source);
        if (translateView == null) {
            return "";
        }
        List<TransResult> transResult = translateView.getTrans_result();
        if (CollectionUtils.isEmpty(transResult)) {
            return "";
        }
        return transResult.stream().map(TransResult::getDst).filter(Objects::nonNull).collect(Collectors.joining("\n"));
    }

    public String translateImage(String src) {
        TranslateBaseView translateView = reqTranslateImage("auto", "en", src);
        if (translateView == null) {
            return "";
        }
        String from = translateView.getData().getFrom();
        String source = translateView.getData().getSumSrc();
        String to;
        if ("zh".equals(from)) {
            to = "en";
        } else {
            to = "zh";
        }
        return translate(from, to, source);
    }

    private TranslateView reqTranslate(String from, String to, String source) {
        String salt = String.valueOf(System.currentTimeMillis());
        String sign = md5(appId + source + salt + appKey);
        Map<String, String> params = ImmutableMap.<String, String>builder().put("from", from).put("to", to).put("q", source).put("appid", appId).put("salt", salt).put("sign", sign).build();
        String result = HttpClientUtil.httpPost("http://api.fanyi.baidu.com/api/trans/vip/translate", params);
        log.info("req={} resp={}", params, result);
        if (isBlank(result)) {
            return null;
        }
        return new Gson().fromJson(result, TranslateView.class);
    }

    private TranslateBaseView reqTranslateImage(String from, String to, String url) {
        String fileType = "png";
        BufferedImage image = FileUtil.downloadImage(url);
        String salt = String.valueOf(System.currentTimeMillis());
        String cuid = "APICUID";
        String mac = "mac";
        String erase = "0";
        String sign = md5(appId+md5Image(image, fileType)+salt+cuid+mac+appKey);
        Map<String, String> params = ImmutableMap.<String, String>builder().put("from", from).put("to", to).put("appid", appId).put("salt", salt).put("cuid", cuid).put("mac", mac).put("erase", erase).put("sign", sign).build();
        String result = HttpClientUtil.httpPostWithFile("https://fanyi-api.baidu.com/api/trans/sdk/picture", params, new UploadFile().setFileName("image").setFileType(fileType).setKey("image").setBufferedImage(image));
        log.info("req={} resp={}", params, result);
        if (isBlank(result)) {
            return null;
        }
        return new Gson().fromJson(result, TranslateBaseView.class);
    }
}
