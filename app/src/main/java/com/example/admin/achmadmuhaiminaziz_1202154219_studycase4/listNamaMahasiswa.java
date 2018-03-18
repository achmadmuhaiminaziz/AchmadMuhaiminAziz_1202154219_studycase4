package com.example.admin.achmadmuhaiminaziz_1202154219_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class listNamaMahasiswa extends AppCompatActivity {
    //membuat String array dengan variable nama
    private String[] nama = { "Fazilla",
            "Faiz",
            "Jasmine",
            "Clara",
            "Hafidzah",
            "Harits",
            "Ibrahim",
            "Adiba",
            "Putri",
            "Reza",
            "Daru",
            "Dicky",
            "Jape",
            "Acong",
            "Ucil"};
    //memberi nama pada komponen yang ada
    private ListView list;
    private ProgressBar progressBar;
    private AddItemToListView addItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama_mahasiswa);

        //mendeklrasikan variabel dari komponen yang ada
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        list = (ListView) findViewById(R.id.listView);

        //Membuat progressbar unvisible ketika aplikasi berjalan
        list.setVisibility(View.GONE);

        //membuat array adapter
        list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));



    }
    //Memulai async task ketika button diklik
    public void mulai(View view) {
        //memanggil class mytask dan mengeksekusinya
        addItem = new AddItemToListView();
        addItem.execute();
    }

    public class AddItemToListView  extends AsyncTask<Void, String, Void> {
        //membuat variabel untuk arrayAdapter
        private ArrayAdapter<String> arrayAdapter;
        //membuat counter dengan tipe integer
        private int counter=1;
        //membuat progressdialog
        ProgressDialog progressDialog = new ProgressDialog(listNamaMahasiswa.this);

        //Dipanggil di thread UI sebelum tugas dijalankan
        //Langkah ini biasanya digunakan untuk mensetup tugas, misalnya dengan menampilkan progress bar pada user interface
        @Override
        protected void onPreExecute() {
            //casting suggestion
            arrayAdapter = (ArrayAdapter<String>) list.getAdapter();

            //Untuk progress dialog
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Loading Data");
            progressDialog.setProgress(0);
            //memperbarui kemajuan dialog dengan beberapa nilai


            //Menghandle tombol cancel pada dialog
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addItem.cancel(true);
                    //Menampilkan menjadi Visible progress bar pada layar dialog setelah diklik cancel
                    progressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            //menampilkan progress dialog
            progressDialog.show();
        }

        //Dipanggil di thread latar belakang segera setelah onPreExecute () selesai mengeksekusi.
        //Langkah ini digunakan untuk melakukan perhitungan background yang bisa memakan waktu lama.
        //Parameter tugas asinkron dilewatkan ke langkah ini.
        //Hasil perhitungan harus dikembalikan oleh langkah ini dan akan diteruskan kembali ke langkah terakhir.
        @Override
        protected Void doInBackground(Void... params) {
            //membuat perulangan untuk memunculkan nama mahasiswa dari array
            for (String item : nama){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    addItem.cancel(true);
                }
            }
            return null;  //mengembalikkan nilai
        }

        //Dipanggil di thread UI setelah ada panggilan untuk publishProgress(Progress ...).
        //Waktu eksekusi tidak terdefinisi.
        //Metode ini digunakan untuk menampilkan segala bentuk kemajuan dalam user interface,
        //sedangkan perhitungan background masih dieksekusi.
        //Misalnya, ini bisa digunakan untuk menghidupkan sebuah progress bar atau show logs dalam field teks.
        @Override
        protected void onProgressUpdate(String... values) {
            //adapter array memluai dari array 0
            arrayAdapter.add(values[0]);
            //menghitung counter untuk onProgressUpdate
            Integer current_status = (int)((counter/(float)nama.length)*100);
            progressBar.setProgress(current_status);

            //Set tampilan progress pada dialog progress
            progressDialog.setProgress(current_status);

            //Set message berupa persentase progress pada dialog progress
            progressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++; //mengeset hitungan di dalam progress dialog
        }

        //Dipanggil di thread UI setelah perhitungan latar belakang selesai.
        //Hasil perhitungan latar belakang dilewatkan ke langkah ini sebagai parameter.
        @Override
        protected void onPostExecute(Void Void) {
            //Menyembunyikan progressbar
            progressBar.setVisibility(View.GONE);

            //setelah loading progress sudah full maka otomatis akan hilang progress dialognya
            progressDialog.dismiss();
            list.setVisibility(View.VISIBLE);
        }

    }
}
