# Fake_call_app



logcat 錯誤訊息:
2022-02-07 14:08:56.789 22040-22040/com.example.fake_call2 E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.example.fake_call2, PID: 22040
    java.lang.RuntimeException: Unable to start activity ComponentInfo{com.example.fake_call2/com.example.fake_call2.MainActivity}: android.view.InflateException: Binary XML file line #16 in com.example.fake_call2:layout/activity_main: Binary XML file line #16 in com.example.fake_call2:layout/activity_main: Error inflating class com.example.fake_call2.caller_information_view
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3800)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3976)
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:85)
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2315)
        at android.os.Handler.dispatchMessage(Handler.java:106)
        at android.os.Looper.loop(Looper.java:246)
        at android.app.ActivityThread.main(ActivityThread.java:8544)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:602)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1130)
     Caused by: android.view.InflateException: Binary XML file line #16 in com.example.fake_call2:layout/activity_main: Binary XML file line #16 in com.example.fake_call2:layout/activity_main: Error inflating class com.example.fake_call2.caller_information_view
     Caused by: android.view.InflateException: Binary XML file line #16 in com.example.fake_call2:layout/activity_main: Error inflating class com.example.fake_call2.caller_information_view
     Caused by: java.lang.NoSuchMethodException: com.example.fake_call2.caller_information_view.<init> [class android.content.Context, interface android.util.AttributeSet]
        at java.lang.Class.getConstructor0(Class.java:2332)
        at java.lang.Class.getConstructor(Class.java:1728)
        at android.view.LayoutInflater.createView(LayoutInflater.java:822)
        at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:1004)
        at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:959)
        at android.view.LayoutInflater.rInflate(LayoutInflater.java:1121)
        at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:1082)
        at android.view.LayoutInflater.rInflate(LayoutInflater.java:1124)
        at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:1082)
        at android.view.LayoutInflater.inflate(LayoutInflater.java:680)
        at android.view.LayoutInflater.inflate(LayoutInflater.java:532)
        at android.view.LayoutInflater.inflate(LayoutInflater.java:479)
        at androidx.appcompat.app.AppCompatDelegateImpl.setContentView(AppCompatDelegateImpl.java:706)
        at androidx.appcompat.app.AppCompatActivity.setContentView(AppCompatActivity.java:195)
        at com.example.fake_call2.MainActivity.onCreate(MainActivity.kt:11)
        at android.app.Activity.performCreate(Activity.java:8198)
        at android.app.Activity.performCreate(Activity.java:8182)
        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1309)
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3773)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3976)
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:85)
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2315)
        at android.os.Handler.dispatchMessage(Handler.java:106)
        at android.os.Looper.loop(Looper.java:246)
        at android.app.ActivityThread.main(ActivityThread.java:8544)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:602)
2022-02-07 14:08:56.790 22040-22040/com.example.fake_call2 E/AndroidRuntime:     at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1130)
