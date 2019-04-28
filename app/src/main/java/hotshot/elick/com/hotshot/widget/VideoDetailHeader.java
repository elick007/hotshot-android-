package hotshot.elick.com.hotshot.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.entity.VideoBean;

public class VideoDetailHeader extends LinearLayout {
    @BindView(R.id.video_detail_title)
    TextView videoDetailTitle;
    @BindView(R.id.video_detail_date)
    TextView videoDetailDate;
    @BindView(R.id.video_detail_content)
    TextView videoDetailContent;
    private VideoBean videoBean;
    public VideoDetailHeader(Context context, VideoBean videoBean) {
        this(context,null,videoBean);
    }

    public VideoDetailHeader(Context context, @Nullable AttributeSet attrs,VideoBean videoBean) {
        super(context,attrs);
        this.videoBean=videoBean;
        LayoutInflater.from(context).inflate(R.layout.video_detail_merge, this);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        videoDetailTitle.setText(videoBean.getTitle());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(Long.parseLong(videoBean.getDate()));
        videoDetailDate.setText(simpleDateFormat.format(date));
        videoDetailContent.setText(videoBean.getDescription());
    }
}
