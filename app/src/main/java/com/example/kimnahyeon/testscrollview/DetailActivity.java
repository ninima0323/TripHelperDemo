package com.example.kimnahyeon.testscrollview;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.Toast;

import com.example.kimnahyeon.testscrollview.fragment.ContentFragment;
import com.example.kimnahyeon.testscrollview.fragment.MemoFragment;
import com.example.kimnahyeon.testscrollview.fragment.StatisticsFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.example.kimnahyeon.testscrollview.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private Context context = this;
    ImageView backImg;
    Trip trip;

    private final int NO_ALPHA_MASKING = 0xFF000000;

    private static final int PICK_FROM_CAMERA = 0000;
    private static final int PICK_FROM_ALBUM = 1111;
    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름
    CollapsingToolbarLayout collapsingToolbarLayout;
    private int BASIC = 445556;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        trip= intent.getParcelableExtra("testData");
        Log.e("!!!!!!!!!!!!!!!!!!!!", ""+trip.toString());

        binding.setVariable(com.example.kimnahyeon.testscrollview.BR.trip, trip);

        trip.setTid(binding.getTrip().getTid());
        binding.peopleTv.setText(trip.getPeople());
        binding.dateTv.setText(trip.getFirstDate().get());
        binding.date2Tv.setText(trip.getLastDate().get());
        binding.placeTv.setText(trip.getPlace());

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


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        changeBarColor();

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

        backImg = (ImageView)findViewById(R.id.htab_header);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);

                dialog.setMessage("변경할 배경을 선택하세요");
                dialog.setCancelable(true);
                dialog.setPositiveButton("앨범선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectGallery();
                        dialog.dismiss();
                    }
                });
                dialog.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("사진촬영", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectCamera();
                        dialog.dismiss();
                    }
                });

                dialog.show();
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
                Intent intentMemo = new Intent(this, EditMemoActivity.class);
                intentMemo.putExtra("barColor", trip.getBarColor());
                intentMemo.putExtra("TripId", trip.getTid());
                startActivity(intentMemo);
                return true;
            case R.id.action_add:
                //지출추가
                Intent intent = new Intent(this, EditContentActivity.class);
                intent.putExtra("barColor", trip.getBarColor());
                intent.putExtra("TripId", trip.getTid());
                startActivity(intent);
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

    private void changeBarColor(){
        //툴바색다르게하기
        try {
            Bitmap bitmap;
            if(BASIC != -1){
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sky);
            }else{
                bitmap = BitmapFactory.decodeFile(getRealPathFromURI(photoUri));
            }
            //Bitmap bitmap = ((BitmapDrawable)backImg.getDrawable()).getBitmap();
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {
                    int vibrantColor = palette.getVibrantColor(R.color.sbDefault);
                    trip.setBarColor(vibrantColor);
                    if(getColorHashCode(vibrantColor).equals("#7f060091"))//{
                        Toast.makeText(DetailActivity.this, "색 추출 실패", Toast.LENGTH_LONG).show();
                        collapsingToolbarLayout.setContentScrimColor(removeAlphaProperty(vibrantColor));
                        changeStatusBarColor(removeAlphaProperty(vibrantColor));
                }
            });

        } catch (Exception e) {
            Toast.makeText(DetailActivity.this, "색 추출 실패", Toast.LENGTH_LONG).show();
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.tbDefault)
            );
            changeStatusBarColor(R.color.sbDefault);
        }
    }

    private String getColorHashCode(int color){
        return "#" + Integer.toHexString(color);
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

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
    }

    private void selectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    private void sendPicture(Uri imgUri) {

        String imagePath = getRealPathFromURI(imgUri); // path 경로
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        backImg.setImageBitmap(bitmap);
        Log.e("!!!!!!!!!!!!!!!!!!!!", bitmap.getWidth()+"");
    }

    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "TestScrollView");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName = timeStamp + ".png";

        File storageDir = new File(dir, mImageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;

    }

    private void selectCamera() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {

                }
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                }
            }

        }
    }

    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        backImg.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //backImg.setImageURI(data.getData());
        Log.e("activity result", resultCode + "");
        if(resultCode != RESULT_OK) return;
        Log.e("activity result", requestCode + "");
        switch (requestCode){
            case PICK_FROM_ALBUM:
                Log.e("activity result", data.getData().toString());
                sendPicture(data.getData()); //갤러리에서 가져오기
                photoUri=data.getData();
                BASIC=-1;
                changeBarColor();
                break;
            case PICK_FROM_CAMERA:
                getPictureForPhoto(); //카메라에서 가져오기
                galleryAddPic();
                BASIC=-1;
                changeBarColor();
                break;
            default:
                break;
        }

    }

}
