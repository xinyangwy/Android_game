package com.example.exp3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
public class MainActivity2 extends AppCompatActivity {
    private double x,y;
    boolean canmove=false;
    boolean canmove2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FrameLayout frameLayout =  findViewById(R.id.activity_main2);

        AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity2.this);
        final RabbitView rabbitView = new RabbitView(this);
        final DogView dogView = new DogView(this);
        dogView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //记录按下位置,若与图片差距20000，则向下传递  、按下时记录初始位置,若与图片差距过大，则不是点的他，向下传递、（(第一个)手指 初次接触到屏幕 时触发）
                        if (Math.pow(( x - dogView.bitmapTX-150), 2) + Math.pow(( y - dogView.bitmapTY-150), 2) > 20000)//判定未点击，直接返回，并传递下去
                        {
                            return true;
                        }
                        canmove2=true;   //选中该图片，移动（点击到了该图片，可以移动）
                        break;
                    case MotionEvent.ACTION_MOVE://手指 在屏幕上滑动 时触发，会多次触发
                        if(canmove2) {
                            dogView.bitmapTX = event.getX()-150;
                            dogView.bitmapTY = event.getY()-150;
                            dogView.invalidate();  //重绘老虎组件
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP://（最后一个）手指 离开屏幕 时触发  、抬起来的时候恢复标志位
                        if (canmove2)   //NO
                        {
                            canmove2=false;
                            break;
                        }
                }
                return true;
            }
        });
        frameLayout.addView(dogView);
        rabbitView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //记录按下位置,若与图片差距20000，则向下传递
                        x = event.getX();
                        y = event.getY();
                        if (Math.pow((x - rabbitView.bitmapRX-150), 2) + Math.pow((y - rabbitView.bitmapRY-150), 2) > 20000)//判定未点击，直接返回，并传递下去
                        {
                            return false;
                        }
                        canmove=true;   //选中该图片，移动
                    case MotionEvent.ACTION_MOVE:
                        if(canmove) {
                            rabbitView.bitmapRX = event.getX()-150;
                            rabbitView.bitmapRY = event.getY()-150;
                            rabbitView.invalidate();  ////重绘兔子组件
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:   //松开时恢复标志位
                        if (canmove)
                        {
                            canmove=false;
                            break;
                        }
                }
                return false;
            }
        });
        frameLayout.addView(rabbitView);
    }
}
