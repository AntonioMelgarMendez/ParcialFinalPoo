package org.example.proyecto.Controllers.Utilities;
import org.example.proyecto.Controllers.Tables.Transaccion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaveTXT {
    //00009123 Definimos el nombre de la carpeta
    private static final String relativePath = "Reportes";
    //00009123 Funcion para guardar datos del reporte A
    public static void SaveAReport(String idCliente, LocalDate inicioDate, LocalDate finDate, List<Transaccion> transacciones) {
        //00009123 Obtenemos el actual path
        Path projectPath = Paths.get("").toAbsolutePath();
        //00009123 Le agregamos la carpeta a la direccion actual
        Path path = projectPath.resolve(relativePath);
        //00009123 Intentamos crear la carpeta
        try {
            //00009123 Sino existe creamos la carpeta
            Files.createDirectories(path);
        } catch (IOException e) {
            System.err.println("Error al crear el directorio: " + e.getMessage());
            return;
        }
        //00009123 Obtenemos la fecha actual
        LocalDateTime now = LocalDateTime.now();
        //00009123 Creamos el formateo para poder formatear las horas para convertir a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        //00009123 Formateamos la fecha actual
        String formattedDateTime = now.format(formatter);
        //00009123 Definimos el nombre del archivo
        String fileName = path.resolve("A-" + formattedDateTime + ".txt").toString();
        //00009123 Intentamos escribir datos en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            //00009123 Escribimos quien hizo la consulta
            writer.write("Consulta realizada por cliente con ID: " + idCliente);
            writer.newLine();
            //00009123 Escribimos la fecha de inicio
            writer.write("Fecha de inicio: " + inicioDate);
            writer.newLine();
            //00009123 Escribimos la fecha final
            writer.write("Fecha de fin: " + finDate);
            writer.newLine();
            writer.write("Resultados de la consulta:");
            writer.newLine();
            writer.write("-----------------------------------------");
            writer.newLine();
            //00009123 Mostramos los campos
            writer.write("Id_transaccion  Id_cliente FechaCompra Monto Descripcion");
            //00009123 Creamos un DateTimeFormatter para formatear la fecha de la transaccion
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            writer.newLine();
            //00009123 Recorremos todas las transacciones
            for (Transaccion transaccion : transacciones) {
                //00009123 Escribimos todos los datos
                writer.write(transaccion.getIdTransaccion()+" ");
                writer.write(transaccion.getIdCliente()+" ");
                //00009123 Debemos convertir la fecha a un string
                writer.write(transaccion.getFechaCompra().toLocalDate().format(dateFormatter)+" ");
                writer.write(Double.toString(transaccion.getTotalMonto())+" ");
                writer.write(transaccion.getDescripcion()+" ");
                writer.newLine();
            }
            //00009123 Imprimimos el archivo creado
            System.out.println("Consulta guardada en: " + fileName);
        } catch (IOException e) {
            //00009123 Imprimimos el error en caso de que no se pudo guardar
            System.err.println("Error al guardar datos en el archivo: " + e.getMessage());
        }
    }
    //00009123 Funcion para guardar en el reporte B
    public static void SaveBReport(String totalGasto, int idCliente, String anio, String mes) {
        //00009123 Obtenemos el path actual
        Path projectPath = Paths.get("").toAbsolutePath();
        Path path = projectPath.resolve(relativePath);
        //00009123 Intentamos crear el folder
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            //00009123 Terminamos la funcion en caso de que haya un problema
            return;
        }
        //00009123 Formatear la fecha y hora actual para el nombre del archivo
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);
        //00009123 Le colocamos la direccion para que sea de tipo B
        String fileName = path.resolve("B-" + formattedDateTime + ".txt").toString();
        //00009123 Intentamos crear el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            //00009123 Colocamos el id del cliente
            writer.write("ID Cliente: " + idCliente);
            writer.newLine();
            //00009123 Colocamos el anio y el mes
            writer.write("Año: " + anio);
            writer.newLine();
            writer.write("Mes: " + mes);
            writer.newLine();
            //00009123 Guardamos el total del gasto
            writer.write("Total gasto: " + totalGasto);
            writer.newLine();
            System.out.println("Contenido guardado en: " + fileName);
        } catch (IOException e) {
            //00009123 Imprimimos en caso de que haya un problema
            System.err.println("Error al guardar datos en el archivo: " + e.getMessage());
        }
    }
}
