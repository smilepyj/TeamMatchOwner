package com.yanggle.teammatch.owner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanggle.teammatch.owner.network.ResponseEvent;
import com.yanggle.teammatch.owner.network.ResponseListener;
import com.yanggle.teammatch.owner.network.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ApproveMatchActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    TextView tv_approve_match_area, tv_approve_match_ground, tv_approve_match_day, tv_approve_match_time, tv_approve_match_cost,
            tv_approve_match_host_name, tv_approve_match_host_level, tv_approve_match_host_member, tv_approve_match_host_manager, tv_approve_match_host_tel,
            tv_approve_match_guest_name, tv_approve_match_guest_level, tv_approve_match_guest_member, tv_approve_match_guest_manager, tv_approve_guest_host_tel;

    ImageButton ib_approve_match_ground, ib_approve_match_host_tel, ib_approve_guest_host_tel;

    CheckBox cb_approve_match_warning;

    LinearLayout ll_approve_match_no, ll_approve_match_yes;

    String match_id, match_hope_ground_id, host_tel, guest_tel, approve_return_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_match);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        tv_approve_match_area = findViewById(R.id.tv_approve_match_area);
        tv_approve_match_ground = findViewById(R.id.tv_approve_match_ground);
        tv_approve_match_day = findViewById(R.id.tv_approve_match_day);
        tv_approve_match_time = findViewById(R.id.tv_approve_match_time);
        tv_approve_match_cost = findViewById(R.id.tv_approve_match_cost);
        tv_approve_match_host_name = findViewById(R.id.tv_approve_match_host_name);
        tv_approve_match_host_level = findViewById(R.id.tv_approve_match_host_level);
        tv_approve_match_host_member = findViewById(R.id.tv_approve_match_host_member);
        tv_approve_match_host_manager = findViewById(R.id.tv_approve_match_host_manager);
        tv_approve_match_host_tel = findViewById(R.id.tv_approve_match_host_tel);
        tv_approve_match_guest_name = findViewById(R.id.tv_approve_match_guest_name);
        tv_approve_match_guest_level = findViewById(R.id.tv_approve_match_guest_level);
        tv_approve_match_guest_member = findViewById(R.id.tv_approve_match_guest_member);
        tv_approve_match_guest_manager = findViewById(R.id.tv_approve_match_guest_manager);
        tv_approve_guest_host_tel = findViewById(R.id.tv_approve_guest_host_tel);

        ib_approve_match_ground = findViewById(R.id.ib_approve_match_ground);
        ib_approve_match_host_tel = findViewById(R.id.ib_approve_match_host_tel);
        ib_approve_guest_host_tel = findViewById(R.id.ib_approve_guest_host_tel);

        cb_approve_match_warning = findViewById(R.id.cb_approve_match_warning);

        ll_approve_match_no = findViewById(R.id.ll_approve_match_no);
        ll_approve_match_yes = findViewById(R.id.ll_approve_match_yes);

        tv_approve_match_ground.setOnClickListener(mOnClickListener);
        ib_approve_match_ground.setOnClickListener(mOnClickListener);
        ib_approve_match_host_tel.setOnClickListener(mOnClickListener);
        ib_approve_guest_host_tel.setOnClickListener(mOnClickListener);
        ll_approve_match_no.setOnClickListener(mOnClickListener);
        ll_approve_match_yes.setOnClickListener(mOnClickListener);

        match_id = getIntent().getStringExtra(mContext.getString(R.string.approve_match_match_id));

        mService.searchOwnerMatchAlertInfo(searchOwnerMatchAlertInfo_Listener, match_id, "7");
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

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
                case R.id.tv_approve_match_ground :
                case R.id.ib_approve_match_ground :
                    mIntent = new Intent(mContext, GroundDetailActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.ground_detail_ground_id), match_hope_ground_id);
                    mContext.startActivity(mIntent);
                    break;
                case R.id.ib_approve_match_host_tel :
                    if(host_tel != null) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(host_tel)));
                        mContext.startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, mContext.getString(R.string.match_proc_no_phone_number));
                    }
                    break;
                case R.id.ib_approve_guest_host_tel :
                    if(guest_tel != null) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(guest_tel)));
                        mContext.startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, mContext.getString(R.string.match_proc_no_phone_number));
                    }
                    break;
                case R.id.ll_approve_match_no :
                    approve_return_type = "R";
                    mService.approveMatch(approveMatch_Listener, match_id, approve_return_type);
                    break;
                case R.id.ll_approve_match_yes :
                    approve_return_type = "A";
                    mService.approveMatch(approveMatch_Listener, match_id, approve_return_type);
                    break;
                default :
                    break;
            }
        }
    };

    ResponseListener searchOwnerMatchAlertInfo_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    JSONObject json = (JSONObject)mJSONArray.get(0);

                    match_hope_ground_id = json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_id));

                    tv_approve_match_area.setText(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_sido_name)) + mContext.getString(R.string.matchproc_listview_hypen) + json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_gugun_name)));
                    tv_approve_match_ground.setText(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_name)));
                    Date mDate = new SimpleDateFormat(mContext.getString(R.string.type_date_format_base), Locale.getDefault()).parse(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_date)));
                    String mDay = new SimpleDateFormat(mContext.getString(R.string.type_date_format_view), Locale.getDefault()).format(mDate);
                    tv_approve_match_day.setText(mDay);
                    Date mTime_1 = new SimpleDateFormat(mContext.getString(R.string.type_time_format_base), Locale.getDefault()).parse(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_start_time)));
                    Date mTime_2 = new SimpleDateFormat(mContext.getString(R.string.type_time_format_base), Locale.getDefault()).parse(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_end_time)));
                    String mStartTime = new SimpleDateFormat(mContext.getString(R.string.type_time_format_view), Locale.getDefault()).format(mTime_1);
                    String mEndTime = new SimpleDateFormat(mContext.getString(R.string.type_time_format_view), Locale.getDefault()).format(mTime_2);
                    tv_approve_match_time.setText(mStartTime + mContext.getString(R.string.matchproc_listview_wave) + mEndTime);
                    String match_hope_ground_cost = String.format("%,d", Integer.parseInt(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_cost))));
                    tv_approve_match_cost.setText(match_hope_ground_cost + "원");
                    tv_approve_match_host_name.setText(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_name)));
                    tv_approve_match_host_level.setText(mApplicationTM.getC002().get(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_lvl))));
                    String host_team_point = "".equals(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_point)))?"0":json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_point));
                    tv_approve_match_host_member.setText(host_team_point + "점");
                    tv_approve_match_host_manager.setText(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_user_name)));
                    host_tel = json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_user_tel));
                    tv_approve_match_host_tel.setText(host_tel);
                    tv_approve_match_guest_name.setText(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_name)));
                    tv_approve_match_guest_level.setText(mApplicationTM.getC002().get(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_lvl))));
                    String guest_team_point = "".equals(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_point)))?"0":json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_point));
                    tv_approve_match_guest_member.setText(guest_team_point + "점");
                    tv_approve_match_guest_manager.setText(json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_user_name)));
                    guest_tel = json.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_user_tel));
                    tv_approve_guest_host_tel.setText(guest_tel);
                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchOwnerMatchAlertInfo_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };

    ResponseListener approveMatch_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(mContext.getString(R.string.result_code)))) {

                    if("A".equals(approve_return_type)) {

                    }else if("R".equals(approve_return_type)) {
                        mApplicationTM.makeToast(mContext, "매치 거절을 완료했습니다.");
                    }

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "approveMatch_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
                finish();
            }
        }
    };
}
