package PM_Ejercicios

data class Tarea(val id: Int, val descripcion: String, var completada: Boolean = false)

fun main(){
    //Inicializamos una lista del objeto PM_Ejercicios.Tarea
    val tareas = mutableListOf<Tarea>()
    //variable que nos permite navegar de tarea en tarea
    var siguienteId = 1

    //Bucle while con funcionalidad de asistente
    while(true){
        println("\n== Gestor de tareas ==")     //Opciones (CRUD)
        println("1. Agregar tarea")             //Insert
        println("2. Listar tarea")              //Select
        println("3. Marcar como completada")    //Update
        println("4. Eliminar tarea")            //Delete
        println("5. Salir")
        println("Opcion")

        //Switch case para elegir opcion
        when (readLine()?.toIntOrNull()) {
            1 -> {
                //Pedimos la descripcion
                print("Descripcion: ")
                val descripcion = readLine() ?: continue
                //Añadimos a la lista una entrada con nuevos id y descripción
                tareas.add(Tarea(siguienteId++, descripcion))
            }
            2 -> {
                if(tareas.isEmpty()){
                    print("No hay tareas")
                }else{  //si hay tareas
                    //Recorremos una a una las tareas
                    tareas.forEach { (id, descripcion, completada) ->
                        val estado = if (completada) "[/]" else "[ ]"
                        println("$id $descripcion $estado")
                    }
                }
            }
            3 -> {
                //Pedimos el id de la tarea a modificar
                print("ID de tarea: ")
                val id = readLine()?.toIntOrNull() ?: continue
                //La busqueda del id  dentro de la lista
                tareas.find {it.id == id}?.completada =true
                println("la tarea ha sido completada")
            }
            4-> {
                //Pedimos el id de la tarea a modificar
                print("ID de tarea: ")
                val id = readLine()?.toIntOrNull() ?: continue
                //La busqueda del id  dentro de la lista
                tareas.removeIf { it.id == id }
                println("la tarea ha sido eliminada")
            }
            5 -> break
        }
    }
}