# InterrapidisimoApp

Aplicación Android desarrollada en Kotlin como prueba técnica.

## Funcionalidades implementadas

- **Control de versiones**: Al iniciar la app consulta la versión actual en el servidor y la compara con la versión local. Muestra un mensaje si está desactualizada o es superior.
- **Login**: Envía credenciales al API de seguridad con los headers requeridos. Maneja errores HTTP y almacena los datos del usuario en SQLite local.
- **Base de datos SQLite**: Almacena localmente el usuario autenticado y las tablas del esquema.
- **Pantalla Home**: Muestra usuario, identificación y nombre. Botones de navegación a Tablas y Localidades.
- **Pantalla Tablas**: Consulta el esquema de tablas del servidor y los almacena localmente.
- **Pantalla Localidades**: Consume el API y muestra AbreviacionCiudad y NombreCompleto de cada registro.

## Estado de los endpoints

| Endpoint | Estado | Observación |
|----------|--------|-------------|
| ConsultarParametrosFramework/VPStoreAppControl |  Funciona | Retorna versión 100 |
| AuthenticaUsuarioApp |  Falla | Retorna JsonNull en el body |
| ObtenerEsquema/true |  Error 401 | No autorizado |
| ObtenerLocalidadesRecogidas |  Funciona | Retorna lista de ciudades |

Varios de los endpoints están fallando o simplemente no devuelven lo que se espera, el endpóint de login no está devolviendo un JWT y sin ese token algunos otros endpoints retornan un código 401. 
El código maneja correctamente todos los errores mostrando mensajes al usuario.

## Capturas de pantalla



## Tecnologías usadas

- Kotlin
- Retrofit 2 (consumo de APIs)
- SQLite (base de datos local)
- Coroutines (operaciones asíncronas)
- AppCompat

## Cómo ejecutar

1. Clonar el repositorio
2. Abrir en Android Studio
3. Conectar dispositivo Android con depuración USB activada o utilizar un emulador Android (AVD) en Android Studio
4. Presionar Run 