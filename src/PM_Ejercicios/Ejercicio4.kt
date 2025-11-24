package PM_Ejercicios

fun main(){
    //Creacion de una lista de numeros
    val numeros = listOf(1,2,3,4,5,6,7,8,9,10)

    //Numeros pares
    val pares = numeros.filter {it % 2 == 0}
    println("numeros pares: $pares")

    //numeros pares multiplicados por 2
    val paresDobles = numeros.filter {it % 2 == 0}.map {it * 2}
    println("Pares duplicados: $paresDobles")

    //Suma de numeros mayores a 5
    val sumaGrandes = numeros.filter{it > 5}.sum()
    println("Suma de numeros mayores a 5: $sumaGrandes")

    //Verificar si hay numeros primos
    fun esPrimo(n: Int): Boolean {
        if(n <= 1) return true
        for (i in 2 until n) {
            //println(i)
            if (n % i == 0) return false
        }
        //println("Fuera del for $n")
        return true
    }

    //Mostrar los numeros primos de la lista
    val primos = numeros.filter{esPrimo(it)}
    println("Primos: $primos")


}