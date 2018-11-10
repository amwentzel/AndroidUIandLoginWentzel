package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private UserProfilePersistence userProfilePersistence;
    private Button signupButton = null;
    private EditText firstName = null;
    private EditText lastName = null;
    private EditText username = null;
    private EditText birthday = null;
    private EditText phoneNumber = null;
    private EditText email = null;
    private EditText password = null;
    private String emailString = null;
    private String passwordString = null;
    private FirebaseAuth auth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        UserData userData = (UserData) getIntent().getSerializableExtra("USER_DATA");

        signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(this);

        firstName = findViewById(R.id.enterFirstName);
        lastName = findViewById(R.id.enterLastName);
        username = findViewById(R.id.enterUsername);
        birthday = findViewById(R.id.enterBirthday);
        phoneNumber = findViewById(R.id.enterPhoneNumber);
        email = findViewById(R.id.enterEmail);
        password = findViewById(R.id.enterPassword);

        emailString = userData.getName();
        passwordString = userData.getProvider();

        email.setHint(emailString);
        password.setHint(passwordString);
    }

    @Override
    public void onClick(View v) {
        signup();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        userProfilePersistence = new UserProfilePersistence(this);
    }

    private void signup(){
        final String email = this.email.getText().toString();
        final String password = this.password.getText().toString();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("USER_AUTH", "createUserWithEmailAndPassword.success");
                    FirebaseUser user = auth.getCurrentUser();

                    String msg = "Someone: "+user.getEmail()+" , ID: "+user.getProviderId();
                    Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();

                    String first = firstName.getText().toString();
                    String last = lastName.getText().toString();
                    String username = SignupActivity.this.username.getText().toString();
                    String phone = phoneNumber.getText().toString();
                    String birthday = SignupActivity.this.birthday.getText().toString();

                    UserProfile userProfile = new UserProfile(first, last, username, birthday, phone, email, password);
                    userProfilePersistence.insert(userProfile);

                    Intent intent = new Intent(SignupActivity.this, LoginSuccessActivity.class);
                    startActivity(intent);

                }else{
                    Log.w("TAG", "createUserWithEmailAndPassword:failure", task.getException());
                    Toast.makeText(SignupActivity.this, "Authentication failed "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}