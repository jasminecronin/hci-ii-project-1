package com.example.project1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import android.graphics.RectF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class OcarinaView extends View {

    public static final int NB = 8;
    private Paint black, yellow, gray;
    private ArrayList<Key> holes = new ArrayList<>();
    private float x, y, radius;
//    private int keyWidth, height;
    private AudioSoundPlayer soundPlayer;
    private RectF resetRect = new RectF(20, 20, 150, 150);
//    int left, right, top, bottom;

    private String password = "";
    private boolean unlock = false;

    public OcarinaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);
        gray = new Paint();
        gray.setColor(0xAAFFFFFF);
        gray.setStyle(Paint.Style.FILL);
        soundPlayer = new AudioSoundPlayer(context);

//        left = 20;
//        top = getHeight() - 380;
//        right = 860;
//        bottom = getHeight() - 80;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        Change rectangles to circles and figure out where to draw each one, plus some radius
        super.onSizeChanged(w, h, oldw, oldh);
        // Circle 1
        x = ((19 * w) / 20) + 22;
        y = (h / 2) - 45;
        radius = 33;
        holes.add(new Key(x, y, radius, 1));

        //Circle 2
        x = (5 * w) / 6;
        y = (h / 2) - 60;
        radius = 50;
        holes.add(new Key(x, y, radius, 2));

        //Circle 3
        x = ((2 * w) / 3) + 75;
        y = (h / 2) + 35;
        radius = 45;
        holes.add(new Key(x, y, radius, 3));

        //Circle 4
        x = (w / 2) + 110;
        y = ((2 * h) / 3) + 35;
        radius = 50;
        holes.add(new Key(x, y, radius, 4));

        //Circle 5
        x = ((2 * w) / 5) + 37;
        y = (h / 3) - 40;
        radius = 60;
        holes.add(new Key(x, y, radius, 5));

        //Circle 6
        x = (w / 4) + 45;
        y = (h / 3) + 33;
        radius = 65;
        holes.add(new Key(x, y, radius, 6));

        //Circle 7
        x = (w / 10);
        y = (h / 3) + 60;
        radius = 50;
        holes.add(new Key(x, y, radius, 7));

        //Circle 8
        x = ((4 * w) / 7) - 10;
        y = (h / 4) - 45;
        radius = 47;
        holes.add(new Key(x, y, radius, 8));

//        keyWidth = w / NB;
//        height = h;

//        for (int i = 0; i < NB; i++) {
//            int left = i * keyWidth;
//            int right = left + keyWidth;
//
//            if (i == NB - 1) {
//                right = w;
//            }
//
//            RectF rect = new RectF(left, 0, right, h);
//            holes.add(new Key(rect, i + 1));
//        }

        // set radius and x and y values for each hole
        // add the hole with a newly created key to the list

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
//        Change to draw circle
//        Circle takes center x, center y, radius, and  painter
//        So need to associate x y and r with each key rather than a rectangle object
        for (Key k : holes) {
            canvas.drawCircle(k.x, k.y, k.radius, k.down ? yellow : black);
        }

        Bitmap reset = BitmapFactory.decodeResource(getResources(), R.drawable.reset);
        Rect resetSource = new Rect(0, 0, reset.getWidth(), reset.getHeight());
        canvas.drawBitmap(reset, resetSource, resetRect, new Paint());

//        left = 20;
//        int top = getHeight() - 380;
//        right = 860;
//        bottom = getHeight() - 80;
        Rect staff = new Rect(20, getHeight() - 380, 860, getHeight() - 80);
//        Log.d("test", "top: " + top);
        canvas.drawRect(staff, gray);

        for (int i = 0; i < 5; i++) {
            canvas.drawLine(20, getHeight() - 380 + 50 + (i * 50), 860, getHeight() - 380 + 50 + (i * 50), black);
        }

        for (int i = 0; i < password.length(); i++) {
            switch(password.charAt(i)) {
                case '1':
                    canvas.drawCircle(140 + (i * 120), getHeight() - 80, 30, black);
                    break;
                case '2':
                    canvas.drawCircle(140 + (i * 120), getHeight() - 80 - 25, 30, black);
                    break;
                case '3':
                    canvas.drawCircle(140 + (i * 120), getHeight() - 80 - 50, 30, black);
                    break;
                case '4':
                    canvas.drawCircle(140 + (i * 120), getHeight() - 80 - 75, 30, black);
                    break;
                case '5':
                    canvas.drawCircle(140 + (i * 120), getHeight() - 80 - 100, 30, black);
                    break;
                case '6':
                    canvas.drawCircle(140 + (i * 120), getHeight() - 80 - 125, 30, black);
                    break;
                case '7':
                    canvas.drawCircle(140 + (i * 120), getHeight() - 80 - 150, 30, black);
                    break;
                case '8':
                    canvas.drawCircle(140 + (i * 120), getHeight() - 80 - 200, 30, black);
                    break;
            }

        }

        if (unlock) {
//            for (int i = 0; i < 100000000; i++); // delay loop
            Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.screenshot);
            Rect bgSource = new Rect(0, 0, background.getWidth(), background.getHeight());
            Rect bgRect = new Rect(0, 0, getWidth() + 110, getHeight());
            canvas.drawBitmap(background, bgSource, bgRect, new Paint());
        }

//        for (int i = 1; i < NB; i++) {
//            canvas.drawLine(i * keyWidth, 0, i * keyWidth, height, yellow);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;

        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            Key k = keyForCoords(x,y);

            if (k != null) {
                k.down = isDownAction;
            }

            if (resetRect.contains(x, y)) {
                password = "";
                Log.d("test", "password reset");
            }
        }

        for (Key k : holes) {
            if (k.down) {
                if (!soundPlayer.isNotePlaying(k.sound)) {
                    soundPlayer.playNote(k.sound);
                    buildPassword(k.sound);
                    invalidate();
                } else {
                    releaseKey(k);
                }
            } else {
                soundPlayer.stopNote(k.sound);
                releaseKey(k);
            }
        }

        if (action == MotionEvent.ACTION_UP) {
            if (password.length() >= 6) {
                checkPassword();
            }
        }


        return true;
    }

    private void buildPassword(int n) {
        if (password.length() == 0) {
            password += n;
            Log.d("test", "current password 1 = " + password);
        } else if (password.charAt(password.length() - 1) != n) {
            password += n;
            Log.d("test", "current password 2 = " + password);
        }

    }

    private void checkPassword() {
        String hardpass = "786786";
        if (password.equals(hardpass)) {
            Log.d("test", "password accepted");
            unlock = true;
        } else {
            Log.d("test", "password denied");
        }

        password = "";

    }

//    public static void flash(final ImageView v,
//                              final int begin_alpha, final int end_alpha, int time,
//                              final boolean toggleVisibility) {
//
//        if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= android.os.Build.VERSION_CODES.JELLY_BEAN)
//            v.setImageAlpha(begin_alpha);
//        else
//            v.setAlpha(begin_alpha);
//
//        if (toggleVisibility) {
//            if (v.getVisibility() == View.GONE)
//                v.setVisibility(View.VISIBLE);
//            else
//                v.setVisibility(View.GONE);
//        }
//
//        Animation a = new Animation() {
//            @Override
//            protected void applyTransformation(float interpolatedTime,
//                                               Transformation t) {
//                if (interpolatedTime == 1) {
//                    if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= android.os.Build.VERSION_CODES.JELLY_BEAN)
//                        v.setImageAlpha(end_alpha);
//                    else
//                        v.setAlpha(end_alpha);
//
//                    if (toggleVisibility) {
//                        if (v.getVisibility() == View.GONE)
//                            v.setVisibility(View.VISIBLE);
//                        else
//                            v.setVisibility(View.GONE);
//                    }
//                } else {
//                    int new_alpha = (int) (begin_alpha + (interpolatedTime * (end_alpha - begin_alpha)));
//                    if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= android.os.Build.VERSION_CODES.JELLY_BEAN)
//                        v.setImageAlpha(new_alpha);
//                    else
//                        v.setAlpha(new_alpha);
//                    v.requestLayout();
//                }
//            }
//
//            @Override
//            public boolean willChangeBounds() {
//                return true;
//            }
//        };
//
//        a.setDuration(time);
//        v.startAnimation(a);
//    }

    private Key keyForCoords(float x, float y) {
        for (Key k : holes) {
            if (isInside(x, y, k.x, k.y, k.radius)) {
                return k;
            }
        }

        return null;
    }

    private boolean isInside(float x1, float y1, float x2, float y2, float radius) {

        float distanceX = x1 - x2;
        float distanceY = y1 - y2;

        return (distanceX * distanceX) + (distanceY * distanceY) <= (radius * radius);
    }

    private void releaseKey(final Key k) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                k.down = false;
                handler.sendEmptyMessage(0);
            }
        }, 100);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };
}
