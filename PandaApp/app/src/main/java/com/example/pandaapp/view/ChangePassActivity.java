package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {
    EditText edittextOldpass, edittextNewpass, edittextConfirmdpass;
    String txtOldPass, txtNewpass, txtConfirmPass;
    Button buttonChangepass;
    GlobalApplication globalApplication;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        init();
        listener();

    }


    private void init() {
        edittextOldpass = (EditText) findViewById(R.id.edittextOldpassEdit);
        edittextNewpass = (EditText) findViewById(R.id.edittextNewpassEdit);
        edittextConfirmdpass = (EditText) findViewById(R.id.edittextConfirmpassEdit);
        buttonChangepass = (Button) findViewById(R.id.signup_buttonEdit_ChangePasss);
        globalApplication=(GlobalApplication) getApplicationContext();
        account=globalApplication.account;
    }

    private int getdatafill() {
        txtOldPass = edittextOldpass.getText().toString().trim();
        txtNewpass = edittextNewpass.getText().toString().trim();
        txtConfirmPass = edittextConfirmdpass.getText().toString().trim();
        if (txtOldPass.length() > 0 && txtNewpass.length() > 0 && txtConfirmPass.length() > 0) {
            if (txtConfirmPass.equals(txtNewpass)){
                return 1;
            }
            return 2;

        } else {
            return 0;
        }


    }

    private void listener() {
        buttonChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getdatafill()==1) {
                    DataClient dataClient= APIUltils.getData();

                    Call<String> stringCall=dataClient.ChangePass(account.getAccountId(),txtOldPass,txtNewpass);
                    stringCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response != null) {
                                if (response.body().equals("1xx1")){
                                    Toasty.success(ChangePassActivity.this,"Đổi mật khẩu thành công! \n Bạn cần phải đăng nhập lại").show();

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(3000);
                                                globalApplication.account = new Account();
                                                Intent intent = new Intent(ChangePassActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    thread.start();

                                } else if (response.body().equals("0xx0")){
                                    Toasty.error(ChangePassActivity.this,"Đổi mật khẩu thất bại! \n Vui lòng thử lại").show();
                                } else if (response.body().equals("0000")){
                                    Toasty.error(ChangePassActivity.this,"Mật khẩu cũ không chính xác").show();

                                } else {
                                    Toasty.error(ChangePassActivity.this,"Đổi mật khẩu thất bại! \n Vui lòng thử lại").show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });


                } else if (getdatafill()==2){
                    Toasty.error(ChangePassActivity.this,"Mật khẩu xác nhận không chính xác").show();


                } else {
                    Toasty.error(ChangePassActivity.this,"Vui lòng điền đầy đủ thông tin").show();

                }
            }
        });


    }


}
