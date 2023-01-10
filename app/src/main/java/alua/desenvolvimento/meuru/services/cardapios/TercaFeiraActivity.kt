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

class TercaFeiraActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terca_feira)

        // set Arrow Back to Menu
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)

        db = FirebaseFirestore.getInstance()
        cardapioTerca()
    }

    private fun cardapioTerca() {
        progressLogin()
        db.collection("Cardapio")
            .document("TercaFeira").get()
            .addOnSuccessListener { documento ->
                if (documento != null && documento.exists()) {
                    val data = documento.data

                    val desjejumBebida = data?.get("desjejum_bebida")
                    val desjejumComplemento = data?.get("desjejum_complemento")
                    val almocoProteina = data?.get("almoco_proteina")
                    val almocoCompleto = data?.get("almoco_complemento")
                    val jantarProteina = data?.get("jantar_proteina")
                    val jantarComplemento = data?.get("jantar_complemento")

                    val desjejumTercBebida =
                        findViewById<TextView>(R.id.refeicao_desjejum_terc_bebida)
                    val desjejumTercComplemento =
                        findViewById<TextView>(R.id.refeicao_desjejum_terc_complemento)
                    val almocoTercProteina =
                        findViewById<TextView>(R.id.refeicao_almoco_terc_proteina)
                    val almocoTercComplemento =
                        findViewById<TextView>(R.id.refeicao_almoco_terc_complemento)
                    val jantaTercProteina =
                        findViewById<TextView>(R.id.refeicao_jantar_terc_proteina)
                    val jantaTercComplemento =
                        findViewById<TextView>(R.id.refeicao_jantar_terc_complemento)

                    desjejumTercBebida.text = "$desjejumBebida"
                    desjejumTercComplemento.text = "$desjejumComplemento"
                    almocoTercProteina.text = "$almocoProteina"
                    almocoTercComplemento.text = "$almocoCompleto"
                    jantaTercProteina.text = "$jantarProteina"
                    jantaTercComplemento.text = "$jantarComplemento"
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