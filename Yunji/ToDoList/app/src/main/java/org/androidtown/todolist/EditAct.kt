package org.androidtown.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
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
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun insertTodo()
    {
        realm.beginTransaction()

        val newItem = realm.createObject<Todo>(nextID())

        newItem.title = TodoEditText.text.toString()
        newItem.date = calendar.timeInMillis

        realm.commitTransaction()

        alert("내용이 추가되었습니다."){
            yesButton { finish() }
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
        updateItem.title =  TodoEditText.text.toString()
        updateItem.date = calendar.timeInMillis

        realm.commitTransaction()

        alert("내용이 변경되었습니다."){
            yesButton{finish()}
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

    var ID = intent.getLongExtra("ID",-1L)
    if(ID == -1L)
    {insertTodo()}
    else{
        update(ID)
    }






    }

