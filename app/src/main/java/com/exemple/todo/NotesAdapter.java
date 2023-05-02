package com.exemple.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NotesAdapter extends FirestoreRecyclerAdapter<Note, NotesAdapter.NoteViewHolder> {
    Context context;

    public NotesAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.titleTextView.setText(note.title);
        holder.contentTextView.setText(note.content); // "contentTexview" should be changed to "contentTextView"
        holder.timestampTextView.setText(Utility.timestampToString(note.timestamp));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "NoteViewHolder.remove(item)" should be changed to "getSnapshots().getSnapshot(position).getReference().delete()"
                getSnapshots().getSnapshot(position).getReference().delete();
                notifyItemRemoved(position);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note, parent, false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView, timestampTextView;
        // "deleteButton" needs to be added as a member variable
        View deleteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title_text_view);
            contentTextView = itemView.findViewById(R.id.note_content_text_view); // "note_title_text_view" should be changed to "note_content_text_view"
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);
            // "delete_button" should be changed to "delete_button_view"
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
