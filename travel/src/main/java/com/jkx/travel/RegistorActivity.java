package com.jkx.travel;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jkx.travel.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by jkx
 */
public class RegistorActivity extends Activity{

    private EditText user_phone;   //注册手机号
    private EditText user_pwd;     //注册密码
    private EditText user_check_num;  //验证码
    private Button next;             //点击注册
    private Button get_check_num;    //获取验证码
    private TimeCount time ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registor);
        /* 初始化Bomb */
        Bmob.initialize(this, "709c71a5ace3d431dab92554cb21dec5");
        time = new TimeCount(60000,1000);
         user_phone = (EditText) findViewById(R.id.user_registor_phone);
         user_pwd = (EditText) findViewById(R.id.user_registor_psd);
         user_check_num = (EditText) findViewById(R.id.user_registor_checknum);
        get_check_num = (Button) findViewById(R.id.user_registor_get_checknum);
        next = (Button) findViewById(R.id.user_registor_next);

        //点击获取验证码，
        get_check_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               time.start() ;
                BmobSMS.requestSMSCode(getApplicationContext(), user_phone.getText().toString(),
                        "travel_num", new RequestSMSCodeListener() {

                            @Override
                            public void done(Integer smsId, BmobException ex) {
                                // TODO Auto-generated method stub
                                if (ex == null) {//验证码发送成功
                                    Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                                }
                            }
                        });
            }
        });

        //点击下一步，判断，然后登录
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobSMS.verifySmsCode(getApplicationContext(), user_phone.getText().toString(),
                        user_check_num.getText().toString(), new VerifySMSCodeListener() {

                            @Override
                            public void done(BmobException ex) {
                                // TODO Auto-generated method stub
                                if (ex == null) {//短信验证码已验证成功
                                    Log.i("bmob", "验证通过");
                                } else {
                                    Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                }
                            }
                        });
            }
        });
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            get_check_num.setText("重新验证");
            get_check_num.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            get_check_num.setClickable(false);
            get_check_num.setText(millisUntilFinished /1000+"秒");
        }
    }
}
