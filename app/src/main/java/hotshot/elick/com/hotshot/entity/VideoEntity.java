package hotshot.elick.com.hotshot.entity;

import java.util.List;

public class VideoEntity extends ResponseBase<VideoEntity.Data> {

    public VideoEntity() {
    }

    public static class Data {
        List<VideoBean> videoList;

        public List<VideoBean> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoBean> videoList) {
            this.videoList = videoList;
        }
    }
}
