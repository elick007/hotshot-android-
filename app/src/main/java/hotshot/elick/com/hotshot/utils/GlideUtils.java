package hotshot.elick.com.hotshot.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.widget.GlideBitmapTransform;
import hotshot.elick.com.hotshot.widget.GlideRoundTransform;

public class GlideUtils {
    private static RequestOptions requestOptions = new RequestOptions()
            .transform(new GlideBitmapTransform(4))
            .placeholder(R.drawable.image_palce_holder)
            .diskCacheStrategy(DiskCacheStrategy.ALL);
    private static RequestOptions borderImageOption = new RequestOptions()
           .transform(new GlideBitmapTransform(4,0))
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public static void loadRoundImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }

    public static void loadOutLineImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).apply(borderImageOption).into(imageView);
    }
    public static void loadCenterCropImage(Context context,String url,ImageView imageView){
        Glide.with(context).load(url).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView);
    }
}
