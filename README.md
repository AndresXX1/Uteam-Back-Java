# Prueba Tecnica Uteam (Backend)

* 💬 Página desarrollada con JavaScript y Vue, es una prueba técnica que cumple con las consignas y trabaja con la API de Marvel
* 🛠️ Intuitiva y de fácil recorrido
* 📁 Utiliza el método CRUD para (Create, Read, Update, Delete)
* 🪟 Realizada con Node.js, Vue, Pinia, Axios, y estilada con CSS puro

 <img width="1459" alt="スクリーンショット 2023-11-20 2 23 51" src="https://github.com/AndresXX1/back-pf-hoteles/blob/main/images/0_S0rWo-lYM3IWsjbH.png"> 

* Consignas:

PRUEBA TÉCNICA BACKEND SPRING

Desarrollar un REST API basado en arquitectura Microservicio, utilizando Spring Boot. Se debe utilizar una de las siguientes build tools: Maven o Gradle.

Se debe generar una estructura de datos In-Memory (a elección), que soporte el almacenado del siguiente modelo:


Corresponde a una persona con datos básicos y una coleccion de películas favoritas:

```
{
"id":1,
"firstName":"pablo",
"lastName":"lamberti",
"birthdate":"1987-04-03",
"hasInsurance":false,
"favouriteMovies":[
{
"title":"The Lord of the Rings",
"genre":"fantasy"
},
{
"title":"Pulp Fiction",
"genre":"action"
}]
}
```

Se deben crear recursos para las siguientes acciones:

Personas

- Listar todas las personas (debe retornar el listado ordenado por apellido, nombre)
- Buscar una persona por id
- Buscar una persona por nombre
- Crear una persona
- Modificar una persona (solo se deben modificar los datos enviados. Si un dato no se envía, no se debe modificar)
- Eliminar una persona

Películas de Personas
- Mostrar las películas de una persona
- Agregar una película a una persona
- Quitar una película de una persona

Consideraciones:

- Se debe parametrizar el número máximo de películas por persona. Un valor entero que determina la máxima cantidad de películas que puede tener asociada una persona.

- El resultado a enviar debe ser un zip, o subirlo a una cuenta pública de git que solo tenga el src, el archivo de la build tool y el archivo pom.xml de manera que para revisar, se pueda hacer la descarga de las dependencias y el run.

# End Points

## Endpoints de Personas

* Listar todas las personas

GET /api/persons
* Buscar una persona por ID

GET /api/persons/{id}

* Buscar personas por nombre

GET /api/persons/search?q={name}

* Crear una persona

POST /api/persons

Body:
```
{
"firstName": "John",
"lastName": "Doe",
"birthdate": "1990-01-01",
"hasInsurance": true,
"favouriteMovies": [
{
"title": "Inception",
"genre": "Sci-Fi"
}
]
}
```
* Modificar una persona

PUT /api/persons/{id}
Body:
```
{
"first-name": "Jael",
"last-name": "Lincon"
}
```
* Eliminar una persona

DELETE /api/persons/{id}

## Endpoints de Películas
* Mostrar las películas de una persona

GET /api/persons/{personId}/movies

* Agregar una película a una persona

POST /api/persons/{personId}/movies

Body:
```bash
{
"title": "The Lord of the Rings",
"genre": "Fantasy"
}
```

* Quitar una película de una persona

DELETE /api/persons/{personId}/movies/{movieTitle}



## 🌟 Quick Start

1. 👤 Recuerda que cualquier duda o consulta puedes comunicarte conmigo en LinkedIn. ¡No te olvides de seguirme! :D

<a href="https://www.linkedin.com/in/andres-vera-676414281/" target="_blank">
    <img src="https://img.shields.io/badge/linkedin-%231E77B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn" style="margin-bottom: 5px;" />
</a>

2. ⬇️ Clona el repositorio

```
git clone https://github.com/AndresXX1/Uteam-Back-Java
```
3. 📦 Instala dependencias
abre la terminal en el directorio raíz de tu proyecto (donde está el pom.xml) 

```
mvn clean install
```

4. Inicia el proyecto

```
mvn spring-boot:run
```
