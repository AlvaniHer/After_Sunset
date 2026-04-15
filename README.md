🌅 After Sunset
=============================

**After Sunset** es una propuesta disruptiva en el ecosistema de aplicaciones de ocio nocturno. No es solo una ticketera; es una ventana inmersiva a la vida de la ciudad cuando cae el sol. Diseñada con un enfoque Mobile-First y una estética Cyber-Night, After Sunset redefine la forma en que los usuarios descubren, viven y comparten la noche.

🛠️ Stack Tecnológico
--------------------
-   **Lenguaje:** [Kotlin](https://kotlinlang.org/)

-   **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Arquitectura declarativa).

-   **Navegación:** [Type-Safe Navigation](https://developer.android.com/guide/navigation/design/type-safety) (Novedad de 2024/25).

-   **Imágenes:** [Coil 3](https://coil-kt.github.io/coil/) (Modular & Network Support).

-   **Backend:** [Firebase](https://firebase.google.com/) (Auth, Firestore, Storage).

🏗️ Arquitectura
----------------

El proyecto sigue el patrón **MVVM (Model-View-ViewModel)** y el **Repository Pattern** para separar la lógica de negocio de la interfaz de usuario.

### Estructura de Carpetas

```
com.example.aftersunset/
├── data/           <-- Implementaciones de Firebase y Repositorios.
├── domain/         <-- Modelos de datos (Event, User) e Interfaces.
├── navigation/     <-- Rutas @Serializable y NavHost principal.
├── ui/
│   ├── theme/      <-- Colores, Tipografías y Formas.
│   ├── components/ <-- Componentes reutilizables (Botones, Cards).
│   └── screens/    <-- Pantallas (Login, Home, Map, etc.).
└── util/           <-- Helpers y extensiones.

```

🛠️ Guía para Desarrolladores (Backend)
---------------------------------------

Para los encargados de **Firebase**, aquí tenéis los puntos de "incrustación" de lógica:

1.  **Autenticación:** La interfaz `AuthRepository` en `domain/repository/` define los métodos necesarios. Solo hay que crear la implementación en `data/` usando `FirebaseAuth`.

2.  **Firestore:** Los eventos de la pantalla principal consumen el modelo `Event`. La colección en la base de datos debería seguir esa misma estructura de campos.

3.  **Imágenes:** Usamos URLs directas. Asegurarse de subir los flyers a Firebase Storage y pasar el link HTTPS al campo `imageUrl`.

🎨 Estilo
-----------------

Para mantener la estética "Cyber-Night", utiliza los tokens de color definidos en `AfterSunsetTheme`:

-   **InkBlack** (`#0B0B1E`): Es nuestro negro profundo con un matiz azulado. Se usa para todos los fondos de pantalla y contenedores base.

-   **IndigoBloom** (`#712DA6`): Un violeta eléctrico diseñado para sombras, degradados de fondo y elementos decorativos.

-   **Pacific Cyan** (`#0F98B3`): Utilízalo para elementos secundarios, estados de selección, iconos del mapa y para dar ese toque tecnológico.

-   **Dragonfruit** (`#EB4990`): Nuestro color primario. Se usa para el logo, bordes destacados y botones de acción.

-   **PumpkinSpice** (`#FF6B00`): Este naranja vibrante representa la luz del atardecer. Úsalo siempre en combinación con *Dragonfruit* para crear el degradado de los botones de acción principal.

📦 Instalación
--------------

1.  Clona el repositorio.

2.  Asegúrate de tener **Android Studio**.

3.  Sincroniza el proyecto con Gradle.

4.  Pide el archivo `google-services.json` para conectar con Firebase.
