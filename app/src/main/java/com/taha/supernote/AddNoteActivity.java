package com.taha.supernote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class AddNoteActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Button confirm;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Note");
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.add_note_title_edit_text);
        description = findViewById(R.id.add_note_description_edit_text);
        confirm = findViewById(R.id.add_note_confirm_button);
        cancel = findViewById(R.id.add_note_cancel_button);

        confirm.setOnClickListener(view -> {
            saveNote();
        });

        cancel.setOnClickListener(view -> {
            finish();
        });
    }

    public void saveNote(){

        String title = this.title.getText().toString();
        String description = this.description.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(MainActivity.TITLE_KEY,title);
        intent.putExtra(MainActivity.DESCRIPTION_KEY,description);
        setResult(RESULT_OK,intent);
        finish();
    }
}