import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    private FileWriter fileWriter;
    DibujoAscii dibujoAscii = new DibujoAscii();
    Set<String> names = new HashSet<>();
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            System.out.println("\u001B[31m----- JUEGO DE TRONOS  -----\u001B[0m");
            System.out.println("----------------------------");
            System.out.println("\u001B[32m----- Menú del Juego -----\u001B[0m");
            System.out.println("Les presento los JUGADORES:");
            System.out.println("ORCO :\n" + dibujoAscii.dibujoOrco());
            System.out.println("ELFO :\n" + dibujoAscii.dibujoElfo( ));
            System.out.println("HUMANO :\n" + dibujoAscii.dibujoHumano());
            System.out.println("1.\u001B[33m Iniciar partida con personajes aleatorios\u001B[0m");
            System.out.println("2.\u001B[36m Iniciar partida con personajes ingresados manualmente\u001B[0m");
            System.out.println("3. Leer logs de partidas jugadas");
            System.out.println("4. Borrar archivo de logs");
            System.out.println("5.\u001B[31m Salir\u001B[0m");
            System.out.println("\u001B[36m----------------------------\u001B[0m");
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
                    System.out.println("\u001B[1m \u001B[32m Te esperamos pronto a jugar de nuevo");
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

        if (characters.size() < 7) {
            System.out.println("No hay suficientes personajes para iniciar el juego.");
            return;
        }

        List<Character> player1Characters = new ArrayList<>(characters.subList(0, 3));
        List<Character> player2Characters = new ArrayList<>(characters.subList(3, 6));



        // Imprime información de los personajes antes de la batalla
        printCharacterInfo( player1Characters,  player2Characters);

        Battle battle = new Battle(player1Characters, player2Characters,characters);
        battle.start();

        saveLogs(battle.getBattleLogs());
    }



    private void startManualGame() {
        Scanner scanner = new Scanner(System.in);
        int characterCount = 0;
        List<Character> player1Characters = createPlayerCharacters(scanner, "Primer");

        characterCount += player1Characters.size();

        if (characterCount < 3) {
            System.out.println("No hay suficientes personajes para el jugador 1.");
            return;
        }

        List<Character> player2Characters = createPlayerCharacters(scanner, "Segundo");

        characterCount += player2Characters.size();

        if (characterCount < 3) {
            System.out.println("No hay suficientes personajes para el jugador 2.");
            return;
        }

        //printCharacterInfo(player1Characters, player2Characters);

        List<Character> allCharacters = new ArrayList<>();
        allCharacters.addAll(player1Characters);
        allCharacters.addAll(player2Characters);

        Battle battle = new Battle(player1Characters, player2Characters, allCharacters);
        battle.start();

        saveLogs(battle.getBattleLogs());
    }
    private List<Character> createPlayerCharacters(Scanner scanner, String player) {

        List<Character> characters = new ArrayList<>();

        System.out.println("Ingrese los datos del " + player + " Jugador:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Ingrese los datos del personaje " + (i + 1) + " del " + player + " jugador:");
            Character character = createManualCharacter(scanner, characters);
            characters.add(character);
        }

        return characters;
    }

    private Character createManualCharacter(Scanner scanner, List<Character> characters) {
        String name = enterName(scanner,names);
        Race race = enterRace(scanner);
        String nickname = enterNickname(scanner, race);
        int velocity = enterStat(scanner, "Velocidad entre 1 a 10", 1, 10);
        int dexterity = enterStat(scanner, "Destreza entre 1 a 20", 1, 20);
        int strength = enterStat(scanner, "Fuerza entre 1 a 300", 1, 30);
        int level = enterStat(scanner, "Nivel entre 1 a 10", 1, 10);
        int armor = enterStat(scanner, "Armadura entre 1 a 100", 1, 100);
        int health = enterStat(scanner, "Salud entre 50 a 100", 50, 100);

        Character character =  new Character(name, race.name(), nickname, velocity, dexterity, strength, level, armor, health);
        characters.add(character);
        return character;

    }

    private String enterName(Scanner scanner, Set<String> names) {
        String name;
        while (true) {
            System.out.print("Nombre: ");
            name = scanner.nextLine();

            if (name.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Por favor, ingresa un nombre válido.");
            } else if (!Character.isLetter(name.charAt(0))) {
                System.out.println("El nombre debe comenzar con una letra mayúscula.");
            } else if (names.contains(name)) {
                System.out.println("El nombre ya ha sido ingresado. Por favor, ingresa un nombre diferente.");
            } else {
                names.add(name); // Agregar el nombre al conjunto
                break; // Salir del bucle si el nombre es válido
            }
        }
        return name;
    }

    private Race enterRace(Scanner scanner) {
        Race race;
        while (true) {
            System.out.println("Elige una raza:");
            for (Race raceOption : Race.values()) {
                System.out.println(raceOption.ordinal() + ". " + raceOption.name());
            }
            int raceChoice = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea

            if (raceChoice >= 0 && raceChoice < Race.values().length) {
                race = Race.values()[raceChoice];
                break;
            } else {
                System.out.println("Error: Opción de raza inválida. Por favor, ingrese un número válido.");
            }
        }
        return race;
    }

    private String enterNickname(Scanner scanner, Race race) {
        String[] nicknames;

        switch (race) {
            case ELFO:
                nicknames = Arrays.stream(NicknameElf.values())
                        .map(Enum::name)
                        .toArray(String[]::new);
                break;
            case ORCO:
                nicknames = Arrays.stream(NicknameOrc.values())
                        .map(Enum::name)
                        .toArray(String[]::new);
                break;
            case HUMAN:
            default:
                nicknames = Arrays.stream(NicknameHuman.values())
                        .map(Enum::name)
                        .toArray(String[]::new);
                break;
        }

        String nickname;
        while (true) {
            System.out.println("Elige un apodo:");
            for (int i = 0; i < nicknames.length; i++) {
                System.out.println(i + ". " + nicknames[i]);
            }
            int nicknameChoice = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea

            if (nicknameChoice >= 0 && nicknameChoice < nicknames.length) {
                nickname = nicknames[nicknameChoice];
                break;
            } else {
                System.out.println("Error: Opción de apodo inválida. Por favor, ingrese un número válido.");
            }
        }
        return nickname;
    }

    private int enterStat(Scanner scanner, String statName, int minValue, int maxValue) {
        int stat;
        while (true) {
            System.out.print(statName + ": ");
            stat = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea

            if (stat >= minValue && stat <= maxValue) {
                break; // Salir del bucle si el valor es válido
            } else {
                System.out.println("Error: Valor de " + statName + " inválido. Por favor, ingrese un número entre " + minValue + " y " + maxValue + ".");
            }
        }
        return stat;
    }

    private List<Character> generateRandomCharacters () {
            List<Character> characters = new ArrayList<>();
            Random random = new Random();

           while (characters.size()<7){
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


        private void printCharacterInfo(List<Character> player1Characters, List<Character> player2Characters) {
            System.out.println("Información de los personajes del Jugador 1:");
            for (Character character : player1Characters) {
                System.out.println("Nombre: " + character.getName());
                System.out.println("Raza: " + character.getRace());
                System.out.println("Apodo: " + character.getNickname());
                System.out.println("Velocidad: " + character.getVelocity());
                System.out.println("Destreza: " + character.getDexterity());
                System.out.println("Fuerza: " + character.getStrength());
                System.out.println("Nivel: " + character.getLevel());
                System.out.println("Armadura: " + character.getArmor());
                System.out.println("Salud: " + character.getHealth());
                System.out.println("-----------");
            }

            System.out.println("Información de los personajes del Jugador 2:");
            for (Character character : player2Characters) {
                System.out.println("Nombre: " + character.getName());
                System.out.println("Raza: " + character.getRace());
                System.out.println("Apodo: " + character.getNickname());
                System.out.println("Velocidad: " + character.getVelocity());
                System.out.println("Destreza: " + character.getDexterity());
                System.out.println("Fuerza: " + character.getStrength());
                System.out.println("Nivel: " + character.getLevel());
                System.out.println("Armadura: " + character.getArmor());
                System.out.println("Salud: " + character.getHealth());
                System.out.println("-----------");
            }
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
                System.out.println("MUESTRA ARCHIVO DE LOGS.");
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error al leer los logs: " + e.getMessage());
            }
        }

        private void deleteLogsFile () {
            try {
                fileWriter.close();
                fileWriter = new FileWriter("logs.txt");
                fileWriter.write("");
                System.out.println("Archivo de logs borrado exitosamente.");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
            } catch (IOException e) {
                System.out.println("Error al borrar el archivo de logs: " + e.getMessage());
            }
        }


}
