package org.example.proyecto.Utilities;
import org.example.proyecto.Tables.Tarjeta;
import org.example.proyecto.Tables.Transaccion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaveTXT {
    private static final String relativePath = "Reportes"; //00009123 Definimos el nombre de la carpeta
    public static void SaveAReport(String idCliente, LocalDate inicioDate, LocalDate finDate, List<Transaccion> transacciones) { //00009123 Funcion para guardar datos del reporte A
        Path projectPath = Paths.get("").toAbsolutePath(); //00009123 Obtenemos el actual path
        Path path = projectPath.resolve(relativePath);//00009123 Le agregamos la carpeta a la direccion actual
        try {//00009123 Intentamos crear la carpeta
            Files.createDirectories(path);//00009123 Sino existe creamos la carpeta
        } catch (IOException e) { //00009123 En caso de error mostramos el mensaje de que no se pudo crear directorio
            System.err.println("Error al crear el directorio: " + e.getMessage());
            return;
        }
        LocalDateTime now = LocalDateTime.now();//00009123 Obtenemos la fecha actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");//00009123 Creamos el formateo para poder formatear las horas para convertir a string
        String formattedDateTime = now.format(formatter);//00009123 Formateamos la fecha actual
        String fileName = path.resolve("Reporte-A-" + formattedDateTime + ".txt").toString();//00009123 Definimos el nombre del archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {//00009123 Intentamos escribir datos en el archivo
            writer.write("Consulta realizada por cliente con ID: " + idCliente); //00009123 Escribimos quien hizo la consulta
            writer.newLine();
            writer.write("Fecha de inicio: " + inicioDate);//00009123 Escribimos la fecha de inicio
            writer.newLine();
            writer.write("Fecha de fin: " + finDate);//00009123 Escribimos la fecha final
            writer.newLine();
            writer.write("Resultados de la consulta:");//00009123 Escribimos un mensaje para todos los resultados
            writer.newLine();
            writer.write("-----------------------------------------");
            writer.newLine();
            writer.write("Id_transaccion\tId_cliente\tFechaCompra\tMonto\tDescripcion");//00009123 Mostramos los campos
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//00009123 Creamos un DateTimeFormatter para formatear la fecha de la transaccion
            writer.newLine();
            for (Transaccion transaccion : transacciones) {//00009123 Recorremos todas las transacciones
                writer.write(transaccion.getIdTransaccion()+"\t\t");//00009123 Escribimos el id de la transaccion
                writer.write(transaccion.getIdCliente()+"\t\t");//00009123 Escribimos el id cliente
                writer.write(transaccion.getFechaCompra().toLocalDate().format(dateFormatter)+"\t");//00009123 Debemos convertir la fecha a un string
                writer.write(Double.toString(transaccion.getTotalMonto())+"\t");//00009123 Escribimos el total
                writer.write(transaccion.getDescripcion());//00009123 Colocamos la descripcion
                writer.newLine();
            }
            System.out.println("Consulta guardada en: " + fileName);//00009123 Imprimimos el archivo creado
        } catch (IOException e) {
            System.err.println("Error al guardar datos en el archivo: " + e.getMessage());//00009123 Imprimimos el error en caso de que no se pudo guardar
        }
    }
    public static void SaveBReport(String totalGasto, int idCliente, String anio, String mes) {//00009123 Funcion para guardar en el reporte B
        Path projectPath = Paths.get("").toAbsolutePath(); //00009123 Obtenemos el path actual
        Path path = projectPath.resolve(relativePath);
        try {//00009123 Intentamos crear el folder
            Files.createDirectories(path);
        } catch (IOException e) {//00009123 Terminamos la funcion en caso de que haya un problema
            return;//00009123 Retornamos
        }
        LocalDateTime now = LocalDateTime.now(); //00009123 Formatear la fecha y hora actual para el nombre del archivo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");//00009123 Creamos un formatter para la fecha
        String formattedDateTime = now.format(formatter); //00009123 Asignamos el formateador
        String fileName = path.resolve("Reporte-B-" + formattedDateTime + ".txt").toString();//00009123 Le colocamos la direccion para que sea de tipo B
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {//00009123 Intentamos crear el archivo
            writer.write("ID Cliente: " + idCliente);//00009123 Colocamos el id del cliente
            writer.newLine();
            writer.write("Año: " + anio);//00009123 Colocamos el anio
            writer.newLine();
            writer.write("Mes: " + mes);//00009123 Colocamos el mes
            writer.newLine();
            writer.write("Total gasto: " + totalGasto);//00009123 Guardamos el total del gasto
            writer.newLine();
            System.out.println("Contenido guardado en: " + fileName);//00009123 Imprimimos mensaje de depuracion
        } catch (IOException e) {
            System.err.println("Error al guardar datos en el archivo: " + e.getMessage()); //00009123 Imprimimos en caso de que haya un problema
        }
    }

    public static void SaveCReport(String idCliente, List<Tarjeta> tarjetas) { // 00018523 Método que imprimirá los datos de la consulta para el Reporte C
        Path projectPath = Paths.get("").toAbsolutePath(); // 00009123 Se obtiene el path actual
        Path path = projectPath.resolve(relativePath); //00009123 Se le agrega la carpeta a la dirección actual

        try { // 00009123 Se intenta crear la carpeta
            Files.createDirectories(path); // 00009123 Si no existe la carpeta, la creamos
        } catch (IOException e) { // 00009123 Agarra un error si ha fallado la creación de la carpeta
            System.err.println("Error al crear el directorio: " + e.getMessage()); // 00009123 Muestra un error en la consola
            return; // 00009123 El método deja de ejecutarse
        }

        LocalDateTime now = LocalDateTime.now(); // 00009123 Se obtiene la fecha actual

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"); // 00009123 Creamos un formatter para poder formatear las horas y pasarlas a String

        String formattedDateTime = now.format(formatter); // 00009123 Formateamos la fecha actual

        String fileName = path.resolve("Reporte-C-" + formattedDateTime + ".txt").toString(); // 00009123 Definimos el nombre del archivo

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { // 00009123 Intentamos escribir datos en el archivo
            writer.write("Consulta realizada por cliente con ID: " + idCliente); // 00009123 Escribimos quien hizo la consulta
            writer.newLine(); // 00009123 Pasa a la siguiente línea
            writer.write("Resultados de la consulta:"); // 00009123 Escribe en el archivo
            writer.newLine(); // 00009123 Pasa a la siguiente línea
            writer.write("-----------------------------------------"); // 00009123 Escribe en el archivo
            writer.newLine(); // 00009123 Pasa a la siguiente línea

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //00009123 Creamos un DateTimeFormatter para formatear la fecha de la transacción
            writer.newLine(); // 00009123 Pasa a la siguiente línea

            List<String> creditCards = new ArrayList<>(); // 00018523 Se crea una lista para el guardado de las tarjetas de crédito
            List<String> debitCards = new ArrayList<>(); // 00018523 Se crea una lista para el guardado de las tarjetas de débito

            for (Tarjeta tarjeta : tarjetas) { // 00018523 Recorremos todas las tarjetas
                String numTarjeta = tarjeta.getNumTarjeta(); // 00018523 Guarda el número de la tarjeta en una variable;
                char[] censoredCard = numTarjeta.toCharArray(); // 00018523 Crea una lista de caracteres a partir del número de la tarjeta
                String numMostrado = ""; // 00018523 Inicializa una variable que será la que censura los primeros 12 dígitos de la tarjeta

                if (!numTarjeta.isEmpty()) { // 00018523 Verifica que el string de la tarjeta no este vacío
                    numMostrado = "XXXX XXXX XXXX " + censoredCard[12] + censoredCard[13] + censoredCard[14] + censoredCard[15]; // 00018523 Guarda en numMostrado la tarjeta con la censura aplicada
                }

                if (tarjeta.getTipoTarjeta().equals("C")) { // 00018523 Si el tipo de tarjeta es C (Crédito) se ejecuta lo siguiente
                    creditCards.add(numMostrado); // 00018523 Agrega la tarjeta a la lista de tarjetas de Crédito
                } else if (tarjeta.getTipoTarjeta().equals("D")) { // 00018523 Si el tipo de tarjeta es D (Débito) se ejecuta lo siguiente
                    debitCards.add(numMostrado); // 00018523 Agrega la tarjeta a la lista de tarjetas de Débito
                }
            }

            writer.write("Tarjetas de Crédito:"); // 00018523 Escribe en el archivo
            writer.newLine(); // 00018523 Pasa a la siguiente línea
            if (!creditCards.isEmpty()) { // 00018523 Verifica que la lista de tarjetas de crédito no este vacía
                for (String card : creditCards) { // 00018523 Recorre todos los elementos de la lista de tarjetas de crédito
                    writer.write("\t" + card); // 00018523 Escribe en el archivo la tarjeta con la censura aplicada
                    writer.newLine(); // 00018523 Pasa a la siguiente línea
                }
            } else { // 00018523 Si la lista está vacía, ejecuta lo siguiente
                writer.write("\tN/A"); // 00018523 Escribe en el archivo "N/A" (No hay tarjetas)
                writer.newLine(); // 00018523 Pasa a la siguiente línea
            }

            writer.newLine(); // 00018523 Pasa a la siguiente línea


            writer.write("Tarjetas de Débito:"); // 00018523 Escribe en el archivo
            writer.newLine(); // 00018523 Pasa a la siguiente línea
            if (!debitCards.isEmpty()) { // 00018523 Verifica que la lista de tarjetas de débito no este vacía
                for (String card : debitCards) { // 00018523 Recorre todos los elementos de la lista de tarjetas de débito
                    writer.write("\t" + card); // 00018523 Escribe en el archivo la tarjeta con la censura aplicada
                    writer.newLine(); // 00018523 Pasa a la siguiente línea
                }
            } else { // 00018523 Si la lista está vacía, ejecuta lo siguiente
                writer.write("\tN/A"); // 00018523 Escribe en el archivo "N/A" (No hay tarjetas)
                writer.newLine(); // 00018523 Pasa a la siguiente línea
            }
            writer.newLine(); // 00018523 Pasa a la siguiente línea

            System.out.println("Consulta guardada en: " + fileName); //00009123 Se imprime en la consola que se creo con éxito el archivo
        } catch (IOException e) { // 00009123 Agarra un error si ha fallado la creación de la carpeta
            System.err.println("Error al guardar datos en el archivo: " + e.getMessage()); // 00009123 Imprimimos el error en caso de que no se pudo guardar
        }
    }
}
