package com.yanggle.teammatch.owner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanggle.teammatch.owner.ApplicationTM;
import com.yanggle.teammatch.owner.GroundDetailActivity;
import com.yanggle.teammatch.owner.R;
import com.yanggle.teammatch.owner.network.ResponseEvent;
import com.yanggle.teammatch.owner.network.ResponseListener;
import com.yanggle.teammatch.owner.network.Service;

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

    private Service mService;

    public MatchProcListViewAdapter(Context context) {
        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        mService = new Service(mContext);
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
        final int mPosition = position;

        if(convertView == null) {
            if(mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            convertView = mLayoutInflater.inflate(R.layout.listview_match_proc, parent, false);
        }

        LinearLayout ll_list_view_match_proc_opponent_line = convertView.findViewById(R.id.ll_list_view_match_proc_opponent_line);
        LinearLayout ll_list_view_match_proc_opponent_info = convertView.findViewById(R.id.ll_list_view_match_proc_opponent_info);

        TextView tv_listview_match_proc_area = convertView.findViewById(R.id.tv_listview_match_proc_area);
        TextView tv_listview_match_proc_ground = convertView.findViewById(R.id.tv_listview_match_proc_ground);
        ImageButton ib_listview_match_proc_ground = convertView.findViewById(R.id.ib_listview_match_proc_ground);
        TextView tv_listview_match_proc_day = convertView.findViewById(R.id.tv_listview_match_proc_day);
        TextView tv_listview_match_proc_time = convertView.findViewById(R.id.tv_listview_match_proc_time);
        TextView tv_listview_match_proc_team_name = convertView.findViewById(R.id.tv_listview_match_proc_team_name);
        TextView tv_listview_match_proc_team_level = convertView.findViewById(R.id.tv_listview_match_proc_team_level);
        TextView tv_listview_match_proc_team_member = convertView.findViewById(R.id.tv_listview_match_proc_team_member);
        TextView tv_listview_match_proc_opponent_name = convertView.findViewById(R.id.tv_listview_match_proc_opponent_name);
        TextView tv_listview_match_proc_opponent_level = convertView.findViewById(R.id.tv_listview_match_proc_opponent_level);
        TextView tv_listview_match_proc_opponent_point = convertView.findViewById(R.id.tv_listview_match_proc_opponent_point);
        ImageView iv_listview_match_proc_pre_payment = convertView.findViewById(R.id.iv_listview_match_proc_pre_payment);
        TextView tv_listview_match_proc_pre_payment = convertView.findViewById(R.id.tv_listview_match_proc_pre_payment);
        TextView tv_match_proc_name = convertView.findViewById(R.id.tv_match_proc_name);
        LinearLayout ll_listview_match_proc = convertView.findViewById(R.id.ll_listview_match_proc);

        String match_proc_cd = "";
        String guest_host_type = "";

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            final String match_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_id)).toString();
            final String match_apply_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_apply_id)).toString();
            final String match_hope_ground_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_id)).toString();

            tv_listview_match_proc_area.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_sido_name)).toString() + mContext.getString(R.string.search_result_list_hyphen) + mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_gugun_name)).toString());
            tv_listview_match_proc_ground.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_name)).toString());
            Date mDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_date)).toString());
            String mHopeDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
            tv_listview_match_proc_day.setText(mHopeDate);
            Date mStartTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_start_time)).toString());
//            String mHopeStartTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_view), Locale.getDefault()).format(mStartTime);
            String mHopeStartTime = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_start_time)).toString().substring(0, 2);
            Date mEndTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_end_time)).toString());
            String mHopeEndTime = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_end_time)).toString().substring(0, 2);
            tv_listview_match_proc_time.setText(mHopeStartTime + "시" + " ~ " + mHopeEndTime + "시");
            tv_listview_match_proc_team_name.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_host_name)).toString());
            tv_listview_match_proc_team_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_team_lvl)).toString()));
            tv_listview_match_proc_team_member.setText(mApplicationTM.getC003().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_team_member)).toString()));
            tv_listview_match_proc_opponent_name.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_name)).toString());
            tv_listview_match_proc_opponent_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_lvl)).toString()));
            String opponent_point = mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_point))==null?"":mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_point)).toString();
            tv_listview_match_proc_opponent_point.setText(opponent_point==""?"":(opponent_point + "점"));
            String pre_payment_at = mJSONObject.get(mContext.getString(R.string.matchProclist_result_pre_payment_at))==null?"":mJSONObject.get(mContext.getString(R.string.matchProclist_result_pre_payment_at)).toString();
            if("Y".equals(pre_payment_at)) {
                iv_listview_match_proc_pre_payment.setVisibility(View.VISIBLE);
                tv_listview_match_proc_pre_payment.setVisibility(View.VISIBLE);
            }else {
                iv_listview_match_proc_pre_payment.setVisibility(View.GONE);
                tv_listview_match_proc_pre_payment.setVisibility(View.GONE);
            }

            Log.e(TAG, mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd_name)).toString());

            String match_proc_cd_name;
            final String match_proc_type;

            match_proc_cd = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd)).toString();
            guest_host_type = mJSONObject.get(mContext.getString(R.string.matchProclist_result_guest_host_type)).toString();

            ll_list_view_match_proc_opponent_line.setVisibility(View.VISIBLE);
            ll_list_view_match_proc_opponent_info.setVisibility(View.VISIBLE);

            String eval_at = mJSONObject.get(mContext.getString(R.string.matchProclist_result_eval_at)).toString();

            if("C004001".equals(match_proc_cd)) {
                if(!"".equals(match_apply_id) && "G".equals(guest_host_type)) {
                    match_proc_cd_name = "매치 신청 중";
                    match_proc_type = "3";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_2));
                }else {
                    if("".equals(match_apply_id)) {
                        ll_list_view_match_proc_opponent_line.setVisibility(View.GONE);
                        ll_list_view_match_proc_opponent_info.setVisibility(View.GONE);
                        match_proc_type = "1";
                    }else {
                        match_proc_type = "2";
                    }

                    match_proc_cd_name = "매치 등록 중";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_1));
                }
            }else if("C004003".equals(match_proc_cd)) {
                match_proc_cd_name = "매치 승인 중";
                match_proc_type = "4";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_3));
            }else if("C004004".equals(match_proc_cd)) {
                match_proc_cd_name = "매치 진행 중";
                match_proc_type = "5";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_4));
            }else if("C004005".equals(match_proc_cd)) {
                if("Y".equals(eval_at)) {
                    match_proc_cd_name = "매치 완료";
                    match_proc_type = "7";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_5));
                }else {
                    match_proc_cd_name = "매치 진행 중";
                    match_proc_type = "6";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_4));
                }
            }else {
                match_proc_cd_name = "매치 완료";
                match_proc_type = "7";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_5));
            }

            tv_match_proc_name.setText(match_proc_cd_name);

            tv_listview_match_proc_ground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, GroundDetailActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.ground_detail_ground_id), match_hope_ground_id);
                    mContext.startActivity(mIntent);
                }
            });

            ib_listview_match_proc_ground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, GroundDetailActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.ground_detail_ground_id), match_hope_ground_id);
                    mContext.startActivity(mIntent);
                }
            });

            ll_listview_match_proc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if("1".equals(match_proc_type)) {
                        mApplicationTM.makeToast(mContext, "등록된 매치에 대해 신청된 정보가 없습니다.");
                    }else if("2".equals(match_proc_type)) {
                        mService.searchMatchAlertInfo(searchMatchAlertInfo_Listener, match_id, match_apply_id, match_proc_type);
                    }else if("3".equals(match_proc_type)) {

                    }else if("4".equals(match_proc_type)) {
                        mService.searchMatchAlertInfo(searchMatchAlertInfo_Listener, match_id, match_apply_id, match_proc_type);
                    }else if("5".equals(match_proc_type)) {

                    }else if("6".equals(match_proc_type)) {
                        mService.searchMatchAlertInfo(searchMatchAlertInfo_Listener, match_id, match_apply_id, match_proc_type);
                    }else if("7".equals(match_proc_type)) {

                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            e.printStackTrace();
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        return convertView;
    }

    ResponseListener searchMatchAlertInfo_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(mContext.getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));
                    JSONObject data = (JSONObject)mJSONArray.get(0);

                    String match_alert_type = data.getString("match_alert_type");

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "searchMatchAlertInfo_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
