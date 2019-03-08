package com.yuntianhe.simplesqlite;

import android.app.Activity;
import android.os.Bundle;

import com.yuntianhe.simplesqlite.entity.TestData;
import com.yuntianhe.simplesqlite.library.Query;
import com.yuntianhe.simplesqlite.table.TestTable;

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

        TestTable table = new TestTable();

        // 插入数据
        List<TestData> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            TestData data = new TestData();
            data.setText("text" + i);
            data.setDuration(i);
            list.add(data);
        }
        table.addAll(list);

        // 查询数据
        Query q = Query.from(TestTable.TABLE_NAME)
                .gt(TestTable.DURATION, "5000")
                .page(20, 1);
        List<TestData> result = table.queryAll(q);

    }

}


