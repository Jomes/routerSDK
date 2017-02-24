
#RouterSDK
RouterSDK is an excellent Router Framwork ,it is easy to integrate in your project. You can start an application through the web page by the custom parameters.  In addition, it can also provides a number of useful features such as dynamic routing configuration, jump animation, task station, jump pretreatment, etc.

* [中文文档](http://www.jianshu.com/p/c7524b0125df)

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
Definition Of type
- Scheme:suggest to define application name which router to use
- Host: suggest to define page where to go, such activity
- path : Transfer parameter,customer type

|   key format |  {i:ikey}  | {f:key} | {l:key}  | {d:key}    |  {s:key}  | {b:key} |
|   :-------:  |:--------:  | :------:| :------: | :--------: |  :-------:| :----:  |
|   type       |   integer  |  float  |   long   |   double   |   string  | boolean |

eg :From A page to B page use url :jomeslu://www?{i:id}=168&{s:jomeslu}=jomeslu

A page
```
 Router.build("jomeslu://www?{i:id}=168&{s:jomeslu}=jomeslu").setIRouteInterceptor(new IRouteInterceptor() {
        @Override
        public boolean interceptor() {
           Router.build("jomeslu://loginactivity?{i:id}=168&{s:jomeslu}=jomeslu").start(MainActivity.this);
           Toast.makeText(MainActivity.this, "login...", Toast.LENGTH_LONG).show();
            return true;
        }
 }).start(MainActivity.this);;
```
B Page Receive parameter as normal
```
  String jomeslu = getIntent().getStringExtra("jomeslu");
  int id = getIntent().getIntExtra("id", -1);
```


## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

