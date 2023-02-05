package alua.desenvolvimento.meuru.ui.utils

import alua.desenvolvimento.meuru.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class SobreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        // set Arrow Back to Menu
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)

        sobreInstituto()
    }

    private fun sobreInstituto(){
        val imageSlider = findViewById<ImageSlider>(R.id.imageSliderIcet)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.icet))
        imageList.add(SlideModel(R.drawable.icet_um))
        imageList.add(SlideModel(R.drawable.icet_dois))
        imageList.add(SlideModel(R.drawable.icet_tres))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }


}