package EjerciciosOpcionales1

fun main() {
    println("--- JUEGO: ADIVINA EL NÚMERO ---")
    
    // Variable para mantener el mejor puntaje durante la sesión
    // Se inicia con el valor máximo posible de un entero
    var recordIntentos = Int.MAX_VALUE
    var seguirJugando = true

    while (seguirJugando) {
        // Genera un número aleatorio entre 1 y 100
        val numeroSecreto = (1..100).random()
        var intentosActuales = 0
        var adivinado = false
        
        println("\nHe pensado un número del 1 al 100. ¡Intenta adivinarlo!")

        while (!adivinado) {
            print("Introduce tu número: ")
            val entrada = readlnOrNull()?.toIntOrNull()

            if (entrada != null) {
                intentosActuales++
                
                // Lógica de comparación para dar pistas
                when {
                    entrada < numeroSecreto -> println(">> El número es MAYOR.")
                    entrada > numeroSecreto -> println(">> El número es MENOR.")
                    else -> {
                        adivinado = true
                        println("¡CORRECTO! Adivinaste en $intentosActuales intentos.")
                        
                        // Actualización del récord si se ha mejorado
                        if (intentosActuales < recordIntentos) {
                            recordIntentos = intentosActuales
                            println("¡NUEVO RÉCORD DE SESIÓN!")
                        } else {
                            println("Tu mejor récord actual es: $recordIntentos intentos.")
                        }
                    }
                }
            } else {
                println("Por favor, introduce un número válido.")
            }
        }

        // Preguntar al usuario si desea reiniciar el bucle principal
        print("\n¿Jugar otra vez? (s/n): ")
        if (readlnOrNull()?.lowercase() != "s") {
            seguirJugando = false
            println("¡Gracias por jugar!")
        }
    }
}