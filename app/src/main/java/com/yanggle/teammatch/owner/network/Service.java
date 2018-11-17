package com.yanggle.teammatch.owner.network;

import android.content.Context;
import android.util.Log;

import com.yanggle.teammatch.owner.ApplicationTM;
import com.yanggle.teammatch.owner.R;

import org.json.JSONObject;


/**
 * Service 실행부
 * Created by maloman72 on 2018-10-31
 */

public class Service {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private ApplicationTM mApplicationTM;

    private DefinitionNetwork mDefinitionNetwork;

    /**
     * 선언부
     * Created by maloman72 on 2018-10-31
     * */
    public Service(Context context) {
        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        mDefinitionNetwork = new DefinitionNetwork(mContext);
    }

    private void Offer(String serviceURL, JSONObject dataObject, ResponseListener responseListener) {
        try {
            String mData = mDefinitionNetwork.requsetParser(dataObject);

            Log.e(TAG, "Offer - " + mData);

            if(mData != null) {
                mApplicationTM.startProgress(mContext, "");

                mDefinitionNetwork.Networking(serviceURL, mData, responseListener);
            } else {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
            }
        } catch (Exception e) {
            Log.e(TAG, "Offer - " + e);
        }
    }

    /**
     * 로그인 서비스
     * Created by maloman72 on 2018-10-31
     * */
    public void userLogin(ResponseListener responseListener, String user_id, String user_email) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.userlogin_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.userlogin_param_user_id), user_id);
            mJSONObject.put(mContext.getString(R.string.userlogin_param_user_email), user_email);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "userLogin - " + e);
        }
    }

    /**
     * 구장 정보 상세 조회 서비스
     * Created by maloman72 on 218-11-13
     * */
    public void searchGroundDetail(ResponseListener responseListener, String ground_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.ground_detail_service);
            String user_id = mApplicationTM.getOwnerId();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.ground_detail_param_user_id), user_id);
            mJSONObject.put(mContext.getString(R.string.ground_detail_param_ground_id), ground_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchGroundDetail - " + e);
        }

    }

    /**
     * 매치 진행 상황 조회
     * Created by maloman72 on 218-11-08
     * */
    public void searchOwnerMatchProcList(ResponseListener responseListener) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "matchProc/searchOwnerMatchProcList";
            String owner_id = mApplicationTM.getOwnerId(), search_count = "50", search_page = "1";

            owner_id = "asdf1234";

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("owner_id", owner_id);
            mJSONObject.put("search_count", search_count);
            mJSONObject.put("search_page", search_page);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchMatchProcList - " + e);
        }
    }

    /**
     * 매치 수락/거절 서비스
     * Created by maloman72 on 218-11-01
     * */
    public void acceptMatch(ResponseListener responseListener, String match_id, String match_apply_id, String accept_reject_type) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.acceptMatch_service);
            String user_id = mApplicationTM.getOwnerId();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.acceptMatch_param_user_id), user_id);
            mJSONObject.put(mContext.getString(R.string.acceptMatch_param_match_id), match_id);
            mJSONObject.put(mContext.getString(R.string.acceptMatch_param_match_apply_id), match_apply_id);
            mJSONObject.put(mContext.getString(R.string.acceptMatch_param_accept_reject_type), accept_reject_type);

            Log.e(TAG, mJSONObject + "");

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "acceptMatch - " + e);
        }

    }

    /**
     * 매치 알람 정보 조회 서비스
     * Created by maloman72 on 218-11-01
     * */
    public void searchMatchAlertInfo(ResponseListener responseListener, String match_id, String match_apply_id, String match_alert_type) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.searchMatchAlertInfo_service);
            String user_id = mApplicationTM.getOwnerId();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.searchMatchAlertInfo_param_user_id), user_id);
            mJSONObject.put(mContext.getString(R.string.searchMatchAlertInfo_param_match_id), match_id);
            mJSONObject.put(mContext.getString(R.string.searchMatchAlertInfo_param_match_apply_id), match_apply_id);
            mJSONObject.put(mContext.getString(R.string.searchMatchAlertInfo_param_match_alert_type), match_alert_type);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchMatchAlertInfo - " + e);
        }

    }
}
