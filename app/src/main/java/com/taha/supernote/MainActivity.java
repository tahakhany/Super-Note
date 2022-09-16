package com.taha.supernote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private final int MENU_ADD_NEW = R.id.menu_add_item;
    private final int ADD_REQUEST_CODE = 1234;
    private final int UPDATE_REQUEST_CODE = 4321;
    public static final String TITLE_KEY = "title";
    public static final String DESCRIPTION_KEY = "description";
    public static final String ID_KEY = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_activity_recycler_view);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                recyclerViewAdapter.setNotes(notes);
            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);

        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        noteViewModel.delete(recyclerViewAdapter.getNote(viewHolder.getAdapterPosition()));

                    }
                }).attachToRecyclerView(recyclerView);

        recyclerViewAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
            intent.putExtra(TITLE_KEY, note.getTitle());
            intent.putExtra(DESCRIPTION_KEY, note.getDescription());
            intent.putExtra(ID_KEY,note.getId());
            startActivityForResult(intent,UPDATE_REQUEST_CODE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ADD_NEW:
                startActivityForResult(new Intent(this, AddNoteActivity.class),
                        ADD_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = Objects.requireNonNull(data).getStringExtra(TITLE_KEY);
            String description = Objects.requireNonNull(data).getStringExtra(DESCRIPTION_KEY);

            Note note = new Note(title, description);
            noteViewModel.insert(note);
        } else if(requestCode == this.UPDATE_REQUEST_CODE && resultCode == RESULT_OK){
            String title = Objects.requireNonNull(data).getStringExtra(TITLE_KEY);
            String description = Objects.requireNonNull(data).getStringExtra(DESCRIPTION_KEY);
            int id = Objects.requireNonNull(data).getIntExtra(ID_KEY,-1);

            Note note = new Note(id,title,description);
            noteViewModel.update(note);
        }
    }

}