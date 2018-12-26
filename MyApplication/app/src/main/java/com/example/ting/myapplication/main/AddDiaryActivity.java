package com.example.ting.myapplication.main;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ting.myapplication.DiaryDatabaseHelper;
import com.example.ting.myapplication.R;
import com.example.ting.myapplication.utils.AppManager;
import com.example.ting.myapplication.utils.GetDate;
import com.example.ting.myapplication.utils.LinedEditText;
import com.example.ting.myapplication.utils.StatusBarCompat;

import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

public class AddDiaryActivity extends AppCompatActivity {

    TextView mAddDiaryTvDate;
    EditText mAddDiaryEtTitle ;

    LinedEditText mAddDiaryEtContent ;

    FloatingActionButton mAddDiaryFabBack ;

    FloatingActionButton mAddDiaryFabAdd;

    FloatingActionsMenu mRightLabels;
    TextView mCommonTvTitle ;
    LinearLayout mCommonTitleLl ;
    ImageView mCommonIvBack;
    ImageView mCommonIvTest ;

    private com.example.ting.myapplication.DiaryDatabaseHelper mHelper;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String title, String content) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        AppManager.getAppManager().addActivity(this);
// ========================================================
        mAddDiaryTvDate = findViewById(R.id.add_diary_tv_date) ;
        mAddDiaryEtTitle = findViewById(R.id.add_diary_et_title) ;

        mAddDiaryEtContent = findViewById(R.id.add_diary_et_content);

        mAddDiaryFabBack = findViewById(R.id.add_diary_fab_back);

        mAddDiaryFabAdd = findViewById(R.id.add_diary_fab_add);

        mRightLabels = findViewById(R.id.right_labels) ;
        mCommonTvTitle = findViewById(R.id.common_tv_title) ;
        mCommonTitleLl = findViewById(R.id.common_title_ll) ;
        mCommonIvBack = findViewById(R.id.common_iv_back) ;
        mCommonIvTest = findViewById(R.id.common_iv_test) ;
        findViewById (R.id.common_iv_back).setOnClickListener ( onClick );
        findViewById (R.id.add_diary_et_title).setOnClickListener ( onClick );
        findViewById (R.id.add_diary_et_content).setOnClickListener ( onClick );
        findViewById (R.id.add_diary_fab_back).setOnClickListener ( onClick );
        findViewById (R.id.add_diary_fab_add).setOnClickListener ( onClick );
        //@OnClick({R.id.common_iv_back, R.id.add_diary_et_title, R.id.add_diary_et_content, R.id.add_diary_fab_back, R.id.add_diary_fab_add})
        //ButterKnife.bind(this);
// ========================================================

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        mAddDiaryEtTitle.setText(intent.getStringExtra("title"));
        StatusBarCompat.compat(this, Color.parseColor("#161414"));

        mCommonTvTitle.setText("增加日記");
        mAddDiaryTvDate.setText("今天，" + GetDate.getDate());
        mAddDiaryEtContent.setText(intent.getStringExtra("content"));
        mHelper = new DiaryDatabaseHelper (this, "Diary.db", null);
    }


    //@OnClick({R.id.common_iv_back, R.id.add_diary_et_title, R.id.add_diary_et_content, R.id.add_diary_fab_back, R.id.add_diary_fab_add})
    public View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.common_iv_back:
                    main.startActivity ( AddDiaryActivity.this );
                case R.id.add_diary_et_title:
                    break;
                case R.id.add_diary_et_content:
                    break;
                case R.id.add_diary_fab_back:
                    String date = GetDate.getDate().toString();
                    String tag = String.valueOf(System.currentTimeMillis());
                    String title = mAddDiaryEtTitle.getText().toString() + "";
                    String content = mAddDiaryEtContent.getText().toString() + "";
                    if (!title.equals("") || !content.equals("")) {
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("date", date);
                        values.put("title", title);
                        values.put("content", content);
                        values.put("tag", tag);
                        db.insert("Diary", null, values);
                        values.clear();
                    }
                    main.startActivity(AddDiaryActivity.this);
                    break;
                case R.id.add_diary_fab_add:
                    final String dateBack = GetDate.getDate().toString();
                    final String titleBack = mAddDiaryEtTitle.getText().toString();
                    final String contentBack = mAddDiaryEtContent.getText().toString();
                    if(!titleBack.isEmpty() || !contentBack.isEmpty()){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddDiaryActivity.this);
                        alertDialogBuilder.setMessage("是否保存內容？").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("date", dateBack);
                                values.put("title", titleBack);
                                values.put("content", contentBack);
                                db.insert("Diary", null, values);
                                values.clear();
                                main.startActivity(AddDiaryActivity.this);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                main.startActivity(AddDiaryActivity.this);
                            }
                        }).show();
                    }else{
                        main.startActivity(AddDiaryActivity.this);
                    }
                    break;
            }
        }
    };
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                    main.startActivity ( this );
            case R.id.add_diary_et_title:
                break;
            case R.id.add_diary_et_content:
                break;
            case R.id.add_diary_fab_back:
                String date = GetDate.getDate().toString();
                String tag = String.valueOf(System.currentTimeMillis());
                String title = mAddDiaryEtTitle.getText().toString() + "";
                String content = mAddDiaryEtContent.getText().toString() + "";
                if (!title.equals("") || !content.equals("")) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", date);
                    values.put("title", title);
                    values.put("content", content);
                    values.put("tag", tag);
                    db.insert("Diary", null, values);
                    values.clear();
                }
                main.startActivity(this);
                break;
            case R.id.add_diary_fab_add:
                final String dateBack = GetDate.getDate().toString();
                final String titleBack = mAddDiaryEtTitle.getText().toString();
                final String contentBack = mAddDiaryEtContent.getText().toString();
                if(!titleBack.isEmpty() || !contentBack.isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("是否保存？").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("date", dateBack);
                            values.put("title", titleBack);
                            values.put("content", contentBack);
                            db.insert("Diary", null, values);
                            values.clear();
                            main.startActivity(AddDiaryActivity.this);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            main.startActivity(AddDiaryActivity.this);
                        }
                    }).show();
                }else{
                    main.startActivity(this);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        main.startActivity(this);
    }
}











