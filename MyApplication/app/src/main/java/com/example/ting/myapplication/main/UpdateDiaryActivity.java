package com.example.ting.myapplication.main;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
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

//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;


public class UpdateDiaryActivity extends AppCompatActivity {

    TextView mUpdateDiaryTvDate;
    EditText mUpdateDiaryEtTitle;
    LinedEditText mUpdateDiaryEtContent;
    FloatingActionButton mUpdateDiaryFabBack;
    FloatingActionButton mUpdateDiaryFabAdd;
    FloatingActionButton mUpdateDiaryFabDelete;
    FloatingActionsMenu mRightLabels;
    TextView mCommonTvTitle;
    LinearLayout mCommonTitleLl;
    ImageView mCommonIvBack;
    ImageView mCommonIvTest;
    TextView mTvTag;

    private DiaryDatabaseHelper mHelper;

    public static void startActivity(Context context, String title, String content, String tag) {
        Intent intent = new Intent(context, UpdateDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);
        AppManager.getAppManager().addActivity(this);
        //ButterKnife.bind(this);


// ========================================================
        mUpdateDiaryTvDate = findViewById (R.id.update_diary_tv_date);

        mUpdateDiaryEtTitle = findViewById (R.id.update_diary_et_title);

        mUpdateDiaryEtContent = findViewById (R.id.update_diary_et_content);

        mUpdateDiaryFabBack = findViewById(R.id.update_diary_fab_back);

        mUpdateDiaryFabAdd = findViewById(R.id.update_diary_fab_add);

        mUpdateDiaryFabDelete =findViewById(R.id.update_diary_fab_delete);

        mRightLabels = findViewById(R.id.right_labels);

        mCommonTvTitle = findViewById(R.id.common_tv_title);

        mCommonTitleLl = findViewById(R.id.common_title_ll);

        mCommonIvBack = findViewById(R.id.common_iv_back);

        mCommonIvTest = findViewById(R.id.common_iv_test);

        mTvTag = findViewById(R.id.update_diary_tv_tag);

        findViewById (R.id.common_iv_back);
        findViewById (R.id.update_diary_tv_date);
        findViewById (R.id.update_diary_et_title);
        findViewById (R.id.update_diary_et_content);
        findViewById (R.id.update_diary_fab_back);
        findViewById (R.id.update_diary_fab_add);
        findViewById (R.id.update_diary_fab_delete);

        findViewById (R.id.common_iv_back).setOnClickListener ( onClick );
        findViewById (R.id.update_diary_tv_date).setOnClickListener ( onClick );
        findViewById (R.id.update_diary_et_title).setOnClickListener ( onClick );
        findViewById (R.id.update_diary_et_content).setOnClickListener ( onClick );
        findViewById (R.id.update_diary_fab_back).setOnClickListener ( onClick );
        findViewById (R.id.update_diary_fab_add).setOnClickListener ( onClick );
        findViewById (R.id.update_diary_fab_delete).setOnClickListener ( onClick );
// ========================================================

        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null);
        initTitle();
        StatusBarCompat.compat(this, Color.parseColor("#161414"));

        Intent intent = getIntent();
        mUpdateDiaryTvDate.setText("今天，" + GetDate.getDate());
        mUpdateDiaryEtTitle.setText(intent.getStringExtra("title"));
        mUpdateDiaryEtContent.setText(intent.getStringExtra("content"));
        mTvTag.setText(intent.getStringExtra("tag"));



    }

    private void initTitle() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mCommonTvTitle.setText("修改日記內容");
    }

    //@OnClick({R.id.common_iv_back, R.id.update_diary_tv_date, R.id.update_diary_et_title, R.id.update_diary_et_content, R.id.update_diary_fab_back, R.id.update_diary_fab_add, R.id.update_diary_fab_delete})
    private View.OnClickListener onClick = new View.OnClickListener () {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.common_iv_back:
                    main.startActivity(UpdateDiaryActivity.this);
                case R.id.update_diary_tv_date:
                    break;
                case R.id.update_diary_et_title:
                    break;
                case R.id.update_diary_et_content:
                    break;
                case R.id.update_diary_fab_back:
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(UpdateDiaryActivity.this);
                    alertDialogBuilder.setMessage("確定要刪除嗎？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

//                        String title = mUpdateDiaryEtTitle.getText().toString();
                            String tag = mTvTag.getText().toString();
                            SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                            dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                            main.startActivity(UpdateDiaryActivity.this);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                    break;
                case R.id.update_diary_fab_add:
                    SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                    ContentValues valuesUpdate = new ContentValues();
                    String title = mUpdateDiaryEtTitle.getText().toString();
                    String content = mUpdateDiaryEtContent.getText().toString();
                    valuesUpdate.put("title", title);
                    valuesUpdate.put("content", content);
                    dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                    dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
                    main.startActivity(UpdateDiaryActivity.this);
                    break;
                case R.id.update_diary_fab_delete:
                    main.startActivity(UpdateDiaryActivity.this);

                    break;
            }
        }
    };
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                main.startActivity(this);
            case R.id.update_diary_tv_date:
                break;
            case R.id.update_diary_et_title:
                break;
            case R.id.update_diary_et_content:
                break;
            case R.id.update_diary_fab_back:
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("確定要刪除該日記？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

//                        String title = mUpdateDiaryEtTitle.getText().toString();
                        String tag = mTvTag.getText().toString();
                        SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                        dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                        main.startActivity(UpdateDiaryActivity.this);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
            case R.id.update_diary_fab_add:
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title = mUpdateDiaryEtTitle.getText().toString();
                String content = mUpdateDiaryEtContent.getText().toString();
                valuesUpdate.put("title", title);
                valuesUpdate.put("content", content);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
                main.startActivity(this);
                break;
            case R.id.update_diary_fab_delete:
                main.startActivity(this);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        main.startActivity(this);
    }
}