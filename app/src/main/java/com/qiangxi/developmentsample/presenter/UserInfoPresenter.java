package com.qiangxi.developmentsample.presenter;

/**
 * Created by qiang_xi on 2017/4/4 18:41.
 */

public interface UserInfoPresenter extends BasePresenter {
    void userInfoRequestSuccess(String state);
    void userInfoRequestFailure(String errorCode);
}
