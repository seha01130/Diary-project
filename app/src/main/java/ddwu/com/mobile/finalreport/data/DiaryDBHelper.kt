package ddwu.com.mobile.finalreport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class DiaryDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "FoodDBHelper"

    companion object {
        const val DB_NAME = "diary_db"
        const val TABLE_NAME = "diary_table"
        const val COL_IMAGE = "image"
        const val COL_DATE = "date"
        const val COL_TITLE = "title"
        const val COL_WEATHER = "weather"
        const val COL_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COL_IMAGE INTEGER, $COL_DATE TEXT, $COL_TITLE TEXT, $COL_WEATHER TEXT, $COL_CONTENT TEXT)"
        Log.d(TAG, CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        /*샘플 데이터 - 필요할 경우 실행*/
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.image01}, '2023.06.20', '제주도 여행 1일차', '맑음'," +
                "'오늘은 가족과 제주도 여행을 갔다. 오랜만에 바다를 보니 좋았다.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.image02}, '2023.06.21', '제주도 여행 2일차', '맑음', " +
                "'오늘은 제주도에서 패러글라이딩을 했다.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.image03}, '2023.06.22', '카페 도장깨기', '흐림', " +
                "'친구가 가고싶다던 카페에 같이 갔다.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.image04}, '2023.06.23', '전시회', '비', " +
                "'라울 뒤피 : 색채의 선율')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.image05}, '2023.06.24', '성수동', '구름', " +
                "'친구랑 성수동에 놀러갔다. 비가 오지 않아 다행이야.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.image06}, '2023.06.25', '언어의 정원', '비', " +
                "'언어의 정원 이라는 애니메이션을 봤다. 비 오는 새벽, 꼭 봐야하는 영화')")
        
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}


