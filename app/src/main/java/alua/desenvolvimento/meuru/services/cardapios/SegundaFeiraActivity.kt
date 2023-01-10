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

class SegundaFeiraActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_feira)

        // set White Arrow Back to Menu
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)

        db = FirebaseFirestore.getInstance()
        cardapioSegunda()

    }

    private fun cardapioSegunda() {
        progressLogin()
        db.collection("Cardapio")
            .document("SegundaFeira").get()
            .addOnSuccessListener { documento ->
                if (documento != null && documento.exists()) {
                    val data = documento.data

                    val desjejumComplemento = data?.get("desjejum_complemento")
                    val desjejumBebidas = data?.get("desjejum_bebida")
                    val almocoProteina = data?.get("almoco_proteina")
                    val almocoComplemento = data?.get("almoco_complemento")
                    val jantarProteina = data?.get("jantar_proteina")
                    val jantarComplemento = data?.get("jantar_complemento")

                    val desjejumSegundaBebida = findViewById<TextView>(R.id.refeicao_desjejum_seg_bebidas)
                    val desjejumSegunda = findViewById<TextView>(R.id.refeicao_desjejum_seg)
                    val almocoSegundaProt = findViewById<TextView>(R.id.refeicao_almoco_seg_proteina)
                    val almocoSegundaComple = findViewById<TextView>(R.id.refeicao_almoco_seg_complemento)
                    val jantaSegundaProteina = findViewById<TextView>(R.id.refeicao_jantar_seg_proteina)
                    val jantaSegundaComple = findViewById<TextView>(R.id.refeicao_jantar_seg_complemento)

                    desjejumSegundaBebida.text = "$desjejumBebidas"
                    desjejumSegunda.text = "$desjejumComplemento"
                    almocoSegundaProt.text = "$almocoProteina"
                    almocoSegundaComple.text = "$almocoComplemento"
                    jantaSegundaProteina.text = "$jantarProteina"
                    jantaSegundaComple.text = "$jantarComplemento"
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