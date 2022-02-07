# Fake_call_app
錯誤好像是:
Caused by: android.view.InflateException: Binary XML file line #16 in com.example.fake_call2:layout/activity_main: Binary XML file line #16 in com.example.fake_call2:layout/activity_main: Error inflating class com.example.fake_call2.caller_information_view
     Caused by: android.view.InflateException: Binary XML file line #16 in com.example.fake_call2:layout/activity_main: Error inflating class com.example.fake_call2.caller_information_view
     Caused by: java.lang.NoSuchMethodException: com.example.fake_call2.caller_information_view.<init> [class android.content.Context, interface android.util.AttributeSet]
已解決:
     解決方案:https://stackoverflow.com/questions/62969541/crash-when-i-use-custom-view-in-android
