package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<UserProfile> userProfiles;
    private UserProfilePersistence userProfilePersistence;

    private Button mButtonSignUp;
    private Button mButtonLogin;

    private EditText mEditTextUser;
    private EditText mEditTextPassword;

    private ConstraintLayout mConstraintLayoutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mButtonLogin = (Button) findViewById(R.id.loginButton);
        mButtonSignUp = (Button) findViewById(R.id.signupButton);

        mEditTextUser = (EditText) findViewById(R.id.emailOrID);
        mEditTextPassword = (EditText) findViewById(R.id.password);

        // The constraint layout will be used for displaying the Snackbar.
        mConstraintLayoutLogin = (ConstraintLayout) findViewById(R.id.layout);

        // TODO replace by a simple query to verify if the user exists in the database.
        // TODO customize query to check this info on the UserProfileTable
        mButtonLogin.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userProfilePersistence = new UserProfilePersistence(this);
        userProfiles = userProfilePersistence.getDataFromDB();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.loginButton: validateInput(); break;
            case R.id.signupButton: signUp(); break;
        }

    }

    private void validateInput() {

        UserProfile userProfile = null;

        if(userProfiles != null && !userProfiles.isEmpty()){

            String user = mEditTextUser.getText().toString();
            String password = mEditTextPassword.getText().toString();

            for (UserProfile up : userProfiles){
                if(up.getUsername().equals(user) ){
                    userProfile = up;
                }
                break;
            }
            if(userProfile == null){
                Toast.makeText(getApplicationContext(), R.string.user_not_found, Toast.LENGTH_LONG).show();
            }else {
                if(!userProfile.getPassword().equals(password)){
                    Toast.makeText(getApplicationContext(), R.string.wrong_password, Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(LoginActivity.this, LoginSuccessActivity.class);
                    intent.putExtra("USER", userProfile.getName());
                    intent.putExtra("PASSWORD", userProfile.getPassword());
                    startActivity(intent);
                }
            }
        }
    }

    private void signUp(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}