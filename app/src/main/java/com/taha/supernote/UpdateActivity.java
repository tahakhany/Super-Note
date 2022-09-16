package com.taha.supernote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {

    EditText description;
    EditText title;
    Button confirm;
    Button cancel;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Update Note");
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.update_note_title_edit_text);
        description = findViewById(R.id.update_note_description_edit_text);
        confirm = findViewById(R.id.update_note_confirm_button);
        cancel = findViewById(R.id.update_note_cancel_button);

        getData();

        confirm.setOnClickListener(view -> {
            updateNote();
        });

        cancel.setOnClickListener(view -> {
            finish();
        });
    }

    private void updateNote() {
        if(id != -1){
            String title = this.title.getText().toString();
            String description = this.description.getText().toString();

            Intent intent = new Intent(UpdateActivity.this,MainActivity.class);

            intent.putExtra(MainActivity.TITLE_KEY,title);
            intent.putExtra(MainActivity.DESCRIPTION_KEY,description);
            intent.putExtra(MainActivity.ID_KEY,id);

            setResult(RESULT_OK,intent);

            finish();
        }
    }

    public void getData(){
        Intent intent = getIntent();

        id = intent.getIntExtra(MainActivity.ID_KEY,-1);
        String title = intent.getStringExtra(MainActivity.TITLE_KEY);
        String description = intent.getStringExtra(MainActivity.DESCRIPTION_KEY);

        this.title.setText(title);
        this.description.setText(description);
    }
}
