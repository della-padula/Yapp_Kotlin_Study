package org.androidtown.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_calender.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.editText
import org.jetbrains.anko.yesButton
import java.util.*

class EditAct : AppCompatActivity() {
    val realm = Realm.getDefaultInstance()
    val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)
        var ID = intent.getLongExtra("ID",-1L)
        if(ID == -1L)
            insert()
        else
            update(ID)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun insert()
    {
        realm.beginTransaction()
        val newItem = realm.createObject<Todo>(nextID())

        newItem.title = TodoEditText.text.toString()
        newItem.date = calendar.timeInMillis

        realm.commitTransaction()

        DoneButt.setOnClickListener{
            insert()
        }

        alert("내용이 추가되었습니다."){
            yesButton { finish() }
        }.show()

    }

    private fun delete(ID: Long){
        realm.beginTransaction()
        val deleteItem = realm.where<Todo>().equalTo("ID", ID).findFirst()!!

        deleteItem.deleteFromRealm()
        realm.commitTransaction()

        alert("내용이 삭제되었습니다."){
            yesButton{finish()}
        }.show()
    }

    private fun nextID() : Int{
        val maxID = realm.where<Todo>().max("ID")
        if(maxID != null)
            return maxID.toInt() + 1
        return 0
    }

    private fun update(ID: Long) {
        realm.beginTransaction()

        val updateItem = realm.where<Todo>().equalTo("ID", ID).findFirst()!!
        val todo = realm.where<Todo>().equalTo("ID", ID).findFirst()!!
        TodoEditText.setText(todo.title)
        calendarView.date = todo.date

        updateItem.title =  TodoEditText.text.toString()
        updateItem.date = calendar.timeInMillis

        realm.commitTransaction()

        alert("내용이 변경되었습니다."){
            yesButton{finish()}
        }.show()

        DoneButt.setOnClickListener{
            insert()
        }

        DeleteButt.setOnClickListener{
            delete(ID)
        }

    }
}

