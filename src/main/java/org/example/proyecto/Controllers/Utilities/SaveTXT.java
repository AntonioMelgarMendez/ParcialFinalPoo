package org.example.proyecto.Controllers.Utilities;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaveTXT {
    private static final String relativePath = "Reportes";
    public static void SaveAReport(String idCliente, LocalDate inicioDate, LocalDate finDate, List<Transaccion> transacciones) {
        Path projectPath = Paths.get("").toAbsolutePath();
        Path path = projectPath.resolve(relativePath);

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            System.err.println("Error al crear el directorio: " + e.getMessage());
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);
        String fileName = path.resolve("A-" + formattedDateTime + ".txt").toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Consulta realizada por cliente con ID: " + idCliente);
            writer.newLine();
            writer.write("Fecha de inicio: " + inicioDate);
            writer.newLine();
            writer.write("Fecha de fin: " + finDate);
            writer.newLine();
            writer.write("Resultados de la consulta:");
            writer.newLine();
            writer.write("-----------------------------------------");
            writer.newLine();
            writer.write("Id_transaccion  Id_cliente FechaCompra Monto Descripcion");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            writer.newLine();
            for (Transaccion transaccion : transacciones) {
                writer.write(transaccion.getIdTransaccion()+" ");
                writer.write(transaccion.getIdCliente()+" ");
                writer.write(transaccion.getFechaCompra().toLocalDate().format(dateFormatter)+" ");
                writer.write(Double.toString(transaccion.getTotalMonto())+" ");
                writer.write(transaccion.getDescripcion()+" ");
                writer.newLine();
            }

            System.out.println("Consulta guardada en: " + fileName);
        } catch (IOException e) {
            System.err.println("Error al guardar datos en el archivo: " + e.getMessage());
        }
    }
    public static void SaveBReport(String totalGasto, int idCliente, String anio, String mes) {

        Path projectPath = Paths.get("").toAbsolutePath();
        Path path = projectPath.resolve(relativePath);

        // Crear el directorio si no existe
        try {
            System.out.println("Creando directorio: " + path);
            Files.createDirectories(path);
        } catch (IOException e) {
            System.err.println("Error al crear el directorio: " + e.getMessage());
            return;
        }

        // Formatear la fecha y hora actual para el nombre del archivo
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);
        String fileName = path.resolve("B-" + formattedDateTime + ".txt").toString();

        // Escribir los datos en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("ID Cliente: " + idCliente);
            writer.newLine();
            writer.write("Año: " + anio);
            writer.newLine();
            writer.write("Mes: " + mes);
            writer.newLine();
            writer.write("Total gasto: " + totalGasto);
            writer.newLine();
            System.out.println("Contenido guardado en: " + fileName);
        } catch (IOException e) {
            System.err.println("Error al guardar datos en el archivo: " + e.getMessage());
        }
    }
}
