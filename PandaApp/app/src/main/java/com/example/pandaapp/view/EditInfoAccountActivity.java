package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.OtherUltil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInfoAccountActivity extends AppCompatActivity {
    EditText edittextpass, edittextName, edittextPhone, edittextAddress, edittextEmail;
    TextView textviewusername;
    RadioGroup radiogroupGioitinh;
    RadioButton checkNu, checkNam;
    Button buttonEdit;
    String txtnamefull, txtphone, txtaddress, txtemail, txtdateofbirth;
    TextView edittextDateOfBirth;
    GlobalApplication globalApplication;
    Account account;
    Account accountnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_account);
        init();
        setdataintilize();
        listener();


    }


//    private void init(View view) {
//        edittextAddressSignup = (EditText) view.findViewById(R.id.edittextAddressSignup);
//        edittextDateOfBirthSignup = (TextView) view.findViewById(R.id.edittextDateOfBirthSignup);
//        edittextEmailSignup = (EditText) view.findViewById(R.id.edittextEmailSignup);
//        edittextNameSignup = (EditText) view.findViewById(R.id.edittextNameSignup);
//        edittextpassSignup = (EditText) view.findViewById(R.id.edittextpassSignup);
//        edittextPhoneSignup = (EditText) view.findViewById(R.id.edittextPhoneSignup);
//        edittextusernameSignup = (EditText) view.findViewById(R.id.edittextusernameSignup);
//        edittextNameShopSignup = (EditText) view.findViewById(R.id.edittextNameShopSignup);
//        radiogroupGioitinh = (RadioGroup) view.findViewById(R.id.radiogroupGioitinh);
//        checkNamSignup = (RadioButton) view.findViewById(R.id.checkNamSignup);
//        checkNuSignup = (RadioButton) view.findViewById(R.id.checkNuSignup);
//        signup_buttonSignup = (Button) view.findViewById(R.id.signup_buttonSignup);
//        editTextIntroduce = (EditText) view.findViewById(R.id.edittextIntroduceShopSignup);
//        editTextAddressShop = (EditText) view.findViewById(R.id.edittextAddressShopSignup);
//        editTextphoneShop = (EditText) view.findViewById(R.id.edittextPhonelShopSignup);
//        editTextmailShop = (EditText) view.findViewById(R.id.edittextEmailShopSignup);
//
//
//    }

    private void init() {
        edittextAddress = (EditText) findViewById(R.id.edittextAddressEdit);
        edittextDateOfBirth = (TextView) findViewById(R.id.edittextDateOfBirthEdit);
        edittextEmail = (EditText) findViewById(R.id.edittextEmailEdit);
        edittextName = (EditText) findViewById(R.id.edittextNameEdit);
        edittextPhone = (EditText) findViewById(R.id.edittextPhoneEdit);
        textviewusername = (TextView) findViewById(R.id.edittextusernameEdit);

        radiogroupGioitinh = (RadioGroup) findViewById(R.id.radiogroupGioitinhEdit);
        checkNam = (RadioButton) findViewById(R.id.checkNamEdit);
        checkNu = (RadioButton) findViewById(R.id.checkNuEdit);
        buttonEdit = (Button) findViewById(R.id.signup_buttonEdit);
        globalApplication = (GlobalApplication) getApplicationContext();
        account = new Account();
        account = globalApplication.account;


    }

    private void setdataintilize() {
        edittextAddress.setText(account.getAddress() + "");
        edittextDateOfBirth.setText(OtherUltil.convertDateFromMysql(account.getDateOfBirth()) + "");
        edittextEmail.setText(account.getEmail() + "");
        edittextName.setText(account.getName() + "");
        textviewusername.setText(account.getUsename());

        edittextPhone.setText(account.getPhone_number() + "");
        switch (account.getGender()) {
            case 1:
                checkNam.setChecked(true);
                break;
            case 0:
                checkNu.setChecked(true);
                break;

        }
    }

    private boolean getdataFill() {

        txtaddress = edittextAddress.getText().toString().trim() + "";
        txtdateofbirth = edittextDateOfBirth.getText().toString().trim() + "";

        String dateOfBirthDB = OtherUltil.convertDatetoMysql(txtdateofbirth);
        txtdateofbirth = dateOfBirthDB;

        txtemail = edittextEmail.getText().toString().trim();
        txtnamefull = edittextName.getText().toString().trim();

        txtphone = edittextPhone.getText().toString().trim();
        int gioitinh = 0;
        switch (radiogroupGioitinh.getId()) {

            case R.id.checkNamSignup:
                gioitinh = 1;
                break;
            case R.id.checkNuSignup:
                gioitinh = 0;
                break;
        }
        if (txtnamefull.length() > 0 && txtaddress.length() > 0 && txtemail.length() > 0 && txtphone.length() > 0) {
            accountnew = account;
            accountnew = account;
            accountnew.setDateOfBirth(txtdateofbirth);
            accountnew.setName(txtnamefull);
            accountnew.setPhone_number(txtphone);
            accountnew.setGender(0);
            accountnew.setEmail(txtemail);
            accountnew.setAddress(txtaddress);
            return true;

        } else {

            return false;
        }


    }

    private void EditSendRequest() {
        if (getdataFill()) {
            DataClient dataClient = APIUltils.getData();
            Call<String> stringCall = dataClient.EditAccount(
                    accountnew.getName(), accountnew.getPhone_number(), accountnew.getAddress(),
                    accountnew.getEmail(), accountnew.getDateOfBirth(), accountnew.getGender(), accountnew.getAccountId());

            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        if (response.body().equalsIgnoreCase("1xx1")) {
                            Toasty.success(EditInfoAccountActivity.this, "Chỉnh sửa tài khoản thành công!\n bạn sẽ đăng nhập lại").show();

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                        globalApplication.account = new Account();
                                        Intent intent = new Intent(EditInfoAccountActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();


                        } else {
                            Toasty.error(EditInfoAccountActivity.this, "Chỉnh sửa tài khoản thất bại!\n Vui lòng thử lại").show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("2222", "onFailure: " + t.toString());

                }
            });
        } else {
            Toasty.error(EditInfoAccountActivity.this, "Vui lòng điền đầy đủ thông tin còn thiếu").show();
        }

    }

    private void listener() {
        edittextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditInfoAccountActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edittextDateOfBirth.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.d("2222", "onClick: "+accountnew.toString());
                EditSendRequest();
            }
        });
    }
}
