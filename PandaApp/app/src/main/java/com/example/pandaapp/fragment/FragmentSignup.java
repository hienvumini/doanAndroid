package com.example.pandaapp.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
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
    EditText edittextusernameSignup, edittextpassSignup, edittextNameSignup, edittextPhoneSignup, edittextAddressSignup, edittextEmailSignup;
    RadioGroup radiogroupGioitinh;
    RadioButton checkNuSignup, checkNamSignup;
    Button signup_buttonSignup;
    String txtusername, txtpassword, txtnamefull, txtphone, txtaddress, txtemail, txtdateofbirth;
    int gioitinh;
    Account account = new Account();
    TextView edittextDateOfBirthSignup;
    int mode;
    View view;
    Bundle bundle;
    private final static String SHARED_PREFERENCES_NAME = "user";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        init(view);
        bundle = this.getArguments();
        mode = bundle.getInt("mode", 1);

        switch (mode) {

            case 1:

                break;
            case 2:

                account = (Account) bundle.getSerializable("key");
                setDataIntilize(account);

                break;
            case 3:

                break;

        }
        try {
            onListener(view);
        } catch (Exception e) {
            System.out.println(e.toString());

        }
        return view;
    }

    private void onListener(final View view) {
        edittextDateOfBirthSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edittextDateOfBirthSignup.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                }, calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
                    Toasty.error(getActivity(), "Vui lòng điền đầy đủ thông tin", 2000).show();
                } else {

                    getdataformSignup();
                    Log.d("AAA", "Cong   " + account.toString());
                    requestSignUp();
                }


            }

            private void requestSignUp() {


                DataClient dataClientRegister = APIUltils.getData();
                Call<String> stringCall = dataClientRegister.RegisterAccount(
                        account.getUsename(), account.getPassword(), account.getName(), account.getPhone_number(),
                        account.getAddress(), account.getGender() + "", account.getEmail(), account.getDateOfBirth()
                );
                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("A111", "onResponse: " + response);
                        if (response.body().equalsIgnoreCase("Exist")) {
                            Toasty.error(getActivity(), "Tài khoản đã tồn tại", 2000).show();
                        } else if (response.body() == "0") {
                            Toasty.error(getActivity(), "Đăng kí thất bại, vui lòng thử lại", 2000).show();

                        } else {
                            Toasty.custom(getActivity(), "Đăng kí thành công", R.drawable.ok, R.color.color_pink2, 2000, false, true).show();
                            try {

                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user", account.getUsename());
                                editor.putString("pass", account.getPassword());
                                editor.apply();
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("BBB", "That bại: " + t.toString());

                    }
                });
            }
        });

    }


    private void init(View view) {
        edittextAddressSignup = (EditText) view.findViewById(R.id.edittextAddressSignup);
        edittextDateOfBirthSignup = (TextView) view.findViewById(R.id.edittextDateOfBirthSignup);
        edittextEmailSignup = (EditText) view.findViewById(R.id.edittextEmailSignup);
        edittextNameSignup = (EditText) view.findViewById(R.id.edittextNameSignup);
        edittextpassSignup = (EditText) view.findViewById(R.id.edittextpassSignup);
        edittextPhoneSignup = (EditText) view.findViewById(R.id.edittextPhoneSignup);
        edittextusernameSignup = (EditText) view.findViewById(R.id.edittextusernameSignup);
        radiogroupGioitinh = (RadioGroup) view.findViewById(R.id.radiogroupGioitinh);
        checkNamSignup = (RadioButton) view.findViewById(R.id.checkNamSignup);
        checkNuSignup = (RadioButton) view.findViewById(R.id.checkNuSignup);
        signup_buttonSignup = (Button) view.findViewById(R.id.signup_buttonSignup);


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

        account = new Account(1, -1, txtusername, txtpassword, txtnamefull, txtphone, txtaddress, gioitinh, txtemail, txtdateofbirth, 1);

    }

    public void setDataIntilize(Account account) {
        edittextNameSignup.setText(account.getName());
        edittextAddressSignup.setText(account.getAddress());
        edittextDateOfBirthSignup.setText(account.getDateOfBirth());
        edittextEmailSignup.setText(account.getEmail());
        if (account.getGender() == 1) {
            checkNamSignup.setChecked(true);

        } else {
            checkNuSignup.setChecked(true);

        }
        edittextusernameSignup.setText(account.getEmail());
        edittextusernameSignup.setEnabled(false);

    }

}
