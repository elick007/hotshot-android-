package hotshot.elick.com.hotshot.entity;

import java.io.Serializable;
import java.util.List;

public class HotVideosEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<VideoBean> videoList;
    private String nextLink;

    public List<VideoBean> getVideoList() {
        return videoList;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setVideoList(List<VideoBean> videoList) {
        this.videoList = videoList;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }
}
