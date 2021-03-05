package com.tilitili.common.entity.resource;

import com.tilitili.common.entity.RecommendVideo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RecommendVideoIssueResource extends Resource {
    private Integer issue;
    public RecommendVideoIssueResource(RecommendVideo recommendVideo) {
        setValue(recommendVideo.getId());
        setText(recommendVideo.getName());
        setIssue(recommendVideo.getIssue());
    }
}
