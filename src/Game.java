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
            System.out.println("ORCO :\n" + ORCO_ASCII);
            System.out.println("ELFO :\n" + ELFO_ASCII);
            System.out.println("HUMANO :\n" + HUMANO_ASCII);
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
        System.out.println("------------------------------------------------------------");
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
        scanner.nextLine();
        System.out.println("------------------------------------------------------------");
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

        //elige raza

        int raceChoice;
        Race[] races = Race.values();
        String race = null;

        do {
            System.out.println("Elige una raza:");
            for (Race raceOption : races) {
                System.out.println(raceOption.ordinal() + ". " + raceOption.name());
            }
            raceChoice = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea

            if (raceChoice >= 0 && raceChoice < races.length) {
                race = races[raceChoice].name();
            } else {
                System.out.println("Error: Opción de raza inválida. Por favor, ingrese un número válido.");
            }
        } while (raceChoice < 0 || raceChoice >= races.length);

        int nicknameChoice;
        String[] nicknames = new String[0];

        if (race.equals("ELFO")) {
            NicknameElf[] nicknameElf = NicknameElf.values();
            nicknames = new String[nicknameElf.length];
            for (int i = 0; i < nicknameElf.length; i++) {
                nicknames[i] = nicknameElf[i].name();
            }
        } else if (race.equals("ORCO")) {
            NicknameOrc[] nicknameOrc = NicknameOrc.values();
            nicknames = new String[nicknameOrc.length];
            for (int i = 0; i < nicknameOrc.length; i++) {
                nicknames[i] = nicknameOrc[i].name();
            }
        } else if (race.equals("HUMAN")) {
            NicknameHuman[] nicknameHuman = NicknameHuman.values();
            nicknames = new String[nicknameHuman.length];
            for (int i = 0; i < nicknameHuman.length; i++) {
                nicknames[i] = nicknameHuman[i].name();
            }
        }

        String  nickname = null;

        do {
            System.out.println("Elige un apodo:");
            for (int i = 0; i < nicknames.length; i++) {
                System.out.println(i + ". " + nicknames[i]);
            }
            nicknameChoice = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea

            if (nicknameChoice >= 0 && nicknameChoice < nicknames.length) {
                nickname = nicknames[nicknameChoice];
            } else {
                System.out.println("Error: Opción de apodo inválida. Por favor, ingrese un número válido.");
            }
        } while (nicknameChoice < 0 || nicknameChoice >= nicknames.length);



        //elige velocidad
        int velocity = 0;

        while (true) {
            System.out.print("Velocidad: del 1 al 10: ");
            try {
                velocity = scanner.nextInt();
                if (velocity >= 1 && velocity <= 10) {
                    break; // Sale del bucle si la velocidad es válida
                } else {
                    System.out.println("Error: La velocidad debe estar entre 1 y 10.");
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Introduce un número del 1 al 10.");
                scanner.nextLine(); // Limpiar el búfer del escáner
            }
        }

        System.out.println(" La Velocidad es: " + velocity);

        //elige destreza

        System.out.print("Destreza: del 1 al 5: ");
        int dexterity = 0;

        while (true) {
            try {
                dexterity = scanner.nextInt();
                if (dexterity >= 1 && dexterity <= 5) {
                    break;// Sale del bucle si la destereza es válida
                } else {
                    System.out.println("Error: El valor de destreza debe estar entre 1 y 5.");
                    System.out.print("Ingresa nuevamente el valor de destreza: ");
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Ingresa un número del 1 al 5.");
                System.out.print("Ingresa nuevamente el valor de destreza: ");
                scanner.nextLine(); // Limpiar el búfer del escáner
            }
        }
        //elige fuerza
        System.out.print("Fuerza: del 1 al 10: ");
        int strength = 0;

        while (true) {
            try {
                strength = scanner.nextInt();
                if (strength >= 1 && strength <= 10) {
                    break;// Sale del bucle si la fuerza es válida
                } else {
                    System.out.println("Error: El valor de fuerza debe estar entre 1 y 10.");
                    System.out.print("Ingresa nuevamente el valor de fuerza: ");
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Ingresa un número del 1 al 10.");
                System.out.print("Ingresa nuevamente el valor de fuerza: ");
                scanner.nextLine(); // Limpiar el búfer del escáner
            }
        }

        //elige nivel

        System.out.print("Nivel: del 1 al 10: ");
        int level = 0;


        while (true) {
            try {
                level = scanner.nextInt();
                if (level >= 1 && level <= 10) {
                    break;// Sale del bucle si el nivel es válido
                } else {
                    System.out.println("Error: El valor de nivel debe estar entre 1 y 10.");
                    System.out.print("Ingresa nuevamente el valor de nivel: ");
                }
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Ingresa un número del 1 al 10.");
                System.out.print("Ingresa nuevamente el valor de nivel: ");
                scanner.nextLine(); // Limpiar el búfer del escáner
            }
        }
         //elige armadura

        System.out.print("Armadura: del 1 al 10: ");
        int armor = 0;

            while (true) {
                try {
                    armor = scanner.nextInt();
                    if (armor >= 1 && armor <= 10) {
                        break; //Sale del bucle si la armadura  es válida

                    } else {
                        System.out.println("Error: El valor de armadura debe estar entre 1 y 10.");
                        System.out.print("Ingresa nuevamente el valor de armadura: ");
                    }
                } catch (Exception e) {
                    System.out.println("Error: Entrada inválida. Ingresa un número del 1 al 10.");
                    System.out.print("Ingresa nuevamente el valor de armadura: ");
                    scanner.nextLine(); // Limpiar el búfer del escáner
                }
            }
        // elige salud

        System.out.print("Salud: del 50 al 100: ");
        int  health = 0;

            while (true) {
                try {
                    health = scanner.nextInt();
                    if (health >= 50 && health <= 100) {
                        break;//Sale del bucle si la salud  es válida
                    } else {
                        System.out.println("Error: El valor de salud debe estar entre 50 y 100.");
                        System.out.print("Ingresa nuevamente el valor de salud: ");
                    }
                } catch (Exception e) {
                    System.out.println("Error: Entrada inválida. Ingresa un número del 50 al 100.");
                    System.out.print("Ingresa nuevamente el valor de salud: ");
                    scanner.nextLine(); // Limpiar el búfer del escáner
                }
            }


        return new Character(name, race, nickname, velocity, dexterity, strength, level, armor, health);

    }

        private List<Character> generateRandomCharacters () {
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

        private String getRandomRace (Random random){
            Race[] races = Race.values();
            int randomIndex = random.nextInt(races.length);
            return races[randomIndex].name();
        }

        private String getRandomNickname (Random random){
            NicknameElf[] nicknames = NicknameElf.values();
            int randomIndex = random.nextInt(nicknames.length);
            return nicknames[randomIndex].name();
        }

        private String getRandomName (Random random){
            Names[] names = Names.values();
            int randomIndex = random.nextInt(names.length);
            return names[randomIndex].name();
        }


        private int getRandomNumber ( int min, int max){
            Random random = new Random();
            return random.nextInt(max - min + 1) + min;
        }
        private void printCharacterInfo (Character characters1, Character characters2){
            System.out.println("Información de los personajes:");
            System.out.println(" ");
            System.out.println("JUGADOR  1 ");
            printCharacterDetails(characters1);
            System.out.println("JUGADOR  2 ");
            printCharacterDetails(characters2);
        }

        // Método para imprimir los detalles de un personaje
        private void printCharacterDetails (Character characters){
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
        private void saveLogs (List < String > logs) {
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

        private void readLogs () {
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

        private void deleteLogsFile () {
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
