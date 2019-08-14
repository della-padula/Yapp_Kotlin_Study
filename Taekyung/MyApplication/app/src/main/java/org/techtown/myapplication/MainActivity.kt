package org.techtown.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.R.id.button3
import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var resultText = findViewById<TextView>(R.id.result_text)
        val numberButton0 = findViewById<Button>(R.id.number_0)
        val numberButton1 = findViewById<Button>(R.id.number_1)
        val numberButton2 = findViewById<Button>(R.id.number_2)
        val numberButton3 = findViewById<Button>(R.id.number_3)
        val numberButton4 = findViewById<Button>(R.id.number_4)
        val numberButton5 = findViewById<Button>(R.id.number_5)
        val numberButton6 = findViewById<Button>(R.id.number_6)
        val numberButton7 = findViewById<Button>(R.id.number_7)
        val numberButton8 = findViewById<Button>(R.id.number_8)
        val numberButton9 = findViewById<Button>(R.id.number_9)
        val plusButton = findViewById<Button>(R.id.plus_button)
        val minusButton = findViewById<Button>(R.id.minus_button)
        val caditionButton = findViewById<Button>(R.id.cadition_button)
        val devideButton = findViewById<Button>(R.id.devide_button)
        val resultButton = findViewById<Button>(R.id.button_result)
        val clearButton = findViewById<Button>(R.id.clear_button)

        val onClickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.number_0 -> resultText.setText("${resultText.text}${numberButton0.text}")
                R.id.number_1 -> resultText.setText("${resultText.text}${numberButton1.text}")
                R.id.number_2 -> resultText.setText("${resultText.text}${numberButton2.text}")
                R.id.number_3 -> resultText.setText("${resultText.text}${numberButton3.text}")
                R.id.number_4 -> resultText.setText("${resultText.text}${numberButton4.text}")
                R.id.number_5 -> resultText.setText("${resultText.text}${numberButton5.text}")
                R.id.number_6 -> resultText.setText("${resultText.text}${numberButton6.text}")
                R.id.number_7 -> resultText.setText("${resultText.text}${numberButton7.text}")
                R.id.number_8 -> resultText.setText("${resultText.text}${numberButton8.text}")
                R.id.number_9 -> resultText.setText("${resultText.text}${numberButton9.text}")
                R.id.plus_button -> resultText.setText("${resultText.text}${plusButton.text}")
                R.id.minus_button -> resultText.setText("${resultText.text}${minusButton.text}")
                R.id.cadition_button -> resultText.setText("${resultText.text}${caditionButton.text}")
                R.id.devide_button -> resultText.setText("${resultText.text}${devideButton.text}")
                R.id.button_result -> {
                    resultText.setText("${resultText.text}${resultButton.text}")
                    val resultValue = onButtonListner(resultText.text.toString())
                    resultText.setText("정답은 ${resultValue}")
                }
                R.id.clear_button ->{ resultText.setText("")}
            }
        }
        numberButton0.setOnClickListener(onClickListener)
        numberButton1.setOnClickListener(onClickListener)
        numberButton2.setOnClickListener(onClickListener)
        numberButton3.setOnClickListener(onClickListener)
        numberButton4.setOnClickListener(onClickListener)
        numberButton5.setOnClickListener(onClickListener)
        numberButton6.setOnClickListener(onClickListener)
        numberButton7.setOnClickListener(onClickListener)
        numberButton8.setOnClickListener(onClickListener)
        numberButton9.setOnClickListener(onClickListener)
        plusButton.setOnClickListener(onClickListener)
        minusButton.setOnClickListener(onClickListener)
        caditionButton.setOnClickListener(onClickListener)
        devideButton.setOnClickListener(onClickListener)
        resultButton.setOnClickListener(onClickListener)
        clearButton.setOnClickListener(onClickListener)


    }
}
fun onButtonListner(args : String) : String{
    val postOrder = toPostOrderString(args)
    val result = calcul(postOrder)
    return result.toString()
}
fun calcul(postOrder : MutableList<String>): Double{
    var tmpStack : MutableList<String> = mutableListOf()
    postOrder.map{ el->
        if(el.matches("^[0-9]*$".toRegex())) {
            tmpStack.add(el)
        }else if(el == "="){
            return tmpStack.last().toDouble()
        }
        else{
            var result : Double = 0.0
            val x = tmpStack.last().toDouble()
            tmpStack.removeAt(tmpStack.lastIndex)
            val y = tmpStack.last().toDouble()
            tmpStack.removeAt(tmpStack.lastIndex)
            result = realCalcul(x,y,el)
            tmpStack.add(result.toString())
        }
    }

    return tmpStack.last().toDouble()
}
fun realCalcul(x : Double, y: Double, operend : String): Double{
    when(operend){
        "+"-> return x+y
        "-"-> return y-x
        "/"-> return y/x
        "*"-> return x*y
        else ->{
            return 0.0
        }
    }
    return 0.0
}
fun toPostOrderString(string : String) : MutableList<String> {
    val target = string.split("").filter { el-> el.length >0 }
    var postString:MutableList<String> = mutableListOf<String>()
    var operend:MutableList<String> = mutableListOf()
    target.map{el->
        if(el.matches("^[0-9]*$".toRegex())){
            println(el)
            postString.add(el)
        }else{
            when(el){
                "+","-","*","/"->{
                    while(operend.isNotEmpty() && prec(el) <= prec(operend.last())){
                        postString.add(operend.last())
                        operend.removeAt(operend.lastIndex)
                    }
                    operend.add(el)
                }
                "("->{
                    operend.add(el)
                }
                ")"->{
                    while(operend.last() != ")"){
                        postString.add(operend.last())
                        operend.removeAt(operend.lastIndex)
                    }
                }
                else->{
                    while(operend.isNotEmpty()){
                        postString.add(operend.last())
                        operend.removeAt(operend.lastIndex)
                    }
                    postString.add(el)

                }
            }
        }
    }

    println(postString)
    return postString
}

fun prec(oper : String) :Int{
    when(oper){
        "(",")"-> return 0
        "+","-"-> return 1
        "*","/"-> return 2
        else -> return -1
    }
}