package com.example.kimnahyeon.testscrollview;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimnahyeon.testscrollview.fragment.ContentFragment;
import com.example.kimnahyeon.testscrollview.fragment.MemoFragment;
import com.example.kimnahyeon.testscrollview.fragment.StatisticsFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.kimnahyeon.testscrollview.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private Context context = this;
    TextView peopletv, datetv, date2tv, placetv;
    ImageView backImg;
    Trip trip;
    int vibrantColor, vibrantDarkColor, vibrantLightColor, MuteColor, MuteLightColor, MuteDarkColor, Do;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

//        peopletv = (TextView)findViewById(R.id.people_tv);
//        datetv = (TextView)findViewById(R.id.date_tv);
//        date2tv = (TextView)findViewById(R.id.date2_tv);
//        placetv = (TextView)findViewById(R.id.place_tv);

        Intent intent = getIntent();
        trip= intent.getParcelableExtra("testData");
        Log.e("!!!!!!!!!!!!!!!!!!!!", ""+trip.toString());

        binding.setVariable(com.example.kimnahyeon.testscrollview.BR.trip, trip);

        binding.peopleTv.setText(trip.getPeople());
        binding.dateTv.setText(trip.getFirstDate().get());
        binding.date2Tv.setText(trip.getLastDate().get());
        binding.placeTv.setText(trip.getPlace());
//        peopletv.setText(trip.getPeople());
//        datetv.setText(trip.getFirstDate().get());
//        date2tv.setText(trip.getLastDate().get());
//        placetv.setText(trip.getPlace());

        final Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
//            Intent intent=new Intent(this.getIntent());
//            String s=intent.getStringExtra("TripTitle");
//            tid = intent.getIntExtra("tid",-1);
            getSupportActionBar().setTitle(trip.getTitle());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);


        binding.peopleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("여행상세 정보");
                dialog.setMessage("방문자를 입력하세요");
                dialog.setCancelable(true);
                final EditText name = new EditText(context);
                dialog.setView(name);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        binding.peopleTv.setText(name.getText().toString());
                        binding.getTrip().setPeople(name.getText().toString());
                        //trip.setPeople(name.getText().toString());
                        dialog.dismiss();
                    }
                });

                dialog.setNegativeButton("no",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        binding.dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//                dialog.setTitle("여행상세 정보");
//                dialog.setMessage("날짜를 입력하세요");
//                dialog.setCancelable(true);
//                final EditText name = new EditText(context);
//                name.setInputType(EditorInfo.TYPE_CLASS_DATETIME);
//                dialog.setView(name);
//                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        datetv.setText(name.getText().toString());
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.setNegativeButton("no",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                binding.dateTv.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                //trip.= String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth);
                                binding.getTrip().getFirstDate().set(String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        binding.date2Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                binding.date2Tv.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                binding.getTrip().getLastDate().set(String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        binding.placeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("여행상세 정보");
                dialog.setMessage("방문장소를 입력하세요");
                dialog.setCancelable(true);
                final EditText name = new EditText(context);
                dialog.setView(name);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        binding.placeTv.setText(name.getText().toString());
                        binding.getTrip().setPlace(name.getText().toString());
                        dialog.dismiss();
                    }
                });

                dialog.setNegativeButton("no",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);

      //툴바색다르게하기
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sky);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {
                    vibrantColor = palette.getVibrantColor(R.color.primary_500);
//                    vibrantDarkColor = palette.getDarkVibrantColor(R.color.primary_700);
//                    vibrantLightColor = palette.getLightVibrantColor(R.color.primary_500);
//                    MuteColor = palette.getMutedColor(R.color.primary_500);
//                    MuteDarkColor = palette.getDarkMutedColor(R.color.primary_500);
//                    MuteLightColor =palette.getLightMutedColor(R.color.primary_500);
//                    Do=palette.getDominantColor(R.color.primary_500);

                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    changeStatusBarColor(vibrantColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.primary_500)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(this, R.color.primary_700)
            );
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                //Log.d(TAG, "onTabSelected: pos: " + tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        // TODO: 31/03/17
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ContentFragment(), "지출상세");
        adapter.addFrag(new StatisticsFragment(), "지출통계");
        adapter.addFrag(new MemoFragment(), "메모");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_addMemo:
                //메모추가
                return true;
            case R.id.action_add:
                //지출추가
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
