package com.tilitili.common.mapper;

import com.google.gson.GsonBuilder;
import com.tilitili.common.StartApplication;
import com.tilitili.common.entity.VideoInfo;
import com.tilitili.common.entity.query.VideoInfoQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
@EnableAutoConfiguration
public class VideoInfoMapperTest {

    @Autowired
    private VideoInfoMapper videoInfoMapper;

    @Test
    public void list() {
        List<VideoInfo> videoInfoList = videoInfoMapper.list(new VideoInfoQuery());
        log.info(new GsonBuilder().setPrettyPrinting().create().toJson(videoInfoList));
    }

}