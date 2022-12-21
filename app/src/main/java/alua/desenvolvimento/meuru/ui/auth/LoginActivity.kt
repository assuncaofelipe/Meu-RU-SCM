package alua.desenvolvimento.meuru.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alua.desenvolvimento.meuru.R
import alua.desenvolvimento.meuru.databinding.ActivityLoginBinding
import alua.desenvolvimento.meuru.ui.fragment.LoadingDialog
import alua.desenvolvimento.meuru.ui.home.MenuActivity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        loginApp()
    }

    private fun loginApp() {
        val editEmail = findViewById<EditText>(R.id.edit_email)
        val editSenha = findViewById<EditText>(R.id.edit_senha)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val ckcMostrarSenha = findViewById<CheckBox>(R.id.ckb_mostrar_senha)

        btnLogin.setOnClickListener {
            val loginEmail = editEmail.text.toString()
            val loginSenha = editSenha.text.toString()

            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginSenha)) {
                Toast.makeText(
                    this@LoginActivity,
                    "Carregando, aguarde...",
                    Toast.LENGTH_SHORT
                ).show()
                //progressLogin()
                mAuth!!.signInWithEmailAndPassword(loginEmail, loginSenha)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            abrirTelaMenu()
                        } else {
                            val error = task.exception!!.message
                            Toast.makeText(this@LoginActivity, "" + error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else if (TextUtils.isEmpty(loginEmail) || (TextUtils.isEmpty(loginSenha))) {
                preencheCampos()
            }
        }
        ckcMostrarSenha.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editSenha.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                editSenha.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun abrirTelaMenu() {
        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun progressLogin() {
        val loading = LoadingDialog(this)
        loading.startLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                loading.isDismiss()
            }
        }, 2000)
    }

    private fun preencheCampos() {
        Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        val usuarioAtual = FirebaseAuth.getInstance().currentUser
        if (usuarioAtual != null) {
            abrirTelaMenu()
        }
    }
}