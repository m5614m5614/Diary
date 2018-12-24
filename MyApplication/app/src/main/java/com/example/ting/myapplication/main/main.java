package com.example.ting.myapplication.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ting.myapplication.DiaryBean;
import com.example.ting.myapplication.DiaryDatabaseHelper;
import com.example.ting.myapplication.R;
import com.example.ting.myapplication.StartUpdateDiaryEvent;
import com.example.ting.myapplication.utils.AppManager;
import com.example.ting.myapplication.utils.GetDate;
import com.example.ting.myapplication.utils.SpHelper;
import com.example.ting.myapplication.utils.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;

public class main extends AppCompatActivity {


    ImageView mCommonIvBack;

    TextView mCommonTvTitle;

    ImageView mCommonIvTest;

    LinearLayout mCommonTitleLl ;

    ImageView mMainIvCircle ;

    TextView mMainTvDate;

    TextView mMainTvContent;

    LinearLayout mItemLlControl ;


    RecyclerView mMainRvShowDiary;

    FloatingActionButton mMainFabEnterEdit ;

    RelativeLayout mMainRlMain;

    LinearLayout mItemFirst;

    LinearLayout mMainLlMain;
    private List<DiaryBean> mDiaryBeanList;

    private DiaryDatabaseHelper mHelper;

    private static String IS_WRITE = "true";

    private int mEditPosition = -1;

    /**
     * 标识今天是否已经写了日记
     */
    private boolean isWrite = false;
    private static TextView mTvTest;

   public static void startActivity(Context context) {
        Intent intent = new Intent(context, main.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(this);


        mCommonIvBack = findViewById(R.id.common_iv_back);

        mCommonTvTitle = findViewById(R.id.common_tv_title);

        mCommonIvTest = findViewById(R.id.common_iv_test);

        mCommonTitleLl = findViewById(R.id.common_title_ll);

        mMainIvCircle = findViewById(R.id.main_iv_circle);

        mMainTvDate = findViewById(R.id.main_tv_date);

        mMainTvContent = findViewById(R.id.main_tv_content);

        mItemLlControl = findViewById(R.id.item_ll_control);

        findViewById (R.id.main_fab_enter_edit).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AddDiaryActivity.startActivity(main.this);
            }
        } );


        RecyclerView mMainRvShowDiary = findViewById(R.id.main_rv_show_diary);

        FloatingActionButton mMainFabEnterEdit = findViewById(R.id.main_fab_enter_edit);

        RelativeLayout mMainRlMain = findViewById(R.id.main_rl_main);

        mItemFirst = findViewById(R.id.item_first);

        mMainLlMain = findViewById(R.id.main_ll_main);

        //ButterKnife.bind(this);
        StatusBarCompat.compat(this, Color.parseColor("#161414"));
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        EventBus.getDefault().register(this);
        SpHelper spHelper = SpHelper.getInstance(this);
        getDiaryBeanList();
        initTitle();
        mMainRvShowDiary.setLayoutManager(new LinearLayoutManager(this));
        mMainRvShowDiary.setAdapter(new DiaryAdapter(this, mDiaryBeanList));
        mTvTest = new TextView(this);
        mTvTest.setText("hello world");
    }

    private void initTitle() {
        mMainTvDate.setText("今天，" + GetDate.getDate());
        mCommonTvTitle.setText("日记");
        mCommonIvBack.setVisibility(View.INVISIBLE);
        mCommonIvTest.setVisibility(View.INVISIBLE);

    }

    private List<DiaryBean> getDiaryBeanList() {

        mDiaryBeanList = new ArrayList<>();
        List<DiaryBean> diaryList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Diary", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String dateSystem = GetDate.getDate().toString();
                if (date.equals(dateSystem)) {
                    mMainLlMain.removeView(mItemFirst);
                    break;
                }
            } while (cursor.moveToNext());
        }


        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                mDiaryBeanList.add(new DiaryBean(date, title, content, tag));
            } while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = mDiaryBeanList.size() - 1; i >= 0; i--) {
            diaryList.add(mDiaryBeanList.get(i));
        }

        mDiaryBeanList = diaryList;
        return mDiaryBeanList;
    }

    @Subscribe
    public void startUpdateDiaryActivity(StartUpdateDiaryEvent event) {
        String title = mDiaryBeanList.get(event.getPosition()).getTitle();
        String content = mDiaryBeanList.get(event.getPosition()).getContent();
        String tag = mDiaryBeanList.get(event.getPosition()).getTag();
        UpdateDiaryActivity.startActivity(this, title, content, tag);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().AppExit(this);
    }
}
