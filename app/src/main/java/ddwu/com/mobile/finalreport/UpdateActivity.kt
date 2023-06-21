package ddwu.com.mobile.finalreport

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding
    lateinit var diaryDao: DiaryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        diaryDao = DiaryDao(this)      // DiaryDao의 객체생성

        /*RecyclerView 에서 선택하여 전달한 dto 를 확인*/
        val dto = intent.getSerializableExtra("dto") as DiaryDto

        /*전달받은 값으로 화면에 표시*/
        updateBinding.updateImage.setImageResource(dto.image)
        updateBinding.showDate.setText(dto.date)
        updateBinding.etUpdateTitle.setText(dto.title)
        updateBinding.showWeather.setText(dto.weather)
        updateBinding.showContent.setText(dto.content)

        updateBinding.btnUpdateDiary.setOnClickListener{
            /*dto 는 아래와 같이 기존의 dto 를 재사용할 수도 있음*/
            dto.date = updateBinding.showDate.text.toString()
            dto.title = updateBinding.etUpdateTitle.text.toString()
            dto.weather = updateBinding.showWeather.text.toString()
            dto.content = updateBinding.showContent.text.toString()

            if (updateBinding.showDate.text.toString() == "" || updateBinding.etUpdateTitle.text.toString() == ""
                || updateBinding.showWeather.text.toString() == "" || updateBinding.showContent.text.toString() == ""){
                Toast.makeText(this, "입력하지 않은 항목이 있습니다", Toast.LENGTH_SHORT).show()
            }
            else{
                if (diaryDao.updateFood(dto) > 0) {
                    setResult(RESULT_OK)      // update 를 수행했으므로 RESULT_OK 를 반환
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }

        updateBinding.btnUpdateCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}