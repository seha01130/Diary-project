package ddwu.com.mobile.finalreport

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.databinding.ActivityAddBinding
import java.util.*

class AddActivity : AppCompatActivity() {
    val TAG = "AddActivity"

    lateinit var addBinding : ActivityAddBinding
    lateinit var diaryDao: DiaryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        diaryDao = DiaryDao(this)      // DiaryDao의 객체생성

        val random = Random()
        val ranNum = random.nextInt(6) //0~5
        var list = listOf(R.mipmap.image01, R.mipmap.image02, R.mipmap.image03
            , R.mipmap.image04, R.mipmap.image05, R.mipmap.image06)

        addBinding.newImage.setImageResource(list[ranNum])

        /*추가버튼 클릭*/
        addBinding.btnAddFood.setOnClickListener{
            val image = list[ranNum]
            val date = addBinding.etAddDate.text.toString()
            val title = addBinding.etAddTitle.text.toString()
            val weather = addBinding.etAddWeather.text.toString()
            val content = addBinding.etAddContent.text.toString()
            val newDto = DiaryDto(0,  image, date, title, weather, content)      // 화면 값으로 dto 생성, id 는 임의의 값 0

            if (date == "" || title == "" || weather == "" || content == ""){
                Toast.makeText(this, "입력하지 않은 항목이 있습니다", Toast.LENGTH_SHORT).show()
            }
            else{
                if ( diaryDao.addDiary(newDto) > 0) {
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }

        /*취소버튼 클릭*/
        addBinding.btnAddCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}