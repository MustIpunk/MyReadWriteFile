package com.example.saiful.myreadwritefile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText edtContent;
    EditText edtTitle;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = findViewById(R.id.btn_new);
        btnOpen = findViewById(R.id.btn_open);
        btnSave = findViewById(R.id.btn_save);
        edtContent = findViewById(R.id.edit_file);
        edtTitle = findViewById(R.id.edt_title);
        path = getFilesDir();

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    public void newFile() {
        edtTitle.setText("");
        edtContent.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this, title);
        edtTitle.setText(fileModel.getFilename());
        edtContent.setText(fileModel.getData());
        Toast.makeText(this, "Loading" + fileModel.getFilename() + "data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    private void showList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, path.list());

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void savedFile() {
        if (edtTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if (edtContent.getText().toString().isEmpty()) {
            Toast.makeText(this, "Konten harus diisi terlebih dahuli", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtTitle.getText().toString();
            String text = edtContent.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving" + fileModel.getFilename() + "file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_new:
                newFile();
                break;
            case R.id.btn_open:
                openFile();
                break;
            case R.id.btn_save:
                savedFile();
                break;

        }

    }
}
