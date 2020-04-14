package com.yuntianhe.simplesqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yuntianhe.simplesqlite.library.AsyncCallback;
import com.yuntianhe.simplesqlite.library.Query;
import com.yuntianhe.simplesqlite.processor.DemoTable;
import com.yuntianhe.simplesqlite.processor.TestTable;
import com.yuntianhe.simplesqlite.table.Demo;
import com.yuntianhe.simplesqlite.table.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    TextView tvTest, tvDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTest = findViewById(R.id.tv_test);
        tvDemo = findViewById(R.id.tv_demo);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestTable testTable = new TestTable();
                Test test = new Test();
                test.setId(55);
                test.setText("我搞定啦");
                test.setDuration(1800);
                testTable.add(test);

                Random r = new Random();
                DemoTable demoTable = new DemoTable();
                Demo demo = new Demo();
                demo.setPrice(r.nextInt(100) + r.nextFloat() + 0.1f);
                demo.setCount(r.nextInt(100));
                demo.setLimit(r.nextInt(100));
                demoTable.add(demo);
            }
        });

        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestTable testTable = new TestTable();
                Test test = testTable.query(Query.from(testTable.getTableName()));
                tvTest.setText(test.toString());

                DemoTable demoTable = new DemoTable();
                Query q = Query.from(demoTable.getTableName()).gt(DemoTable.limit, 50);
                demoTable.queryAllAsync(q, new AsyncCallback<List<Demo>>() {

                    @Override
                    public void onResult(List<Demo> result) {
                        String text = "";
                        for (Demo d : result) {
                            tvDemo.setText(d.toString());
                            text += d.toString() + "\n";
                        }

                        tvDemo.setText(text);
                    }
                });
            }
        });
    }

    private void initData() {

        TestTable table = new TestTable();

        // 插入数据
        List<Test> sourceList1 = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Test data = new Test();
            data.setText("text" + i);
            data.setDuration(i);
            sourceList1.add(data);
        }
        table.addAll(sourceList1);

        // 异步插入数据
        List<Test> sourceList2 = new ArrayList<>();
        for (int i = 101; i <= 200; i++) {
            Test data = new Test();
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
        List<Test> sourceList = table.queryAll(sq);

        // 相等
        Query q1 = Query.from(TestTable.TABLE_NAME).equal(TestTable.id, "100");
        Test data1 = table.query(q1);

        // 相似
        Query q2 = Query.from(TestTable.TABLE_NAME).like(TestTable.text, "text50");
        Test data2 = table.query(q2);

        // 大于 小于
        Query q3 = Query.from(TestTable.TABLE_NAME)
                .gt(TestTable.duration, "30")
                .and()
                .lt(TestTable.duration, "50");
        List<Test> list3 = table.queryAll(q3);

        // 异步查询
        table.queryAllAsync(q3, new AsyncCallback<List<Test>>() {
            @Override
            public void onResult(List<Test> result) {
                List<Test> list3Async = result; // 异步查询
            }
        });

        // 范围查询
        Query q4 = Query.from(TestTable.TABLE_NAME)
                .between(TestTable.duration, 30, 50);
        List<Test> list4 = table.queryAll(q4);

        // 分页查询
        Query q5 = Query.from(TestTable.TABLE_NAME)
                .gt(TestTable.duration, "50")
                .page(70, 20);
        List<Test> list5 = table.queryAll(q5);

        // 更新指定行指定列数据
        Query q6 = Query.from(TestTable.TABLE_NAME)
                .equal(TestTable.id, "99");
        HashMap<String, Object> map = new HashMap<>();
        map.put(TestTable.text, "新修改的数据");
        map.put(TestTable.duration, "999999");
        long updatedRows = table.update(q6, map);
        Test data6 = table.query(q6);

        // 删除指定行
        Query q7 = Query.from(TestTable.TABLE_NAME)
                .equal(TestTable.duration, 44);
        long deletedRow = table.delete(q7);
        Log.w("SQLite", "delete row: " + deletedRow);
        Test data7 = table.query(q7); // data7没有数据证明被删除了

        // 删除多行
        Query q8 = Query.from(TestTable.TABLE_NAME)
                .between(TestTable.duration, 61, 70);
        long deletedRows = table.delete(q8);
        Log.w("SQLite", "delete rows: " + deletedRows);
        List<Test> data8 = table.queryAll(q8); // data7没有数据证明被删除了

    }

}


