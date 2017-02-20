package jomeslu.com.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by jomeslu on 17-1-14.
 */
public class RouterEngine {
    private volatile static RouterEngine instance = null;
    private static String TAG = "top_RouterEngine";
    private RouterParam param;
    private RouteTableHandle mRouteTableHandle = new RouteTableHandle();


    public static RouterEngine getInstance() {
        if (instance == null) {
            synchronized (RouterEngine.class) {
                if (instance == null) {
                    instance = new RouterEngine();
                }
            }
        }
        return instance;
    }

    public void initRouteTable(IRouteTableMapping routeTable) {
        mRouteTableHandle.initRouteTable(routeTable);
    }


    public RouterEngine build(Uri uri) {
        if (TextUtils.isEmpty(uri.toString())) {
            return this;
        }
        param = new RouterParam(uri);

        return this;
    }

    public void start(Context context) {
        Intent intent = getIntent(context);
        if (intent == null) {
            return;
        }
        context.startActivity(intent);
    }

    public void startForResult(Activity activity, int requestCode) {
        Intent intent = getIntent(activity);
        if (intent == null) {
            return;
        }
        activity.startActivityForResult(intent, requestCode);

    }

    private Intent getIntent(Context context) {

        MatchParse mathchParse = new MatchParse(mRouteTableHandle, context);
        int mathchCode = mathchParse.mathch(param);
        switch (mathchCode) {
            case RuleConstant.MATHCH_SUCCESS:
                if (param.getmRouterResultCallback() != null) {
                    param.getmRouterResultCallback().succeed(param.getUri());
                }
                if (param.getIRouteInterceptor() != null && param.getIRouteInterceptor().interceptor()) {
                    return null;
                }
                Intent intent = new Intent(context, param.getClazz());
                intent.putExtras(param.getmBundle());
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                return intent;
            case RuleConstant.MATHCH_OTHER:
                if (param.getmRouterResultCallback() != null) {
                    param.getmRouterResultCallback().succeed(param.getUri());
                }
                if (param.getIRouteInterceptor() != null && param.getIRouteInterceptor().interceptor()) {
                    return null;
                }
                //跳到其他应用的
                Intent intentOther = new Intent(Intent.ACTION_VIEW, param.getUri());
                if (!(context instanceof Activity)) {
                    intentOther.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                return intentOther;

            case RuleConstant.MATHCH_ERROR_BUNDLE:
                if (param.getmRouterResultCallback() != null) {
                    param.getmRouterResultCallback().failure(param.getUri(), "pase error ！");
                }
                break;
            case RuleConstant.MATHCH_ERROR_URL:
                if (param.getmRouterResultCallback() != null) {
                    param.getmRouterResultCallback().failure(param.getUri(), "mathch url error,input correct URL ！");
                }
                break;
        }
        return null;

    }

    public RouterEngine setRouterResultCallback(IRouterResultCallback mRouterResultCallback) {
        param.setmRouterResultCallback(mRouterResultCallback);
        return this;
    }

    public RouterEngine setIRouteInterceptor(IRouteInterceptor mIRouteInterceptor) {
        param.setIRouteInterceptor(mIRouteInterceptor);
        return this;
    }


}
