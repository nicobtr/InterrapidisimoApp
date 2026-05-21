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

Muestra correctamente la pantalla de login e indica que la versión local es menor que la versión del servidor. 
<img width="720" height="1514" alt="WhatsApp Image 2026-05-21 at 6 44 08 PM (3)" src="https://github.com/user-attachments/assets/00415a09-23ef-4660-b161-1c5a6e1bad00" />

Pantalla de Login

<img width="720" height="1515" alt="WhatsApp Image 2026-05-21 at 6 44 08 PM (2)" src="https://github.com/user-attachments/assets/71e1ebdd-02bc-455f-9e72-4fd352a244d3" />


El endpoint de la API externa que debería verificar las credenciales y retornar un token no funciona, por lo tanto no hay token y el login no es posible. 

<img width="720" height="1514" alt="WhatsApp Image 2026-05-21 at 6 44 08 PM (1)" src="https://github.com/user-attachments/assets/1bbe4ec4-83dc-4f81-a7d6-49e3b7462a7a" />

Forzando el login al cambiar algunas líneas de código se puede omitir la validación de credenciales y vemos la pantalla principal. 

<img width="720" height="1520" alt="WhatsApp Image 2026-05-21 at 6 44 08 PM" src="https://github.com/user-attachments/assets/e7953ab7-0fd2-4130-8e41-5c3056517318" />

Dado que no es posible obtener un token para verificar la identidad del usuario, el endpoint de las tablas retorna un código 401: Unathorized.
<img width="720" height="1518" alt="WhatsApp Image 2026-05-21 at 6 44 07 PM (1)" src="https://github.com/user-attachments/assets/fdc03241-3d81-4347-9efc-a90ba26501f0" />

El endpoint de las localidades funciona correctamente y no requiere token, retorna una lista de ciudades.
<img width="720" height="1518" alt="WhatsApp Image 2026-05-21 at 6 44 07 PM" src="https://github.com/user-attachments/assets/ed065c0e-b1d5-427c-88d9-85be0c2bf9d5" />

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
