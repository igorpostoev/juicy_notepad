package com.example.host.juicynotepad;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class NotesRVAdapter extends RecyclerView.Adapter<NotesRVAdapter.NoteViewHolder> {
    private List<Note> notes;
    private MainActivity mContext;

    NotesRVAdapter(List<Note> notes, MainActivity mContext){
        this.notes = notes;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note,viewGroup,false);

        return new NoteViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteVH, int position) {
        Note note = notes.get(Helper.curRowCount - position - 1 );
        noteVH.tvData.setText(note.preview);
        noteVH.tvTime.setText(note.time);
        noteVH.itemView.setTag(note.id);

        if(mContext.isSelectionMode()){
            noteVH.note = notes.get(position);
            noteVH.checkBox.setVisibility(View.VISIBLE);
        } else {
            noteVH.checkBox.setVisibility(View.GONE);
        }
    }

    static public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        VerticalItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvData;
        TextView tvTime;
        CheckBox checkBox;
        Note note;

        NoteViewHolder(View itemView, final MainActivity mContext){
            super(itemView);

            float density = mContext.getResources().getDisplayMetrics().density;
            float pixels = mContext.getResources().getInteger(R.integer.rvItemHeigth);
            int height  = Math.round(pixels*density);
            int margin_bottom = mContext.getResources().getInteger(R.integer.rvItemMarginBottom);
            LinearLayout.LayoutParams bottomMargin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            bottomMargin.setMargins(0,0, 0, margin_bottom);

            tvData = itemView.findViewById(R.id.tvNoteData);
            tvTime = itemView.findViewById(R.id.tvNoteTime);
            checkBox = itemView.findViewById(R.id.chSelect);

            checkBox.setVisibility(View.GONE);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mContext.setSelectionMode(true);
                    notifyDataSetChanged();
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NoteActivity.class);
                    intent.putExtra("id", view.getTag().toString());
                    mContext.startActivity(intent);
                }
            });

            itemView.setLayoutParams(bottomMargin);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked()){
                        note.setSelected(true);
                    } else {
                        note.setSelected(false);
                    }
                }
            });
        }
    }

    List<Note> getSelectedItems() {
        List<Note> selectedNotes = new ArrayList<>();
        for(Note note : notes){
          if(note.isSelected()){
              selectedNotes.add(note);
          }
        }

        return  selectedNotes;
    }

    void updateView(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    interface Selectable{
        void setSelectionMode(boolean isSelected);
    }
}
