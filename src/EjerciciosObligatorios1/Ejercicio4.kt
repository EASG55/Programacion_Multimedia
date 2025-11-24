package EjerciciosObligatorios1

// Excepción personalizada para validaciones fallidas
class ContactoInvalidoException(mensaje: String) : Exception(mensaje)

// Data Class con propiedad mutable 'esFavorito'
data class Contacto(
    val nombre: String,
    val email: String,
    val telefono: String,
    var esFavorito: Boolean = false
)

// Almacenamiento en memoria (lista mutable)
val agenda = mutableListOf<Contacto>()

fun main() {
    // 14. Carga inicial de datos de prueba
    val c1 = crearContacto("Juan Perez", "juan@mail.com", "123456789").getOrNull()
    val c2 = crearContacto("Ana Lopez", "ana@mail.com", "987654321").getOrNull()

    if (c1 != null) agenda.add(c1)
    if (c2 != null) agenda.add(c2)

    // Iniciar el bucle del menú
    menuInteractivo()
}

// Validar formato de Email con Regex
fun validarEmail(email: String): Boolean {
    // Patrón simple: texto + @ + texto + . + texto
    return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
}

// Validar Teléfono (numérico y longitud mínima)
fun validarTelefono(telefono: String): Boolean {
    return telefono.all { it.isDigit() } && telefono.length >= 9
}

// Validar Nombre (no vacío)
fun validarNombre(nombre: String): Boolean {
    return nombre.isNotBlank()
}

// Crear contacto usando Result para manejo de errores
fun crearContacto(nombre: String, email: String, telefono: String): Result<Contacto> {
    return try {
        if (!validarNombre(nombre))
            throw ContactoInvalidoException("El nombre no puede estar vacío.")
        if (!validarEmail(email))
            throw ContactoInvalidoException("El formato del email no es válido.")
        if (!validarTelefono(telefono))
            throw ContactoInvalidoException("El teléfono debe contener solo números (mínimo 9).")

        // Validación extra: verificar duplicados por email
        if (agenda.any { it.email == email }) {
            throw ContactoInvalidoException("Ya existe un contacto con ese email.")
        }

        Result.success(Contacto(nombre, email, telefono))
    } catch (e: ContactoInvalidoException) {
        Result.failure(e)
    }
}

// Buscar por nombre (coincidencia parcial, insensible a mayúsculas)
fun buscarPorNombre(nombre: String): List<Contacto> {
    return agenda.filter { it.nombre.contains(nombre, ignoreCase = true) }
}

// Obtener lista de favoritos
fun obtenerFavoritos(): List<Contacto> {
    return agenda.filter { it.esFavorito }
}

// Obtener lista ordenada alfabéticamente por nombre
fun obtenerOrdenados(): List<Contacto> {
    return agenda.sortedBy { it.nombre }
}

// Alternar estado favorito (Toggle)
fun toggleFavorito(nombreExacto: String): Boolean {
    // Buscamos el objeto en la lista mutable
    val contacto = agenda.find { it.nombre.equals(nombreExacto, ignoreCase = true) }
    return if (contacto != null) {
        contacto.esFavorito = !contacto.esFavorito // Invierte true/false
        true
    } else {
        false
    }
}

// Eliminar contacto
fun eliminarContacto(nombreExacto: String): Boolean {
    // removeIf elimina elementos que cumplen la condición y retorna true si borró algo
    return agenda.removeIf { it.nombre.equals(nombreExacto, ignoreCase = true) }
}

// Mostrar detalle de contacto formateado
fun mostrarContacto(contacto: Contacto) {
    val marcaFav = if (contacto.esFavorito) "-" else "*"
    println("[$marcaFav] ${contacto.nombre}")
    println("    Email: ${contacto.email}")
    println("    Tlf:   ${contacto.telefono}")
}

// Menú interactivo principal con manejo de errores de entrada
fun menuInteractivo() {
    var salir = false
    while (!salir) {
        println("\n--- GESTOR DE CONTACTOS ---")
        println("1. Nuevo Contacto")
        println("2. Buscar por nombre")
        println("3. Ver todos (Ordenados)")
        println("4. Ver Favoritos")
        println("5. Cambiar Favorito (Toggle)")
        println("6. Eliminar Contacto")
        println("7. Salir")
        print("Seleccione una opción: ")

        try {
            // Conversión segura de entrada a entero
            when (readln().toInt()) {
                1 -> {
                    println("\n--- Nuevo Contacto ---")
                    print("Nombre: "); val n = readln()
                    print("Email: "); val e = readln()
                    print("Teléfono: "); val t = readln()

                    crearContacto(n, e, t)
                        .onSuccess {
                            agenda.add(it)
                            println("¡Contacto añadido correctamente!")
                        }
                        .onFailure { println("ERROR: ${it.message}") }
                }
                2 -> {
                    print("Buscar (término): ")
                    val termino = readln()
                    val resultados = buscarPorNombre(termino)
                    if (resultados.isEmpty()) println("No se encontraron coincidencias.")
                    else resultados.forEach { mostrarContacto(it) }
                }
                3 -> {
                    println("\n--- Agenda Completa ---")
                    val ordenados = obtenerOrdenados()
                    if (ordenados.isEmpty()) println("Agenda vacía.")
                    else ordenados.forEach { mostrarContacto(it) }
                }
                4 -> {
                    println("\n--- Favoritos ---")
                    val favs = obtenerFavoritos()
                    if (favs.isEmpty()) println("No tienes favoritos guardados.")
                    else favs.forEach { mostrarContacto(it) }
                }
                5 -> {
                    print("Escribe el nombre EXACTO del contacto: ")
                    val nombre = readln()
                    if (toggleFavorito(nombre)) println("Estado de favorito actualizado.")
                    else println("Contacto no encontrado.")
                }
                6 -> {
                    print("Escribe el nombre EXACTO del contacto a eliminar: ")
                    val nombre = readln()
                    if (eliminarContacto(nombre)) println("Contacto eliminado.")
                    else println("Contacto no encontrado.")
                }
                7 -> salir = true
                else -> println("Opción no reconocida.")
            }
        } catch (e: NumberFormatException) {
            println("Por favor, introduce un número válido.")
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
        }
    }
}