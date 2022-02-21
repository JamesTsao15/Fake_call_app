# Fake_call_app(假來電App)

動作說明:

1.新增假來電者資訊:

可新增照片姓名電話資訊，也可利用輸入假來電者姓名此刪除此caller資料

          ![image](https://github.com/JamesTsao15/Fake_call_app/blob/master/gif%20pictrue/add_caller.gif)




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
     
 遇到問題6:
     
 thread如何停止:
     
     thread.interrupt()
     
     interrupt後出現的問題:
     
     1.在mainloop while迴圈加上判斷條件

     2.在thread內加try  catch來解決crash
     
遇到問題7:
     
mediarecorder crash:
     
Caused by: android.system.ErrnoException: open failed: EISDIR (Is a directory)

     造成原因:
     
     檔名未進入變數，recorder就prepare了造成crash
     
     解決方案:
     
     利用Thread監聽變數狀態及按鈕狀態，並利用上述中開始即結束方法完成thread

遇到問題8:

隱藏status bar解決方案:
     
資料來源:https://stackoverflow.com/questions/42968600/how-to-hide-status-bar-in-android-in-just-one-activity/42968869#42968869?newreg=55ab1411effc4ed08764264c9e5beedc
     
     Java code:
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add Below line to hide 
        getSupportActionBar().hide();
    }
     
遇到問題9:
     
mp3檔儲存位置(推薦):
     
資料來源:https://stackoverflow.com/questions/10959554/right-place-for-putting-mp3-files-in-an-android-project
     
      Java code:
      AssetFileDescriptor afd = getAssets().openFd("AudioFile.mp3");
      MediaPlayer player = new MediaPlayer();
      player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
      player.prepare();
      player.start();
     
遇到問題10:
     
 如何用聽筒播放音訊:
     
     val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
     audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
     audioManager.isSpeakerphoneOn=false
  
  參考網址:
     
https://blog.csdn.net/u012440207/article/details/121725566

https://www.itread01.com/content/1546908316.html
     
