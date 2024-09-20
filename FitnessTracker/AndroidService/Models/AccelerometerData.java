package com.example.myapplication.Models;

import com.google.gson.Gson;

import java.util.List;

public class AccelerometerData {
    float X_accelerom, Y_accelerom, Z_accelerom;
    String Timestamp;

    public AccelerometerData(float x, float y, float z, String timestamp) {
        this.X_accelerom = x;
        this.Y_accelerom = y;
        this.Z_accelerom = z;
        this.Timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AccelerometerData{" +
                "x=" + X_accelerom +
                ", y=" + Y_accelerom +
                ", z=" + Z_accelerom +
                ", timestamp='" + Timestamp + '\'' +
                '}';
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static String toJson(List<AccelerometerData> dataList) {
        return new Gson().toJson(dataList);
    }
}