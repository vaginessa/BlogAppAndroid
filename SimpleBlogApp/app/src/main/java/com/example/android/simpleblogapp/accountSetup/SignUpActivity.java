package com.example.android.simpleblogapp.accountSetup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.simpleblogapp.MainActivity;
import com.example.android.simpleblogapp.R;
import com.example.android.simpleblogapp.databinding.DataBinding_activity_signup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by android on 2/28/2017.
 */

public class SignUpActivity extends AppCompatActivity {
    UserDetail mUserDetail;
    FirebaseAuth mFirebaseAuth;
    
    DataBinding_activity_signup mDataBinding_activity_signup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();



        mDataBinding_activity_signup = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        mDataBinding_activity_signup.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mDataBinding_activity_signup.signupAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        mDataBinding_activity_signup.signupCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fname
                String fname = mDataBinding_activity_signup.signupFirstName.getText().toString().trim();
                if (TextUtils.isEmpty(fname))
                {
                    Toast.makeText(SignUpActivity.this, getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
                    return;
                }

                String lname = mDataBinding_activity_signup.signupLastName.getText().toString().trim();
                if (TextUtils.isEmpty(lname))
                {
                    Toast.makeText(SignUpActivity.this, getString(R.string.enter_last_name), Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = mDataBinding_activity_signup.signupEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(SignUpActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = mDataBinding_activity_signup.signupPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(SignUpActivity.this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length()<6)
                {
                    Toast.makeText(SignUpActivity.this, getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
                }
                mUserDetail = new UserDetail(fname,lname,email,password);
                mDataBinding_activity_signup.signupProgressbar.setVisibility(View.VISIBLE);
                mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mDataBinding_activity_signup.signupProgressbar.setVisibility(View.GONE);
                        if (!task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
        mDataBinding_activity_signup.signupAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}