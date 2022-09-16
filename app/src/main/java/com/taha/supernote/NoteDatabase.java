package com.taha.supernote;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public static NoteDatabase noteDatabase;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(noteDatabase == null){
            noteDatabase = Room.databaseBuilder(context.getApplicationContext()
                    ,NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
            return noteDatabase;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(noteDatabase).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;
        private NoteDatabase noteDatabase;

        public populateDbAsyncTask(NoteDatabase noteDatabase) {
            this.noteDatabase = noteDatabase;
            this.noteDao = noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.Insert(new Note("title 1","description 1"));
            noteDao.Insert(new Note("title 2","description 2"));
            noteDao.Insert(new Note("title 3","description 3"));
            return null;
        }
    }

}
