package com.yuntianhe.simplesqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yuntianhe.simplesqlite.library.AsyncCallback;
import com.yuntianhe.simplesqlite.library.IOpenHelper;
import com.yuntianhe.simplesqlite.library.Query;
import com.yuntianhe.simplesqlite.processor.DemoTable;
import com.yuntianhe.simplesqlite.processor.SimpleSQLite;
import com.yuntianhe.simplesqlite.processor.TestTable;
import com.yuntianhe.simplesqlite.table.Demo;
import com.yuntianhe.simplesqlite.table.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestTable testTable = new TestTable();
                Test test = new Test();
                test.setPrice(85.5f);
                test.setCount(100);
                test.setLimit(10);
                testTable.add(test);
                Log.w("SimpleSQLite", "insert: " + test.toString());

                DemoTable demoTable = new DemoTable();
                Demo demo = new Demo();
                demo.setId(55);
                demo.setText("我搞定啦");
                demo.setDuration(1800);
                demoTable.add(demo);
                Log.w("SimpleSQLite", "insert: " + demo.toString());
            }
        });

        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestTable testTable = new TestTable();
                Test test = testTable.query(Query.from(testTable.getTableName()));
                Log.w("SimpleSQLite", "query: " + test.toString());

                DemoTable demoTable = new DemoTable();
                Demo demo = demoTable.query(Query.from(demoTable.getTableName()));
                Log.w("SimpleSQLite", "query: " + demo.toString());
            }
        });
    }

    private void initData() {

        TestTable table = new TestTable();
//
//        // 插入数据
//        List<Test> sourceList1 = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
//            Test data = new Test();
//            data.setText("text" + i);
//            data.setDuration(i);
//            sourceList1.add(data);
//        }
//        table.addAll(sourceList1);
//
//        // 异步插入数据
//        List<Test> sourceList2 = new ArrayList<>();
//        for (int i = 101; i <= 200; i++) {
//            Test data = new Test();
//            data.setText("text" + i);
//            data.setDuration(i);
//            sourceList2.add(data);
//        }
//        table.addAllAsync(sourceList2, new AsyncCallback<List<Long>>() {
//            @Override
//            public void onResult(List<Long> result) {
//                Log.w("SQLite", "async insert first: " + result.get(0));
//                Log.w("SQLite", "async insert: " + result.size());
//            }
//        });
//
//        Query sq = Query.from(Test.class);
//        List<Test> sourceList = table.queryAll(sq);
//
//        // 相等
//        Query q1 = Query.from(Test.class).equal(Test._ID, "100");
//        Test data1 = table.query(q1);
//
//        // 相似
//        Query q2 = Query.from(Test.class).like(Test.TEXT, "text50");
//        Test data2 = table.query(q2);
//
//        // 大于 小于
//        Query q3 = Query.from(Test.class)
//                .gt(Test.DURATION, "30")
//                .and()
//                .lt(Test.DURATION, "50");
//        List<Test> list3 = table.queryAll(q3);
//
//        // 异步查询
//        table.queryAllAsync(q3, new AsyncCallback<List<Test>>() {
//            @Override
//            public void onResult(List<Test> result) {
//                List<Test> list3Async = result; // 异步查询
//            }
//        });
//
//        // 范围查询
//        Query q4 = Query.from(Test.class)
//                .between(Test.DURATION, 30, 50);
//        List<Test> list4 = table.queryAll(q4);
//
//        // 分页查询
//        Query q5 = Query.from(Test.class)
//                .gt(Test.DURATION, "50")
//                .page(70, 20);
//        List<Test> list5 = table.queryAll(q5);
//
//        // 更新指定行指定列数据
//        Query q6 = Query.from(Test.class)
//                .equal(Test._ID, "99");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(Test.TEXT, "新修改的数据");
//        map.put(Test.DURATION, "999999");
//        int updatedRows = table.update(q6, map);
//        Test data6 = table.query(q6);
//
//        // 删除指定行
//        Query q7 = Query.from(Test.class)
//                .equal(Test.DURATION, 44);
//        int deletedRow = table.delete(q7);
//        Log.w("SQLite", "delete row: " + deletedRow);
//        Test data7 = table.query(q7); // data7没有数据证明被删除了
//
//        // 删除多行
//        Query q8 = Query.from(Test.class)
//                .between(Test.DURATION, 61, 70);
//        int deletedRows = table.delete(q8);
//        Log.w("SQLite", "delete rows: " + deletedRows);
//        List<Test> data8 = table.queryAll(q8); // data7没有数据证明被删除了

    }

}


