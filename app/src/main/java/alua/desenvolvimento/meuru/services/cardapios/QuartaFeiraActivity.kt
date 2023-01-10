package alua.desenvolvimento.meuru.services.cardapios

import alua.desenvolvimento.meuru.R
import alua.desenvolvimento.meuru.ui.fragment.LoadingDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class QuartaFeiraActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quarta_feira)

        // set Arrow Back to Menu
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)

        db = FirebaseFirestore.getInstance()
        cardapioQuarta()
    }

    private fun cardapioQuarta() {
        progressLogin()
        db.collection("Cardapio")
            .document("QuartaFeira").get()
            .addOnSuccessListener { documento ->
                if (documento != null && documento.exists()) {
                    val data = documento.data

                    val desjejumBebida = data?.get("desjejum_bebida")
                    val desjejumComplemento = data?.get("desjejum_complemento")
                    val almocoProteina = data?.get("almoco_proteina")
                    val almocoComplemento = data?.get("almoco_complemento")
                    val jantarProteina = data?.get("jantar_proteina")
                    val jantarComplemento = data?.get("jantar_complemento")

                    val desjejumQuartaBebida = findViewById<TextView>(R.id.refeicao_desjejum_quarta_bebida)
                    val desjejumQuartaComplemento = findViewById<TextView>(R.id.refeicao_desjejum_quarta_complemento)

                    val almocoQuartaPro = findViewById<TextView>(R.id.refeicao_almoco_quarta_proteina)
                    val almocoQuartaCom = findViewById<TextView>(R.id.refeicao_almoco_quarta_complemento)

                    val jantaQuartaPro = findViewById<TextView>(R.id.refeicao_jantar_quarta_proteina)
                    val jantaQuartaCom = findViewById<TextView>(R.id.refeicao_jantar_quarta_complemento)

                    desjejumQuartaBebida.text = "$desjejumBebida"
                    desjejumQuartaComplemento.text = "$desjejumComplemento"

                    almocoQuartaPro.text = "$almocoProteina"
                    almocoQuartaCom.text = "$almocoComplemento"

                    jantaQuartaPro.text = "$jantarProteina"
                    jantaQuartaCom.text = "$jantarComplemento"
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