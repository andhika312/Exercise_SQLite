package com.example.exercisesql_lite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CRUDkontak extends AppCompatActivity {
   //o
    EditText nama, phone, email, alamat;
    Spinner tipeUser;String mode="",idUser="0";
    Button btnSimpan, btnKembali;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();

                    return true;
            }
        }
        return false;

    }

    private static  String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudkontak);

        nama =(EditText) findViewById(R.id.editTextNama);
        phone =(EditText) findViewById(R.id.editTextNoTelp);
        email =(EditText) findViewById(R.id.editTextEmail);
        alamat =(EditText) findViewById(R.id.editTextAlamat);

        btnSimpan=(Button) findViewById(R.id.btnsimpan);
        btnKembali=(Button) findViewById(R.id.btnkembali);

        Intent in = this.getIntent();
        mode= in.getStringExtra("mode");

        if(mode.equals("Edit")){
            idUser =  in.getStringExtra("id_user");

            DBHelper db = new DBHelper(getApplicationContext());
            ModKontak modKontak = db.getUserById(idUser);

            nama.setText(modKontak.getNama());
            phone.setText(modKontak.getPhone());
            email.setText(modKontak.getEmail());
            alamat.setText(modKontak.getAlamat());

        }
    }
    public View Simpan(View v){
        if(nama.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Data Harus Diisi Semua !.",Toast.LENGTH_SHORT).show();
        }else if(phone.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Data Harus Diisi Semua !.",Toast.LENGTH_SHORT).show();
        }else if(email.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Data Harus Diisi Semua !.",Toast.LENGTH_SHORT).show();
        }else if(alamat.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Data Harus Diisi Semua !.",Toast.LENGTH_SHORT).show();
        }else if(!email.getText().toString().matches(EMAIL_REGEX)){
            Toast.makeText(getApplicationContext(),
                    "Format Email Salah !.",Toast.LENGTH_SHORT).show();
        }else{
            ModKontak modKontak = new ModKontak();
            modKontak.setNama(nama.getText().toString());
            modKontak.setPhone(phone.getText().toString());
            modKontak.setEmail(email.getText().toString());
            modKontak.setAlamat(alamat.getText().toString());

            DBHelper db = new DBHelper(getApplicationContext());


            if(mode.equals("Edit")){
                modKontak.setIdUser(idUser);
                int status = db.UpdateData(modKontak);
                if(status   >0){
                    Toast.makeText(CRUDkontak.this, "Data Berhasil Diubah ",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(CRUDkontak.this, "Data Gagal Diubah ",Toast.LENGTH_LONG).show();
                }

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();


            }else{
                db.TambahKontak(modKontak);
                Toast.makeText(CRUDkontak.this, "Kontak Berhasil Ditambah.",Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }

        }

        return v;
    }

    public View Kembali(View v){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
        return v;
    }

}
