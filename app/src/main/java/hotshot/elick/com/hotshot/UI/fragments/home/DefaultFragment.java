package hotshot.elick.com.hotshot.UI.fragments.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.player.PlayerActivity;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.adapter.DYRVAdapter;
import hotshot.elick.com.hotshot.adapter.OEMultiRVAdapter;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.DensityUtil;
import hotshot.elick.com.hotshot.utils.GlideUtils;
import hotshot.elick.com.hotshot.widget.StatusLayout;

public class DefaultFragment extends BaseFragment<DefaultFragmentPresenter> implements DefaultFragmentContract.View {
    @BindView(R.id.default_video_fragment_date)
    TextView defaultVideoFragmentDate;
    @BindView(R.id.default_video_fragment_horizon_rv)
    RecyclerView defaultVideoFragmentHorizonRv;
    @BindView(R.id.default_video_fragment_card_rv)
    RecyclerView defaultVideoFragmentCardRv;
    @BindView(R.id.default_video_fragment_vertical_rv)
    RecyclerView defaultVideoFragmentVerticalRv;
    @BindView(R.id.convenient_banner)
    ConvenientBanner convenientBanner;
    private OEMultiRVAdapter oeMultiRVAdapter;
    private BaseQuickAdapter<VideoBean, BaseViewHolder> oeAdapter;
    private DYRVAdapter dyAdapter;
    private BaseQuickAdapter<VideoBean, BaseViewHolder> lspAdapter;
    private List<VideoBean> bannerVideoList = new ArrayList<>();
    private List<VideoBean> oeVideoList = new ArrayList<>();
    private List<VideoBean> dyVideoList = new ArrayList<>();
    private List<VideoBean> lspVideoList = new ArrayList<>();

    @Override
    protected DefaultFragmentPresenter setPresenter() {
        return new DefaultFragmentPresenter(this);
    }

    @Override
    protected void initView() {
//        defaultVideoFragmentDate.setText("2019/03/07");
        initBanner();
        initOE();
        initDY();
        initLSP();
    }

    private void initBanner() {
        for (int i = 0; i < 5; i++) {
            VideoBean videoBean = new VideoBean();
            videoBean.setPlayUrl("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=151280&resourceType=video&editionType=default&source=aliyun");
            videoBean.setDescription("i have a pen");
            videoBean.setTitle("i have an apple");
            videoBean.setCover("http://img.kaiyanapp.com/7d483bb5fb6b74eb579b9ca06cd96839.jpeg?imageMogr2/quality/60/format/jpg");
            videoBean.setDate("1551315600000");
            videoBean.setDuration("237");
            bannerVideoList.add(videoBean);
        }
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetworkImageLoader createHolder(View itemView) {
                return new NetworkImageLoader(itemView, context);
            }

            @Override
            public int getLayoutId() {
                return R.layout.banner_item_layout;
            }
        }, bannerVideoList);
        convenientBanner.setPageIndicator(new int[]{R.drawable.banner_bottom_indicator_selected, R.drawable.banner_bottom_indicator_select});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    private void initOE() {
        oeAdapter = new BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.default_fragment_oe_rv_item, oeVideoList) {
            @Override
            protected void convert(BaseViewHolder helper, VideoBean item) {
                GlideUtils.loadRoundImage(context, item.getCover(), helper.getView(R.id.default_fragment_oe_item_image));
                helper.setText(R.id.default_fragment_oe_item_title, item.getTitle());
            }
        };

        defaultVideoFragmentHorizonRv.setAdapter(oeAdapter);
        defaultVideoFragmentHorizonRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //listener
        oeAdapter.setOnItemClickListener((adapter, view, position) -> {
            PlayerActivity.startUp(context, "oe", (VideoBean) adapter.getData().get(position));
            getActivity().overridePendingTransition(R.anim.activity_start_from_bottom_to_top_anim, 0);
        });
    }

    private void initDY() {
        dyAdapter = new DYRVAdapter(R.layout.videos_list_item_dy, dyVideoList);
        TextView textView = new TextView(context);
        textView.setText("-没有更多内容-");
        textView.setGravity(Gravity.CENTER);
        textView.setMinHeight(DensityUtil.dip2px(context, 30));
        dyAdapter.setFooterView(textView, 2);
        defaultVideoFragmentCardRv.setAdapter(dyAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (dyAdapter.getItemViewType(i) == DYRVAdapter.FOOTER_VIEW) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        defaultVideoFragmentCardRv.setLayoutManager(gridLayoutManager);
        //listener
        dyAdapter.setOnItemClickListener((adapter, view, position) -> {
            PlayerActivity.startUp(context, "dy", (VideoBean) adapter.getData().get(position));
            getActivity().overridePendingTransition(R.anim.activity_start_from_bottom_to_top_anim, 0);
        });
    }

    private void initLSP() {
        lspAdapter = new BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.video_list_item_lsp_header, lspVideoList) {
            @Override
            protected void convert(BaseViewHolder helper, VideoBean item) {
                GlideUtils.loadRoundImage(context, item.getCover(), helper.getView(R.id.lsp_item_image_header));
                helper.setText(R.id.lsp_item_title_header, item.getTitle());
            }
        };
        defaultVideoFragmentVerticalRv.setAdapter(lspAdapter);
        defaultVideoFragmentVerticalRv.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.default_fragment_list_divider));
        defaultVideoFragmentVerticalRv.addItemDecoration(dividerItemDecoration);
        //listener
        lspAdapter.setOnItemClickListener((adapter, view, position) -> {
            PlayerActivity.startUp(context, "lsp", (VideoBean) adapter.getData().get(position));
            getActivity().overridePendingTransition(R.anim.activity_start_from_bottom_to_top_anim, 0);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!convenientBanner.isTurning()) {
            convenientBanner.startTurning(3000);
        }
    }

    @Override
    public void updateOE(List<VideoBean> list) {
        oeVideoList.clear();
        oeVideoList.addAll(list);
        oeAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateDY(List<VideoBean> list) {
        dyVideoList.clear();
        dyVideoList.addAll(list);
        dyAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateLSP(List<VideoBean> list) {
        lspVideoList.clear();
        lspVideoList.addAll(list);
        lspAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateBanner(List<VideoBean> list) {

    }

    static class NetworkImageLoader extends Holder<VideoBean> {
        private ImageView imageView;
        private TextView textView;
        private Context context;

        public NetworkImageLoader(View itemView, Context context) {
            super(itemView);
            this.context = context;
        }

        @Override
        protected void initView(View itemView) {
            this.imageView = itemView.findViewById(R.id.banner_image_view);
            this.textView = itemView.findViewById(R.id.banner_text_view);
        }

        @Override
        public void updateUI(VideoBean data) {
            Glide.with(context).load(data.getCover()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()).into(imageView);
            textView.setText(data.getTitle());
        }
    }

    @Override
    protected void startLoadData() {
        basePresenter.getOERandom();
        basePresenter.getDYRandom();
        basePresenter.getLSPRandom();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.default_video_fragment_layout;
    }


    @Override
    public void onRetry() {
        startLoadData();
    }


}
