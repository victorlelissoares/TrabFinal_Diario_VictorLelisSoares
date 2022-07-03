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

import com.example.trabfinal_diario_victorlelissoares.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    DBHelper db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //caso o email ou senha estejam vazios
                if(TextUtils.isEmpty(binding.txtEmail.getText()) ||
                        TextUtils.isEmpty(binding.txtPassword.getText())) {

                    if(TextUtils.isEmpty(binding.txtEmail.getText()))
                        binding.txtEmail.setError("Campo de Email vazio");

                    if(TextUtils.isEmpty(binding.txtPassword.getText()))
                        binding.txtPassword.setError("Campo de Senha vazio");
                }
                //caso não
                else{
                    db = new DBHelper(getContext());
                    String password = db.buscarSenha(binding.txtEmail.getText().toString());
                    if(password.equals(binding.txtPassword.getText().toString())) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userNote", db.buscarUser(binding.txtEmail.getText().toString()));
                        NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_displayNotesFragment, bundle);
                    }

                    else{
                        Toast toast = Toast.makeText(getContext(),
                                "Email ou senha inválido", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });

        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}