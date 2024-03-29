package com.tilitili.common.entity.view.view;

import com.tilitili.common.entity.view.owner.OwnerView;

import java.util.List;

public class VideoView {
    public Long aid;
    public String bvid;
    public String tname;
    public Integer copyright;
    public String pic;
    public String title;
    public Long pubdate;
    public String desc;
    public Long state;
    public Long attribute;
    public Long duration;
    public String dynamic;
    public Rights rights;
    public OwnerView owner;
    public String short_link;
    public VideoDataView stat;
    public List<PageView> pages;
}
