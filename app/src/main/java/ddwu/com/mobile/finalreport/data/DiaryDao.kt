package ddwu.com.mobile.finalreport

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns

class DiaryDao(val context: Context) {
    /*전체 레코드를 가져와 dto 로 저장한 후 dto를 저장한 list 반환*/
    fun getAllDiaries() : ArrayList<DiaryDto> {
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase

//        val cursor = db.rawQuery("SELECT * FROM ${FoodDBHelper.TABLE_NAME}", null)
        val cursor = db.query(DiaryDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val diaries = arrayListOf<DiaryDto>()

        with (cursor) {
            while (moveToNext()) {
                val id = getLong( getColumnIndex(BaseColumns._ID) )
                val image = getInt( getColumnIndex(DiaryDBHelper.COL_IMAGE) )
                val date = getString ( getColumnIndex(DiaryDBHelper.COL_DATE) )
                val title = getString ( getColumnIndex(DiaryDBHelper.COL_TITLE) )
                val weather = getString ( getColumnIndex(DiaryDBHelper.COL_WEATHER) )
                val content = getString ( getColumnIndex(DiaryDBHelper.COL_CONTENT) )
                val dto = DiaryDto(id, image, date, title, weather, content)
                diaries.add(dto)
            }
        }
        cursor.close()      // cursor 사용을 종료했으므로 close()
        helper.close()      // DB 사용이 끝났으므로 close()

        return diaries
    }

    /*추가할 정보를 담고 있는 dto 를 전달받아 DB에 추가, id 는 autoincrement 이므로 제외
    * DB추가 시 추가한 항목의 ID 값 반환, 추가 실패 시 -1 반환 */
    fun addDiary(newDto : DiaryDto) : Long  {
        val helper = DiaryDBHelper(context)
        val db = helper.writableDatabase

        val newValues = ContentValues()
        newValues.put(DiaryDBHelper.COL_IMAGE, newDto.image)
        newValues.put(DiaryDBHelper.COL_DATE, newDto.date)
        newValues.put(DiaryDBHelper.COL_TITLE, newDto.title)
        newValues.put(DiaryDBHelper.COL_WEATHER, newDto.weather)
        newValues.put(DiaryDBHelper.COL_CONTENT, newDto.content)

        /*insert 한 항목의 id 를 반환*/
        val result = db.insert(DiaryDBHelper.TABLE_NAME, null, newValues)

        helper.close()      // DB 작업 후 close() 수행

        return result
    }

    /*update 정보를 담고 있는 dto 를 전달 받아 dto 의 id 를 기준으로 수정*/
    fun updateFood(dto: DiaryDto): Int {
        val helper = DiaryDBHelper(context)
        val db = helper.writableDatabase
        val updateValue = ContentValues()
        updateValue.put(DiaryDBHelper.COL_TITLE, dto.title)
        val whereCaluse = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        /*upate 가 적용된 레코드의 개수 반환*/
        val result =  db.update(DiaryDBHelper.TABLE_NAME,
            updateValue, whereCaluse, whereArgs)

        helper.close()      // DB 작업 후에는 close()

        return result
    }

    /*ID 에 해당하는 레코드를 삭제 후 삭제된 레코드 개수 반환*/
    fun deleteDiary(id: Int) : Int {
        val helper = DiaryDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(DiaryDBHelper.TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }
}