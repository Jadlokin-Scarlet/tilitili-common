package com.tilitili.common.manager;

import com.tilitili.common.entity.mirai.MiraiMessage;
import com.tilitili.common.entity.view.baidu.TranslateBaseView;
import com.tilitili.common.entity.view.baidu.TranslateView;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class BaiduManagerTest {

    @Autowired
    private BaiduManager baiduManager;
    @Test
    public void reqTranslateImage() {
        String result = baiduManager.translateImage("http://gchat.qpic.cn/gchatpic_new/294195656/942271894-2616503631-62F8B6B006C042B0BC91C412CF6E3F9F/0?term=2");
        System.out.println(result);
    }
}