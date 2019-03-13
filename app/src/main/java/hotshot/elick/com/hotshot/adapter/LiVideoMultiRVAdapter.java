package hotshot.elick.com.hotshot.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.GlideUtils;

public class LiVideoMultiRVAdapter extends BaseMultiItemQuickAdapter<VideoBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public LiVideoMultiRVAdapter(List<VideoBean> data) {
        super(data);
        addItemType(VideoBean.TYPE_HEAD, R.layout.video_list_item_lsp_header);
        addItemType(VideoBean.TYPE_NORMOL, R.layout.video_list_item_lsp_normal);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        switch (item.getItemType()) {
            case VideoBean.TYPE_HEAD:
                GlideUtils.loadRoundImage(mContext, item.getCover(), helper.getView(R.id.lsp_item_image_header));
                helper.setText(R.id.lsp_item_title_header, item.getTitle());
                break;
            case VideoBean.TYPE_NORMOL:
                GlideUtils.loadRoundImage(mContext, item.getCover(), helper.getView(R.id.lsp_item_image_normal));
                helper.setText(R.id.lsp_item_title_normal, item.getTitle());
                break;
        }
    }
}
