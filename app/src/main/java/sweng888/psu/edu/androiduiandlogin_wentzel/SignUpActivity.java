package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpButton;
    private EditText mEditTextFirst;
    private EditText mEditTextLast;
    private EditText mEditTextUsername;
    private EditText mEditTextBirthday;
    private EditText mEditTextPass;
    private EditText mEditTextEmail;
    private EditText mEditTextPhone;

    private UserProfilePersistence userProfilePersistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpButton = (Button) findViewById(R.id.signupButton);
        mEditTextFirst = (EditText) findViewById(R.id.enterFirstName);
        mEditTextLast = (EditText) findViewById(R.id.enterLastName);
        mEditTextUsername = (EditText) findViewById(R.id.enterUsername);
        mEditTextBirthday = (EditText) findViewById(R.id.enterBirthday);
        mEditTextPass = (EditText) findViewById(R.id.enterPassword);
        mEditTextEmail = (EditText) findViewById(R.id.enterEmail);
        mEditTextPhone = (EditText) findViewById(R.id.enterPhoneNumber);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = mEditTextFirst.getText().toString();
                String last = mEditTextLast.getText().toString();
                String username = mEditTextUsername.getText().toString();
                String pass = mEditTextPass.getText().toString();
                String email = mEditTextEmail.getText().toString();
                String phone = mEditTextPhone.getText().toString();
                String birthday = mEditTextBirthday.getText().toString();

                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                UserProfile user = new UserProfile(first, last, username, phone, email, pass, birthday);

                userProfilePersistence.insert(user);

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.putExtra("USER_DATA", user);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), R.string.signup_success, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userProfilePersistence = new UserProfilePersistence(this);
    }

    private void insertData(){

        userProfilePersistence.insert(new UserProfile("Allie", "Wentzel",
                "amwentzel", "222-555-444", "abc123@psu.edu",
                "1234", "10-10-2018"));

        userProfilePersistence.insert(new UserProfile("Connor", "Valan",
                "cjv", "222-333-444", "abc456@psu.edu",
                "1234", "10-10-2018"));

    }
}