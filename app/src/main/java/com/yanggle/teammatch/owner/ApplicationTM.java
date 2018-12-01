package com.yanggle.teammatch.owner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplicationTM extends Application {
    private final String TAG = this.getClass().getSimpleName();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    Context mContext;

    Map<String, String> C001, C002, C003, C004, C005;

    JSONObject OwnerData = new JSONObject();

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = getSharedPreferences(getString(R.string.application), Service.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();

        mContext = this;

        setMapbyCode();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Code 정보 초기화
     * Created by maloman72 on 2018-11-01
     * */
    private void setMapbyCode() {
        C001 = new HashMap<>();
        String[] C001_code = getResources().getStringArray(R.array.C001_code);
        String[] C001_data = getResources().getStringArray(R.array.C001_data);

        for(int i = 0; i < C001_code.length; i++) {
            C001.put(C001_code[i], C001_data[i]);
        }

        C002 = new HashMap<>();
        String[] C002_code = getResources().getStringArray(R.array.C002_code);
        String[] C002_data = getResources().getStringArray(R.array.C002_data);

        for(int i = 0; i < C002_code.length; i++) {
            C002.put(C002_code[i], C002_data[i]);
        }

        String[] C003_code = getResources().getStringArray(R.array.C003_code);
        String[] C003_data = getResources().getStringArray(R.array.C003_data);

        C003 = new HashMap<>();
        for(int i = 0; i < C003_code.length; i++) {
            C003.put(C003_code[i], C003_data[i]);
        }

        C004 = new HashMap<>();
        String[] C004_code = getResources().getStringArray(R.array.C004_code);
        String[] C004_data = getResources().getStringArray(R.array.C004_data);

        for(int i = 0; i < C004_code.length; i++) {
            C004.put(C004_code[i], C004_data[i]);
        }

        C005 = new HashMap<>();
        String[] C005_code = getResources().getStringArray(R.array.C005_code);
        String[] C005_data = getResources().getStringArray(R.array.C005_data);

        for(int i = 0; i < C005_code.length; i++) {
            C005.put(C005_code[i], C005_data[i]);
        }
    }

    /**
     * 어플리케이션 버전 정보 가져오기
     * Created by maloman72 on 2018-10-02
     */
    public String getVersion() {
        String mVersion = "0.0.0";

        try {
            PackageManager mPackageManager = this.getPackageManager();
            mVersion = mPackageManager.getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e(TAG, "getVersion" + e);
        }

        return mVersion;
    }

    /**
     * get Code Map
     * Created by maloman72 on 2018-11-02
     * */
    public Map<String, String> getC001() {
        return C001;
    }

    public Map<String, String> getC002() {
        return C002;
    }

    public Map<String, String> getC003() {
        return C003;
    }

    public Map<String, String> getC004() {
        return C004;
    }

    public Map<String, String> getC005() {
        return  C005;
    }


    /**
     * Data 선언 - Owner ID
     * Created by maloman72 on 2018-11-30
     * */
    public void setOwnerId(String ownerid) {
        mEditor.putString("OWNER_ID", ownerid);
        mEditor.apply();
    }

    public String getOwnerId() {
        return mSharedPreferences.getString("OWNER_ID", "");
    }

    /**
     * Data 선언 - Owner Password
     * Created by maloman72 on 2018-10-31
     * */
    public void setOwnerPassword(String ownerPassword) {
        mEditor.putString("OWNER_PASSWORD", ownerPassword);
        mEditor.apply();
    }

    public String getOwnerPassword() {
        return mSharedPreferences.getString("OWNER_PASSWORD", "");
    }

    /**
     * Data 선언 - Owner Data
     * Created by maloman72 on 2018-10-31
     * */
    public void setOwnerData(JSONObject jsonObject) {
        OwnerData = jsonObject;
    }

    public JSONObject getOwnerData() {
        return OwnerData;
    }

    /**
     * Common Toast Message
     * Created by maloman72 on 2018-10-02
     */
    Toast mToast;

    public void makeToast(Context context, String message) {
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        LinearLayout mLinearLayout = (LinearLayout) mToast.getView();
        TextView mTextView = (TextView) mLinearLayout.getChildAt(0);

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(20);

        mToast.show();
    }

    public void closeToast() {
        mToast.cancel();
    }

    /**
     * Progress Dialog
     * Created by maloman72 on 2018-10-31
     * */
    private ProgressDialog mProgressDialog;

    public void startProgress(Context context, String message) {
        if(context != null && !((Activity)context).isFinishing()) {
            if(mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }

            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            if("".equals(message)) {
                mProgressDialog.setMessage("잠시만 기다려 주세요...");
            } else {
                mProgressDialog.setMessage(message);
            }

            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    public void stopProgress() {
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

/**
 * Alert Dialogs
 * Created by maloman72 on 2017-02-17
 * */

    /** Common */
    public void commonAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder mBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            mBuilder = new AlertDialog.Builder(context);
        }

        mBuilder.setTitle(title);
        mBuilder.setMessage(message);
        mBuilder.setCancelable(false);
        mBuilder.setNegativeButton(context.getString(R.string.alert_dialog_close), null);
        mBuilder.show();
    }


    /**
     * ArrayList to String
     * Created by maloman72 on 2018-11-01
     * */
    public String ArrayListToStringParser(ArrayList<String> arrayList) {
        String returnData = "";

        for(int i = 0; i < arrayList.size(); i++) {
            returnData += arrayList.get(i);

            if(i != arrayList.size() - 1) {
                returnData += "|";
            }
        }

        return returnData;
    }

    /**
     * HashMap get key by value
     * Created by maloman72 on 2018-11-06
     * */
    public String getKeybyMap(Map<String, String> map, String value) {
        for(String object: map.keySet()) {
            if(map.get(object).equals(value)) {
                return object;
            }
        }

        return null;
    }

    /**
     * PhoneNumber hyphen delete
     * Created by maloman72 on 2018-11-14
     */
    public String setCallingPhoneNumber(String number){
        String mResult = mContext.getString(R.string.tel);

        String mSplit[] = number.split("-");
        for(int i = 0; i < mSplit.length; i++) {
            mResult += mSplit[i];
        }

        return mResult;
    }
}
