package com.taha.supernote;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewLayout> {

    Context context;
    List<Note> notes;

    public RecyclerViewAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    public RecyclerViewAdapter() {
    }

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.notes = new ArrayList<>();
    }

    @NonNull
    @Override
    public CardViewLayout onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new CardViewLayout(view);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewLayout holder, int position) {

        Note note = notes.get(position);
        holder.titleTextView.setText(note.title);
        holder.descriptionTextView.setText(note.description);
        holder.cardView.setBackgroundColor(Color.parseColor("#191C1E"));
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position){
        return notes.get(position);
    }

    public class CardViewLayout extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        CardView cardView;

        public CardViewLayout(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.card_view_title_text);
            descriptionTextView = itemView.findViewById(R.id.card_view_description_text);
            cardView = itemView.findViewById(R.id.card_view_card_view);
        }
    }
}
