package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NamesActivity extends AppCompatActivity {

    private EditText editTxt1;
    private EditText editTxt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);

        editTxt1 = (EditText) findViewById(R.id.editTextTextPersonName);
        editTxt2 = (EditText) findViewById(R.id.editTextTextPersonName2);
    }

    public void onClickSubmitNames(View view) {

        String name1 = editTxt1.getText().toString();
        String name2 = editTxt2.getText().toString();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("PLAYER_NAMES", new String[]{name1, name2});
        startActivity(intent);
    }
}