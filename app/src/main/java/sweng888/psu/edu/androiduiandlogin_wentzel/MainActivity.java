package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image;
    private EditText email;
    private EditText password;
    private Button loginButton;
    private Button signupButton;
    private TextView or;
    private UserProfilePersistence userProfilePersistence;
    private ArrayList<UserProfile> userProfiles;
    private FirebaseAuth auth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);
        email = (EditText) findViewById(R.id.emailOrID);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);
        or = (TextView) findViewById(R.id.orText);

        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        userProfilePersistence = new UserProfilePersistence(this);
        userProfiles = userProfilePersistence.getDataFromDB();
    }

    @Override
    public void onClick(View v) {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        switch (v.getId()){
            case R.id.loginButton: signIn(email, password); break;
            case R.id.signupButton: signup(email, password); break;
        }
    }

    private void signIn(String email, String password){
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                            String msg = "User: "+user.getEmail()+" , ID: "+user.getProviderId();
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, LoginSuccessActivity.class);
                            intent.putExtra("USER_DATA", new UserData(user.getEmail(), user.getProviderId()));
                            startActivity(intent);

                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            String msgFailed = R.string.auth_fail + task.getException().getMessage();
                            Toast.makeText(MainActivity.this,msgFailed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void validateInput() {
        UserProfile userProfile = null;

        if(userProfiles != null && !userProfiles.isEmpty()){
            String user = email.getText().toString();
            String password = this.password.getText().toString();

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
                    Intent intent = new Intent(MainActivity.this, LoginSuccessActivity.class);
                    intent.putExtra("USER", userProfile.getUsername());
                    intent.putExtra("PASSWORD", userProfile.getPassword());
                    startActivity(intent);
                }
            }
        }
    }

    private void signup(String email, String password){
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);

        if (!email.matches("") || !password.matches("")) {
            intent.putExtra("USER_DATA", new UserData(email, password));
        }
        else {
            intent.putExtra("USER_DATA", new UserData("Email", "Password"));
        }
        startActivity(intent);
    }
}
