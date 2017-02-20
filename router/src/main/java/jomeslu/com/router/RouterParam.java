package jomeslu.com.router;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by jomeslu on 17-1-14.
 */
public class RouterParam {
    private Uri uri;
    private Bundle mBundle;
    private Class<? extends Activity> clazz;
    private IRouteInterceptor IRouteInterceptor;
    private IRouterResultCallback mRouterResultCallback;

    public IRouteInterceptor getIRouteInterceptor() {
        return IRouteInterceptor;
    }

    public void setIRouteInterceptor(IRouteInterceptor IRouteInterceptor) {
        this.IRouteInterceptor = IRouteInterceptor;
    }

    public Class<? extends Activity> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends Activity> clazz) {
        this.clazz = clazz;
    }

    public RouterParam(Uri uri) {

        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bundle getmBundle() {
        return mBundle;
    }

    public void setmBundle(Bundle mBundle) {
        this.mBundle = mBundle;
    }

    public IRouterResultCallback getmRouterResultCallback() {
        return mRouterResultCallback;
    }

    public void setmRouterResultCallback(IRouterResultCallback mRouterResultCallback) {
        this.mRouterResultCallback = mRouterResultCallback;
    }
}
