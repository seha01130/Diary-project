package ddwu.com.mobile.finalreport

import java.io.Serializable

/*Intent에 저장하여야 하므로 Serializable 인터페이스 구현*/
data class DiaryDto(val id: Long, var image: Int, var date: String, var title: String, var weather: String, var content: String) : Serializable {

    //data클래스는 게터세터 알아서 처리해줌. 근데 그냥 함수 처리해봄
    fun toStringDate() = "$date"
    fun toStringTitle() = "$title"
    fun toStringWeather() = "$weather"
}
