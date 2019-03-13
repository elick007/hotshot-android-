package hotshot.elick.com.hotshot.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.GlideUtils;
import hotshot.elick.com.hotshot.utils.TimeUtil;

public class OEMultiRVAdapter extends BaseMultiItemQuickAdapter<VideoBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public OEMultiRVAdapter(List<VideoBean> data) {
        super(data);
        addItemType(VideoBean.TYPE_HEAD, R.layout.videos_list_item_oe_header);
        addItemType(VideoBean.TYPE_NORMOL, R.layout.videos_list_item_oe_normal);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        switch (helper.getItemViewType()) {
            case VideoBean.TYPE_HEAD:
                GlideUtils.loadRoundImage(mContext,item.getCover(),helper.getView(R.id.item_header_image_view));
                helper.setText(R.id.item_header_duration_tv, TimeUtil.secondToMinute(item.getDuration()));
                helper.setText(R.id.oe_item_bottom_title,item.getTitle());
                break;
            case VideoBean.TYPE_NORMOL:
                //Glide.with(mContext).load(item.getCover()).apply(new RequestOptions().centerCrop()).into((ImageView) helper.getView(R.id.item_image_view));
                GlideUtils.loadRoundImage(mContext,item.getCover(),helper.getView(R.id.item_image_view));
                helper.setText(R.id.item_duration_tv,TimeUtil.secondToMinute(item.getDuration()));
                helper.setText(R.id.item_bottom_title,item.getTitle());
                break;
        }
    }
}
