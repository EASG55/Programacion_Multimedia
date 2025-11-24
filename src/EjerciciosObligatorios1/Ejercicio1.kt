package EjerciciosObligatorios1

data class Libro(
    val titulo: String,
    val autor: String,
    val anioPublicacion: Int,
    val disponible: Boolean
)

fun main() {
    // 2. Crear lista de libros
    val biblioteca = listOf(
        Libro("Cien años de soledad", "Gabriel Garcia Marquez", 1967, true),
        Libro("1984", "George Orwell", 1949, false),
        Libro("El Señor de los Anillos", "J.R.R. Tolkien", 1954, true),
        Libro("Don Quijote", "Miguel de Cervantes", 1605, true),
        Libro("Crónica de una muerte anunciada", "Gabriel Garcia Marquez", 1981, false)
    )

    // Pruebas de las funciones
    println("--- Libros de Gabriel Garcia Marquez ---")
    buscarPorAutor(biblioteca, "Gabriel Garcia Marquez").forEach { println(it.titulo) }

    println("\n--- Libros publicados entre 1950 y 1990 ---")
    buscarPorAnio(biblioteca, 1950, 1990).forEach { println("${it.titulo} (${it.anioPublicacion})") }

    println("\n--- Libros disponibles actualmente ---")
    librosDisponibles(biblioteca).forEach { println(it.titulo) }

    println("\n--- Estadísticas ---")
    calcularEstadisticas(biblioteca)
}

// 3. Implementar buscarPorAutor
fun buscarPorAutor(libros: List<Libro>, autor: String): List<Libro> {
    // Filtramos la lista comparando el autor ignorando mayúsculas y minúsculas
    return libros.filter { it.autor.equals(autor, ignoreCase = true) }
}

// 4. Implementar buscarPorAnio
fun buscarPorAnio(libros: List<Libro>, inicio: Int, fin: Int): List<Libro> {
    // Usamos un rango para verificar si el año está dentro de los límites
    return libros.filter { it.anioPublicacion in inicio..fin }
}

// 5. Implementar librosDisponibles
fun librosDisponibles(libros: List<Libro>): List<Libro> {
    // Retorna solo los libros donde el atributo disponible es true
    return libros.filter { it.disponible }
}

// 6. Implementar calcularEstadisticas
fun calcularEstadisticas(libros: List<Libro>) {
    val totalLibros = libros.size
    // Agrupamos por autor para contar cuantos libros tiene cada uno
    val librosPorAutor = libros.groupBy { it.autor }.mapValues { it.value.size }
    // Encontramos el libro más antiguo usando minByOrNull
    val libroMasAntiguo = libros.minByOrNull { it.anioPublicacion }

    println("Total de libros: $totalLibros")
    println("Libros por autor: $librosPorAutor")
    println("Libro más antiguo: ${libroMasAntiguo?.titulo} del año ${libroMasAntiguo?.anioPublicacion}")
}