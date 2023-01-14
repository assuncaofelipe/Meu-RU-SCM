package alua.desenvolvimento.meuru.services.cardapios

import alua.desenvolvimento.meuru.R
import alua.desenvolvimento.meuru.ui.fragment.LoadingDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class QuintaFeiraActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quinta_feira)

        // set Arrow Back to Menu
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)

        db = FirebaseFirestore.getInstance()
        cardapioQuinta()
    }

    private fun cardapioQuinta() {
        progressLogin()
        db.collection("Cardapio")
            .document("QuintaFeira").get()
            .addOnSuccessListener { documento ->
                if (documento != null && documento.exists()) {
                    val data = documento.data

                    val desjejumBebida = data?.get("desjejum_bebida")
                    val desjejumCom = data?.get("desjejum_complemento")

                    val almocoPro = data?.get("almoco_proteina")
                    val almocoCom = data?.get("almoco_complemento")
                    val jantarPro = data?.get("jantar_proteina")
                    val jantarCom = data?.get("jantar_complemento")

                    val desjejumQuinBebida = findViewById<TextView>(R.id.refeicao_desjejum_quinta_bebida)
                    val desjejumQuinCom = findViewById<TextView>(R.id.refeicao_desjejum_quinta_complemento)
                    val almocoQuinPro = findViewById<TextView>(R.id.refeicao_almoco_quinta_proteina)
                    val almocoQuinCom = findViewById<TextView>(R.id.refeicao_almoco_quinta_complemento)
                    val jantaQuinPro = findViewById<TextView>(R.id.refeicao_jantar_quinta_proteina)
                    val jantaQuinCom = findViewById<TextView>(R.id.refeicao_jantar_quinta_complemento)

                    desjejumQuinBebida.text = "$desjejumBebida"
                    desjejumQuinCom.text = "$desjejumCom"
                    almocoQuinPro.text = "$almocoPro"
                    almocoQuinCom.text = "$almocoCom"
                    jantaQuinPro.text = "$jantarPro"
                    jantaQuinCom.text = "$jantarCom"
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