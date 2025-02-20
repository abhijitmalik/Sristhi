package com.srishti.sda;


import android.os.Bundle;
import android.widget.HorizontalScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HorizontalScrollView horizontalHeaderScroll;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.recyclerView);
        horizontalHeaderScroll = findViewById(R.id.horizontalHeaderScroll);

        List<Model> dataList = getDataList();
        adapter = new RecyclerViewAdapter(this, dataList, horizontalHeaderScroll);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<Model> getDataList() {
        List<Model> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Model("A" + i, "Name" + i, "S" + i, "R" + i, "F" + i,
                    "O" + i, "2024-02-20", "2024-02-21", "2000-01-01", "V" + i,
                    "Father" + i, "Address" + i, "email"+i+"@test.com", "1234567890",
                    "Nominee" + i, "2010-01-01", "Relation" + i));
        }
        return list;
    }
}

