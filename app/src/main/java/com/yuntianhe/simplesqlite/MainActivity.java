package com.yuntianhe.simplesqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.daiwj.database.model.GIMMessage;
import com.example.daiwj.database.table.GIMMessageTable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                GIMMessageTable table = new GIMMessageTable();
                long start = System.currentTimeMillis();
                List<GIMMessage> messageList = new ArrayList<>();
                for (int i = 0; i < 10000; i++) {
                    GIMMessage message = new GIMMessage();
                    message.setConversationId(10000 + i);
                    message.setUserId(1000000 + i);
                    message.setMessage("开始计数message" + i);
                    message.setType(1);
                    message.setTimeMillis(System.currentTimeMillis());
                    messageList.add(message);
                }
                table.addAll(messageList);
                long end = System.currentTimeMillis();
                Log.w("Database", "time: " + (end - start) / 1000 + "s");
            }
        }.start();

//        GIMMessageTable table = new GIMMessageTable();
////        List<GIMMessage> list = table.queryAll(null);
////        for (GIMMessage message : list) {
////            Log.w("QueryResult", "list: " + message.toString());
////        }
////
////        List<GIMMessage> list2 = table.rawQueryAll(RawQuery.from("select * from " + GIMMessageTable.TABLE_NAME + " where id in(1, 2, 3, 4);"));
////        for (GIMMessage message : list2) {
////            Log.w("QueryResult", "list2: " + message.toString());
////        }
////
////        GIMMessage message = table.rawQuery(RawQuery.from("select * from " + GIMMessageTable.TABLE_NAME + " where id = 25;"));
////        Log.w("QueryResult", message.toString());
////
////        GIMMessage message2 = table.query(50l);
////        Log.w("QueryResult", message2.toString());
//
//        Query query = Query.from(GIMMessageTable.TABLE_NAME)
//                .between("id", 50L, 90L)
//                .and()
//                .gt("conversationId", 10075).log();
//        List<GIMMessage> list = table.getDatabaseOpenHelper().queryAll(query, new GIMMessage());
//        for (GIMMessage message : list) {
//            Log.w("QueryResult", "list: " + message.toString());
//        }
//
//        GIMMessage message2 = table.query(50l);
//        Log.w("QueryResult", message2.toString());

    }

}


