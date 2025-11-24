package PM_Ejercicios

class Ejercicio2 {

}

fun main(){
    println("== Calculadora ==")

    //Pedimos el primer numero
    println("Primer numero: ")
    val a = readLine()?.toDoubleOrNull() ?: return

    //pedimos la operacion
    println("Operacion: +, -, /, *")
    val operacion = readLine() ?: return

    //Pedimos el segundo numero
    println("Segundo numero: ")
    val b = readLine()?.toDoubleOrNull() ?: return

    //Calcular el resultado de la operacion
    val resultado = when (operacion) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> if(b != 0.0) a / b else {println("Error division por cero"); return }
        else -> {println("Operacion desconocida"); return }

    }

    //imprimir el resultado
    println("Resultado: $resultado")
}