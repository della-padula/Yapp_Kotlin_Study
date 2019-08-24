

fun main(args : Array<String>){
    val postOrder = toPostOrderString("1+3*6-4=")
    val result = calcul(postOrder)
    println(result)
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
                    postString.add(operend.last())
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