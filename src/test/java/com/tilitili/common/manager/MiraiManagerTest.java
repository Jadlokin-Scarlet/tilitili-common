package com.tilitili.common.manager;

import com.tilitili.common.StartApplication;
import com.tilitili.common.emnus.TagType;
import com.tilitili.common.entity.RecommendVideo;
import com.tilitili.common.entity.Tag;
import com.tilitili.common.entity.query.*;
import com.tilitili.common.mapper.*;
import com.tilitili.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
@EnableAutoConfiguration
public class MiraiManagerTest {

    @Resource
    private MiraiManager miraiManager;
    @Resource
    private VideoInfoMapper videoInfoMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private RecommendVideoMapper recommendVideoMapper;
    @Resource
    private RecommendMapper recommendMapper;
    @Resource
    private RecommendTalkMapper recommendTalkMapper;

    @Value("${mirai.friend-qq}")
    private Long friendQQ;

    @Test
    public void test0() {}


}
