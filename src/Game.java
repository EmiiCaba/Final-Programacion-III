import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class Game {
        private List<String> logs;
        private FileWriter fileWriter;

        public Game() {
            logs = new ArrayList<>();
        }
    private static final String ORCO_ASCII =
            "    ,      ,   \n" +
                    "   / \\  . / \\  \n" +
                    "  /   \\/   \\\n" +
                    " |  o   :  o  |\n" +
                    " |  .   :   .  |\n" +
                    "  \\   /\\   /\n" +
                    "   \\_/  `-'";

    private static final String ELFO_ASCII =
            "     |    |  \n" +
                    "    /     \\   \n" +
                    "   /_______\\ \n" +
                    "   |  o o  |  \n" +
                    "   |   ^   |  \n" +
                    "   |   -   |  \n" +
                    "    \\_____/  ";

    private static final String HUMANO_ASCII =
            "    O\n" +
                    "   /|\\  \n" +
                    "   / \\  ";
        public void start() {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("----- Menú del Juego -----");
                System.out.println("Les presento los JUGADORES:");
                System.out.println("Orco:\n" + ORCO_ASCII);
                System.out.println("Elfo:\n" + ELFO_ASCII);
                System.out.println("Humano:\n" + HUMANO_ASCII);
                System.out.println("1. Iniciar partida con personajes aleatorios");
                System.out.println("2. Iniciar partida con personajes ingresados manualmente");
                System.out.println("3. Leer logs de partidas jugadas");
                System.out.println("4. Borrar archivo de logs");
                System.out.println("5. Salir");
                System.out.print("Ingrese una opción: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (option) {
                    case 1:
                        startRandomGame();
                        break;
                    case 2:
                        startManualGame();
                        break;
                    case 3:
                        readLogs();
                        break;
                    case 4:
                        deleteLogsFile();
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                        break;
                }
            }
        }

        private void startRandomGame() {
            List<Character> characters = generateRandomCharacters();
            Character player1Character = characters.get(0);
            Character player2Character = characters.get(1);

            // Imprime información de los personajes antes de la batalla
            printCharacterInfo(player1Character, player2Character);

            Battle battle = new Battle(player1Character, player2Character);
            battle.start();

            saveLogs(battle.getBattleLogs());
        }


        private void startManualGame() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Ingrese los datos del primer personaje:");
            Character player1Character = createCharacter(scanner);

            System.out.println("Ingrese los datos del segundo personaje:");
            Character player2Character = createCharacter(scanner);

            // Imprime información de los personajes antes de la batalla
            printCharacterInfo(player1Character, player2Character);

            Battle battle = new Battle(player1Character, player2Character);
            battle.start();

            saveLogs(battle.getBattleLogs());
        }

        private Character createCharacter(Scanner scanner) {
            System.out.print("Nombre: ");
            String name = scanner.nextLine();

            System.out.println("Elige una raza:");
            for (Race raceOption : Race.values()) {
                System.out.println(raceOption.ordinal() + ". " + raceOption.name());
            }
            int raceChoice = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea

            Race[] races = Race.values();
            String race = races[raceChoice].name();


            System.out.println("Elige un apodo:");
            for (Nickname nicknameOption : Nickname.values()) {
                System.out.println(nicknameOption.ordinal() + ". " + nicknameOption.name());
            }
            int nicknameChoice = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea


            Nickname[] nicknames = Nickname.values();
            String nickname = nicknames[nicknameChoice].name();

            System.out.print("Velocidad: del 1 al 10 :");
            int velocity = scanner.nextInt();

            System.out.print("Destreza: del 1 al 5 :");
            int dexterity = scanner.nextInt();

            System.out.print("Fuerza:  del 1 al 10 :");
            int strength = scanner.nextInt();

            System.out.print("Nivel: del 1 al 10  :");
            int level = scanner.nextInt();

            System.out.print("Armadura: del 1 al 10  :");
            int armor = scanner.nextInt();

            System.out.print("Salud: del 50 al 100  :");
            int health = scanner.nextInt();

            return new Character(name, race, nickname, velocity, dexterity, strength, level, armor, health);
        }


       private List<Character> generateRandomCharacters() {
           List<Character> characters = new ArrayList<>();
           Random random = new Random();

           for (int i = 0; i < 2; i++) {
               String name = getRandomName(random);
               String race = getRandomRace(random);
               String nickname = getRandomNickname(random);
               int velocity = getRandomNumber(1, 10);
               int dexterity = getRandomNumber(1, 5);
               int strength = getRandomNumber(1, 10);
               int level = getRandomNumber(1, 10);
               int armor = getRandomNumber(1, 10);
               int health = getRandomNumber(50, 100);

               Character character = new Character(name, race, nickname, velocity, dexterity, strength, level, armor, health);
               characters.add(character);
           }

           return characters;
       }

    private String getRandomRace(Random random) {
        Race[] races = Race.values();
        int randomIndex = random.nextInt(races.length);
        return races[randomIndex].name();
    }

    private String getRandomNickname(Random random) {
        Nickname[] nicknames = Nickname.values();
        int randomIndex = random.nextInt(nicknames.length);
        return nicknames[randomIndex].name();
    }

    private String getRandomName(Random random) {
        Names [] names = Names.values();
        int randomIndex = random.nextInt(names.length);
        return names[randomIndex].name();
    }


        private int getRandomNumber(int min, int max) {
            Random random = new Random();
            return random.nextInt(max - min + 1) + min;
        }
    private void printCharacterInfo(Character characters1, Character characters2) {
        System.out.println("Información de los personajes:");
        System.out.println("JUGADOR  1 ");
        printCharacterDetails(characters1);
        System.out.println("JUGADOR  2 ");
        printCharacterDetails(characters2);
    }

    // Método para imprimir los detalles de un personaje
    private void printCharacterDetails(Character characters) {
        System.out.println("Nombre: " + characters.getName());
        System.out.println("Raza: " + characters.getRace());
        System.out.println("Apodo: " + characters.getNickname());
        System.out.println("Velocidad: " + characters.getVelocity());
        System.out.println("Destreza: " + characters.getDexterity());
        System.out.println("Fuerza: " + characters.getStrength());
        System.out.println("Nivel: " + characters.getLevel());
        System.out.println("Armadura: " + characters.getArmor());
        System.out.println("Salud: " + characters.getHealth());
        System.out.println();
    }
        private void saveLogs(List<String> logs) {
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

        private void readLogs() {
            try {
                FileReader fileReader = new FileReader("logs.txt");
                Scanner scanner = new Scanner(fileReader);

                while (scanner.hasNextLine()) {
                    String log = scanner.nextLine();
                    System.out.println(log);
                }

                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error al leer los logs: " + e.getMessage());
            }
        }

        private void deleteLogsFile() {
            try {
                fileWriter = new FileWriter("logs.txt");
                fileWriter.write("");
                fileWriter.close();
                System.out.println("Archivo de logs borrado exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al borrar el archivo de logs: " + e.getMessage());
            }
        }

    }
