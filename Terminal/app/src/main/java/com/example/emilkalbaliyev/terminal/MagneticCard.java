package com.example.emilkalbaliyev.terminal;

/**
 * Created by EmilKelbali on 3/16/18.
 */

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class MagneticCard {


    public static synchronized void open() throws Exception {

    }

    public static synchronized void open(Context context) throws Exception {

    }

    public static synchronized void close() {

    }

    public static synchronized String[] check(int timeout) throws Exception {
        Thread.sleep(timeout);
        int x = new Random().nextInt() % 30;
        if (x == 0) {
            return new String[]{"%B7777222288881111^Emil/K.^99011200000000000000**XXX******?*", "", ""};
        }
        throw new Exception();
    }

    private static String[] ParseData(int size, byte[] data) throws Exception {
        String[] result = new String[3];
        int pos = 0;
        int len = data[pos];
        if (len == 0) {
            result[0] = "";
        } else {
            try {
                result[0] = new String(data, pos + 1, len, "GBK");
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
                throw new Exception();
            }
        }

        pos = data[0] + 1;
        len = data[pos];
        if (len == 0) {
            result[1] = "";
        } else {
            try {
                result[1] = new String(data, pos + 1, len, "GBK");
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
                throw new Exception();
            }
        }

        pos += data[pos] + 1;
        len = data[pos];
        if (len == 0) {
            result[2] = "";
        } else {
            try {
                result[2] = new String(data, pos + 1, len, "GBK");
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
                throw new Exception();
            }
        }

        return result;
    }

    public static int startReading() {
        return 0;
    }

    // private static int open_msr(){}

    // private static native void close_msr();

    //  private static native int check_msr(int var0, byte[] var1);

    // private static native int ready_for_read();
}
