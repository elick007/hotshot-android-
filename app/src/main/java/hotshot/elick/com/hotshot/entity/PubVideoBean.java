package hotshot.elick.com.hotshot.entity;

import java.io.Serializable;

public class PubVideoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;
    private String cover;
    private String playUrl;
    private Author author;
    private String created;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getContent() {
        return content;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public Author getAuthor() {
        return author;
    }

    public String getCreated() {
        return created;
    }

    public String getCover() {
        return cover;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public static class Author{
        private String userName;
        private String avatar;

        public String getUserName() {
            return userName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
