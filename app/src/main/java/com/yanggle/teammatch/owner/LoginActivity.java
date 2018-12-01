package com.yanggle.teammatch.owner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    EditText et_login_id, et_login_password;
    Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mBackPressCloseHandler = new BackPressCloseHandler(this);

        mService = new Service(mContext);

        et_login_id = findViewById(R.id.et_login_id);
        et_login_password = findViewById(R.id.et_login_password);
        bt_login = findViewById(R.id.bt_login);

        bt_login.setOnClickListener(mOnClickListener);
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
            switch (v.getId()) {
                case R.id.bt_login :
                    OwnerLogin();
                    break;
                default :
                    break;
            }
        }
    };

    private void OwnerLogin() {
        if("".equals(et_login_id.getText().toString())) {
            mApplicationTM.makeToast(mContext, getString(R.string.login_not_id));
            return;
        }
        if("".equals(et_login_password.getText().toString())) {
            mApplicationTM.makeToast(mContext, getString(R.string.login_not_password));
            return;
        }

        mService.ownerLogin(ownerLogin_Listener, et_login_id.getText().toString(), et_login_password.getText().toString());
    }

    /**
     * userLogin Service Listener
     * Created by maloman72 on 2018-11-02
     * */
    ResponseListener ownerLogin_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                if(responseEvent.getResultData() != null) {
                    JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                    if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                        JSONObject mResult = mJSONObject.getJSONObject(mContext.getString(R.string.result_data));

                        mApplicationTM.setOwnerId(et_login_id.getText().toString());
                        mApplicationTM.setOwnerPassword(et_login_password.getText().toString());
                        mApplicationTM.setOwnerData(mResult);

                        Intent mIntent = new Intent(mContext, MatchProcActivity.class);
                        startActivity(mIntent);
                        finish();
                    } else {
                        mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                    }
                } else {
                    mApplicationTM.makeToast(mContext, getString(R.string.error_network));
//                    Intent mIntent = new Intent(mContext, MatchProcActivity.class);
//                    startActivity(mIntent);
//                    finish();
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
