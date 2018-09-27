package com.example.kimnahyeon.testscrollview;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kimnahyeon.testscrollview.data.Memo;

import java.util.Calendar;

public class EditMemoActivity extends AppCompatActivity {

    private final int NO_ALPHA_MASKING = 0xFF000000;
    EditText titleTv, bodyTv;
    TextView dateTv;
    Button saveBtn;
    Memo memo = new Memo();
    String title, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        Intent intent = getIntent();
        int barcolor = intent.getIntExtra("barColor", R.color.sbDefault);
        changeStatusBarColor(removeAlphaProperty(barcolor));
        memo.setTid(intent.getIntExtra("TripId",-1));

        titleTv = (EditText)findViewById(R.id.memo_title_et);
        bodyTv = (EditText)findViewById(R.id.memo_body_et);

        dateTv = (TextView)findViewById(R.id.memo_date_tv);
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditMemoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateTv.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                //.set(String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        saveBtn = (Button)findViewById(R.id.memo_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleTv.getText().toString();
                body = bodyTv.getText().toString();
                if(title != "") memo.setTitle(title);
                if(body != "") memo.setBody(body);
                finish();
            }
        });
    }

    private int removeAlphaProperty(int color){
        return color | NO_ALPHA_MASKING;
    }

    private void changeStatusBarColor(int color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}
