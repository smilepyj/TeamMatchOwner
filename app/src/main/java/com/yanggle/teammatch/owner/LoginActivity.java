package com.yanggle.teammatch.owner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.yanggle.teammatch.owner.network.ResponseEvent;
import com.yanggle.teammatch.owner.network.ResponseListener;
import com.yanggle.teammatch.owner.network.Service;
import com.yanggle.teammatch.owner.util.BackPressCloseHandler;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    private Service mService;

    TextView tv_login_term_service, tv_login_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mBackPressCloseHandler = new BackPressCloseHandler(this);

        mService = new Service(mContext);

        tv_login_term_service = findViewById(R.id.tv_login_term_service);
        tv_login_privacy = findViewById(R.id.tv_login_privacy);

        tv_login_term_service.setOnClickListener(mOnClickListener);
        tv_login_privacy.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Back Button Listener
     * 프로그램 종료
     * Created by maloman72 on 2018-10-05
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mBackPressCloseHandler.onBackPressed();
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
                case R.id.bt_login_kakao :
                    mApplicationTM.makeToast(mContext, getString(R.string.login_kakao_not_support));
                    mService.userLogin(userLogin_Listener, mApplicationTM.getOwnerId(), mApplicationTM.getUserEmail());
                    break;
                default :
                    break;
            }
        }
    };

    private void GoMain() {
        Intent mIntent = new Intent(mContext, MainActivity.class);
        startActivity(mIntent);
        finish();
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

                        mApplicationTM.setTeamId(mResult.get(mContext.getString(R.string.userlogin_result_team_id)).toString());

                        Intent mIntent;
                        mIntent = new Intent(mContext, MainActivity.class);

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
