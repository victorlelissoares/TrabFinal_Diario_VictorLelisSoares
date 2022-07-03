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

import com.example.trabfinal_diario_victorlelissoares.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    User newUser = new User();
    DBHelper helper;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.txtRegisterEmail.getText()) ||
                        TextUtils.isEmpty(binding.txtRegisterPassword.getText()) ||
                        TextUtils.isEmpty(binding.txtRegisterName.getText()) ||
                        TextUtils.isEmpty(binding.txtRegisterConfPassword.getText())){

                    if(TextUtils.isEmpty(binding.txtRegisterEmail.getText()))
                        binding.txtRegisterEmail.setError("Campo de email vazio");

                    if(TextUtils.isEmpty(binding.txtRegisterPassword.getText()))
                        binding.txtRegisterPassword.setError("Campo de senha vazio");

                    if(TextUtils.isEmpty(binding.txtRegisterName.getText()))
                        binding.txtRegisterName.setError("Campo de nome vazio");

                    if(TextUtils.isEmpty(binding.txtRegisterConfPassword.getText()))
                        binding.txtRegisterConfPassword.setError("Campo de Conf. de senha vazio");
                }
                else {
                    newUser.setNome(binding.txtRegisterName.getText().toString());
                    newUser.setEmail(binding.txtRegisterEmail.getText().toString());
                    String senha = binding.txtRegisterPassword.getText().toString();
                    String confSenha = binding.txtRegisterConfPassword.getText().toString();

                    if (!confSenha.equals(senha)) {
                        Toast toast = Toast.makeText(getContext(),
                                "Confirmação de senha difere da senha", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        newUser.setSenha(senha);
                        helper = new DBHelper(getContext());
                        //falta verificar antes de inserir e um campo de confirmar a senha
                        helper.insereUser(newUser);
                        NavHostFragment.findNavController(RegisterFragment.this).
                                navigate(R.id.action_registerFragment_to_loginFragment);
                    }

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