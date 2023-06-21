package ddwu.com.mobile.finalreport

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport.databinding.ActivityMainBinding
//과제명 : 다이어리 엡
//분반 : 02분반
//학번 : 20200991 성명: 윤수연
//제출일 : 2023년 6월 22일
class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200

    lateinit var binding : ActivityMainBinding
    lateinit var adapter : DiaryAdapter
    lateinit var diaries : ArrayList<DiaryDto>
    lateinit var diaryDao: DiaryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*RecyclerView 의 layoutManager 지정*/
        binding.rvDiaries.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        diaryDao = DiaryDao(this)      // DiaryDao의 객체생성

        diaries = diaryDao.getAllDiaries()             // DB 에서 모든 diary를 가져옴
        adapter = DiaryAdapter(diaries)       // adapter 에 데이터 설정
        binding.rvDiaries.adapter = adapter   // RecylcerView 에 adapter 설정

        /*RecyclerView 항목 클릭 시 실행할 객체*/
        val onClickListener = object : DiaryAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                /*클릭 항목의 dto 를 intent에 저장 후 UpdateActivity 실행*/
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", diaries.get(position) )
                startActivityForResult(intent, REQ_UPDATE)
            }
        }

        adapter.setOnItemClickListener(onClickListener)

        /*RecyclerView 항목 롱클릭 시 실행할 객체*/
        val onLongClickListener = object: DiaryAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {

                AlertDialog.Builder(this@MainActivity).run{
                    setTitle("일기 삭제")
                    setMessage(diaries[position].date + "일의 일기를 삭제하시겠습니까?")
                    setNegativeButton("No", null)
                    setPositiveButton("Yes", object: DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            /*롱클릭 항목의 dto 에서 id 확인 후 함수에 전달*/
                            if ( diaryDao.deleteDiary(diaries.get(position).id.toInt()) > 0) {
                                refreshList(RESULT_OK)
                                Toast.makeText(this@MainActivity, "삭제되었습니다", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
                    show()
                }
            }
        }
        adapter.setOnItemLongClickListener(onLongClickListener)
    }

    /*화면이 보일 때마다 화면을 갱신하고자 할 경우에는 onResume()에 갱신작업 추가*/
//    override fun onResume() {
//        super.onResume()
//        adapter.notifyDataSetChanged()
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addDiary -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivityForResult(intent, REQ_ADD)
            }
            R.id.introduce -> {
                val intent = Intent(this, IntroduceActivity::class.java)
                startActivity(intent)
            }
            R.id.exit -> {
                AlertDialog.Builder(this@MainActivity).run{
                setTitle("exit")
                setMessage("앱을 종료합니다")
                setNegativeButton("No", null)
                setPositiveButton("Yes", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        finish()
                    }
                })
                show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_UPDATE -> {
                refreshList(resultCode)
            }
            REQ_ADD -> {
                refreshList(resultCode)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*다른 액티비티에서 DB 변경 시 DB 내용을 읽어와 Adapter 의 list 에 반영하고 RecyclerView 갱신*/
    private fun refreshList(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            diaries.clear()
            diaries.addAll(diaryDao.getAllDiaries())
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
        }
    }
}