package org.opencv.samples.facedetect.activity.login;

import androidx.lifecycle.MutableLiveData;

import com.bench.android.core.arch.viewmodel.HttpViewModel;
import com.bench.android.core.net.http.RequestFormBody;
import com.bench.android.core.net.http.base.RequestContainer;

import org.json.JSONException;
import org.opencv.samples.facedetect.utils.UrlEnum;

public class LoginModel extends HttpViewModel {

    public MutableLiveData<GetVerifyNumBean> getVerifyNumLiveData = new MutableLiveData<>();
    public MutableLiveData<LoginBean> loginLiveData = new MutableLiveData<>();
    public MutableLiveData<String> setUserLoginName = new MutableLiveData<>();

    public void getVerify(String cell) {
        RequestFormBody body = new RequestFormBody(UrlEnum.USER_LOGIN_SEND_SMS_ACK);
        body.put("cell", cell);
        body.setGenericClaz(GetVerifyNumBean.class);
        httpHelper.startRequest(body);
    }

    public void login(String cell,  String smsId) {
        RequestFormBody body = new RequestFormBody(UrlEnum.USER_LOGIN);
        body.put("cellphone", cell);
        body.put("verifyCode", smsId);
        body.setRequestUrl("/user/login");
        body.setUseJsonRequest(true);
        body.setGenericClaz(LoginBean.class);
        httpHelper.startRequest(body);
    }

//    public RequestFormBody setUserLoginName(String userName) {
//        RequestFormBody body = new RequestFormBody(UrlEnum.SET_USER_LOGIN_NAME);
//        body.put("loginName", userName);
//        body.setGenericClaz(LoginBean.class);
//        return body;
//    }

    @Override
    public void onSuccess(RequestContainer request, Object o) throws JSONException {
        UrlEnum urlEnum = (UrlEnum) request.getRequestEnum();
        switch (urlEnum) {
            case USER_LOGIN:
                loginLiveData.setValue((LoginBean) o);
                break;
            case USER_LOGIN_SEND_SMS_ACK:
                getVerifyNumLiveData.setValue((GetVerifyNumBean) o);
                break;
            case SET_USER_LOGIN_NAME:
                setUserLoginName.setValue("true");
                break;
            default:
                break;
        }
    }
}
