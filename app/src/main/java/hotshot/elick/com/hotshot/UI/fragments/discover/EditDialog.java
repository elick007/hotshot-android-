package hotshot.elick.com.hotshot.UI.fragments.discover;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hotshot.elick.com.hotshot.R;

/**
 * Created by admin on 2019/5/4.
 */

public class EditDialog extends Dialog {

    @BindView(R.id.content_edit)
    EditText contentEdit;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.public_btn)
    Button publicBtn;
    private Unbinder unbinder;
    private PublicClickListener publicClickListener;

    public EditDialog(@NonNull Context context,PublicClickListener publicClickListener) {
        this(context, R.style.MyDialogStyle);
        this.publicClickListener=publicClickListener;
    }

    public EditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    private void initView() {
        setContentView(R.layout.discover_edit_dialog);
        unbinder=ButterKnife.bind(this);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @OnClick({R.id.cancel_btn, R.id.public_btn})
    public void onClick(View view) {
        this.dismiss();
        switch (view.getId()) {
            case R.id.cancel_btn:
                if (publicClickListener!=null){
                    publicClickListener.onCancel();
                }
                break;
            case R.id.public_btn:
                if (publicClickListener!=null){
                    publicClickListener.onPublic(contentEdit.getText().toString());
                }
                break;
        }
    }

    public interface PublicClickListener{
        void onCancel();
        void onPublic(String content);
    }
}
