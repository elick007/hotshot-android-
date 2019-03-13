package hotshot.elick.com.hotshot.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.GlideUtils;

public class DYRVAdapter extends BaseQuickAdapter<VideoBean,BaseViewHolder> {

    public DYRVAdapter(int layoutResId, @Nullable List<VideoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        helper.setText(R.id.dy_description,item.getDescription());
        helper.setText(R.id.dy_author,item.getAuthor());
        //helper.setImageResource(R.id.dy_item_image_view,R.drawable.login_bg);
        //Glide.with(mContext).load(item.getCover()).apply(new RequestOptions().transform(new CenterCrop())).into((ImageView) helper.getView(R.id.dy_item_image_view));
        GlideUtils.loadOutLineImage(mContext,item.getCover(),helper.getView(R.id.dy_item_image_view));
    }
}
