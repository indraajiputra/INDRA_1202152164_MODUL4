package com.example.indrapro.indra_1202152164_studycase4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListMahasiswa extends AppCompatActivity {

    //membuat array nama mahasiswa
    private String[] data = { "A","B", "C", "D", "E", "F", "G", "H"};
    ListView listdata;
    Button btnmulai;

    private static Parcelable mListViewScrollPos = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        listdata = findViewById(R.id.list_data);
        btnmulai = findViewById(R.id.btn_mulai);

        //mengeset adapter array
        listdata.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<String>()));

        // Restore the ListView position
        if (mListViewScrollPos != null) {
            listdata.onRestoreInstanceState(mListViewScrollPos);
        }


        btnmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil class mytask
                new mytask().execute();
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the ListView position
        mListViewScrollPos = listdata.onSaveInstanceState();
    }

    class mytask extends AsyncTask<Void,String,String> {

        ArrayAdapter<String> adapter;
        ProgressDialog progressdialog;
        int count;


        @Override
        protected void onPreExecute() {
            //mengambil adapter dari array
            adapter = (ArrayAdapter<String>)listdata.getAdapter();

            //membuat object progress dialog
            progressdialog = new ProgressDialog(ListMahasiswa.this);
            //set judul progress dialog
            progressdialog.setTitle("Loading Data");
            progressdialog.setProgressStyle(progressdialog.STYLE_HORIZONTAL);
            progressdialog.setMax(8);
            progressdialog.setProgress(0);
            //menampilkan progress dialog
            progressdialog.show();
            count = 0;


        }


        @Override
        protected String doInBackground(Void... voids) {
            //membuat perulangan untuk memunculkan nama data
            for (String namamhs : data){
                publishProgress(namamhs);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            //mengembalikan nilai dengan teks
            return "complete";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //memulai array dari 0
            adapter.add(values[0]);
            //increment perhitungan
            count++;
            //mengeset hitungan di  progress dialog
            progressdialog.setProgress(count);
        }

        @Override
        protected void onPostExecute(String result) {
            //menampilkan nilai dari return yang ada di method doInBackground
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            //setelah loading progress sudah full maka otomatis akan hilang progress dialognya
            progressdialog.hide();


        }

    }


}
