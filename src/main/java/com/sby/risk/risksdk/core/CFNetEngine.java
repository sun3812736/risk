package com.sby.risk.risksdk.core;

import android.content.Context;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sby.risk.risksdk.constants.NetConfig;
import com.sby.risk.risksdk.constants.StaticsConfig;
import com.sby.risk.risksdk.http.CFHttpClient;
import com.sby.risk.risksdk.util.JsonUtil;
import com.sby.risk.risksdk.util.StatLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

/**
 * Created by sby on 2017/8/10.
 */
public class CFNetEngine {


    private Context context;

    private CFHttpClient mHttpClient;

    private String mKey;

    /** 重试次数 */
    protected int mRetrytimes = NetConfig.RETRY_TIMES;

    public static final String TAG = "SunStat::TaNetEngine";

    /** 是否支持断点 */
    protected boolean mCanContinue;

    private String mHostUrl = NetConfig.ONLINE_URL;

    private PaJsonHttpResponseHandler mTaskHandler;

    private IUpLoadlistener mUpLoadlistener;
    private ForResultListener mForResultListener;
    private HashMap<String, String> headers;

    private RequestParams requestParams;

    Header[] reqHeaders;

    Header header;

    public CFNetEngine(Context context, IUpLoadlistener upLoadlistener) {

        this(context, null, upLoadlistener);

    }

    public CFNetEngine(Context context, CFHttpClient httpClient, IUpLoadlistener upLoadlistener) {
        this.context = context;
        mHttpClient = httpClient;
        mCanContinue = true;
        mTaskHandler = new PaJsonHttpResponseHandler(true);
        mUpLoadlistener = upLoadlistener;
        init ();
    }
    public CFNetEngine(Context context, CFHttpClient httpClient, IUpLoadlistener upLoadlistener, ForResultListener forResultListener) {
        this.context = context;
        mHttpClient = httpClient;
        mCanContinue = true;
        mTaskHandler = new PaJsonHttpResponseHandler(true);
        mUpLoadlistener = upLoadlistener;
        mForResultListener = forResultListener;
        init ();
    }
    private void init() {

        if (StaticsConfig.DEBUG) {
            mHostUrl = NetConfig.URL;
        }
        headers = new HashMap<String, String>();
        requestParams = new RequestParams();
    }

    public CFHttpClient getHttpClient() {
        return mHttpClient;
    }

    public void setHttpClient(CFHttpClient mHttpClient) {
        this.mHttpClient = mHttpClient;
    }

    public String start(final String... strings) {



        String str = JsonUtil.toJSONString(HeadrHandle.getHeader(context));

        StatLog.d(TAG, "head:" + str);
        if (headers.size() >= 0) {
            headers.clear();
        }
        headers.put(NetConfig.HEADERS_KEY, str);
        //headers.put("Accept", "application/json");
        requestParams.remove(NetConfig.PARAMS_KEY);
        requestParams.put(NetConfig.PARAMS_KEY, strings[0]);
        StatLog.d(TAG, "body:" + strings[0]);
        ByteArrayEntity entity = null;
        entity = new ByteArrayEntity(strings[0].getBytes());

        if (headers != null && headers.size() > 0) {
            reqHeaders = new Header[headers.size()];
            Set<String> keys = headers.keySet();
            int index = 0;
            for (final String mykey : keys) {
                header = new Header() {
                    @Override
                    public String getName() {
                        return mykey;
                    }

                    @Override
                    public String getValue() {
                        return headers.get(mykey);
                    }

                    @Override
                    public HeaderElement[] getElements() throws ParseException {
                        return new HeaderElement[0];
                    }
                };
                reqHeaders[index++] = header;
            }

        }


        try {
            CFHttpClient.post(context, mHostUrl, reqHeaders, entity, "application/json", mTaskHandler );
//            CFHttpClient.post(context, mHostUrl, reqHeaders, requestParams, "application/x-www-form-urlencoded; charset=UTF-8", mTaskHandler );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void cancel() {

        CFHttpClient.cancle(mKey, true);
    }

    private  class  PaJsonHttpResponseHandler extends AsyncHttpResponseHandler {

        public PaJsonHttpResponseHandler() {
        }

        public PaJsonHttpResponseHandler(Looper looper) {
            super(looper);
        }

        public PaJsonHttpResponseHandler(boolean usePoolThread) {
            super(usePoolThread);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
          String result=  new String(responseBody);
            if (mForResultListener != null) {
                String resCode = null;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    resCode = jsonObject.getString("success");
                    if (resCode.equals("true")) {
                        mForResultListener.onSucess();
                        mForResultListener = null;
                    } else {
                        String errordes = jsonObject.getString("errorDescription");
                        mForResultListener.onFailure(errordes);
                        mForResultListener = null;
                    }
                } catch (JSONException e) {
                }
            }
            if (mUpLoadlistener != null) {
                mUpLoadlistener.onSucess();
            }

            for (Header tmp : headers) {
                StatLog.d(TAG, tmp.getName() + ":" + tmp.getValue());
            }

            StatLog.d(TAG, "response code: " + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                StatLog.d(TAG, "onSuccess");
                mCanContinue = false;
            } else if (statusCode == HttpStatus.SC_PARTIAL_CONTENT) {
                mCanContinue = true;
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            String result=  new String(responseBody);
            if (mUpLoadlistener != null) {
                mUpLoadlistener.onFailure();
            }
            if (mForResultListener != null) {
                mForResultListener.onFailure(result);
            }
            cancel();
        }

    }

}
