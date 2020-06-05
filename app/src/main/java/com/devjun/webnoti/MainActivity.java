package com.devjun.webnoti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createNotification(View view) { show();}

    private void show() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"default"); // OREO 이상 버전은 channalId 필수 else 필요없음

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("알림 제목");
        builder.setContentText("알림 세부 텍스트");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // FLAG_UPDATE_CURRENT 클릭 내용 업데이트 한다.

        builder.setContentIntent(pendingIntent);    // Notification 클릭시 pending intent의 intent가 실행

        //LargeIcon은 smallIcon처럼 Resource Id를 직접 지정이 아닌 Bitmap 변환이 필요
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(largeIcon);

        // 색 지정
        builder.setColor(Color.RED);

        //알림음 지정
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        // 진동
        // long 배열 준비 [규칙] 0 ms 이후 0.1s, 0.2s, 0.3s
        long[] vibrate = {0, 100, 200, 300};
        builder.setVibrate(vibrate);

        // Notification 클릭시 intent 실행되고 notification 설정
        builder.setAutoCancel(true);

        // ★ OREO 이상에서는 알림매니저에 Chnnal Id를 등록해야 함 ★
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1, builder.build());
    }

    public void removeNotification(View view)
    {
        hide();
    }

    private void hide()
    {
        NotificationManagerCompat.from(this).cancel(1);
    }
}