package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginSuccessActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private Button viewAllUsersButton;

    private String firstName = null;
    private String lastName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcomeMessage = (TextView) findViewById(R.id.welcome);
        viewAllUsersButton = (Button) findViewById(R.id.viewAllUsersButton);

        Intent data = getIntent();
        firstName = data.getStringExtra("NAME");
        lastName = data.getStringExtra("SURNAME");
        welcomeMessage.setText("Welcome, " + firstName + " " + lastName);

        viewAllUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSuccessActivity.this, UserActivity.class));
            }
        });
    }
}