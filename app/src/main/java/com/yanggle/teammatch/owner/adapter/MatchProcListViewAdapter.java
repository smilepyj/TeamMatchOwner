package com.yanggle.teammatch.owner.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanggle.teammatch.owner.ApplicationTM;
import com.yanggle.teammatch.owner.ApproveMatchActivity;
import com.yanggle.teammatch.owner.GroundDetailActivity;
import com.yanggle.teammatch.owner.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MatchProcListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray = null;
    private int mDataJSONArrayCnt = 0;

    private Context mContext;
    private ApplicationTM mApplicationTM;

    private LinearLayout ll_listview_match_proc_contents, ll_listview_match_proc_state;
    private TextView tv_listview_match_proc_area, tv_listview_match_proc_ground, tv_listview_match_proc_day, tv_listview_match_proc_time, tv_listview_match_proc_cost, tv_listview_match_proc_host_name,
            tv_listview_match_proc_host_level, tv_listview_match_proc_host_member, tv_listview_match_proc_host_manager, tv_listview_match_proc_host_tel, tv_listview_match_proc_guest_name,
            tv_listview_match_proc_guest_level, tv_listview_match_proc_guest_member, tv_listview_match_proc_guest_manager, tv_listview_match_proc_guest_tel, tv_listview_match_proc_state;

    private String match_hope_ground_id, host_tel, guest_tel;

    public MatchProcListViewAdapter(Context context) {
        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();
    }

    public void setMDataJSONArray(JSONArray jsonArray) {
        mDataJSONArray = jsonArray;
        mDataJSONArrayCnt = mDataJSONArray.length();
    }

    @Override
    public int getCount() {
        return mDataJSONArrayCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final int mPosition = position;

        if(convertView == null) {
            if(mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            convertView = mLayoutInflater.inflate(R.layout.listview_match_proc, parent, false);

            ll_listview_match_proc_contents = convertView.findViewById(R.id.ll_listview_match_proc_contents);
            ll_listview_match_proc_state = convertView.findViewById(R.id.ll_listview_match_proc_state);

            tv_listview_match_proc_area = convertView.findViewById(R.id.tv_listview_match_proc_area);
            tv_listview_match_proc_ground = convertView.findViewById(R.id.tv_listview_match_proc_ground);
            tv_listview_match_proc_day = convertView.findViewById(R.id.tv_listview_match_proc_day);
            tv_listview_match_proc_time = convertView.findViewById(R.id.tv_listview_match_proc_time);
            tv_listview_match_proc_cost = convertView.findViewById(R.id.tv_listview_match_proc_cost);
            tv_listview_match_proc_host_name = convertView.findViewById(R.id.tv_listview_match_proc_host_name);
            tv_listview_match_proc_host_level = convertView.findViewById(R.id.tv_listview_match_proc_host_level);
            tv_listview_match_proc_host_member = convertView.findViewById(R.id.tv_listview_match_proc_host_member);
            tv_listview_match_proc_host_manager = convertView.findViewById(R.id.tv_listview_match_proc_host_manager);
            tv_listview_match_proc_host_tel = convertView.findViewById(R.id.tv_listview_match_proc_host_tel);
            tv_listview_match_proc_guest_name = convertView.findViewById(R.id.tv_listview_match_proc_guest_name);
            tv_listview_match_proc_guest_level = convertView.findViewById(R.id.tv_listview_match_proc_guest_level);
            tv_listview_match_proc_guest_member = convertView.findViewById(R.id.tv_listview_match_proc_guest_member);
            tv_listview_match_proc_guest_manager = convertView.findViewById(R.id.tv_listview_match_proc_guest_manager);
            tv_listview_match_proc_guest_tel = convertView.findViewById(R.id.tv_listview_match_proc_guest_tel);
            tv_listview_match_proc_state = convertView.findViewById(R.id.tv_listview_match_proc_state);

            ImageButton ib_listview_match_proc_ground = convertView.findViewById(R.id.ib_listview_match_proc_ground);
            ImageButton ib_listview_match_proc_host_tel = convertView.findViewById(R.id.ib_listview_match_proc_host_tel);
            ImageButton ib_listview_match_proc_guest_tel = convertView.findViewById(R.id.ib_listview_match_proc_guest_tel);

//            ll_listview_match_proc_state.setOnClickListener(mOnClickListener);

            tv_listview_match_proc_ground.setOnClickListener(mOnClickListener);
            ib_listview_match_proc_ground.setOnClickListener(mOnClickListener);
            ib_listview_match_proc_host_tel.setOnClickListener(mOnClickListener);
            ib_listview_match_proc_guest_tel.setOnClickListener(mOnClickListener);

//            tv_listview_match_proc_state.setOnClickListener(mOnClickListener);
        }

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            final String match_id = mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_id));
            match_hope_ground_id = mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_id));

            tv_listview_match_proc_area.setText(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_sido_name)) + mContext.getString(R.string.matchproc_listview_hypen) + mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_gugun_name)));
            tv_listview_match_proc_ground.setText(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_name)));
            Date mDate = new SimpleDateFormat(mContext.getString(R.string.type_date_format_base), Locale.getDefault()).parse(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_date)));
            String mDay = new SimpleDateFormat(mContext.getString(R.string.type_date_format_view), Locale.getDefault()).format(mDate);
            tv_listview_match_proc_day.setText(mDay);
            Date mTime_1 = new SimpleDateFormat(mContext.getString(R.string.type_time_format_base), Locale.getDefault()).parse(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_start_time)));
            Date mTime_2 = new SimpleDateFormat(mContext.getString(R.string.type_time_format_base), Locale.getDefault()).parse(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_end_time)));
            String mStartTime = new SimpleDateFormat(mContext.getString(R.string.type_time_format_view), Locale.getDefault()).format(mTime_1);
            String mEndTime = new SimpleDateFormat(mContext.getString(R.string.type_time_format_view), Locale.getDefault()).format(mTime_2);
            tv_listview_match_proc_time.setText(mStartTime + mContext.getString(R.string.matchproc_listview_wave) + mEndTime);
            String match_hope_ground_cost = String.format("%,d", Integer.parseInt(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_hope_ground_cost))));
            tv_listview_match_proc_cost.setText(match_hope_ground_cost + "원");
            tv_listview_match_proc_host_name.setText(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_name)));
            tv_listview_match_proc_host_level.setText(mApplicationTM.getC002().get(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_lvl))));
            String host_team_point = "".equals(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_point)))?"0":mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_point));
            tv_listview_match_proc_host_member.setText(host_team_point + "점");
            tv_listview_match_proc_host_manager.setText(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_user_name)));
            host_tel = mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_host_team_user_tel));
            tv_listview_match_proc_host_tel.setText(host_tel);
            tv_listview_match_proc_guest_name.setText(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_name)));
            tv_listview_match_proc_guest_level.setText(mApplicationTM.getC002().get(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_lvl))));
            String guest_team_point = "".equals(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_point)))?"0":mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_point));
            tv_listview_match_proc_guest_member.setText(guest_team_point + "점");
            tv_listview_match_proc_guest_manager.setText(mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_user_name)));
            guest_tel = mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_guest_team_user_tel));
            tv_listview_match_proc_guest_tel.setText(guest_tel);

            final String match_proc_cd = mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_proc_cd));
//            String[] C004_code = mContext.getResources().getStringArray(R.array.C004_code);

            Log.e(TAG, "match_proc_cd - " + match_proc_cd + ", searchOwnerMatchProcList_result_match_proc_cd_name - " + mJSONObject.getString(mContext.getString(R.string.searchOwnerMatchProcList_result_match_proc_cd_name)));

            if("C004003".equals(match_proc_cd)) {  // 사용승인 확인중
                ll_listview_match_proc_state.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_0D68BE));
                tv_listview_match_proc_state.setText("사용승인\n확인중");
            }else if("C004004".equals(match_proc_cd)) {  // 사용승인 완료
                ll_listview_match_proc_state.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_007338));
                tv_listview_match_proc_state.setText("사용승인\n완료");
            }else if("C004005".equals(match_proc_cd)) {  // 구장이용 완료
                ll_listview_match_proc_state.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_A8A8A8));
                tv_listview_match_proc_state.setText("구장이용\n완료");
            }else if("C004006".equals(match_proc_cd)) {  // 구장이용 완료
                ll_listview_match_proc_state.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_A8A8A8));
                tv_listview_match_proc_state.setText("구장이용\n완료");
            }else if("C004007".equals(match_proc_cd)) {  // 사용승인 거절
                ll_listview_match_proc_state.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_E04549));
                tv_listview_match_proc_state.setText("사용승인\n거절");
            }

            ll_listview_match_proc_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e(TAG, match_proc_cd);
                    Log.e(TAG, match_id);

                    if("C004003".equals(match_proc_cd)) {
                        Intent mIntent = new Intent(mContext, ApproveMatchActivity.class);
                        mIntent.putExtra(mContext.getString(R.string.approve_match_match_id), match_id);
                        mContext.startActivity(mIntent);
                    }
                }
            });

//            tv_listview_match_proc_state.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Log.e(TAG, match_proc_cd);
//                    Log.e(TAG, match_id);
//
//                    if("C004003".equals(match_proc_cd)) {
//                        Intent mIntent = new Intent(mContext, ApproveMatchActivity.class);
//                        mIntent.putExtra(mContext.getString(R.string.approve_match_match_id), match_id);
//                        mContext.startActivity(mIntent);
//                    }
//                }
//            });

        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            e.printStackTrace();
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        return convertView;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
                case R.id.tv_listview_match_proc_ground :
                case R.id.ib_listview_match_proc_ground :
                    mIntent = new Intent(mContext, GroundDetailActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.ground_detail_ground_id), match_hope_ground_id);
                    mContext.startActivity(mIntent);
                    break;
                case R.id.ib_listview_match_proc_host_tel :
                    if(host_tel != null) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(host_tel)));
                        mContext.startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, mContext.getString(R.string.match_proc_no_phone_number));
                    }
                    break;
                case R.id.ib_listview_match_proc_guest_tel :
                    if(guest_tel != null) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(guest_tel)));
                        mContext.startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, mContext.getString(R.string.match_proc_no_phone_number));
                    }
                    break;
//                case R.id.ll_listview_match_proc_state :
//                case R.id.tv_listview_match_proc_state :
//                    Log.e(TAG, match_proc_cd);
//
//                    if("C004003".equals(match_proc_cd)) {
//                        mIntent = new Intent(mContext, ApproveMatchActivity.class);
//                        mIntent.putExtra(mContext.getString(R.string.approve_match_match_id), match_id);
//                        mContext.startActivity(mIntent);
//                    }
//                    break;
                default :
                    break;
            }
        }
    };
}
