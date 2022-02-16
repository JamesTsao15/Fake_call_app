# Fake_call_app

專案筆記:

遇到的錯誤1:
com.example.fake_call2.caller_information_view
     Caused by: java.lang.NoSuchMethodException: com.example.fake_call2.caller_information_view.<init> [class android.content.Context, interface android.util.AttributeSet]
     
     已修復(解決方案如下):
     解決方案:https://stackoverflow.com/questions/62969541/crash-when-i-use-custom-view-in-android
遇到問題2:
Arraylist儲存至sharepreference
     
參考影片:
     網址(JAVA範例作法):https://www.youtube.com/watch?v=jcliHGR3CHo&list=PLfosMn46PuFY05waY2ulSZkwG3G7tgLi7&index=19
     
TypeToken in kotlin 用法:
     
     val turnsType = object : TypeToken<List<Turns>>() {}.type
     val turns = Gson().fromJson<List<Turns>>(pref.turns, turnsType)
     
     參考網址:https://stackoverflow.com/questions/33381384/how-to-use-typetoken-generics-with-gson-in-kotlin
遇到問題3:
     
ImageButton背景灰底
     解決方案:https://blog.csdn.net/henan_csdn/article/details/49943561
     
遇到問題4:
     
gridview選擇後外框變色:
     
     方法:
     android:listSelector="你想要的背景轉換檔"
 遇到問題5:
 
  thread用法:
     
     val thread=Thread(Runnable {
            while(條件){
                runOnUiThread{    //在改變UI的thread需用到
                    //須執行的東西
                }
                Thread.sleep(200)
            }
        })

        thread.start()
     
 
 thread如何停止:
     
     thread.interrupt()
     
     interrupt後出現的問題:
     
     1.在mainloop while迴圈加上判斷條件

     2.在thread內加try  catch來解決crash
     
