package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pandaapp.R;

public class EditInfoAccountActivity extends AppCompatActivity {
    EditText edittextusernameSignup, edittextpassSignup, edittextNameSignup, edittextPhoneSignup, edittextAddressSignup, edittextEmailSignup, edittextNameShopSignup,
            editTextIntroduce, editTextAddressShop, editTextphoneShop, editTextmailShop;
    RadioGroup radiogroupGioitinh;
    RadioButton checkNuSignup, checkNamSignup;
    Button signup_buttonSignup;
    String txtusername, txtpassword, txtnamefull, txtphone, txtaddress, txtemail, txtdateofbirth, txtNameShop, txtIntrude, txtAddressShop, txtPhoneShop, txtEmailShop;
    TextView edittextDateOfBirthSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_account);

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
        editTextIntroduce = (EditText) view.findViewById(R.id.edittextIntroduceShopSignup);
        editTextAddressShop = (EditText) view.findViewById(R.id.edittextAddressShopSignup);
        editTextphoneShop = (EditText) view.findViewById(R.id.edittextPhonelShopSignup);
        editTextmailShop = (EditText) view.findViewById(R.id.edittextEmailShopSignup);


    }
}
