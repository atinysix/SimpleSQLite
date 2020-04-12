package com.yuntianhe.simplesqlite.library;

import android.os.Handler;

/**
 * desc:
 * author: daiwj on 2020-04-12 19:23
 */
public class AsyncExecutor {

    private static Handler mHandler = new Handler();

    public static final void runBackground(Runnable async) {
        new InnerThread(async).start();
    }

    private static class InnerThread extends Thread {

        InnerThread(Runnable r) {
            super(r);
            setName("SimpleSQLite-Thread");
        }

    }

    public static class Task<T> implements Runnable {

        @Override
        public void run() {

        }

        public void post(final AsyncCallback<T> callback, final T result) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onResult(result);
                }
            });
        }
    }
}