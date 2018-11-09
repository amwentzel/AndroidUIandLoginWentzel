package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginSuccessActivity extends AppCompatActivity {

    private Button viewAllUsersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewAllUsersButton = (Button) findViewById(R.id.viewAllUsersButton);

        viewAllUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSuccessActivity.this, UserActivity.class));
            }
        });
    }
}