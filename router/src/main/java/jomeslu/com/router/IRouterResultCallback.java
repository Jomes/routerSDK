package jomeslu.com.router;

import android.net.Uri;

/**
 * Created by Jomelu on 2017/2/3.
 */
public interface IRouterResultCallback {

    void succeed(Uri uri);

    void failure(Uri uri, String message);
}
