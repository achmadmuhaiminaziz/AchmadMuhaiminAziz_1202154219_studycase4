package com.example.admin.achmadmuhaiminaziz_1202154219_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //method onClick nama
    public void nama(View view) {
        //membuat intent dari MainActivity ke listNamaMahasiswa
        Intent intent = new Intent(MainActivity.this,listNamaMahasiswa.class);
        startActivity(intent);
    }

    public void gambar(View view) {
        //membuat intent dari MainActivity ke pencariGambar
        Intent intent = new Intent(MainActivity.this,pencariGambar.class);
        startActivity(intent);
    }
}
