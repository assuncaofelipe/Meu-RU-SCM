package alua.desenvolvimento.meuru.ui.utils

import alua.desenvolvimento.meuru.R
import alua.desenvolvimento.meuru.ui.fragment.LoadingDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FichasRefeicaoActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fichas_refeicao)

        // set Arrow Back to Menu
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)

        // Aciona a instancia do Firebase pelo db
        db = FirebaseFirestore.getInstance()

        // chama a função ouvinte de aluno
        headerStudent()

    }

    // chamase-se "ouve_aluno()" pois o metodo addOnCompleteListener
    // é um ouvinte do documento que pertence a colecao
    private fun headerStudent() {
        progressLogin()
        //val userLogado = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val userLogado = FirebaseAuth.getInstance().uid!!
        db.collection("Alunos")
            .document(userLogado)
            .get()
            .addOnSuccessListener { documento ->
                if (documento != null && documento.exists()) {
                    val data = documento.data
                    val desjejum = data?.get("desjejum")
                    val almoco = data?.get("almoco")
                    val jantar = data?.get("jantar")

                    val txtDesjejum = findViewById<TextView>(R.id.refeicao_desjejum_seg)
                    val txtAlmoco = findViewById<TextView>(R.id.qtd_ficha_almoco)
                    val txtJantar = findViewById<TextView>(R.id.qtd_ficha_jantar)

                    txtDesjejum.text = "$desjejum"
                    txtAlmoco.text = "$almoco"
                    txtJantar.text = "$jantar"

                } else {
                    Toast.makeText(
                        baseContext,
                        "error ao ler documento, ele não existe ou está vazio",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }.addOnFailureListener { error ->
                Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT).show()
            }
    }

    private fun progressLogin() {
        val loading = LoadingDialog(this)
        loading.startLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                loading.isDismiss()
            }
        }, 3000)
    }
}