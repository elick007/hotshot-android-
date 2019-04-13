package hotshot.elick.com.hotshot.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import hotshot.elick.com.hotshot.R;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public static class Builder{
        private Context context;
        private boolean isCancelable=false;
        private boolean isCancelOutside=false;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        public Builder setCancelOutside(boolean cancelOutside) {
            isCancelOutside = cancelOutside;
            return this;
        }
        public LoadingDialog build(){
            LoadingDialog loadingDialog=new LoadingDialog(context, R.style.MyDialogStyle);
            View view=LayoutInflater.from(context).inflate(R.layout.dialog_loading,null);
            loadingDialog.setContentView(view);
            loadingDialog.setCancelable(isCancelable);
            loadingDialog.setCanceledOnTouchOutside(isCancelOutside);
            return loadingDialog;
        }
    }
}
