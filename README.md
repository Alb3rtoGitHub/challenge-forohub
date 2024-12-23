<h1 align="center">🎫Foro Hub🎫</h1>

**Foro Hub** es un desarrollo de un Foro, donde todos los participantes de una plataforma pueden colocar sus preguntas sobre determinados asuntos,
 se crea una API REST usando Spring, tiene validaciones según reglas de negocio, implementación de una Base de Datos MySQL para la persistencia de
información y Servicio de Autenticación/Autorización, finalmente se documenta con Swagger.

<p align="center">
<img src="ForoHub1.png" alt="Foro Hub Banner" style="width: 400px">
</p>

## 💽Insignia Challenge Foro Hub
El programa **ONE Oracle Next Education** junto con la academa **Alura Latam** otorgan una insignia a quienes finalizan este Challenge.
<p align="center">
<img src="ForoHub3.png" alt="Insignia Challenge LiterAlura" style="width: 400px">
</p>
<p align="center">
<img src="https://img.shields.io/badge/STATUS-%20TERMINADO-green">
<img src="https://img.shields.io/badge/Free-blue">
<img src="https://img.shields.io/badge/Spring%20Boot-v3.3.5-yellow">
</p>

---

## 📑Índice

1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Funcionalidades](#funcionalidades)
3. [Acceso al Proyecto](#acceso-al-proyecto)
4. [Tecnologías Utilizadas](#tecnologías-utilizadas)
5. [Contribuyentes](#contribuyentes)
6. [Desarrolladores](#desarrolladores)
7. [Licencia](#licencia)

---

## 📝Descripción del Proyecto

Foro Hub es una API REST que simula el backend de un foro donde los usuarios pueden interactuar creando y gestionando tópicos de discusión. Inspirada en la dinámica de los foros educativos, esta API permite realizar las operaciones CRUD necesarias para administrar los usuarios, tópicos y respuestas a esos tópicos,  promoviendo la colaboración y el aprendizaje.

Este proyecto se desarrolló como parte del Challenge Back End de Alura, aplicando las mejores prácticas en desarrollo de software y diseño de APIs REST.

---

## 🚀Funcionalidades

### Topico

- **Crear un nuevo tópico:** Permite registrar un tópico con su título, mensaje, autor y curso con su categoría.
- **Listar todos los tópicos:** Devuelve un listado de los tópicos almacenados en la base de datos, paginados y ordenados por fecha en forma ascendente.
- **Consultar un tópico específico:** Obtiene los detalles de un tópico según su ID.
- **Consultar un tópico por Curso y Año:** Devuelve un listado de los topicos por Nombre de Curso y Año.
- **Actualizar un tópico:** Modifica los datos de un tópico existente.
- **Eliminar un tópico:** Borra un tópico identificado por su ID.
- **Resolución de un tópico** Permite dar por resuelto un tópico con un ID especificado.

### Respuesta
- **Crear una respuesta a un tópico:** Permite registrar una respuesta según el ID del tópico y usuario, con el mensaje, que no se pueder repetir el mismo texto en el mismo topico.
- **Listar todas las respuestas:** Devuelve un listado de las respuestas almacenados en la base de datos, paginados y ordenados por fecha en forma ascendente.
- **Consultar una respuesta específica:** Obtiene los detalles de una respuesta según su ID.
- **Consultar una respuesta por varios parámetros:** Devuelve un listado de las respuestas segun todos o algunos de los siguientes parámetros: **mensaje**, **tópico o parte del mismo**, **autor**.
- **Actualizar una respuesta:** Modifica los datos de una respuesta existente.
- **Eliminar una respuesta:** Borra una respuesta identificada por su ID.

### Usuario

- **Crear un nuevo usuario:** Permite registrar un usuario con su nombre, correo electrónico, contraseña y perfil(rol según una lista enumerada).
- **Listar todos los usuarios:** Devuelve un listado de los usuarios almacenados en la base de datos, paginados y ordenados por nombre en forma ascendente.
- **Consultar un usuario específico:** Obtiene los detalles de un usuario según su ID.
- **Consultar un usuario por Nombre o correo electrónico:** Devuelve un listado de los usuarios por Nombre o correo electrónico, o ambos valores.
- **Actualizar un usuario:** Modifica los datos de un usuario existente.
- **Eliminar un usuario:** Borra un usuario identificado por su ID.

### Documentación
- **Documentación interactiva con Swagger:** Accede a la especificación de la API de manera visual y prueba las rutas implementadas.

---

## Acceso al Proyecto

### 📁 Clonación del Repositorio

```bash
# Clonar el repositorio
$ git clone https://github.com/tuusuario/forohub.git
```

### 🛠️ Ejecución del Proyecto

1. Asegúrate de tener instalado Java 17 y Maven.
2. Configura la base de datos en el archivo `application.properties` según tu entorno.
3. Ejecuta el siguiente comando:

```bash
$ mvn spring-boot:run
```
Tambien puedes ejecutar el archivo:

### 📚 Acceso a la Documentación

La documentación interactiva está disponible en:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Tecnologías Utilizadas

- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3.3.5
- **Base de Datos:** MySQL
- **Herramientas:** Swagger para documentación, IntelliJ IDEA

---

## Contribuyentes

Si deseas contribuir a este proyecto, por favor crea un *fork* del repositorio, realiza tus cambios y envía un *pull request*. ¡Tus aportes son bienvenidos!

---

## Desarrolladores

| [<img src="https://avatars.githubusercontent.com/u/12345678?v=4" width=115><br><sub>Nombre del Desarrollador</sub>](https://github.com/tuusuario) |
| :---: |

---

## Licencia

Este proyecto está licenciado bajo la licencia MIT. Para más detalles, consulta el archivo [LICENSE](./LICENSE).

---

Gracias por visitar este repositorio. ¡Esperamos que encuentres útil esta API y te animes a colaborar con el proyecto!

