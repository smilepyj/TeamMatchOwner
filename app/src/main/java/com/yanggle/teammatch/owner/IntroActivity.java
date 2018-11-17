package com.yanggle.teammatch.owner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.yanggle.teammatch.owner.network.ResponseEvent;
import com.yanggle.teammatch.owner.network.ResponseListener;
import com.yanggle.teammatch.owner.network.Service;

import org.json.JSONObject;

public class IntroActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    TextView tv_intro_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        tv_intro_version = findViewById(R.id.tv_intro_version);

        tv_intro_version.setText(mApplicationTM.getVersion());

        Handler mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1500);
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            CheckPermission();
        }
    };

    /**
     * Back Button Listener
     * Intro 화면 Back Button 사용 차단
     * Created by maloman72 on 2018-10-05
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * Android M 이상 Permission 체크
     * Created by maloman72 on 2018-11-10
     * */
    private void CheckPermission() {
        if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 1);
        } else {
            CheckLogin();
        }
    }

    /**
     * Permission 동의 확인
     * Created by maloman72 on 2018-11-10
     * */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            /*for(int mRssult : grantResults) {
                if(mRssult != PackageManager.PERMISSION_GRANTED) {
                    mApplicationTM.makeToast(mContext, getString(R.string.permission_message));
                    return;
                }
            }*/

            Intent mIntent = new Intent(mContext, MatchProcActivity.class);
            startActivity(mIntent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

//            CheckLogin();
        }
    }

    /**
     * Check Login
     * Created by maloman72 on 2018-11-10
     * */
    private void CheckLogin() {
        if("".equals(mApplicationTM.getOwnerId())) {
            Intent mIntent = new Intent(mContext, LoginActivity.class);
            startActivity(mIntent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            mService.userLogin(userLogin_Listener, mApplicationTM.getOwnerId(), mApplicationTM.getUserEmail());
        }
    }

    /**
     * userLogin Service Listener
     * Created by maloman72 on 2018-11-02
     * */
    ResponseListener userLogin_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                if(responseEvent.getResultData() != null) {
                    JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                    if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                        JSONObject mResult = mJSONObject.getJSONObject(mContext.getString(R.string.result_data));

                        mApplicationTM.setUserName(mResult.get(mContext.getString(R.string.userlogin_result_user_name)).toString());
                        mApplicationTM.setTeamId(mResult.get(mContext.getString(R.string.userlogin_result_team_id)).toString());

                        Intent mIntent;
                        mIntent = new Intent(mContext, MatchProcActivity.class);

                        /*if("".equals(mResult.get(getString(R.string.userlogin_result_user_name)))) {
                            mIntent = new Intent(mContext, UserInfoActivity.class);
                            mIntent.putExtra(getString(R.string.user_info_intent_extra), getString(R.string.user_info_type_input));
                        } else {
                            mIntent = new Intent(mContext, MainActivity.class);
                        }*/

                        startActivity(mIntent);
                        finish();
                    } else {
                        mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                    }
                } else {
                    mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "userLogin_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
