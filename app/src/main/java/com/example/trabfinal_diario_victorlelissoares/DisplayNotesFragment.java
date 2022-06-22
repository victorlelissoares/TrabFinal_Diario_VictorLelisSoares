package com.example.trabfinal_diario_victorlelissoares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabfinal_diario_victorlelissoares.databinding.FragmentDisplayNotesBinding;

public class DisplayNotesFragment extends Fragment {

    private FragmentDisplayNotesBinding binding;
    User actualUser;
    DBHelper db;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentDisplayNotesBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle!=null) {
            //recebe o usuario que foi logado, no fragmento de login
            actualUser = (User) bundle.getSerializable("userNote");
            binding.txtNome.setText("Bem vindo (a), " + actualUser.getNome());
        }

        binding.btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("userOfNote", actualUser);
                NavHostFragment.findNavController(DisplayNotesFragment.this).navigate(R.id.action_displayNotesFragment_to_newNoteFragment, bundle);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}