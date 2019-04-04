package hotshot.elick.com.hotshot.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class VideoBean implements MultiItemEntity ,Serializable{
    private static final long serialVersionUID=1L;
    public static final int TYPE_HEAD=0x00000001;
    public static final int TYPE_NORMOL=0x00000002;
    private int itemType=TYPE_NORMOL;
    private int id;
    private String created;
    private String type;
    private String date;
    private String duration;
    private String title;
    private String description;
    private String cover;
    private String playUrl;
    private String author;

    public VideoBean() {
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCover() {
        return cover;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int getItemType() { return itemType;
    }
}
