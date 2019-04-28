package hotshot.elick.com.hotshot.entity;

public class VideoComment {
    private String content;
    private User user;

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
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
