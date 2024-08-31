package com.example.analisesatisfacao_rt.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.analisesatisfacao_rt.R
import com.example.analisesatisfacao_rt.databinding.ActivityCadastroBinding
import com.example.analisesatisfacao_rt.databinding.ActivityLoginBinding
import com.example.analisesatisfacao_rt.databinding.ActivityViewAdmBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ViewLogin: AppCompatActivity() {
    lateinit var  binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btEntrar.setOnClickListener { view ->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar =
                    Snackbar.make(view, "Preencha Todos os Campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener { autencicacao ->
                    if(autencicacao.isSuccessful){
                        val navegarQuestionario = Intent(this,ViewTangibilidade::class.java)
                        startActivity(navegarQuestionario)
                        finish()
                    }
                }
            }

            binding.naoCadastrado.setOnClickListener {
                val intent = Intent(this, ViewCadastro::class.java)
                startActivity(intent)
                finish()
            }
        }

        fun onStart() {
            super.onStart()

            val usuarioAtual = FirebaseAuth.getInstance().currentUser

            if(usuarioAtual != null){
                val navegarQuestionario = Intent(this,viewADM::class.java)
                startActivity(navegarQuestionario)
                finish()
            }
        }
    }
}