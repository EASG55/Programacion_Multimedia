package EjerciciosObligatorios1

import java.io.File

// --- ESTRUCTURA DE DATOS ---
// Usamos un 'data class' para guardar todos los resultados del análisis en un solo objeto.
data class EstadisticasTexto(
    val totalLineas: Int,
    val totalCaracteres: Int,
    val totalPalabras: Int,
    val palabraMasFrecuente: String,
    val longitudPromedio: Double,
    // Campos Extra
    val palabrasConPatron: List<String>, // Lista de palabras que empiezan con X letra
    val fraseMasFrecuente: String,       // Frase repetida de 3 palabras
    val sentimiento: String,             // "Positivo", "Negativo" o "Neutro"
    val puntajeSentimiento: Int          // Valor numérico del sentimiento
)

// --- FUNCIÓN PRINCIPAL ---
fun main() {
    // Texto de prueba (puedes cambiarlo por lo que quieras)
    val textoEjemplo = """
        Kotlin es genial y muy divertido.
        Aprender a programar es genial y muy útil.
        El código sucio es malo, muy malo.
        Kotlin es genial.
    """.trimIndent()

    // Paso 1: Ejecutamos el análisis completo buscando palabras que inicien con "p"
    val resultado = analizarTexto(textoEjemplo, letraInicio = "p")

    // Paso 2: Convertimos el resultado a un texto bonito para leer
    val reporte = generarReporteString(textoEjemplo, resultado)

    // Paso 3: Mostramos el reporte en la consola
    println(reporte)

    // Paso 4 (Extra): Guardamos el reporte en un archivo de texto en tu computadora
    exportarAArchivo(reporte, "reporte_analisis.txt")
}

// --- FUNCIÓN CONTROLADORA ---
// Esta función coordina todo: limpia el texto, llama a las funciones de cálculo y empaqueta el resultado.
fun analizarTexto(texto: String, letraInicio: String = "a"): EstadisticasTexto {

    // Primero limpiamos el texto para poder analizarlo bien (quitamos comas, puntos, mayúsculas)
    val textoNormalizado = normalizarTexto(texto)

    // Convertimos el texto limpio en una lista de palabras
    // filter { it.isNotEmpty() } evita que se cuelen espacios vacíos en la lista
    val listaPalabras = textoNormalizado.split(" ").filter { it.isNotEmpty() }

    // Calculamos el sentimiento (Extra)
    val (puntaje, etiqueta) = analizarSentimiento(listaPalabras)

    // Retornamos el objeto con todos los datos calculados
    return EstadisticasTexto(
        totalLineas = contarLineas(texto),
        totalCaracteres = contarCaracteres(texto),
        totalPalabras = contarPalabras(texto),
        palabraMasFrecuente = encontrarPalabraMasFrecuente(listaPalabras),
        longitudPromedio = longitudPromedioPalabras(listaPalabras),
        palabrasConPatron = buscarPalabrasPorPatron(listaPalabras, letraInicio),
        fraseMasFrecuente = encontrarFraseFrecuente(listaPalabras, 3),
        sentimiento = etiqueta,
        puntajeSentimiento = puntaje
    )
}

// --- FUNCIONES DE UTILIDAD ---

// Convierte todo a minúsculas y elimina signos de puntuación para facilitar el conteo.
fun normalizarTexto(texto: String): String {
    return texto.lowercase()
        .replace(Regex("[^a-záéíóúñ0-9\\s]"), "") // Borra todo lo que NO sea letra o número
        .replace(Regex("\\s+"), " ") // Si hay muchos espacios juntos, los convierte en uno solo
        .trim() // Borra espacios al inicio y final
}

// Cuenta caracteres reales (ignorando los espacios en blanco)
fun contarCaracteres(texto: String) = texto.replace(Regex("\\s"), "").length

// Cuenta cuántas palabras hay usando la lógica de normalización
fun contarPalabras(texto: String): Int {
    if (texto.isBlank()) return 0 // Si está vacío, retorna 0
    return normalizarTexto(texto).split(" ").filter { it.isNotEmpty() }.size
}

// Cuenta las líneas separando el texto por el salto de línea "\n"
fun contarLineas(texto: String) = if (texto.isEmpty()) 0 else texto.split("\n").size

// Encuentra la palabra que más se repite
fun encontrarPalabraMasFrecuente(palabras: List<String>): String {
    return palabras
        .groupBy { it } // Agrupa las palabras iguales: { "hola"=[hola, hola], "mundo"=[mundo] }
        .maxByOrNull { it.value.size } // Busca el grupo más grande
        ?.key ?: "N/A" // Devuelve la palabra (key) o "N/A" si no hay nada
}

// Calcula la longitud promedio (ej: "hola" (4) + "sol" (3) = promedio 3.5)
fun longitudPromedioPalabras(palabras: List<String>): Double {
    if (palabras.isEmpty()) return 0.0
    return palabras
        .map { it.length } // Transforma la lista de palabras en lista de números (longitudes)
        .average() // Calcula el promedio matemático
}

// --- 5. FUNCIONES EXTRA ---

// Busca palabras que empiecen con una letra específica
fun buscarPalabrasPorPatron(palabras: List<String>, inicio: String): List<String> {
    return palabras
        .filter { it.startsWith(inicio.lowercase()) } // Filtra las que coinciden
        .distinct() // Elimina duplicados (para no mostrar la misma palabra dos veces)
        .sorted() // Las ordena alfabéticamente
}

// Detecta frases repetidas de 'n' palabras (ej: "Kotlin es genial")
fun encontrarFraseFrecuente(palabras: List<String>, longitudFrase: Int): String {
    if (palabras.size < longitudFrase) return "Texto insuficiente"

    // 'windowed' desliza una ventana sobre la lista para crear grupos de 3 palabras consecutivas
    val frases = palabras.windowed(longitudFrase) { it.joinToString(" ") }

    // Buscamos la frase que más se repite
    val fraseFrecuente = frases
        .groupBy { it }
        .maxByOrNull { it.value.size }

    // Solo la mostramos si aparece más de 1 vez
    return if (fraseFrecuente != null && fraseFrecuente.value.size > 1) {
        "'${fraseFrecuente.key}' (x${fraseFrecuente.value.size})"
    } else {
        "Sin frases repetidas"
    }
}

// Decide si el texto es positivo o negativo basándose en palabras clave
fun analizarSentimiento(palabras: List<String>): Pair<Int, String> {
    // Listas de palabras clave
    val positivas = setOf("genial", "bueno", "divertido", "útil", "seguro", "feliz", "excelente", "sí")
    val negativas = setOf("malo", "feo", "difícil", "error", "no", "lento", "triste", "sucio")

    var puntaje = 0
    // Recorremos cada palabra del texto y sumamos o restamos puntos
    palabras.forEach { palabra ->
        if (palabra in positivas) puntaje++
        if (palabra in negativas) puntaje--
    }

    // Determinamos la etiqueta según el puntaje final
    val etiqueta = when {
        puntaje > 0 -> "Positivo"
        puntaje < 0 -> "Negativo"
        else -> "Neutro"
    }
    // Retornamos dos valores a la vez: el número y el texto
    return Pair(puntaje, etiqueta)
}

// Guarda el texto en un archivo físico en el disco
fun exportarAArchivo(contenido: String, nombreArchivo: String) {
    try {
        val archivo = File(nombreArchivo)
        archivo.writeText(contenido) // Escribe (o sobreescribe) el archivo
        println("\n[ÉXITO] Reporte guardado en: ${archivo.absolutePath}")
    } catch (e: Exception) {
        // Si falla (ej: falta de permisos), avisa en lugar de romper el programa
        println("\n[ERROR] No se pudo guardar el archivo: ${e.message}")
    }
}

// Función auxiliar solo para dar formato visual al texto de salida
fun generarReporteString(textoOriginal: String, resultado: EstadisticasTexto): String {
    return """
        =========================================
        REPORTE DE ANÁLISIS DE TEXTO
        =========================================
        
        --- TEXTO ORIGINAL ---
        $textoOriginal
        
        --- ESTADÍSTICAS BÁSICAS ---
        • Total Líneas:       ${resultado.totalLineas}
        • Total Palabras:     ${resultado.totalPalabras}
        • Total Caracteres:   ${resultado.totalCaracteres}
        • Promedio Longitud:  ${String.format("%.2f", resultado.longitudPromedio)}
        
        --- ANÁLISIS DE CONTENIDO ---
        • Palabra Top:        '${resultado.palabraMasFrecuente}'
        • Frase Top (3 words): ${resultado.fraseMasFrecuente}
        • Palabras con 'p':   ${resultado.palabrasConPatron}
        
        --- ANÁLISIS DE SENTIMIENTO ---
        • Puntaje:            ${resultado.puntajeSentimiento}
        • Conclusión:         ${resultado.sentimiento}
        =========================================
    """.trimIndent()
}