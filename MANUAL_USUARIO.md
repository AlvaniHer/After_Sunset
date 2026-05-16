# Manual de Usuario y Gestión - After Sunset 🌅

Este manual explica cómo interactuar con la aplicación **After Sunset**, tanto desde la perspectiva de un usuario que busca disfrutar de la noche malagueña como desde la gestión de la plataforma.

---

## 👤 1. Manual del Usuario Final

### Registro e Inicio de Sesión
- **Crear cuenta:** Introduce tu nombre, email y contraseña. El sistema generará automáticamente un `username` sugerido que puedes modificar.
- **Acceso:** Usa tus credenciales para entrar a la experiencia inmersiva.

### Descubrimiento de Eventos
- **Pantalla de Inicio:** Visualiza los próximos eventos más destacados con un diseño moderno.
- **Detalle del Evento:** Pulsa sobre cualquier tarjeta para ver la descripción, edad mínima, precio y ubicación.
- **Mapa Interactivo:** Usa la pestaña del mapa para ver los locales abiertos cerca de ti en Málaga. Filtra por zonas como "Centro", "Teatinos" o "Torremolinos".

### Compra de Entradas
1.  Dentro de un evento, pulsa **"Comprar Entrada"**.
2.  Selecciona una oferta disponible (si las hay). 
3.  Confirma la compra en el **Checkout**.

### Gestión de Entradas (Mis Entradas)
- Accede a la pestaña de tickets para ver tus compras.
- **Giro de Ticket:** Pulsa sobre un ticket para darle la vuelta y ver el código QR de acceso y el enlace a la ubicación en el mapa.

### Social y Perfil
- **Amigos:** Busca a otros usuarios por su `@username`, envía solicitudes y gestiona tu lista de amigos.
- **Reseñas:** Lee las opiniones de otros usuarios sobre los locales y deja la tuya con una puntuación de estrellas.
- **Fidelización:** Tu perfil muestra tu rango (STANDARD, VIP, GOLD, LEGENDARY) según los puntos acumulados por asistir a eventos.

---

## 🛠️ 2. Manual del Administrador / Gestor

Actualmente, la gestión de After Sunset se realiza directamente a través de la **Consola de Firebase**.

### Gestión de Usuarios (Firebase Auth)
- El administrador puede ver la lista de usuarios registrados, deshabilitar cuentas o restablecer contraseñas desde la pestaña *Authentication*.

### Gestión de Contenido (Cloud Firestore)
La estructura de datos permite al gestor añadir o modificar información en tiempo real sin actualizar la app:

#### Colección `eventos`:
Para añadir un nuevo evento, crea un documento con campos como:
- `nombre_evento`: Título.
- `precio_entrada`: Coste base.
- `id_local`: ID del local vinculado (debe existir en la colección `locales`).
- `fecha`: Timestamp de realización.
- `latitude` / `longitude`: Para la ubicación directa.

#### Colección `locales`:
Aquí se definen las salas. Es crítico mantener estos campos para que los tickets funcionen:
- `nombre`: Nombre comercial.
- `latitud` / `longitud`: Coordenadas exactas (ej: 36.7213, -4.4214).
- `direccion`: Texto de la dirección postal.

#### Moderación de Reseñas:
- En la colección `resenas`, el administrador puede eliminar comentarios inapropiados o falsos que afecten a la reputación de los locales.

### Mantenimiento de Imágenes (Storage)
- Las fotos de los eventos y locales deben subirse a *Firebase Storage* y pegar la URL de descarga en los documentos correspondientes de Firestore.
