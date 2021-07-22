package com.tilitili.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain=true)
public class Owner implements Serializable {
    private Long uid;
    private String name;
    private String face;
    private String sex;
    private String sign;
    private Integer rank;
    private Integer level;
    private Integer jointime;
    private Integer moral;
    private Integer silence;
    private String birthday;
    private Integer coins;
    private Boolean fansBadge;
    private Integer officialRole;
    private String officialTitle;
    private String officialDesc;
    private Integer officialType;
    private Integer vipType;
    private Integer vipStatus;
    private Integer vipThemeType;
    private String vipLabelPath;
    private String vipLabelText;
    private String vipLabelTheme;
    private Integer vipAvatarSubscript;
    private String vipNicknameColor;
    private Integer pendantPid;
    private String pendantName;
    private String pendantImage;
    private Integer pendantExpire;
    private String pendantImageEnhance;
    private String pendantImageEnhanceFrame;
    private Integer nameplateNid;
    private String nameplateName;
    private String nameplateImage;
    private String nameplateImageSmall;
    private String nameplateLevel;
    private String nameplateCondition;
    private String topPhoto;
    private Integer type;
    private Integer status;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private Long roomId;
    private String roomUrl;
    private Integer roomStatus;
}