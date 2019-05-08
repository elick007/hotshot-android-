package hotshot.elick.com.hotshot.UI.act.player;

import android.os.Environment;

import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.liulishuo.filedownloader.util.FileDownloadHelper;

import java.io.File;

public class VideoCache {
    private static SimpleCache sDownloadCache;

    public static SimpleCache getInstance() {
        if (sDownloadCache == null) sDownloadCache = new SimpleCache(new File(getCacheDir(), "exoCache"), new NoOpCacheEvictor());
        return sDownloadCache;
    }

    public static String getCacheDir() {
        if (FileDownloadHelper.getAppContext().getExternalCacheDir() == null) {
            return Environment.getDownloadCacheDirectory().getAbsolutePath();
        } else {
            //noinspection ConstantConditions
            return FileDownloadHelper.getAppContext().getExternalCacheDir().getAbsolutePath();
        }
    }
}
