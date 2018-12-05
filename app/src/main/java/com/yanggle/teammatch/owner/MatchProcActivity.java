package com.yanggle.teammatch.owner;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yanggle.teammatch.owner.adapter.MatchProcListViewAdapter;
import com.yanggle.teammatch.owner.network.ResponseEvent;
import com.yanggle.teammatch.owner.network.ResponseListener;
import com.yanggle.teammatch.owner.network.Service;
import com.yanggle.teammatch.owner.util.BackPressCloseHandler;
import com.yanggle.teammatch.owner.util.DialogReportActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.maps.helper.Utility.getPackageInfo;

public class MatchProcActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    private Service mService;

    Toolbar toolbar;

    DrawerLayout dl_activity_match_proc;

    View ll_navigation_draw;

    LinearLayout ll_navigation_logout, ll_navigation_report, ll_navigation_space, ll_navigation_footer;

    ListView lv_match_hist;

    ImageButton ib_navigation_close;

    TextView tv_navigation_user_name;

    MatchProcListViewAdapter mMatchProcListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_proc);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mBackPressCloseHandler = new BackPressCloseHandler(this);

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        dl_activity_match_proc = findViewById(R.id.dl_activity_match_proc);

        ll_navigation_draw = findViewById(R.id.ll_navigation_draw);
        ll_navigation_logout = findViewById(R.id.ll_navigation_logout);
        ll_navigation_report = findViewById(R.id.ll_navigation_report);
        ll_navigation_space = findViewById(R.id.ll_navigation_space);
        ll_navigation_footer = findViewById(R.id.ll_navigation_footer);

        lv_match_hist = findViewById(R.id.lv_match_hist);

        ib_navigation_close = findViewById(R.id.ib_navigation_close);

        tv_navigation_user_name = findViewById(R.id.tv_navigation_user_name);

        ll_navigation_logout.setOnClickListener(mOnClickListener);
        ll_navigation_report.setOnClickListener(mOnClickListener);
        ll_navigation_space.setOnClickListener(mOnClickListener);
        ll_navigation_footer.setOnClickListener(mOnClickListener);

        ib_navigation_close.setOnClickListener(mOnClickListener);

        mMatchProcListViewAdapter = new MatchProcListViewAdapter(mContext);
        lv_match_hist.setAdapter(mMatchProcListViewAdapter);

        try {
            tv_navigation_user_name.setText(mApplicationTM.getOwnerData().getString(getString(R.string.ownerLogin_result_owner_name)));
        } catch (Exception e) {
            Log.e(TAG, "onCreate - " + e);
        }

        Log.e(TAG, "keyHash : " + getKeyHash(mContext));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mService.searchOwnerMatchProcList(searchOwnerMatchProcList_Listener);
        }catch(Exception e) {
            Log.e(TAG, "MatchProcActivity onResume - " + e);
        }
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
                if (dl_activity_match_proc.isDrawerOpen(GravityCompat.END)) {
                    dl_activity_match_proc.closeDrawers();
                } else {
                    mBackPressCloseHandler.onBackPressed();
                }

                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_toolbar_navigation:
                dl_activity_match_proc.openDrawer(ll_navigation_draw);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_navigation_close :
                    dl_activity_match_proc.closeDrawers();
                    break;
                case R.id.ll_navigation_logout :
                    Logout();
                    break;
                case R.id.ll_navigation_report :
                    dl_activity_match_proc.closeDrawers();
                    Intent mIntent = new Intent(mContext, DialogReportActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.ll_navigation_space :
                case R.id.ll_navigation_footer :
                    break;
                default :
                    break;
            }
        }
    };

    public String getKeyHash(Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.e(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    private void Logout() {
        mApplicationTM.setOwnerId(null);
        mApplicationTM.setOwnerPassword(null);
        mApplicationTM.setOwnerData(new JSONObject());

        Intent mIntent = new Intent(mContext, LoginActivity.class);
        startActivity(mIntent);
        finish();
    }

    ResponseListener searchOwnerMatchProcList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mMatchProcListViewAdapter.setMDataJSONArray(mJSONArray);
                    mMatchProcListViewAdapter.notifyDataSetChanged();

                } else {
                    mMatchProcListViewAdapter.setMDataJSONArray(new JSONArray());
                    mMatchProcListViewAdapter.notifyDataSetChanged();

                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchMatchProcList_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
