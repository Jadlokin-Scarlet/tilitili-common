package com.tilitili.common.mapper;

import com.tilitili.common.entity.query.BaseQuery;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TouhouAllMapper {
    List<Long> list(BaseQuery<?> baseQuery);
    int count(BaseQuery<?> baseQuery);

    @Select("select touhou_all.av from (select * from touhou_all union select * from touhou_new ) touhou_all \n" +
            "    left join video_info on touhou_all.av = video_info.av\n" +
            "    left join video_data on touhou_all.av = video_data.av and issue = 7\n" +
            "    left join [right] on touhou_all.av = [right].av\n" +
            "    left join touhouTag on touhou_all.av = touhouTag.av\n" +
            "    left join (select av, count(1) as page from page group by av) page on touhou_all.av = page.av\n" +
            "    left join owner on video_info.owner = owner.name\n" +
            "where video_info.av is null\n" +
            "    or [right].av is null\n" +
            "    or touhouTag.av is null\n" +
            "    or page.av is null\n" +
            "    or owner.uid is null\n" +
            "    or video_info.bv is null\n" +
            "    or video_info.is_delete = 1\n" +
            "    or video_data.danmaku is null\n" +
            "    or touhouTag.tag1 is null\n" +
            "    or page.page != video_data.page\n" +
            "order by touhou_all.av desc")
    List<Long> selectOtherAv();

    @Select("select touhou_all.av from (select * from touhou_all union select * from touhou_new) touhou_all\n" +
            "left join video_info on touhou_all.av = video_info.av\n" +
            "where video_info.av is null\n" +
            "order by touhou_all.av desc")
    List<Long> checkVideoInfo();

    @Select("select touhou_all.av from (select * from touhou_all union select * from touhou_new) touhou_all\n" +
            "left join video_tag_relation on touhou_all.av = video_tag_relation.av\n" +
            "where video_tag_relation.av is null\n" +
            "order by touhou_all.av desc")
    List<Long> checkVideoTag();

    @Select("select touhou_all.av from (select * from touhou_all union select * from touhou_new) touhou_all\n" +
            "left join video_info on touhou_all.av = video_info.av\n" +
            "where video_info.update_time is null or video_info.update_time < dateadd(dd, 7, getdate())")
    List<Long> selectAllAv();

}
