     import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class PruebaBaseDatos_SQL_Local {

    public static void main(String[] args) {
        try {
            // Conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Transacciones";

            Scanner sc = new Scanner(System.in);
            int opcion;
            Connection conexion;
            conexion = DriverManager.getConnection(url,"root","");

            do {
          
                System.out.println("1. Crear transaccion");
                System.out.println("2. Listar transacciones");
                System.out.println("3. Consultar por ID");
                System.out.println("4. Actualizar transaccion");
                System.out.println("5. Eliminar transaccion");
                System.out.println("0. Salir");
                System.out.print("Elige una opcion: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        sc.nextLine(); // limpiar buffer del menú

                        System.out.print("Tipo (INGRESO/EGRESO): ");
                        String tipo = sc.nextLine();

                        System.out.print("Monto: ");
                        float monto = sc.nextFloat();
                        sc.nextLine(); // limpiar buffer

                        System.out.print("Descripcion: ");
                        String descripcion = sc.nextLine();
                        System.out.print("Metodo de pago: ");
                        String metodo = sc.nextLine();
                        System.out.print("Categoria: ");
                        String categoria = sc.nextLine();

                        crearTransaccion(conexion, tipo, monto, descripcion, metodo, categoria, LocalDateTime.now());
                        break;

                    case 2:
                        listarTransacciones(conexion);
                        break;

                    case 3:
                        System.out.print("ID a consultar: ");
                        int idConsulta = sc.nextInt();
                        consultarPorId(conexion, idConsulta);
                        break;

                    case 4:
                        System.out.print("ID a actualizar: ");
                        int idUpdate = sc.nextInt();
                        System.out.print("Nuevo monto: ");
                        float nuevoMonto = sc.nextFloat();
                        sc.nextLine();
                        System.out.print("Nueva descripcion: ");
                        String nuevaDesc = sc.nextLine();
                        actualizarTransaccion(conexion, idUpdate, nuevoMonto, nuevaDesc);
                        break;

                    case 5:
                        System.out.print("ID a eliminar: ");
                        int idDelete = sc.nextInt();
                        eliminarTransaccion(conexion, idDelete);
                        break;

                    case 0:
                        System.out.println(" Saliendo del programa...");
                        break;

                    default:
                        System.out.println(" Opción inválida.");
                }
            } while (opcion != 0);

            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // crear transaccion
    public static void crearTransaccion(Connection conexion, String tipo, float monto, String descripcion, String metodoPago, String categoria, LocalDateTime fecha) throws SQLException {
        String sql = "INSERT INTO info_transacciones (Tipo, Monto, Descripcion, Metodo_pago, Categoria, Fecha) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, tipo); // ENUM: "INGRESO" o "EGRESO"
        ps.setFloat(2, monto);
        ps.setString(3, descripcion);
        ps.setString(4, metodoPago);
        ps.setString(5, categoria);
        ps.setTimestamp(6, Timestamp.valueOf(fecha));
        ps.executeUpdate();
        System.out.println("Transaccion creada correctamente.");
    }

    // mostrar transacciones 
    public static void listarTransacciones(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM info_transacciones";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println("Id: " + rs.getInt("Id") +
                               ", Tipo: " + rs.getString("Tipo") +
                               ", Monto: " + rs.getFloat("Monto") +
                               ", Descripcion: " + rs.getString("Descripcion") +
                               ", Metodo: " + rs.getString("Metodo_pago") +
                               ", Categoria: " + rs.getString("Categoria") +
                               ", Fecha: " + rs.getTimestamp("Fecha"));
        }
    }

    //consultar por id de transaccion
    public static void consultarPorId(Connection conexion, int id) throws SQLException {
        String sql = "SELECT * FROM info_transacciones WHERE Id = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("Id: " + rs.getInt("Id") +
                               ", Tipo: " + rs.getString("Tipo") +
                               ", Monto: " + rs.getFloat("Monto") +
                               ", Descripción: " + rs.getString("Descripcion") +
                               ", Método: " + rs.getString("Metodo_pago") +
                               ", Categoría: " + rs.getString("Categoria") +
                               ", Fecha: " + rs.getTimestamp("Fecha"));
        } else {
            System.out.println("️ No se encontro la transacción con id " + id);
        }
    }

    // actualizar Transaccion
    public static void actualizarTransaccion(Connection conexion, int id, float monto, String descripcion) throws SQLException {
        String sql = "UPDATE info_transacciones SET Monto = ?, Descripcion = ? WHERE Id = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setFloat(1, monto);
        ps.setString(2, descripcion);
        ps.setInt(3, id);
        int filas = ps.executeUpdate();
        System.out.println(" Transacciones actualizadas: " + filas);
    }

    // eliminar transaccion
    public static void eliminarTransaccion(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM info_transacciones WHERE Id = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, id);
        int filas = ps.executeUpdate();
        System.out.println(" Transacciones eliminadas: " + filas);
    }
}
