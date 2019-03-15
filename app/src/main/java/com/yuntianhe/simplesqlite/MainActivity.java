package com.yuntianhe.simplesqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.yuntianhe.simplesqlite.entity.TestData;
import com.yuntianhe.simplesqlite.library.AsyncCallback;
import com.yuntianhe.simplesqlite.library.Query;
import com.yuntianhe.simplesqlite.table.TestTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {

        TestTable table = new TestTable();

        // 插入数据
        List<TestData> sourceList1 = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            TestData data = new TestData();
            data.setText("text" + i);
            data.setDuration(i);
            sourceList1.add(data);
        }
        table.addAll(sourceList1);

        // 异步插入数据
        List<TestData> sourceList2 = new ArrayList<>();
        for (int i = 101; i <= 200; i++) {
            TestData data = new TestData();
            data.setText("text" + i);
            data.setDuration(i);
            sourceList2.add(data);
        }
        table.addAllAsync(sourceList2, new AsyncCallback<List<Long>>() {
            @Override
            public void onResult(List<Long> result) {
                Log.w("SQLite", "async insert first: " + result.get(0));
                Log.w("SQLite", "async insert: " + result.size());
            }
        });

        Query sq = Query.from(TestTable.TABLE_NAME);
        List<TestData> sourceList = table.queryAll(sq);

        // 相等
        Query q1 = Query.from(TestTable.TABLE_NAME).equal(TestTable._ID, "100");
        TestData data1 = table.query(q1);

        // 相似
        Query q2 = Query.from(TestTable.TABLE_NAME).like(TestTable.TEXT, "text50");
        TestData data2 = table.query(q2);

        // 大于 小于
        Query q3 = Query.from(TestTable.TABLE_NAME)
                .gt(TestTable.DURATION, "30")
                .and()
                .lt(TestTable.DURATION, "50");
        List<TestData> list3 = table.queryAll(q3);
        // 异步查询
        table.queryAllAsync(q3, new AsyncCallback<List<TestData>>() {
            @Override
            public void onResult(List<TestData> result) {
                List<TestData> list3Async = result; // 异步查询
            }
        });

        // 范围查询
        Query q4 = Query.from(TestTable.TABLE_NAME)
                .between(TestTable.DURATION, 30, 50);
        List<TestData> list4 = table.queryAll(q4);

        // 分页查询
        Query q5 = Query.from(TestTable.TABLE_NAME)
                .gt(TestTable.DURATION, "50")
                .page(20, 70);
        List<TestData> list5 = table.queryAll(q5);

        // 更新指定行指定列数据
        Query q6 = Query.from(TestTable.TABLE_NAME)
                .equal(TestTable._ID, "99");
        HashMap<String, Object> map = new HashMap<>();
        map.put(TestTable.TEXT, "新修改的数据");
        map.put(TestTable.DURATION, "999999");
        int updatedRows = table.update(q6, map);
        TestData data6 = table.query(q6);

        // 删除指定行
        Query q7 = Query.from(TestTable.TABLE_NAME)
                .equal(TestTable.DURATION, 44);
        int deletedRow = table.delete(q7);
        Log.w("SQLite", "delete row: " + deletedRow);
        TestData data7 = table.query(q7); // data7没有数据证明被删除了

        // 删除多行
        Query q8 = Query.from(TestTable.TABLE_NAME)
                .between(TestTable.DURATION, 61, 70);
        int deletedRows = table.delete(q8);
        Log.w("SQLite", "delete rows: " + deletedRows);
        List<TestData> data8 = table.queryAll(q8); // data7没有数据证明被删除了

    }

}


