package com.servershit;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    private List<NoteDto> notesList = new ArrayList<>();
    private NoteDtoAdapter adapter;
    private TextView noNotesView;
    private DatabaseHelper helper;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);

        initData();
        initRecycleView();
        toggleEmptyNotes();
        rowClickListener();
    }

    private void initData() {
        recyclerView = findViewById(R.id.recycler_view);
        noNotesView = findViewById(R.id.empty_notes_view);
        helper = new DatabaseHelper(this);
        adapter = new NoteDtoAdapter(notesList);
    }

    private void initRecycleView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        // adding built-in divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        notesList.addAll(helper.getAllNotes());
    }

    public void newBtn (View v){
        showNoteDialog(false, null, -1);
    }

    private void rowClickListener() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    /**
     * Inserting new note in helper
     * and refreshing the list
     */
    private void createNote(String note) {
        // inserting note in helper and getting
        // newly inserted note id
        long id = helper.insertNote(note);

        // get the newly inserted note from helper
        NoteDto n = helper.getNote(id);

        if (n != null) {
            // adding new note to array list at 0 position
            notesList.add(0, n);

            // refreshing the list
            adapter.notifyDataSetChanged();

            toggleEmptyNotes();
        }
    }

    /**
     * Updating note in helper and updating
     * item in the list by its position
     */
    private void updateNote(String note, int position) {
        NoteDto n = notesList.get(position);
        // updating note text
        n.setNote(note);

        // updating note in helper
        helper.updateNote(n);

        // refreshing the list
        notesList.set(position, n);
        adapter.notifyItemChanged(position);

        toggleEmptyNotes();
    }

    /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
    private void deleteNote(int position) {
        // deleting the note from helper
        helper.deleteNote(notesList.get(position));

        // removing the note from the list
        notesList.remove(position);
        adapter.notifyItemRemoved(position);

        toggleEmptyNotes();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, notesList.get(position), position);
                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }

    /**
     * Shows alert dialog with EditText options to enter / edit
     * a note.
     * when shouldUpdate=true, it automatically displays old note and changes the
     * button text to UPDATE
     */
    private void showNoteDialog(final boolean shouldUpdate, final NoteDto note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(RecycleViewActivity.this, R.style.AppTheme);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNote = view.findViewById(R.id.note);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && note != null) {
            inputNote.setText(note.getNote());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    Toast.makeText(MyApplication.context, "Enter note!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && note != null) {
                    // update note by it's id
                    updateNote(inputNote.getText().toString(), position);
                } else {
                    // create new note
                    createNote(inputNote.getText().toString());
                }
            }
        });
    }

    /**
     * Toggling list and empty notes view
     */
    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (helper.getNotesCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }
}
