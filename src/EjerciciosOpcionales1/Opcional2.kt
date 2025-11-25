package EjerciciosOpcionales1

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Data class con la estructura de la nota, incluyendo fecha automática
data class Nota(
    val titulo: String,
    val contenido: String,
    val fecha: LocalDateTime = LocalDateTime.now()
)

val listaNotas = mutableListOf<Nota>()

fun main() {
    var salir = false
    while (!salir) {
        println("\n--- NOTAS RÁPIDAS ---")
        println("1. Crear nota")
        println("2. Ver todas")
        println("3. Buscar nota")
        println("4. Exportar a archivo")
        println("5. Salir")
        print("Opción: ")

        when (readlnOrNull()?.toIntOrNull()) {
            1 -> crearNota()
            2 -> mostrarNotas(listaNotas)
            3 -> buscarNota()
            4 -> exportarNotas()
            5 -> salir = true
            else -> println("Opción no válida.")
        }
    }
}

fun crearNota() {
    print("Título: ")
    val titulo = readln()
    print("Contenido: ")
    val contenido = readln()
    
    if (titulo.isNotBlank() && contenido.isNotBlank()) {
        listaNotas.add(Nota(titulo, contenido))
        println("Nota guardada.")
    } else {
        println("El título y contenido no pueden estar vacíos.")
    }
}

fun mostrarNotas(notas: List<Nota>) {
    if (notas.isEmpty()) {
        println("No hay notas.")
        return
    }
    
    // Formateador para mostrar la fecha de forma legible
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    
    notas.forEach { nota ->
        println("--------------------------------")
        println("Título: ${nota.titulo}")
        println("Fecha:  ${nota.fecha.format(formatter)}")
        println("Cuerpo: ${nota.contenido}")
    }
}

fun buscarNota() {
    print("Término de búsqueda: ")
    val termino = readln().lowercase()
    
    // Filtra si el término está en el título O en el contenido
    val encontradas = listaNotas.filter { 
        it.titulo.lowercase().contains(termino) || 
        it.contenido.lowercase().contains(termino) 
    }
    
    mostrarNotas(encontradas)
}

fun exportarNotas() {
    try {
        // Uso de StringBuilder para construir el texto eficientemente
        val sb = StringBuilder()
        sb.append("MIS NOTAS EXPORTADAS\n")
        sb.append("====================\n\n")
        
        listaNotas.forEach { 
            sb.append("Título: ${it.titulo}\n")
            sb.append("Fecha: ${it.fecha}\n")
            sb.append("Contenido: ${it.contenido}\n")
            sb.append("---\n")
        }
        
        // Escribe el contenido en un archivo físico llamado 'notas_exportadas.txt'
        val archivo = File("notas_exportadas.txt")
        archivo.writeText(sb.toString())
        
        println("Exportado con éxito a: ${archivo.absolutePath}")
    } catch (e: Exception) {
        println("Error al exportar: ${e.message}")
    }
}