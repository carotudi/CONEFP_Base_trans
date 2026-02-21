package co.edu.sena.pruebabasedatos_sql_local;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PruebaBaseDatos_SQL_Local {

    public static void main(String[] args) {
        try {
            int Id;
            float Monto;
            String Descripcion, Metodo_pago, Categoria;
            java.time.LocalDateTime Fecha;
            
            

            

            
            
                    //Se carga el driver correspondiente a MariaDB
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            /* Se obtiene una conexión a la base de datos usando el DriverManager
            Nos conectaremos a un servidor SQL en nuestra máquina (localhost)
            y a una base de datos existente llamada concesionario */
            String url = "jdbc:mysql://localhost:3306/Transacciones";
            Connection conexion;
            conexion = DriverManager.getConnection(url, "root", "");
            //Se crea una consulta, en este caso para obtener todos los registros de la tabla vendedores
            String sentencia = "SELECT * FROM info_transacciones;";
            Statement consulta = conexion.createStatement();
            
            //Se crea un ResultSet con los resultados la consulta y se itera sobre el mismo
            ResultSet resultados = consulta.executeQuery(sentencia);
            
            while (resultados.next()) {
                Id = resultados.getInt("Id");
                Monto = resultados.getFloat("Monto");
                Descripcion = resultados.getString("Descripcion");
                Metodo_pago = resultados.getString("Metodo_pago");
                Categoria = resultados.getString("Categoria");
                java.sql.Timestamp fecha = resultados.getTimestamp("Fecha");


                
                System.out.println("Id: " + Id + "\n" +
                                   "Monto: " + Monto + "\n" +
                                   "Descripcion: " + Descripcion + "\n" +
                                   "Matodo_pago: " + Metodo_pago + "\n" +
                                    "Matodo_pago: " + Categoria + "\n" +
                                    "Fecha: " + fecha + "\n" +
                                           
                                   "-----------------------");
            }
            conexion.close();
        
        } catch (ClassNotFoundException e) {
            System.out.println("No fue posible cargar el driver.");
        
        } catch (SQLException e) {
            System.out.println("Hubo un error al acceder a la base de datos: " + e.getMessage());
        }
    }
}
