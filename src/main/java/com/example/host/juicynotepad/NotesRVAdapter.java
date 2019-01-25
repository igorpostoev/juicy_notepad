package com.example.host.juicynotepad;

import android.content.Context;
import android.graphics.Rect;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class NotesRVAdapter extends RecyclerView.Adapter<NotesRVAdapter.NoteViewHolder> {
    private List<Note> notes;
    NotesRVAdapter(List<Note> notes){
        this.notes = notes;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note,viewGroup,false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteVH, int i) {
        noteVH.tvData.setText(notes.get(i).data);
        noteVH.tvTime.setText(notes.get(i).time);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView tvData;
        TextView tvTime;

        NoteViewHolder(View itemView){
            super(itemView);

            tvData = itemView.findViewById(R.id.tvNoteData);
            tvTime = itemView.findViewById(R.id.tvNoteTime);

        }
    }

    static public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
