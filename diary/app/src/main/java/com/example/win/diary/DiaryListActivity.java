package com.example.win.diary;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DiaryListActivity extends AppCompatActivity {

     private  ListView diaryListView;
    private   Button addDiaryButton;

    private DiaryDb diaryDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();

        findView();

        setView();

        setListener();
    }

    private void setLayout(){
        setContentView(android.R.layout.activity_list_item);
    }

    private void findView(){
        diaryListView = (ListView) findViewById(R.id.diary_list);
        addDiaryButton = (Button) findViewById(R.id.add_diary_button);
    }

    private void setView(){
        if(diaryDb == null) {
            diaryDb = new DiaryDb(this);
        }
        ArrayList<Diary> diaries = diaryDb.getAllDiaries();

        DiaryAdapter diaryAdapter = new DiaryAdapter(this, diaries);
        diaryListView.setAdapter(diaryAdapter);
    }

    private void setListener(){

        addDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryListActivity.this, DiaryEditActivity.class);
                startActivity(intent);
            }
        });

        diaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diary diary = (Diary)diaryListView.getItemAtPosition(position);
                Intent intent = DiaryReadActivity.createIntent(DiaryListActivity.this, diary);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setView();
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        diaryDb.close();
    }
}
