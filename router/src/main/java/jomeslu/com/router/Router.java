package jomeslu.com.router;

import android.net.Uri;

/**
 * 三方调用API
 * Created by Jomelu on 2017/2/3.
 */
public class Router {
    public static RouterEngine build(String path) {
        return build(path == null ? null : Uri.parse(path));
    }

    public static RouterEngine build(Uri uri) {
        return RouterEngine.getInstance().build(uri);
    }

    public static void initRouteTable(IRouteTableMapping routeTable) {
        RouterEngine.getInstance().initRouteTable(routeTable);
    }

}
