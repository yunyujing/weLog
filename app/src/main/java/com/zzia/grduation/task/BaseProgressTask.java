package com.zzia.grduation.task;


import com.zzia.graduation.common.bean.BaseBean;

/**
 * Created by apple on 15/7/16.
 * 通用的api请求策略，只返回最后一个error，如果有一个成功，就不会返回出错信息
 */
public abstract class BaseProgressTask<Success> extends BaseAsyncTask<Void, Success, Void> {
    public BaseBean mFailureRet;
    public Throwable mT;
    public boolean success;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        success = false;
        onStart();
    }

    public abstract void onStart();

    @Override
    protected Void doInBackground(Void... params) {
        try {
            mFailureRet = requestExe();
        } catch (Exception e) {
            mT = e;
        }
        return null;
    }

    public abstract BaseBean requestExe() throws Exception;

    /**
     * 使用publishSuccess不要使用onProgressUpdate
     */
    protected void publishSuccess(Success success) {
        publishProgress(success);
    }

    @Override
    protected void onProgressUpdate(Success... values) {
        super.onProgressUpdate(values);
        if (values == null || values.length != 1)
            return;

        onSuccessUpdate(values[0]);
    }

    /**
     * 覆盖onSuccessUpdate，覆盖onSuccessUpdate
     */
    protected void onSuccessUpdate(Success successRet) {
        onSuccess(successRet);
        success = true;
    }

    public abstract void onSuccess(Success successRet);

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (!success) {
            onError(mFailureRet, mT);
        }

    }

    public abstract void onError(BaseBean failureRet, Throwable t);
}
