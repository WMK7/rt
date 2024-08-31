package com.example.analisesatisfacao_rt.view

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.analisesatisfacao_rt.R
import com.example.analisesatisfacao_rt.databinding.ActivityCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class ViewCadastro : AppCompatActivity() {

    lateinit var binding : ActivityCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCadastrar.setOnClickListener {view ->
            val nome =  binding.editNomeCadastro.text.toString()
            val email = binding.editEmailCadastro.text.toString()
            val senha = binding.editSenha.text.toString()

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                val  snackbar = Snackbar.make(view,"Preencha Todos os Campos",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener { cadastro ->
                   if (cadastro.isSuccessful){
                       val  snackbar = Snackbar.make(view,"Usuario Cadastrado com Sucesso!",Snackbar.LENGTH_SHORT)
                       snackbar.setBackgroundTint(Color.BLUE)
                       snackbar.show()
                       binding.editNomeCadastro.setText("")
                       binding.editEmailCadastro.setText("")
                       binding.editNomeCadastro.setText("")
                   }
                }.addOnFailureListener {exeption ->
                    val menssagemErro = when(exeption){
                        is FirebaseAuthWeakPasswordException ->"Digite uma senha de no minimo 6 numeros"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um Email Válido"
                        is FirebaseAuthUserCollisionException-> "Este Email ja foi Cadastrado Vá para Esqueci minha Senha"
                        is FirebaseNetworkException -> " Sem Conexão com a Internet!"
                        else -> "Erro ao Cadastrar Aluno"
                    }
                    val  snackbar = Snackbar.make(view,menssagemErro,Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }


        }
    }
}