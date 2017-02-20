
#RouterSDK
RouterSDK is excellent Router framwork ,it is easy to  integrate in your project.
You can start the application through the web page by the custom parameters, but also provides a number of useful features such as dynamic routing configuration, jump animation, task station, jump pretreatment, etc..

## Screenshot
![route](./gif/routersdk.gif)

## Build

Step 1. Add the JitPack repository to your build file

add the JitPack maven to your project in root  build.gradle

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

```
Step 2. Add module dependency build.gradle

```
	dependencies {
		 compile 'com.github.Jomes:routerSDK:v1.0.3-alpha'
	}

```
That's it! 

## How to use

init RouteTable in application
```
       Router.initRouteTable(new IRouteTableMapping() {
            @Override
            public void operaRouterTable(Map<String, Class<? extends Activity>> map) {
                map.put("jomeslu://www", OneActivity.class);
                map.put("jomeslu://loginactivity", LoginActivity.class);
            }
        });

```
Router Jump page
```
       Router.build("http://androidblog.cn/index.php/Source").start(MainActivity.this);

```

