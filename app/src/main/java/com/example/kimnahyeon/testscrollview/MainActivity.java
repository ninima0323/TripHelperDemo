package com.example.kimnahyeon.testscrollview;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kimnahyeon.testscrollview.databinding.ActivityMainBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TripAdapter mAdapter;
    ObservableArrayList<Trip> tripList = new ObservableArrayList<>();
    ImageView backImg;
    private static final int PICK_FROM_CAMERA = 0000;
    private static final int PICK_FROM_ALBUM = 1111;
    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름
    CollapsingToolbarLayout collapsingToolbarLayout;
    private final int NO_ALPHA_MASKING = 0xFF000000;

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

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("카메라/앨범 권한")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);


        backImg = (ImageView)findViewById(R.id.ivParallax);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

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

        changeBarColor();
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

    void show() {
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

    private void changeBarColor(){
        //툴바색다르게하기
        try {
            Bitmap bitmap = ((BitmapDrawable)backImg.getDrawable()).getBitmap();
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {
                    int vibrantColor = palette.getVibrantColor(R.color.sbDefault);
                    int vibrantLightColor = palette.getDarkVibrantColor(R.color.tbDefault);
                    //collapsingToolbarLayout.setContentScrimColor(removeAlphaProperty(vibrantLightColor));
                    Log.e("color", getColorHashCode(vibrantColor) );
                    if(getColorHashCode(vibrantColor).equals("#7f060091"))//{
                        Toast.makeText(MainActivity.this, "색 추출 실패", Toast.LENGTH_LONG).show();
//                        collapsingToolbarLayout.setContentScrimColor(R.color.sbDefault);
//                        changeStatusBarColor(R.color.tbDefault);
//                    }else{
                        collapsingToolbarLayout.setContentScrimColor(removeAlphaProperty(vibrantColor));
                        changeStatusBarColor(removeAlphaProperty(vibrantColor));
                    //}

                }
            });

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "색 추출 실패", Toast.LENGTH_LONG).show();
            // if Bitmap fetch fails, fallback to primary colors
            //Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.tbDefault)
            );
            changeStatusBarColor(R.color.sbDefault);
//            collapsingToolbarLayout.setStatusBarScrimColor(
//                    ContextCompat.getColor(this, R.color.primary_700)
//            );
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
                changeBarColor();
                break;
            case PICK_FROM_CAMERA:
                getPictureForPhoto(); //카메라에서 가져오기
                galleryAddPic();
                changeBarColor();
                break;
            default:
                break;
        }

    }
}
