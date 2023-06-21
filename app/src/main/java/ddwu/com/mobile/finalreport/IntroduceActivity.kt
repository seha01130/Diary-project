package ddwu.com.mobile.finalreport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.databinding.ActivityIntroduceBinding

class IntroduceActivity : AppCompatActivity() {
    lateinit var introBinding : ActivityIntroduceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introBinding = ActivityIntroduceBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        introBinding.btnGoBack.setOnClickListener{
            finish()
        }
    }
}