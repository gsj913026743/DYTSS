package com.nhsoft.dormitory.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;

import com.google.gson.Gson;
import com.lzf.http.data.source.entity.LoginModel;
import com.lzf.http.data.source.repository.Repository;
import com.nhsoft.base.webSocket.WsManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.constant.Constant;
import priv.lzf.mvvmhabit.http.BaseResponse;
import priv.lzf.mvvmhabit.http.ResponseThrowable;
import priv.lzf.mvvmhabit.utils.EncryptionUtil;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/12/15.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class DormitoryViewModel extends BaseViewModel<Repository> {

    public BindingCommand click;



    public DormitoryViewModel(@NonNull Application application) {
        super(application);
        model=Repository.getInstance();
        bindingCommand();
    }

    @Override
    public void bindingCommand() {
        super.bindingCommand();
        click=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                WsManager.getInstance().sendText();
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        login();
    }

    public void login(){
        String nonce= EncryptionUtil.getNonce();
        String timestamp=EncryptionUtil.getTimestamp();
        @SuppressLint("MissingPermission") String serial=(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)?Build.getSerial(): Build.SERIAL;
        String signature=EncryptionUtil.signatureString(Constant.APPSECRET,timestamp,nonce);
        KLog.e("signature:"+signature+"-------timestamp:"+timestamp+"------------nonce:"+nonce+"-------APPID:"+Constant.APPID+"------serial:"+serial);
        addSubscribe(model.login(signature,timestamp,nonce,Constant.APPID,serial)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse<LoginModel>>() {
                    @Override
                    public void accept(BaseResponse<LoginModel> response) throws Exception {
                        KLog.e(new Gson().toJson(response.getData()));
                        if (response.isOk()) {
//                            LoginModel loginModel=response.getData();
                            model.saveAccessToken(response.getData().getAccess_token());
                            WsManager.getInstance().init();
                        }else {
                            ToastUtils.showShort(response.getMsg());
                            dismissDialog();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
//                        ErrorUtil.request(throwable.toString());
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WsManager.getInstance().disconnect();
    }
}
