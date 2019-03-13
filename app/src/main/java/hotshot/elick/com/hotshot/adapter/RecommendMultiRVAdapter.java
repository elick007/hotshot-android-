package hotshot.elick.com.hotshot.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.GlideUtils;

public class RecommendMultiRVAdapter extends BaseQuickAdapter<VideoBean,BaseViewHolder> {
    public RecommendMultiRVAdapter(int layoutResId, @Nullable List<VideoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        helper.setText(R.id.recommend_item_title,item.getTitle());
        helper.setText(R.id.recommend_item_author,item.getAuthor());
        GlideUtils.loadRoundImage(mContext,item.getCover(),helper.getView(R.id.recommend_item_image));
    }
}
