package com.yanggle.teammatch.owner.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanggle.teammatch.owner.ApplicationTM;
import com.yanggle.teammatch.owner.R;
import com.yanggle.teammatch.owner.network.ResponseEvent;
import com.yanggle.teammatch.owner.network.ResponseListener;
import com.yanggle.teammatch.owner.network.Service;

import org.json.JSONObject;

public class DialogMatchSuccessActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    LinearLayout ll_match_success_dialog_host_regist, ll_match_success_dialog_guest_regist;
    TextView tv_match_success_sub_title, tv_match_success_host_team_name, tv_match_success_guest_team_name, tv_match_success_host_team_lvl, tv_match_success_guest_team_lvl,
            tv_match_success_host_team_member, tv_match_success_guest_team_member, tv_match_success_host_manager, tv_match_success_guest_manager, tv_match_success_host_phone_manager, tv_match_success_guest_phone_manager,
            tv_match_success_time, tv_match_success_ground, tv_match_success_cost;
    ImageButton ib_match_success_host_call_manager, ib_match_success_guest_call_manager;
    CheckBox cb_match_success_check;
    Button bt_match_success_yes, bt_match_success_no;

    String host_phone_manager, guest_phone_manager, match_id, approve_return_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_match_success);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        ll_match_success_dialog_host_regist = findViewById(R.id.ll_match_success_dialog_host_regist);
        ll_match_success_dialog_guest_regist = findViewById(R.id.ll_match_success_dialog_guest_regist);

        tv_match_success_sub_title = findViewById(R.id.tv_match_success_sub_title);
        tv_match_success_host_team_name = findViewById(R.id.tv_match_success_host_team_name);
        tv_match_success_guest_team_name = findViewById(R.id.tv_match_success_guest_team_name);
        tv_match_success_host_team_lvl = findViewById(R.id.tv_match_success_host_team_lvl);
        tv_match_success_guest_team_lvl = findViewById(R.id.tv_match_success_guest_team_lvl);
        tv_match_success_host_team_member = findViewById(R.id.tv_match_success_host_team_member);
        tv_match_success_guest_team_member = findViewById(R.id.tv_match_success_guest_team_member);
        tv_match_success_host_manager = findViewById(R.id.tv_match_success_host_manager);
        tv_match_success_guest_manager = findViewById(R.id.tv_match_success_guest_manager);
        tv_match_success_host_phone_manager = findViewById(R.id.tv_match_success_host_phone_manager);
        tv_match_success_guest_phone_manager = findViewById(R.id.tv_match_success_guest_phone_manager);
        tv_match_success_time = findViewById(R.id.tv_match_success_time);
        tv_match_success_ground = findViewById(R.id.tv_match_success_ground);
        tv_match_success_cost = findViewById(R.id.tv_match_success_cost);

        ib_match_success_host_call_manager = findViewById(R.id.ib_match_success_host_call_manager);
        ib_match_success_guest_call_manager = findViewById(R.id.ib_match_success_guest_call_manager);
        cb_match_success_check = findViewById(R.id.cb_match_success_check);

        bt_match_success_yes = findViewById(R.id.bt_match_success_yes);
        bt_match_success_no = findViewById(R.id.bt_match_success_no);

        ib_match_success_host_call_manager.setOnClickListener(mOnClickListener);
        ib_match_success_guest_call_manager.setOnClickListener(mOnClickListener);
        tv_match_success_host_phone_manager.setOnClickListener(mOnClickListener);
        tv_match_success_guest_phone_manager.setOnClickListener(mOnClickListener);
        bt_match_success_yes.setOnClickListener(mOnClickListener);
        bt_match_success_no.setOnClickListener(mOnClickListener);

        setData();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
                case R.id.ib_match_success_host_call_manager :
                    if(host_phone_manager != null && !"".equals(host_phone_manager)) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(host_phone_manager)));
                        startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, getString(R.string.manager_no_phone_num));
                    }
                    break;
                case R.id.tv_match_success_host_phone_manager :
                    if(host_phone_manager != null && !"".equals(host_phone_manager)) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(host_phone_manager)));
                        startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, getString(R.string.manager_no_phone_num));
                    }
                    break;
                case R.id.ib_match_success_guest_call_manager :
                    if(guest_phone_manager != null && !"".equals(guest_phone_manager)) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(guest_phone_manager)));
                        startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, getString(R.string.manager_no_phone_num));
                    }
                    break;
                case R.id.tv_match_success_guest_phone_manager :
                    if(guest_phone_manager != null && !"".equals(guest_phone_manager)) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(guest_phone_manager)));
                        startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, getString(R.string.manager_no_phone_num));
                    }
                    break;
                case R.id.bt_match_success_yes :
                    if (!cb_match_success_check.isChecked()) {
                        mApplicationTM.makeToast(mContext, "구장이용료 관련 알림에 동의해 주세요.");
                        return;
                    }
                    approve_return_type = "A";
                    mService.approveMatch(approveMatch_Listener, match_id, approve_return_type);
                    break;
                case R.id.bt_match_success_no :
                    if (!cb_match_success_check.isChecked()) {
                        mApplicationTM.makeToast(mContext, "구장이용료 관련 알림에 동의해 주세요.");
                        return;
                    }
                    approve_return_type = "R";
                    mService.approveMatch(approveMatch_Listener, match_id, approve_return_type);
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * Dialog Data Set
     * Created by maloman72 on 2018-11-14
     * */
    private void setData() {
        String mType = getIntent().getStringExtra(getString(R.string.match_success_extra_type));

            String sub_title = getIntent().getStringExtra("SUB_TITLE");
            match_id = getIntent().getStringExtra("MATCH_ID");
            String host_team_name = getIntent().getStringExtra("HOST_TEAM_NAME");
            String host_team_lvl = getIntent().getStringExtra("HOST_TEAM_LVL");
            String host_team_point = "".equals(getIntent().getStringExtra("HOST_TEAM_POINT"))?"0":getIntent().getStringExtra("HOST_TEAM_POINT");
            String host_team_user_name = getIntent().getStringExtra("HOST_TEAM_USER_NAME");
            host_phone_manager = getIntent().getStringExtra("HOST_TEAM_USER_TEL");
            String guest_team_name = getIntent().getStringExtra("GUEST_TEAM_NAME");
            String guest_team_lvl = getIntent().getStringExtra("GUEST_TEAM_LVL");
            String guest_team_point = "".equals(getIntent().getStringExtra("GUEST_TEAM_POINT"))?"0":getIntent().getStringExtra("GUEST_TEAM_POINT");
            String guest_team_user_name = getIntent().getStringExtra("GUEST_TEAM_USER_NAME");
            guest_phone_manager = getIntent().getStringExtra("GUEST_TEAM_USER_TEL");
            String hope_match_ground = getIntent().getStringExtra("HOPE_MATCH_GROUND");
            String match_time = getIntent().getStringExtra("MATCH_TIME");
            String ground_cost = getIntent().getStringExtra("GROUND_COST");

            tv_match_success_sub_title.setText(sub_title);
            tv_match_success_host_team_name.setText(host_team_name);
            tv_match_success_guest_team_name.setText(guest_team_name);
            tv_match_success_host_team_lvl.setText(host_team_lvl);
            tv_match_success_guest_team_lvl.setText(guest_team_lvl);
            tv_match_success_host_team_member.setText(host_team_point + "점");
            tv_match_success_guest_team_member.setText(guest_team_point + "점");
            tv_match_success_host_manager.setText(host_team_user_name);
            tv_match_success_guest_manager.setText(guest_team_user_name);
            tv_match_success_host_phone_manager.setText(host_phone_manager);
            tv_match_success_guest_phone_manager.setText(guest_phone_manager);
            tv_match_success_time.setText(match_time);
            tv_match_success_ground.setText(hope_match_ground);
            tv_match_success_cost.setText(ground_cost);

    }

    ResponseListener approveMatch_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(mContext.getString(R.string.result_code)))) {

                    if("A".equals(approve_return_type)) {
                        mApplicationTM.makeToast(mContext, "매치 승인을 완료했습니다.");
                    }else if("R".equals(approve_return_type)) {
                        mApplicationTM.makeToast(mContext, "매치 반려를 완료했습니다.");
                    }

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "searchMatchList_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
                finish();
            }
        }
    };
}
