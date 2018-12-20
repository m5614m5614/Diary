package com.example.win.diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DiaryReadActivity extends AppCompatActivity {

    public static Intent createIntent(DiaryListActivity diaryListActivity, Diary diary) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.diary_read_activity);
        setContentView(android.R.layout.read);
    }
}
