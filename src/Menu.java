import java.util.Scanner;

public class Menu {
        public static void showMenu() {
        System.out.println("\u001B[31m----- JUEGO DE TRONOS  -----\u001B[0m");
        System.out.println("----------------------------");
        System.out.println("\u001B[32m----- Menú del Juego -----\u001B[0m");
        System.out.println("Les presento los JUGADORES:");
        System.out.println("ORCO :\n" + DibujoAscii.dibujoOrco());
        System.out.println("ELFO :\n" + DibujoAscii.dibujoElfo());
        System.out.println("HUMANO :\n" + DibujoAscii.dibujoHumano());
        System.out.println("1.\u001B[33m Iniciar partida con personajes aleatorios\u001B[0m");
        System.out.println("2.\u001B[36m Iniciar partida con personajes ingresados manualmente\u001B[0m");
        System.out.println("3. Leer logs de partidas jugadas");
        System.out.println("4. Borrar archivo de logs");
        System.out.println("5.\u001B[31m Salir\u001B[0m");
        System.out.println("\u001B[36m----------------------------\u001B[0m");
    }

        public static int getUserChoice(Scanner scanner) {
            System.out.print("Ingrese una opción: ");
            return scanner.nextInt();
        }

        public static void showInvalidOptionMessage() {
            System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
        }

        public static void showGoodbyeMessage() {
            System.out.println("\u001B[1m \u001B[32m Te esperamos pronto a jugar de nuevo");
        }
    }


