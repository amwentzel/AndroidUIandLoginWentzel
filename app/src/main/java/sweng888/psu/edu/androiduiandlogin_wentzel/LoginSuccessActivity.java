package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginSuccessActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private Button viewAllUsersButton;

    private String firstName = null;
    private String lastName = null;

    private FirebaseAuth auth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        welcomeMessage = (TextView) findViewById(R.id.welcome);
        viewAllUsersButton = (Button) findViewById(R.id.viewAllUsersButton);
        auth = FirebaseAuth.getInstance();

        Intent data = getIntent();
        firstName = data.getStringExtra("FIRST_NAME");
        lastName = data.getStringExtra("LAST_NAME");
        String message = "Welcome, " + firstName + " " + lastName;
        welcomeMessage.setText(message);

        viewAllUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSuccessActivity.this, UserActivity.class));
            }
        });
    }
}