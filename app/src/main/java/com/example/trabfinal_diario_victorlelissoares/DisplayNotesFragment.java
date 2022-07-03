package com.example.trabfinal_diario_victorlelissoares;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabfinal_diario_victorlelissoares.databinding.FragmentDisplayNotesBinding;

import java.util.ArrayList;
import java.util.Objects;

public class DisplayNotesFragment extends Fragment {

    private FragmentDisplayNotesBinding binding;
    User actualUser;
    DBHelper db;
    ArrayList <Note> notesOfUser;
    ArrayAdapter <Note> adapterOfNotes;

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
        registerForContextMenu(binding.listNotes);//menu de contexto
        db = new DBHelper(getContext());
        Bundle bundle = getArguments();
        if(bundle!=null) {
            //recebe o usuario que foi logado, no fragmento de login
            actualUser = (User) bundle.getSerializable("userNote");
            binding.txtNome.setText("Bem vindo (a), " + actualUser.getNome());
            //preenche o list view com as notas registradas
            String id = String.valueOf(actualUser.getIdUser());
            Toast toast = Toast.makeText(getContext(),
                    "1 if " + id, Toast.LENGTH_LONG);
            toast.show();
            //caso o bundle venha do fragmento de adicionar notas
            //significa que tem notas para serem mostradas

            //getBundle retorna null se não tem nenhum valor associado a chave
            try{
                notesOfUser = db.listNotes(id);
                db.close();

                adapterOfNotes = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_list_item_1, notesOfUser);

                binding.listNotes.setAdapter(adapterOfNotes);
            }catch (Exception e){
                e.printStackTrace();
            }
            /*if(bundle.getBundle("userOfNote") != null) {
                toast = Toast.makeText(getContext(),
                        "2 if" + id, Toast.LENGTH_LONG);
                toast.show();
                notesOfUser = db.listNotes(id);
                db.close();
            }
            //caso a lista não esteja vazia
            if(notesOfUser != null){
                toast = Toast.makeText(getContext(),
                        "3 if" + id, Toast.LENGTH_LONG);
                toast.show();
                adapterOfNotes = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_list_item_1, notesOfUser);

                binding.listNotes.setAdapter(adapterOfNotes);
            }*/
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
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add(Menu.NONE, 1, 1,"Deletar Nota");
        MenuItem mEdita = menu.add(Menu.NONE, 2, 2,"Editar Nota");
        MenuItem mSair = menu.add(Menu.NONE, 3, 3,"Cancelar");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false; }
        });

        mEdita.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });



        super.onCreateContextMenu(menu, v, menuInfo);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}