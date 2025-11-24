package PM_Ejercicios

//Creacion del objeto usuario
data class Usuario(val nombre: String, var email: String, val edad: Int)

//funcion que valida el nombre del usuario
fun validarNombre(nombre: String): Result<String> {
    return if(nombre.length < 3){
        Result.failure(Exception("Nombre no valido"))
    }else{
        Result.success(nombre)
    }
}

//Funcion que valida el email del usuario
fun validarEmail(email: String): Result<String> {
    return if(email.contains("@") && email.contains(".")) {
        Result.success(email)
    }else{
        Result.failure(Exception("Email no valido"))
    }
}

//Funcion que valida la edad de usuario
fun validarEdad(edad: Int): Result<Int> {
    return if (edad in 18..120) {
        Result.success(edad)
    } else {
        Result.failure(Exception("Edad debe ser entre 18 y 120"))
    }
}

//Ejecucion del codigo
fun main(){
    //Peticion al usuario para que se registre
    //Su nombre
    print("Nombre: ")
    val nombre = readLine()?: " "

    //Su email
    print("email: ")
    val email = readLine()?: " "

    //Su edad
    print("edad: ")
    val edad = readLine()?.toIntOrNull() ?: -1

    val resultadoNombre = validarNombre(nombre)
    val resultadoEmail = validarEmail(email)
    val resultadoEdad = validarEdad(edad)

    //Devolucion de errores y demás
    when{
        resultadoNombre.isFailure -> println("Error en nombre: ${resultadoNombre.exceptionOrNull()?.message}" )
        resultadoEmail.isFailure -> println("Error en email: ${resultadoEmail.exceptionOrNull()?.message}" )
        resultadoEdad.isFailure -> println("Error en edad: ${resultadoEdad.exceptionOrNull()?.message}" )
        else -> {
            //Inicializamos el objeto usuario tras verificar que son correctos los campos
            val usuario = Usuario(nombre, email, edad)
            println("Usuario creado: $usuario")
        }
    }
}