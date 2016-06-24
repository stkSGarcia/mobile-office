package stk.mobileoffice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Author: stk
 * Date: 2016/6/24
 * Time: 21:43
 */
public class Login extends Activity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (DemoUsers.login(username.getText().toString(), password.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
