package com.example.admin.achmadmuhaiminaziz_1202154219_studycase4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class pencariGambar extends Activity {
    //mendeklarasikan nama untuk setiap komponen
    private ImageView gambar;
    private ProgressDialog progressDialog;
    private EditText linkUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //membuat layout tanpa title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pencari_gambar);

        //mendeklarasikan variabel pada komponen yang ada
        gambar = (ImageView) findViewById(R.id.ImageView);
        linkUrl = (EditText)findViewById(R.id.url);


    }
    //pada saat button cari diklik
    public void cari(View view) {
        //mendapatkan text dari EditText
        String url = linkUrl.getText().toString();
        //kondisi jika link kosong dan tidak kosong
        if(url.isEmpty()){
            //Menampilkan toast ketika button diklik namun edit text url kosong
            Toast.makeText(pencariGambar.this,"Masukkan URL gambar yang ingin ditampilkan",Toast.LENGTH_SHORT).show();
        }else {
            // Execute DownloadImage AsyncTask
            new ImageDownloader().execute(url);
        }
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        //Dipanggil di thread UI sebelum tugas dijalankan
        //Langkah ini biasanya digunakan untuk mensetup tugas, misalnya dengan menampilkan progress bar pada user interface
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create progress dialog
            progressDialog = new ProgressDialog(pencariGambar.this);
            // Set judul progress dialog
            progressDialog.setTitle("Search Image");
            // Set pesan pada progress dialog
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            // Show progress dialog
            progressDialog.show();
        }

        //Dipanggil di thread latar belakang segera setelah onPreExecute () selesai mengeksekusi.
        //Langkah ini digunakan untuk melakukan perhitungan background yang bisa memakan waktu lama.
        //Parameter tugas asinkron dilewatkan ke langkah ini.
        //Hasil perhitungan harus dikembalikan oleh langkah ini dan akan diteruskan kembali ke langkah terakhir.
        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image dari URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        //Dipanggil di thread UI setelah perhitungan latar belakang selesai.
        //Hasil perhitungan latar belakang dilewatkan ke langkah ini sebagai parameter.
        @Override
        protected void onPostExecute(Bitmap result) {
            // Set bitmap ke dalam ImageView
            gambar.setImageBitmap(result);
            // Tutup progress dialog
            progressDialog.dismiss();
        }

    }
}
