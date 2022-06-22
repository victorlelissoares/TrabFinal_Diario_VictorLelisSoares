package com.example.trabfinal_diario_victorlelissoares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentNewNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null){
            //recupera informações do usuario que está adicionando a nota
            actualUser = (User) bundle.getSerializable("userOfNote");
        }

        binding.btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNote = new Note();
                newNote.setExternIdUser(actualUser.getIdUser());
                newNote.setNoteTitle(binding.txtTitulo.getText().toString());
                newNote.setNoteText(binding.txtNote.getText().toString());
                db.insereNote(newNote);
                NavHostFragment.findNavController(NewNoteFragment.this).navigate(R.id.action_newNoteFragment_to_displayNotesFragment, bundle);

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}