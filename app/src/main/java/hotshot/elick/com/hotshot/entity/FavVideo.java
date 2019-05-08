package hotshot.elick.com.hotshot.entity;

public class FavVideo {
    private int id;
    private VideoBean video;

    public int getId() {
        return id;
    }

    public VideoBean getVideo() {
        return video;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }
}
