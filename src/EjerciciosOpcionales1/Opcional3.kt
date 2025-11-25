package EjerciciosOpcionales1

data class RegistroIMC(
    val peso: Double,
    val altura: Double,
    val imc: Double,
    val categoria: String
)

val historialIMC = mutableListOf<RegistroIMC>()

fun main() {
    while (true) {
        println("\n--- CALCULADORA IMC ---")
        println("1. Nuevo cálculo")
        println("2. Ver historial y tendencias")
        println("3. Salir")
        print("Opción: ")

        when (readlnOrNull()?.toIntOrNull()) {
            1 -> realizarCalculo()
            2 -> mostrarHistorial()
            3 -> return
            else -> println("Opción incorrecta.")
        }
    }
}

fun realizarCalculo() {
    println("Introduce peso (kg): ")
    val peso = readlnOrNull()?.toDoubleOrNull()
    
    println("Introduce altura (metros, ej: 1.75): ")
    val altura = readlnOrNull()?.toDoubleOrNull()
    
    // Validación de entrada: datos deben existir y ser positivos
    if (peso != null && altura != null && peso > 0 && altura > 0) {
        // Fórmula IMC: peso / altura al cuadrado
        val imc = peso / (altura * altura)
        val categoria = clasificarIMC(imc)
        
        // Guardamos el registro
        val registro = RegistroIMC(peso, altura, imc, categoria)
        historialIMC.add(registro)
        
        println("\n>> Tu IMC es: %.2f".format(imc))
        println(">> Categoría: $categoria")
        
    } else {
        println("Error: Introduce valores numéricos positivos válidos.")
    }
}

// Clasificación según rangos de la OMS usando 'when'
fun clasificarIMC(imc: Double): String {
    return when {
        imc < 18.5 -> "Bajo peso"
        imc in 18.5..24.9 -> "Peso normal"
        imc in 25.0..29.9 -> "Sobrepeso"
        else -> "Obesidad"
    }
}

fun mostrarHistorial() {
    if (historialIMC.isEmpty()) {
        println("No hay registros previos.")
        return
    }

    println("\n--- HISTORIAL ---")
    // Iteramos con índice para poder comparar con el anterior
    historialIMC.forEachIndexed { index, registro ->
        var tendencia = ""
        
        // Si no es el primero, comparamos con el anterior (index - 1)
        if (index > 0) {
            val anterior = historialIMC[index - 1]
            val diferencia = registro.imc - anterior.imc
            
            // Definimos el texto de tendencia según si subió o bajó
            tendencia = when {
                diferencia > 0 -> " (Subió %.2f)".format(diferencia)
                diferencia < 0 -> " (Bajó %.2f)".format(kotlin.math.abs(diferencia))
                else -> " (Sin cambios)"
            }
        }
        
        println("Reg ${index + 1}: IMC %.2f - ${registro.categoria}$tendencia".format(registro.imc))
    }
}