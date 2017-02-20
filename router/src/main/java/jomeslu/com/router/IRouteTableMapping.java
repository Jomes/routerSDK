package jomeslu.com.router;

import android.app.Activity;

import java.util.Map;

/**
 * Created by jomeslu on 17-1-14.
 */
public interface IRouteTableMapping {
    public void operaRouterTable(Map<String, Class<? extends Activity>> map);
}
