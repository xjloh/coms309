package com.example.coms309;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private ProgressBar progressBar;
    private TextView Error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name=findViewById(R.id.emel);
        Password=findViewById(R.id.etPass);
        Login=findViewById(R.id.login);
        progressBar=findViewById(R.id.load);
        Error=findViewById(R.id.errormsg);

        progressBar.setVisibility(View.GONE);
        Error.setVisibility(View.GONE);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Error.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }

    private void validate(String username, String password){
        if(username.equals("XinJun") && password.equals("12345")){
            Intent setup=new Intent(MainActivity.this, SetupPage.class);
            startActivity(setup);
        }
        else{
//            Login.setEnabled(false);
            progressBar.setVisibility(View.GONE);
            Error.setVisibility(View.VISIBLE);
        }
    }



}
