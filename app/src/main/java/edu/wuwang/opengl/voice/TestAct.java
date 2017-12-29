package edu.wuwang.opengl.voice;

import android.app.Activity;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

import edu.wuwang.opengl.R;

/**
 * Created by Administrator on 2017/12/29.
 */

public class TestAct extends Activity implements TextToSpeech.OnInitListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testact);
         textToSpeech=new TextToSpeech(this,this);

    }
    TextToSpeech textToSpeech;
    public void onClickabc(View view){
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            textToSpeech.setPitch(0.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.speak("你是谁",
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }
}
