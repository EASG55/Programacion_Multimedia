package PM_Ejercicios

import java.time.LocalDateTime

class Ejercicio1 {

}

fun main(){
    println("Cual es tu nombre?")
    //Esperamos entrada por parte del usuario
    val nombre = readLine() ?: "Visitante"

    //Cogemos de la libreria de java.time la hora a partir de la funcion
    //now del objeto LocalDateTime
    val horaActual = LocalDateTime.now().hour

    //Creacion de una funcion que se autoejecuta
    val saludo = when {
        horaActual >= 18 -> "Buenas tardes"
        horaActual >= 12 -> "Buenos dias"
        else -> "Buenas noches"
    }

    //Devolver resultados al usuario
    println("$saludo, $nombre")

}