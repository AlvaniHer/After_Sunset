# Manual de Instalación y Puesta en Marcha - After Sunset 🌅

Este documento detalla los pasos necesarios para configurar el entorno de desarrollo, instalar las dependencias y poner en funcionamiento la aplicación **After Sunset**.

---

## 1. Requisitos Previos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

*   **Android Studio Jellyfish (2023.3.1)** o superior.
*   **Java Development Kit (JDK) 17** o superior.
*   **Git** (para clonar el repositorio).
*   Un dispositivo Android físico o un emulador con **API 26 (Android 8.0)** o superior.

---

## 2. Obtención del Código

Clona el repositorio desde GitHub a tu máquina local:

```bash
git clone https://github.com/tu-usuario/After_Sunset.git
```

---

## 3. Configuración de Firebase 🔥

After Sunset utiliza Firebase para la autenticación, base de datos y almacenamiento. Para que la app funcione, debes configurar tu propio proyecto de Firebase:

1.  Pide el archivo **`google-services.json`**. 
2.  Copia este archivo en la carpeta `app/` del proyecto.

---

## 4. Configuración de Google Maps 🗺️

Para la funcionalidad del mapa, necesitas una API Key de Google Maps:

1.  Consigue una clave en [Google Cloud Console](https://console.cloud.google.com/) o pide al equipo de AfterSunset la clave.
2.  Habilita la **Maps SDK for Android**.
3.  Crea (si no existe) un archivo llamado `local.properties` en la raíz de tu proyecto.
4.  Añade tu clave al archivo:

```properties
MAPS_API_KEY=TU_API_KEY_AQUI
```

---

## 5. Compilación y Ejecución

1.  Abre el proyecto en **Android Studio**.
2.  Espera a que el proyecto se sincronice con **Gradle** (esto descargará automáticamente todas las librerías necesarias como Coil 3, Lottie, etc.).
3.  Si la carpeta `.idea` no está presente, Android Studio generará una nueva basada en la configuración del proyecto.
4.  Conecta tu dispositivo o inicia el emulador.
5.  Pulsa el botón **"Run" (flecha verde)** o usa `Shift + F10`.

---

## 6. Resolución de Problemas Comunes

*   **Error de Firebase:** Verifica que el `google-services.json` esté en la carpeta correcta (`app/`) y que el nombre del paquete coincida.
*   **El Mapa aparece en blanco:** Comprueba que la `MAPS_API_KEY` sea válida y tenga la SDK de Android activada en la consola de Google.
*   **Errores de Gradle:** Limpia el proyecto (`Build > Clean Project`) y vuelve a sincronizar.
