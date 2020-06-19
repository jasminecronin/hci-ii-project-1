package com.example.project1;

//Need to change rectangle to circle

import android.graphics.RectF;

public class Key {
    public int sound;
//    public RectF rect;
    public float x;
    public float y;
    public float radius;
    public boolean down;

    public Key (float x, float y, float radius, int sound) {
        this.sound = sound;
//        this.rect = rect;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
}
