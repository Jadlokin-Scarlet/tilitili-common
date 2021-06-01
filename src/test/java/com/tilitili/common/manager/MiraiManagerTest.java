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


    @Test
    public void test0() {}

//    @Test
    public void test() {
        //统计昨日数据
        List<Tag> tagList = tagMapper.list(new TagQuery().setType(TagType.TOUHOU_TAG.value).setStatus(0));
        String tagListStr = tagList.parallelStream().map(Tag::getName).map(String::valueOf).collect(Collectors.joining(","));
        int count = videoInfoMapper.count(new VideoInfoQuery().setPubTimeStart(DateUtils.getT1()).setPubTimeEnd(DateUtils.getCurrentDate()).setIsDelete(false).setStatus(0));
        Integer messageId = miraiManager.sendGroupMessage("Plain", String.format("周刊小助手提醒您，昨日新增视频%s，当前监听tag：%s", count, tagListStr));
    }

//    @Test
    public void test2() {
        RecommendVideoQuery query = new RecommendVideoQuery().setStatus(0).setPageSize(1);
        RecommendVideo recommendVideo = recommendVideoMapper.list(query).get(0);
        Integer issueId = recommendVideo.getId();
        int recommendNumber = recommendMapper.count(new RecommendQuery().setIssueId(issueId).setType(0));
        int selfRecommendNumber = recommendMapper.count(new RecommendQuery().setIssueId(issueId).setType(1));
        boolean hasTalk = !recommendTalkMapper.list(new RecommendTalkQuery().setIssueId(issueId).setStatus(0).setPageSize(1)).isEmpty();


        String weekStr = new SimpleDateFormat("E").format(new Date());
        String talkMessage = hasTalk? "已有对话": "尚无对话";
        miraiManager.sendGroupMessage("Plain", String.format("推荐刊小助手提醒您，已经%s啦，当前为[%s]期，已有推荐%s，自荐%s，%s", weekStr, recommendVideo.getName(), recommendNumber, selfRecommendNumber, talkMessage));
    }

}
