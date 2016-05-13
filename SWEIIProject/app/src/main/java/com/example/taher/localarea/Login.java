package com.example.taher.localarea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Login extends Activity {

    private Button signup, login, forget;
    private EditText _uname, _upassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _uname = (EditText) findViewById(R.id.username_signup);
        _upassword = (EditText) findViewById(R.id.password_signup);

        signup = (Button) findViewById(R.id.signupButton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Signup.class);
                startActivityForResult(intent, 0);
            }
        });

        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_uname.length() == 0 ||_upassword.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter username and password"
                            , Toast.LENGTH_LONG).show();
                }
                else
                {
                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("pass",_upassword.getText().toString());
                    params.put("email",_uname.getText().toString());

                    Connection conn = new Connection(params, new ConnectionPostListener() {
                        @Override
                        public void doSomething(String result) {
                            try {
                                JSONObject reader = new JSONObject(result);
                                if (reader != null){
                                    Toast.makeText(getApplicationContext(), "Welcome ^_^", Toast.LENGTH_LONG).show();

                                    UserModel user = new UserModel(reader.getInt("id")
                                                                ,reader.getString("name")
                                                                ,reader.getString("email")
                                                                ,reader.getString("pass")
                                                                ,reader.getDouble("long")
                                                                ,reader.getDouble("lat"));


                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    intent.putExtra("userModel",  user);
                                    startActivityForResult(intent, 0);

                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    conn.execute(Constants.LOGIN);


                }
            }
        });

        forget = (Button) findViewById(R.id.forgetButton);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
