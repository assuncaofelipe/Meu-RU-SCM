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

class SabadoActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sabado)

        // set Arrow Back to Menu
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)

        db = FirebaseFirestore.getInstance()
        cardapioSabado()

    }

    private fun cardapioSabado() {
        progressLogin()
        db.collection("Cardapio")
            .document("Sabado").get()
            .addOnSuccessListener { documento ->
                if (documento != null && documento.exists()) {
                    val data = documento.data

                    val desjejumBebida = data?.get("desjejum_bebida")
                    val desjejumCom = data?.get("desjejum_complemento")
                    val almocoPro = data?.get("almoco_proteina")
                    val almocoCom = data?.get("almoco_complemento")
                    val jantarPro = data?.get("jantar_proteina")
                    val jantarCom = data?.get("jantar_complemento")

                    val desjejumSabadoBebida = findViewById<TextView>(R.id.refeicao_desjejum_sabado_bebida)
                    val desjejumSabadoCom = findViewById<TextView>(R.id.refeicao_desjejum_sabado_complemento)
                    val almocoSabadoPro = findViewById<TextView>(R.id.refeicao_almoco_sabado_proteina)
                    val almocoSabadoCom = findViewById<TextView>(R.id.refeicao_almoco_sabado_complemento)
                    val jantaSabadoPro = findViewById<TextView>(R.id.refeicao_jantar_sabado_proteina)
                    val jantaSabadoCom = findViewById<TextView>(R.id.refeicao_jantar_sabado_complemento)

                    desjejumSabadoBebida.text = "$desjejumBebida"
                    desjejumSabadoCom.text = "$desjejumCom"
                    almocoSabadoPro.text = "$almocoPro"
                    almocoSabadoCom.text = "$almocoCom"
                    jantaSabadoPro.text = "$jantarPro"
                    jantaSabadoCom.text = "$jantarCom"
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