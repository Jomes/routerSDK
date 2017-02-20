package jomeslu.com.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * core
 * Created by jomeslu on 17-1-14.
 */
public class MatchParse implements IMatch {
    private static final String TAG = "top_MathchParse";
    private RouteTableHandle mRouteTableHandle;
    private Context context;

    public MatchParse(RouteTableHandle mRouteTableHandle,Context context) {
        this.mRouteTableHandle = mRouteTableHandle;
        this.context = context;
    }

    @Override
    public int mathch(RouterParam mRouterParam) {

        if (matchPath(mRouterParam.getUri(), mRouterParam) && mRouterParam != null) {
            Bundle bundle = null;
            try {
                bundle = buildBundle(mRouterParam.getUri());
                mRouterParam.setmBundle(bundle);
                return RuleConstant.MATHCH_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return RuleConstant.MATHCH_ERROR_BUNDLE;
            }

        } else if (isIntentAvailable(getIntent(mRouterParam.getUri().toString()))) {
            return RuleConstant.MATHCH_OTHER;
        }
        return RuleConstant.MATHCH_ERROR_URL;
    }

    private boolean matchPath(Uri uri, RouterParam mRouterParam) {
        if (mRouteTableHandle == null) return false;
        Map<String, Class<? extends Activity>> routerTable = mRouteTableHandle.getRouterTable();
        for (String path : routerTable.keySet()) {
            if (uri.toString().endsWith(path)) {
                mRouterParam.setClazz(routerTable.get(path));
                return true;
            }
            Uri route = Uri.parse(path);
            if ((uri.isAbsolute() && route.isAbsolute())) {
                if (!uri.getScheme().equals(route.getScheme())) {
                    continue;
                }
                if (TextUtils.isEmpty(uri.getAuthority()) && TextUtils.isEmpty(route.getAuthority())) {
                    mRouterParam.setClazz(routerTable.get(path));
                    return true;
                }
                if (!TextUtils.isEmpty(uri.getAuthority()) && !TextUtils.isEmpty(route.getAuthority())
                        && uri.getAuthority().equals(route.getAuthority())) {
                    mRouterParam.setClazz(routerTable.get(path));
                    return true;
                }
            }

        }

        return false;
    }

    private Bundle buildBundle(Uri uri) throws Exception {

        Bundle bundle = new Bundle();
        HashMap<String, String> parameters = getParametersByPath(uri);
        Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            setKeyValueBundle(key, value, bundle);
        }
        return bundle;
    }


    private void setKeyValueBundle(String key, String value, Bundle bundle) throws Exception {
        //符合自定义的规则 {s:te}
        if (!TextUtils.isEmpty(key) && key.startsWith(RuleConstant.PARAM_SPIT_LEFT) && key.endsWith(RuleConstant.PARAM_SPIT_RIGHT) &&
                key.contains(RuleConstant.PARAM_SPIT_SIGN)) {

            String realKey = key.substring(key.indexOf(RuleConstant.PARAM_SPIT_SIGN) + 1, key.lastIndexOf(RuleConstant.PARAM_SPIT_RIGHT));

            String type = key.substring(1, key.indexOf(RuleConstant.PARAM_SPIT_SIGN));
            switch (type.toUpperCase()) {

                case RuleConstant.PARAM_B:
                    Boolean aBoolean = Boolean.valueOf(value);
                    bundle.putBoolean(realKey, aBoolean);
                    break;
                case RuleConstant.PARAM_D:
                    bundle.putDouble(realKey, Double.valueOf(value));
                    break;
                case RuleConstant.PARAM_F:
                    bundle.putFloat(realKey, Float.valueOf(value));
                    break;
                case RuleConstant.PARAM_I:
                    bundle.putInt(realKey, Integer.valueOf(value));
                    break;
                case RuleConstant.PARAM_L:
                    bundle.putLong(realKey, Long.valueOf(value));
                    break;
                case RuleConstant.PARAM_S:
                    bundle.putString(realKey, value);
                    break;
                case RuleConstant.PARAM_O:

                    //bundle.putParcelable(realKey,);
                    break;
            }

        } else if (!TextUtils.isEmpty(key)) {
            //默认是字符串
            bundle.putString(key, value);
        }
    }

    /**
     * 参数集合
     *
     * @param uri
     * @return
     */
    public HashMap<String, String> getParameters(Uri uri) {
        HashMap<String, String> parameters = new HashMap<>();
        try {
            Set<String> keys = uri.getQueryParameterNames();
            for (String key : keys) {
                parameters.put(key, uri.getQueryParameter(key));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return parameters;
    }

    public HashMap<String, String> getParametersByPath(Uri uri) {
        HashMap<String, String> parameters = new HashMap<>();
        String query = uri.getQuery();
        if (query != null && !query.isEmpty()) {
            String[] entries = query.split("&");
            for (String entry : entries) {
                if (entry.contains("=")) {
                    String[] kv = entry.split("=");
                    if (kv.length > 1) {
                        parameters.put(kv[0], kv[1]);
                    }
                }
            }
        }
        return parameters;
    }

    public boolean isIntentAvailable(Intent intent) {
        if (intent == null) return false;
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public Intent getIntent(String url) {
        try {
            return Intent.parseUri(url, 0);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
