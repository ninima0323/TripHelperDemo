package com.example.kimnahyeon.testscrollview;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.kimnahyeon.testscrollview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TripAdapter mAdapter;
    ObservableArrayList<Trip> tripList = new ObservableArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//트루면 백버튼이 생김


        mAdapter = new TripAdapter(this, tripList);
        binding.mainRv.setAdapter(mAdapter);
        binding.setTripList(tripList);

        prepareData();

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        //툴바색다르게하기
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.city);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.primary_500);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.primary_700);
                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    //collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                    changeStatusBarColor(vibrantColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            //Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.primary_500)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(this, R.color.primary_700)
            );
        }
    }

    //액션바 적용한것
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    //액션바 아이템이 클릭되었을때 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add :
                // TODO : process the click event for action_search item.
                //리스트에 아이템 추가하기
//                Intent intent = new Intent(MainActivity.this, AddActivity.class);
//                startActivityForResult(intent, RC_ADD);

                show();
                return true ;
            // ...
            // ...
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }

    public void prepareData(){
        int i;
        for(i=0; i<20; i++) {
            Trip trip = new Trip("여행"+i,"장소"+i);
            trip.setTid(i);
            tripList.add(trip);
        }
        mAdapter.notifyDataSetChanged();
    }

    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.main_dialog, null);
        builder.setView(view);
        final Button dialAddBtn = (Button) view.findViewById(R.id.add_btn);
        final EditText title = (EditText) view.findViewById(R.id.et1);
        final EditText place= (EditText) view.findViewById(R.id.et2);

        final AlertDialog dialog = builder.create();
        dialAddBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Trip trips = new Trip(title.getText().toString(), place.getText().toString());
                tripList.add(trips);
                mAdapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(), city1.getText().toString()+city2.getText().toString(),Toast.LENGTH_LONG).show();

                dialog.dismiss();
            }
        });

        dialog.show();

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
