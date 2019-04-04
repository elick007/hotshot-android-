package hotshot.elick.com.hotshot.entity;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ResponseBase<T> {
    public final static int SUCCESS_CODE = 1;
    public final static int ERROR_CODE = 0;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SUCCESS_CODE, ERROR_CODE})
    @interface ResultCode {

    }
    @ResultCode
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
