package jomeslu.com.router;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jomeslu on 17-1-14.
 */
public class RouteTableHandle {
    private Map<String, Class<? extends Activity>> routerTable = new HashMap<>();

    public void initRouteTable(IRouteTableMapping routeTable) {
        if (routerTable != null) {
            routeTable.operaRouterTable(routerTable);
        }
    }

    public void addRouterTable(String targerStr, Class<? extends Activity> clazz) {
        routerTable.put(targerStr, clazz);
    }

    public Map<String, Class<? extends Activity>> getRouterTable() {
        return routerTable;
    }
}
