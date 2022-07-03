package com.example.trabfinal_diario_victorlelissoares;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabfinal_diario_victorlelissoares.databinding.FragmentNewNoteBinding;

public class NewNoteFragment extends Fragment {

    private FragmentNewNoteBinding binding;
    Note newNote;
    User actualUser;
    DBHelper db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentNewNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null){
            if (bundle.containsKey("editNote")){
                actualUser = new User();
                newNote = (Note) bundle.getSerializable("editNote");
                db = new DBHelper(getContext());
                actualUser = db.buscarUserId(String.valueOf(newNote.getExternIdUser()));
                binding.txtTitulo.setText(newNote.getNoteTitle());
                binding.txtNote.setText(newNote.getNoteText());
                binding.btnSaveNote.setText("Editar nota");
            }
            else {
                //recupera informações do usuario que está adicionando a nota
                actualUser = (User) bundle.getSerializable("userOfNote");
            }
        }

        binding.btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(binding.txtTitulo.getText()) || TextUtils.isEmpty(binding.txtNote.getText())) {

                    if(TextUtils.isEmpty(binding.txtTitulo.getText())) {
                        binding.txtTitulo.setError("Campo de titulo vazio");
                    }

                    if(TextUtils.isEmpty(binding.txtNote.getText())) {
                        binding.txtNote.setError("Campo de texto vazio");
                    }
                }
                else {
                    db = new DBHelper(getContext());
                    if (bundle != null && bundle.containsKey("editNote")) {
                        newNote.setExternIdUser(actualUser.getIdUser());
                        newNote.setNoteTitle(binding.txtTitulo.getText().toString());
                        newNote.setNoteText(binding.txtNote.getText().toString());
                        db.atualizarNote(newNote);
                    } else {
                        newNote = new Note();
                        newNote.setExternIdUser(actualUser.getIdUser());
                        newNote.setNoteTitle(binding.txtTitulo.getText().toString());
                        newNote.setNoteText(binding.txtNote.getText().toString());
                        db.insereNote(newNote);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userNote", actualUser);
                    NavHostFragment.findNavController(NewNoteFragment.this).navigate(R.id.action_newNoteFragment_to_displayNotesFragment, bundle);
                }

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}