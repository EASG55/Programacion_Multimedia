package EjerciciosObligatorios1

fun main() {
    // Instanciamos la clase y llamamos al método principal
    val ejercicio = Ejercicio2()
    ejercicio.menuInteractivo()
}

class Ejercicio2 {

    // Excepción personalizada
    class TemperaturaInvalidaException(mensaje: String) : Exception(mensaje)

    fun menuInteractivo() {
        while (true) {
            println("\n--- Conversor de Temperaturas ---")
            println("1. Celsius a Fahrenheit")
            println("2. Kelvin a Celsius")
            println("3. Salir")
            print("Seleccione una opción: ")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> {
                    print("Ingrese grados Celsius: ")
                    val input = readlnOrNull()?.toDoubleOrNull()
                    if (input != null) {
                        // Llamamos a las funciones miembro de la clase
                        val resultado = convertir(input) { celsiusAFahrenheit(it) }
                        mostrarResultado(resultado)
                    } else {
                        println("Entrada no válida.")
                    }
                }
                2 -> {
                    print("Ingrese grados Kelvin: ")
                    val input = readlnOrNull()?.toDoubleOrNull()
                    if (input != null) {
                        val resultado = convertir(input) { kelvinACelsius(it) }
                        mostrarResultado(resultado)
                    } else {
                        println("Entrada no válida.")
                    }
                }
                3 -> return
                else -> println("Opción incorrecta.")
            }
        }
    }

    // Transforma el valor si es válido usando una función de orden superior
    private fun convertir(valor: Double, operacion: (Double) -> Double): Result<Double> {
        return try {
            validarTemperatura(valor) // Primero validamos lógica física
            val res = operacion(valor)
            Result.success(res)
        } catch (e: TemperaturaInvalidaException) {
            Result.failure(e)
        }
    }

    // Verifica si la temperatura es menor al cero absoluto (-273.15 C)
    private fun validarTemperatura(temp: Double) {
        if (temp < -273.15) {
            throw TemperaturaInvalidaException("La temperatura no es físicamente posible (menor al cero absoluto).")
        }
    }

    // Fórmulas de conversión
    private fun celsiusAFahrenheit(c: Double): Double = (c * 9 / 5) + 32
    private fun kelvinACelsius(k: Double): Double = k - 273.15

    // Función auxiliar para imprimir el Result de forma segura
    private fun mostrarResultado(resultado: Result<Double>) {
        resultado.onSuccess { valor ->
            println("Resultado: %.2f".format(valor))
        }.onFailure { error ->
            println("Error: ${error.message}")
        }
    }
}