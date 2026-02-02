# Sistema de Recuperación de Información (SRI) - Medline

![Java](https://img.shields.io/badge/Java-JDK%2021-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![IDE](https://img.shields.io/badge/IDE-NetBeans%2022-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
![Solr](https://img.shields.io/badge/Apache%20Solr-9.x-D22128?style=for-the-badge&logo=apache&logoColor=white)
![RAG](https://img.shields.io/badge/AI_Model-Gemma_(RAG)-8A2BE2?style=for-the-badge&logo=google-gemini&logoColor=white)
![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)

Este proyecto implementa un Sistema de Recuperación de Información (SRI) avanzado para el corpus médico **Medline**, desarrollado para la asignatura de **Motores de Búsqueda (MB)** de la Universidad de Huelva.

El sistema combina la potencia de **Apache Solr** para la indexación y recuperación clásica, con una interfaz gráfica en **Java Swing** y técnicas modernas de **Generación Aumentada por Recuperación (RAG)** utilizando **LM Studio** (modelo Gemma) para sintetizar respuestas con Inteligencia Artificial.

---

## 📋 Características Principales

* **Motor de Búsqueda Solr:** Indexación y búsqueda sobre documentos médicos con configuración optimizada (tokenizadores, filtros y stopwords) en el esquema `managed-schema`.
* **Módulo RAG (Retrieval-Augmented Generation):** Integración con LLMs locales (Gemma) mediante LM Studio. El sistema recupera documentos relevantes y los utiliza como contexto para generar una respuesta en lenguaje natural.
* **Interfaz Gráfica (GUI):** Aplicación de escritorio desarrollada en Java Swing para facilitar la interacción del usuario (búsquedas por texto y por ID).
* **Validación de Resultados:** Evaluación del rendimiento mediante el estándar `trec_eval`.

---

## 🗓️ Historial de Versiones

El desarrollo del proyecto ha seguido una evolución incremental a través de las siguientes versiones:

* **v0.1 - Indexación Base:** Aplicación Java capaz de leer el fichero del corpus Medline e indexar su contenido en el servidor mediante SolrJ.
* **v0.2 - Procesamiento de Consultas:** Lectura automatizada del fichero de consultas `MED.QRY`. El sistema extrae las primeras 5 palabras de cada consulta, las envía a Apache Solr y guarda las respuestas recibidas en un fichero local.
* **v0.3 - Formato TREC:** Generación automática del fichero de resultados en formato `trec_top_file`, permitiendo la ejecución de la herramienta de evaluación estandarizada `trec_eval`.
* **v1.0 - Buscador SolrJ:** Consolidación de la aplicación como un buscador funcional (Backend).
* **v2.0 - Mejoras del SRI:** Optimización de la precisión y cobertura mediante mejoras en el esquema de Solr (`managed-schema`), incluyendo nuevas definiciones de tipos de campo y listas de *stopwords*.
* **v3.0 - RAG e Interfaz (Versión Final):** Implementación de la arquitectura RAG utilizando LM Studio (Modelo Gemma) para la generación de respuestas y desarrollo de la interfaz gráfica (GUI) con Java Swing.

---

## 🏗️ Estructura del Proyecto

El código fuente se organiza en dos paquetes principales para separar la lógica de negocio de la interfaz de usuario:

### 1. Backend y Lógica (`src/practica`)
* **`Principal.java`**: Punto de entrada (`Main`). Gestiona la orquestación entre la GUI, Solr y el módulo RAG.
* **`Solr.java`**: Clase encargada de la comunicación con el servidor Apache Solr (indexación de documentos XML y ejecución de consultas).
* **`Rag.java`**: Gestiona la construcción del prompt (contexto + pregunta) y la conexión HTTP con el servidor local de LM Studio para obtener la respuesta de la IA.
* **`Separadora.java`**: Procesa el fichero original de Medline, dividiéndolo en documentos individuales y gestionando el muestreo.
* **`Fichero.java`**: Utilidades para la lectura y escritura de archivos en disco.

### 2. Frontend (`src/gui`)
* **`Vista_Buscador.java`**: Implementación de la interfaz gráfica en Swing. Permite al usuario introducir consultas, visualizar los documentos recuperados y leer la respuesta generada por la IA.

---

## 📊 Evaluación y Resultados

El sistema ha sido evaluado utilizando `trec_eval` sobre la colección `coleccionMejorada` (con mejoras en tipos de campo de texto y stopwords). A continuación se presentan las métricas de precisión y cobertura:

<table>
  <tr>
    <td> Para k=10 </td>
    <td> <img width="865" height="731" alt="Resultados trec_eval_k_10" src="https://github.com/user-attachments/assets/dc733e4e-4905-4471-a4de-68b5d39cb35c" /> </td>
  </tr>
  <tr>
    <td> Para k=todos </td>
    <td> <img width="864" height="724" alt="Resultados trec_eval_k_todos" src="https://github.com/user-attachments/assets/1853eb81-c894-42ec-898a-e105c916246c" /> </td>
  </tr>
</table>

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java (JDK 21).
* **IDE:** NetBeans 22.
* **Interfaz Gráfica (GUI):** Java Swing.

---

## ⚙️ Instalación y Ejecución

### Requisitos Previos
* Java Development Kit (JDK) 21.
* **NetBeans 22** (recomendado) o cualquier otro IDE compatible con proyectos Maven.
* **Apache Solr 9.x** (Corriendo en `localhost:8983`).
* **LM Studio** (Corriendo en modo servidor local).
    * Modelo cargado: `gemma` (o cualquier modelo compatible con la API de OpenAI/Local).

### Pasos para ejecutar

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/antonioabadpro/MB_Proyecto_AAHG.git
    ```

2.  **Configuración de Solr:**
    * Iniciar Solr y crear un core llamado `coleccionMejorada`.
    * Sustituir el archivo `managed-schema` del core por el proporcionado en este proyecto (para aplicar filtros y stopwords).
    * Indexar los documentos ejecutando la clase `Separadora` o a través de la interfaz de carga de Solr.

3.  **Configuración de IA (LM Studio):**
    * Abrir LM Studio y navegar a la pestaña de "Local Server".
    * Cargar el modelo `gemma`.
    * Iniciar el servidor (Start Server).

4.  **Ejecución:**
    * Compilar y ejecutar la clase `Principal.java` desde NetBeans o generar el JAR ejecutable.

---

## 💻 Uso de la Aplicación

La interfaz gráfica ofrece dos modalidades de búsqueda:

1.  **Búsqueda por Texto Libre:**
    * Introduce una consulta médica (ej. *"blood pressure in newborns"*).
    * El sistema mostrará los documentos más relevantes de Medline y una **Respuesta Generada por IA** que resume la información encontrada.
2.  **Búsqueda por ID:**
    * Introduce el ID único de un documento Medline para recuperarlo directamente.

---

## 📑 Documentación y Presentación

[![Memoria Técnica](https://img.shields.io/badge/PDF-Ver_Presentación-EC1C24?style=for-the-badge&logo=adobeacrobatreader&logoColor=white)](https://github.com/antonioabadpro/MB_Proyecto_AAHG/blob/main/MB_Presentacion_Practicas_AAHG.pdf)

> **Nota:** Haz clic en el botón de arriba para visualizar o descargar la presentación del proyecto en PDF donde se explican en detalle las mejoras del motor de búsqueda, la arquitectura RAG y el análisis exhaustivo de los resultados obtenidos.

---

## 👤 Autor

**Antonio Abad Hernández Gálvez**
* GitHub: [@antonioabadpro](https://github.com/antonioabadpro)
* *Proyecto realizado para la Universidad de Huelva (UHU)*

---

## ©️​ Licencia y Derechos de Autor

**© 2025. Antonio Abad Hernández Gálvez**. <br>
Todos los derechos reservados.

Este proyecto es propiedad intelectual de su autor. <br>
El código se proporciona únicamente con fines de **consulta y demostración de portfolio**.

El código fuente de este proyecto es propiedad exclusiva de su autor. 
Se permite su visualización con fines educativos y de evaluación académica.

⛔ **Prohibido su uso:** Queda estrictamente prohibida su reproducción total o parcial, modificación, distribución o uso para fines comerciales o académicos por parte de terceros sin la autorización expresa y por escrito del autor.

Este proyecto es el resultado de una evaluación académica para la Universidad de Huelva. <br>
El plagio o uso indebido de este código en otros proyectos académicos será reportado.
