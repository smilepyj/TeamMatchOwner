package com.yanggle.teammatch.owner;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.yanggle.teammatch.owner.adapter.MatchProcListViewAdapter;
import com.yanggle.teammatch.owner.network.ResponseEvent;
import com.yanggle.teammatch.owner.network.ResponseListener;
import com.yanggle.teammatch.owner.network.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.maps.helper.Utility.getPackageInfo;

public class MatchProcActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    ListView lv_match_hist;

    MatchProcListViewAdapter mMatchProcListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_proc);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        lv_match_hist = findViewById(R.id.lv_match_hist);

        mMatchProcListViewAdapter = new MatchProcListViewAdapter(mContext);
        lv_match_hist.setAdapter(mMatchProcListViewAdapter);

        Log.e(TAG, "keyHash : " + getKeyHash(mContext));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mService.searchOwnerMatchProcList(searchMatchProcList_Listener);
        }catch(Exception e) {
            Log.e(TAG, "MatchProcActivity onResume - " + e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ResponseListener searchMatchProcList_Listener = new ResponseListener() {
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
}
