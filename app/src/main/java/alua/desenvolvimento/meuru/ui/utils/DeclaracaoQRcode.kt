package alua.desenvolvimento.meuru.ui.utils

import alua.desenvolvimento.meuru.R
import alua.desenvolvimento.meuru.databinding.ActivityDeclaracaoQrcodeBinding
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class DeclaracaoQRcode : AppCompatActivity() {
    private lateinit var binding: ActivityDeclaracaoQrcodeBinding
    private lateinit var user: FirebaseAuth

    private lateinit var imageView: ImageView
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeclaracaoQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set Arrow Back to Menu
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)

        // Aciona a instancia do Firebase pelo db
        db = FirebaseFirestore.getInstance()

        // chamando funções
        seeCurrentUser()
        mainQRcode()
    }

    private fun seeCurrentUser() {
        user = FirebaseAuth.getInstance()
        if (user.currentUser != null) {
            user.currentUser?.let {
                binding.txtResultEmail.text = it.email
            }
        }
    }

    private fun mainQRcode() {
        imageView = findViewById(R.id.img_qr_code)
        val userLogado = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("Alunos")
            .document(userLogado)
            .get()
            .addOnSuccessListener { documento ->
                if (documento != null && documento.exists()) {
                    val data = documento.data
                    val email = data?.get("email")

                    val screenWidth = resources.displayMetrics.widthPixels
                    val qrImage = generateQR(email as String?, screenWidth)
                    if (null != qrImage) {
                        imageView.setImageBitmap(qrImage)
                    }
                }
            }
    }

    private fun generateQR(content: String?, size: Int): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.encodeBitmap(
                content,
                BarcodeFormat.QR_CODE, size, size,
            )
        } catch (e: WriterException) {
            Log.e("generateQR()", "qr gerado")
        }
        return bitmap
    }
}