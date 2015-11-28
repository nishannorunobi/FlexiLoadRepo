package com.zia.nishan.flexyload;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zia.nishan.controller.FlashController;
import com.zia.nishan.interfaces.OnCameraButtonClickListener;
import com.zia.nishan.views.CameraFragment;
import com.zia.nishan.views.HomeFragment;

/**
 * Created by nishan on 11/24/15.
 */
public class FlexActivity extends AppCompatActivity implements View.OnClickListener {
    private FlashController flashController;
    private Button callBtn;
    private ImageView flash_on_btn;
    private ImageView flash_off_btn;
    private ImageView camera_on_btn;
    private ImageView camera_off_btn;

    private HomeFragment homeFragment;
    private CameraFragment cameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flexy_home);
        /*Initialize fragment*/
        homeFragment = new HomeFragment();
        cameraFragment = new CameraFragment();
        cameraFragment.setOnCameraButtonClickListener(onCameraButtonClickListener);
        /*Init view*/
        callBtn = (Button) findViewById(R.id.btn_call);
        callBtn.setOnClickListener(this);
        flashController = new FlashController(this);
        flash_on_btn=(ImageView)findViewById(R.id.flash_on_btn);
        flash_on_btn.setOnClickListener(this);
        flash_off_btn=(ImageView)findViewById(R.id.flash_off_btn);
        flash_off_btn.setOnClickListener(this);

        camera_on_btn=(ImageView)findViewById(R.id.camera_on_btn);
        camera_on_btn.setOnClickListener(cameraFragment);
        camera_off_btn=(ImageView)findViewById(R.id.camera_off_btn);
        camera_off_btn.setOnClickListener(cameraFragment);

        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.flash_on_btn:
                flash_on_btn.setVisibility(View.GONE);
                flash_off_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.flash_off_btn:
                flash_off_btn.setVisibility(View.GONE);
                flash_on_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_call:
                call();
                break;
            default:
                loadFragment(new HomeFragment());
                break;
        }
    }

    private void call() {
        String ussdCode = "*" + "123" + Uri.encode("#");
        Uri uri = Uri.parse("tel:"+ussdCode);
        Intent in = new Intent(Intent.ACTION_CALL,uri);
        try{
            startActivity(in);
        }catch (android.content.ActivityNotFoundException ex){
            Log.e(ex.getMessage(), ex.toString());
        }
    }

    private OnCameraButtonClickListener onCameraButtonClickListener = new OnCameraButtonClickListener() {
        @Override
        public void turnOn() {
            camera_on_btn.setVisibility(View.GONE);
            camera_off_btn.setVisibility(View.VISIBLE);
            loadFragment(cameraFragment);
        }

        @Override
        public void turnOff() {
            camera_off_btn.setVisibility(View.GONE);
            camera_on_btn.setVisibility(View.VISIBLE);
            loadFragment(homeFragment);
        }
    };
}