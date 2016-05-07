package com.zzia.grduation.task;


import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.http.ASJsonApiUtil;

/**
 * Created by apple on 15/7/16.
 */
public abstract class BaseReturnTask extends BaseAsyncTask<Void, Void, BaseBean> {

    public BaseBean mFailureRet;
    public Throwable mT;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onStart();
    }

    public abstract void onStart();

    @Override
    protected BaseBean doInBackground(Void... params) {
        try {
            BaseBean baseBean = requestExe();
            if (ASJsonApiUtil.retcodeSuccess(baseBean)) {
                return baseBean;
            }

            mFailureRet = baseBean;
        } catch (Exception e) {
            mT = e;
        }
        return null;
    }

    public abstract BaseBean requestExe() throws Exception;

    @Override
    protected void onPostExecute(BaseBean baseBean) {
        super.onPostExecute(baseBean);
        if (baseBean == null) {
            onError(mFailureRet, mT);
        } else {
            onSuccess(baseBean);
        }
    }

    public abstract void onSuccess(BaseBean successRet);

    public abstract void onError(BaseBean failureRet, Throwable t);
}
