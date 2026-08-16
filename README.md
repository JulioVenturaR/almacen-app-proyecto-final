# Sistema de Gestión de Productos de Almacén

Aplicación de escritorio en **Java + Swing** con persistencia
en **MySQL** vía JDBC, para gestionar usuarios y productos de un almacén.

## Tecnología usada
- Java 17, Swing (interfaz gráfica)
- FlatLaf (look and feel moderno y plano, botones y tablas con estilo consistente)
- JDBC + MySQL Connector/J (persistencia)
- Maven (gestión de dependencias y empaquetado)

## Estructura del proyecto
```
src/main/java/com/almacen/
├── Main.java                     → punto de entrada
├── modelo/
│   ├── Persona.java               → clase abstracta (ABSTRACCIÓN)
│   ├── Usuario.java                → extends Persona (HERENCIA), implements Validable
│   ├── Producto.java                → implements Validable
│   └── interfaces/Validable.java     → contrato de validación (POLIMORFISMO)
├── dao/
│   ├── ConexionDB.java             → conexión a BD (patrón SINGLETON)
│   ├── IDAO.java                    → contrato genérico (patrón DAO)
│   ├── UsuarioDAO.java               → acceso a datos de usuarios
│   └── ProductoDAO.java               → acceso a datos de productos
├── vista/
│   ├── LoginFrame.java, RegistroFrame.java
│   ├── PrincipalFrame.java
│   ├── UsuariosFrame.java, UsuarioFormDialog.java
│   └── ProductosFrame.java, ProductoFormDialog.java
└── util/
    └── Styles.java                → paleta de colores y fuentes centralizada
```

## Pilares de POO aplicados (comentados en el código fuente)
- **Abstracción**: `Persona` es una clase abstracta que modela lo esencial de una persona.
- **Encapsulamiento**: todos los atributos de `Usuario` y `Producto` son privados, con getters/setters.
- **Herencia**: `Usuario extends Persona`, reutilizando `getNombreCompleto()`.
- **Polimorfismo**: la interfaz `Validable` es implementada de forma distinta por `Usuario` y `Producto` (cada uno con su propia lógica de `validar()`).

## Patrones de diseño aplicados (comentados en el código fuente)
- **Singleton** (`ConexionDB`): una única instancia de conexión a la base de datos en toda la app.
- **DAO** (`IDAO`, `UsuarioDAO`, `ProductoDAO`): separa la lógica de acceso a datos (SQL/JDBC) de la lógica de negocio y de la interfaz gráfica.

## Cómo ejecutar

### Opción A — Maven (recomendado)
1. Asegúrate de tener conexión a internet (para descargar el conector de MySQL la primera vez) y Java 17+ y Maven instalados.
2. Desde la carpeta del proyecto:
   ```bash
   mvn clean package
   java -jar target/almacen-app.jar
   ```
   El plugin `maven-shade-plugin` genera un jar ejecutable con el driver de MySQL ya incluido.

### Opción B — Eclipse
1. `File > Import > Existing Maven Project` y selecciona esta carpeta.
2. Eclipse descargará automáticamente la dependencia `mysql-connector-j` definida en `pom.xml`.
3. Clic derecho en `Main.java` → `Run As > Java Application`.
4. Si prefieres no usar Maven en Eclipse, descarga manualmente el jar de
   [mysql-connector-j](https://dev.mysql.com/downloads/connector/j/) y agrégalo al Build Path
   (`Project Properties > Java Build Path > Libraries > Add External JARs`).

## Base de datos
La conexión remota ya viene configurada en `ConexionDB.java` con los datos
provistos en el enunciado (host, usuario y contraseña de Aiven Cloud), apuntando
a las tablas `usuarios` y `productos` ya existentes. No necesitas crear nada
para usarla tal cual.

Si prefieres usar tu propia base de datos local, incluí `schema.sql` con la
estructura de tablas equivalente; solo tendrías que actualizar `URL`,
`USUARIO` y `PASSWORD` en `ConexionDB.java`.

## Flujo de la aplicación
1. **Login** → si el usuario no existe o los campos están vacíos, muestra el mensaje de error indicado en el mandato. Desde aquí se puede ir a **Registrarse**.
2. Al autenticar correctamente se abre la **pantalla principal** con los botones **Usuarios** y **Productos**, y un botón **Cerrar Sesión** (que regresa al login).
3. **Gestión de Usuarios**: tabla con Nombre, Apellido, Teléfono, Correo y Usuario. Botones Nuevo / Actualizar / Eliminar y Volver. Los cambios se reflejan automáticamente en la tabla.
4. **Gestión de Productos**: tabla con todos los datos del producto. Botón Nuevo abre el formulario en blanco; hacer clic en una fila abre el mismo formulario con los datos del producto y los botones Guardar / Eliminar. Botón Volver.
