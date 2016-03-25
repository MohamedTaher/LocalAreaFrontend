package com.example.taher.localarea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Signup extends Activity {

    private EditText username, email, password, repassword;
    private Button supmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.username_signup);
        email = (EditText) findViewById(R.id.email_signup);
        password = (EditText) findViewById(R.id.password_signup);
        repassword = (EditText) findViewById(R.id.repassword_signup);

        supmit = (Button) findViewById(R.id.supmitButton);
        supmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.length() == 0 || email.length() == 0
                        || password.length() == 0 || repassword.length() == 0){
                    Toast.makeText(getApplicationContext(), "Enter ALL data"
                            , Toast.LENGTH_LONG).show();
                } else {
                    if (!(password.getText().toString().equals(repassword.getText().toString())))
                    {
                        Toast.makeText(getApplicationContext(), "re-password and password are not same"
                                , Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        HashMap<String, String> params = new HashMap<String, String>();

                        params.put("name",username.getText().toString());
                        params.put("email",email.getText().toString());
                        params.put("pass", password.getText().toString());

                        Connection conn = new Connection(params, new ConnectionPostListener() {
                            @Override
                            public void doSomething(String result) {
                                try {
                                    JSONObject reader = new JSONObject(result);
                                    if (reader != null){

                                        Toast.makeText(getApplicationContext(), "Done ^_^", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivityForResult(intent, 0);

                                    }
                                    else
                                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                                    //if the same username
                                    //verify email


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        conn.execute(Constants.SIGNUP);

                    }
                }

            }
        });

    }

}