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
     * Created by maloman72 on 2018-11-30
     * */
    public void ownerLogin(ResponseListener responseListener, String owner_id, String owner_password) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.ownerLogin_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.ownerLogin_param_owner_id), owner_id);
            mJSONObject.put(mContext.getString(R.string.ownerLogin_param_owner_password), owner_password);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "ownerLogin - " + e);
        }
    }

    /**
     * 구장주 매치 진행 상황 조회
     * Created by maloman72 on 2018-12-01
     * */
    public void searchOwnerMatchProcList(ResponseListener responseListener) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.searchOwnerMatchProcList_service);

            JSONObject mJSONObject = new JSONObject();
//            mJSONObject.put(mContext.getString(R.string.searchOwnerMatchProcList_param_owner_id), "asdf1234");
            mJSONObject.put(mContext.getString(R.string.searchOwnerMatchProcList_param_owner_id), mApplicationTM.getOwnerId());
            mJSONObject.put(mContext.getString(R.string.searchOwnerMatchProcList_param_search_count), mContext.getString(R.string.match_proc_search_count));
            mJSONObject.put(mContext.getString(R.string.searchOwnerMatchProcList_param_search_page), mContext.getString(R.string.match_proc_search_page));

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchOwnerMatchProcList" + e);
        }
    }

    /**
     * 매치 승인 요청 정보 상세 조회
     * Created by maloman72 on 2018-12-02
     * */
    public void searchReqMatchApproveDetail(ResponseListener responseListener, String match_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.searchReqMatchApproveDetail_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.searchReqMatchApproveDetail_param_owner_id), mApplicationTM.getOwnerId());
            mJSONObject.put(mContext.getString(R.string.searchReqMatchApproveDetail_param_match_id), match_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchReqMatchApproveDetail - " + e);
        }
    }

    /**
     * 매치 승인/반려
     * Created by maloman72 on 2018-12-02
     * */
    public void approveMatch(ResponseListener responseListener, String match_id, String approve_return_type) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.approveMatch_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.approveMatch_param_owner_id), mApplicationTM.getOwnerId());
            mJSONObject.put(mContext.getString(R.string.approveMatch_param_match_id), match_id);
            mJSONObject.put(mContext.getString(R.string.approveMatch_param_approve_return_type), approve_return_type);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "approveMatch - " + e);
        }
    }

    /**
     * 구장 정보 상세 조회 서비스
     * Created by maloman72 on 218-11-13
     * */
    public void searchGroundDetail(ResponseListener responseListener, String ground_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.ground_detail_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.ground_detail_param_user_id), mApplicationTM.getOwnerId());
            mJSONObject.put(mContext.getString(R.string.ground_detail_param_ground_id), ground_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchGroundDetail - " + e);
        }

    }
}
