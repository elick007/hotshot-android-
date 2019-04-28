package hotshot.elick.com.hotshot.entity;

public class FavVideo {
    private int id;
    private VideoBean videoBean;

    public int getId() {
        return id;
    }

    public VideoBean getVideoBean() {
        return videoBean;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideoBean(VideoBean videoBean) {
        this.videoBean = videoBean;
    }
}
