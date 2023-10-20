import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Logs {

    private static FileWriter fileWriter;
    static void saveLogs(List<String> logs) {
    try {
        fileWriter = new FileWriter("logs.txt", true);

        for (String log : logs) {
            fileWriter.write(log + "\n");

        }

       fileWriter.close();
    } catch (IOException e) {
        System.out.println("Error al guardar los logs: " + e.getMessage());
    }

}

    static void readLogs() {
        try {
            System.out.println("MUESTRA ARCHIVO DE LOGS.");

            FileReader fileReader = new FileReader("logs.txt");
            Scanner scanner = new Scanner(fileReader);

            if (!scanner.hasNextLine()) {
                System.out.println("El archivo de logs está vacío.");

            } else {


                while (scanner.hasNextLine()) {

                    String log = scanner.nextLine();
                    System.out.println(log);

                }

            }

            fileReader.close();
        } catch (IOException e) {
            System.out.println("Error al leer los logs: " + e.getMessage());

        }

    }


    static void deleteLogsFile() {
        Scanner scanner = new Scanner(System.in);
        try {
            File logsFile = new File("logs.txt");
            if (logsFile.exists() && logsFile.length() > 0) {
                if (fileWriter != null) {
                    fileWriter.close();
                }
                fileWriter = new FileWriter(logsFile);
                fileWriter.write("");
                System.out.println("Archivo de logs borrado exitosamente.");
            } else {
                System.out.println("No se puede borrar el archivo de logs porque está vacío.");
            }
        } catch (IOException e) {
            System.out.println("Error al borrar el archivo de logs: " + e.getMessage());
        }
    }




}
