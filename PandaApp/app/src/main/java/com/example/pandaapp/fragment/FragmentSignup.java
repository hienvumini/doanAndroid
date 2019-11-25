package com.example.pandaapp.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSignup extends Fragment {
    EditText edittextusernameSignup, edittextpassSignup, edittextNameSignup, edittextPhoneSignup, edittextAddressSignup, edittextEmailSignup,  edittextNameShopSignup;
    RadioGroup radiogroupGioitinh;
    RadioButton checkNuSignup, checkNamSignup;
    Button signup_buttonSignup;
    String txtusername, txtpassword, txtnamefull, txtphone, txtaddress, txtemail, txtdateofbirth, txtNameShop;
    int gioitinh;
    boolean enableShop = false;
    int idShop, AccountId;
    int idrole = 1;
    Account account = new Account();
    CheckBox checkenableShop;
    TextView edittextDateOfBirthSignup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);
        init(view);
        edittextDateOfBirthSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();

                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        edittextDateOfBirthSignup.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                },2019,11,11);
                datePickerDialog.show();


            }
        });
        signup_buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edittextNameSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextusernameSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextpassSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextAddressSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextDateOfBirthSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextPhoneSignup.getText().toString().equalsIgnoreCase("")
                ) {
                    Toasty.error(getActivity(),"Vui lòng điền đầy đủ thông tin",2000).show();
                } else {

                    getdataformSignup();
                    Log.d("AAA", "Cong   " + account.toString());
                    requestSignUp();
                }


            }

            private void requestSignUp() {

                DataClient dataClientRegister = APIUltils.getData();
                Call<String> stringCall = dataClientRegister.RegisterAccount(
                        txtusername, txtpassword, idrole, txtnamefull, txtphone,
                        txtaddress, gioitinh + "", txtemail, txtdateofbirth, txtNameShop);
                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("A111", "onResponse: " + response);
                        if (response.body().equalsIgnoreCase("Exist")) {
                            Toasty.error(getActivity(),"Tài khoản đã tồn tại",2000).show();
                        } else if (response.body() == "0") {
                            Toasty.error(getActivity(),"Đăng kí thất bại, vui lòng thử lại",2000).show();

                        } else {
                            Toasty.custom(getActivity(),"Đăng kí thành công",R.drawable.ok,R.color.color_pink2,2000,false,true).show();
                            ((LoginActivity) getActivity()).toSigninFragment();

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("BBB", "That bại: " + t.toString());

                    }
                });
            }
        });
        checkenableShop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableShop = true;
                    ((TextView) view.findViewById(R.id.texviewlableOpenShopSignUp)).setVisibility(View.VISIBLE);
                    edittextNameShopSignup.setVisibility(View.VISIBLE);
                    edittextNameShopSignup.setText(edittextNameSignup.getText().toString().trim());
                    idrole = 2;


                } else {
                    ((TextView) view.findViewById(R.id.texviewlableOpenShopSignUp)).setVisibility(View.INVISIBLE);
                    edittextNameShopSignup.setVisibility(View.INVISIBLE);
                    idrole = 1;


                }
            }
        });
        return view;
    }


    private void init(View view) {
        edittextAddressSignup = (EditText) view.findViewById(R.id.edittextAddressSignup);
        edittextDateOfBirthSignup = (TextView) view.findViewById(R.id.edittextDateOfBirthSignup);
        edittextEmailSignup = (EditText) view.findViewById(R.id.edittextEmailSignup);
        edittextNameSignup = (EditText) view.findViewById(R.id.edittextNameSignup);
        edittextpassSignup = (EditText) view.findViewById(R.id.edittextpassSignup);
        edittextPhoneSignup = (EditText) view.findViewById(R.id.edittextPhoneSignup);
        edittextusernameSignup = (EditText) view.findViewById(R.id.edittextusernameSignup);
        edittextNameShopSignup = (EditText) view.findViewById(R.id.edittextNameShopSignup);
        radiogroupGioitinh = (RadioGroup) view.findViewById(R.id.radiogroupGioitinh);
        checkNamSignup = (RadioButton) view.findViewById(R.id.checkNamSignup);
        checkNuSignup = (RadioButton) view.findViewById(R.id.checkNuSignup);
        signup_buttonSignup = (Button) view.findViewById(R.id.signup_buttonSignup);
        checkenableShop = (CheckBox) view.findViewById(R.id.checkboxOpenShop);
    }

    public void getdataformSignup() {
        txtaddress = edittextAddressSignup.getText().toString().trim();
        txtdateofbirth = edittextDateOfBirthSignup.getText().toString().trim();
        String dateOfBirthDB = OtherUltil.convertDatetoMysql(txtdateofbirth);
        txtdateofbirth = dateOfBirthDB;
        SimpleDateFormat dateDB = new SimpleDateFormat("yyyy-mm-dd");
        if (edittextusernameSignup.getText().toString().equalsIgnoreCase("")) {
            txtemail = "";
        } else {
            txtemail = edittextEmailSignup.getText().toString().trim();
        }
        txtnamefull = edittextNameSignup.getText().toString().trim();
        txtpassword = edittextpassSignup.getText().toString().trim();
        txtphone = edittextPhoneSignup.getText().toString().trim();
        txtusername = edittextusernameSignup.getText().toString().trim();
        switch (radiogroupGioitinh.getId()) {
            case R.id.checkNamSignup:
                gioitinh = 1;
                break;
            case R.id.checkNuSignup:
                gioitinh = 0;
                break;
        }

        account = new Account(idrole, idShop, txtusername, txtpassword, txtnamefull, txtphone, txtaddress, gioitinh, txtemail, txtdateofbirth, 1);
        txtNameShop = txtnamefull;
        idrole = 1;
        if (enableShop == true) {
            txtNameShop = edittextNameShopSignup.getText().toString().trim();
            idrole = 2;
        }
        if (txtNameShop == null || txtNameShop.equalsIgnoreCase("")) {
            txtNameShop = txtnamefull;
        }
    }
}
