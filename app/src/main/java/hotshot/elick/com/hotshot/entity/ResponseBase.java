package hotshot.elick.com.hotshot.entity;

public class ResponseBase<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = this.msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
