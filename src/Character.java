import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class Character {
    private String name;
    private String race;
    private String nickname;
    private int velocity;
    private int dexterity;
    private int strength;
    private int level;
    private int armor;
    private int health;
    private int age;
    private  LocalDate birthDate;


    public static boolean isLetter(char charAt) {
        return charAt >= 65 && charAt <= 90;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public int getVelocity() {

        return velocity;
    }


    public int getDexterity() {

        return dexterity;
    }


    public int getStrength() {

        return strength;
    }

    public int getArmor() {

        return armor;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Character(String name, String race, String nickname, int velocity, int dexterity, int strength, int level, int armor, int health, int age, LocalDate birthDate) {
        this.name = name;
        this.race = race;
        this.nickname = nickname;
        this.velocity = velocity;
        this.dexterity = dexterity;
        this.strength = strength;
        this.level = level;
        this.armor = armor;
        this.health = health;
        this.age = age;
        this.birthDate = birthDate;
    }



    public int getLevel() {
        return level;
    }

    public String getRace() {
        return race;
    }

    public int getHealth() {
        return health;
    }

    public int calculateAttackPower() {
        return dexterity * strength * level;
    }

    public int calculateDefensePower() {
        return armor * velocity;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }
    public String getName(int level) {
        if (this.getRace().equalsIgnoreCase("human")) {
            return "Humano";
        } else if (this.getRace().equalsIgnoreCase("elfo")) {
            return "Elfo";
        } else {
            return "Orco";
        }

    }
    static List<Character> createPlayerCharacters(Scanner scanner, String player) {

        List<Character> characters = new ArrayList<>();

        System.out.println("Ingrese los datos del " + player + " Jugador:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Ingrese los datos del personaje " + (i + 1) + " del " + player + " jugador:");
            Character character = createManualCharacter(scanner, characters);
            characters.add(character);
        }

        return characters;
    }

    private static Character createManualCharacter(Scanner scanner, List<Character> characters) {
        Set<String> names = new HashSet<>();
        String name = enterName(scanner, names);
        Race race = enterRace(scanner);
        String nickname = enterNickname(scanner, race);
        int velocity = enterStat(scanner, "Velocidad entre 1 a 10", 1, 10);
        int dexterity = enterStat(scanner, "Destreza entre 1 a 10", 1, 10);
        int strength = enterStat(scanner, "Fuerza entre 1 a 5", 1, 5);
        int level = enterStat(scanner, "Nivel entre 1 a 10", 1, 10);
        int armor = enterStat(scanner, "Armadura entre 1 a 10", 1, 10);
        int health = enterStat(scanner, "Salud entre 0 a 300", 0, 300);

        LocalDate birthDate = AgeAndBirthDate.enterBirthDateManually(scanner, race);
        int age = AgeAndBirthDate.calculateAge(race.name(), birthDate);

        Character character = new Character(name, race.name(), nickname, velocity, dexterity, strength, level, armor, health, age, birthDate);
        characters.add(character);
        return character;
    }
    private static String enterName(Scanner scanner, Set<String> names) {
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
    private static Race enterRace(Scanner scanner) {
        Race race;
        while (true) {
            System.out.println("Elige una raza:");
            for (Race raceOption : Race.values()) {
                System.out.println(raceOption.ordinal() + ". " + raceOption.name());
            }
            String raceChoiceStr = scanner.nextLine();

            if (raceChoiceStr.matches("[0-2]")) {
                int raceChoice = Integer.parseInt(raceChoiceStr);
                race = Race.values()[raceChoice];
                break;
            } else {
                System.out.println("Error: Opción de raza inválida. Por favor, ingrese un número válido (0, 1 o 2).");
            }
        }
        return race;
    }

    private static String enterNickname(Scanner scanner, Race race) {
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
            String nicknameChoiceStr = scanner.nextLine();

            try {
                int nicknameChoice = Integer.parseInt(nicknameChoiceStr);
                if (nicknameChoice >= 0 && nicknameChoice < nicknames.length) {
                    nickname = nicknames[nicknameChoice];
                    break;
                } else {
                    System.out.println("Error: Opción de apodo inválida. Por favor, ingrese un número válido.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Opción de apodo inválida. Por favor, ingrese un número válido.");
            }
        }
        return nickname;
    }


    private static int enterStat(Scanner scanner, String statName, int minValue, int maxValue) {
        int stat;
        while (true) {
            System.out.print(statName + ": ");
            String statStr = scanner.nextLine();

            try {
                stat = Integer.parseInt(statStr);
                if (stat >= minValue && stat <= maxValue) {
                    break; // Salir del bucle si el valor es válido
                } else {
                    System.out.println("Error: Valor de " + statName + " inválido. Por favor, ingrese un número entre " + minValue + " y " + maxValue + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Valor de " + statName + " inválido. Por favor, ingrese un número válido.");
            }
        }
        return stat;
    }
    private static String getRandomRace(Random random) {
        Race[] races = Race.values();
        int randomIndex = random.nextInt(races.length);
        return races[randomIndex].name();
    }

    private static String getRandomNickname(Random random, Race race) {
        switch (race) {
            case HUMAN:
                NicknameHuman[] humanNicknames = NicknameHuman.values();
                int randomIndex = random.nextInt(humanNicknames.length);
                return humanNicknames[randomIndex].name();
            case ORCO:
                NicknameOrc[] orcNicknames = NicknameOrc.values();
                randomIndex = random.nextInt(orcNicknames.length);
                return orcNicknames[randomIndex].name();
            case ELFO:
                NicknameElf[] elfNicknames = NicknameElf.values();
                randomIndex = random.nextInt(elfNicknames.length);
                return elfNicknames[randomIndex].name();
            default:
                throw new IllegalArgumentException("Raza inválida");
        }
    }

    private static String getRandomName(Random random, Race race, Set<String> usedNames) {
        switch (race) {
            case HUMAN:
                NamesHuman[] humanNames = NamesHuman.values();
                int randomIndex = random.nextInt(humanNames.length);
                String name = humanNames[randomIndex].name();
                while (usedNames.contains(name)) {
                    randomIndex = random.nextInt(humanNames.length);
                    name = humanNames[randomIndex].name();
                }
                usedNames.add(name);
                return name;
            case ORCO:
                NamesOrc[] orcNames = NamesOrc.values();
                randomIndex = random.nextInt(orcNames.length);
                name = orcNames[randomIndex].name();
                while (usedNames.contains(name)) {
                    randomIndex = random.nextInt(orcNames.length);
                    name = orcNames[randomIndex].name();
                }
                usedNames.add(name);
                return name;
            case ELFO:
                NamesElf[] elfNames = NamesElf.values();
                randomIndex = random.nextInt(elfNames.length);
                name = elfNames[randomIndex].name();
                while (usedNames.contains(name)) {
                    randomIndex = random.nextInt(elfNames.length);
                    name = elfNames[randomIndex].name();
                }
                usedNames.add(name);
                return name;
            default:
                throw new IllegalArgumentException("Raza inválida");
        }
    }

    static List<Character> generateRandomCharacters() {
        List<Character> characters = new ArrayList<>();
        Random random = new Random();
        Set<String> usedNames = new HashSet<>();

        while (characters.size() < 7) {
            Race race = Race.valueOf(getRandomRace(random));
            String name = getRandomName(random, race, usedNames);
            String nickname = getRandomNickname(random, race);
            int velocity = getRandomNumber(1, 10);
            int dexterity = getRandomNumber(1, 5);
            int strength = getRandomNumber(1, 10);
            int level = getRandomNumber(1, 10);
            int armor = getRandomNumber(1, 10);
            int health = getRandomNumber(50, 100);
            AgeAndBirthDate ageAndBirthDate = AgeAndBirthDate.getRandomAge(random, String.valueOf(race));
            int age = ageAndBirthDate.getAge();
            LocalDate birthDate = ageAndBirthDate.getBirthDate();

            Character character = new Character(name, race.name(), nickname, velocity, dexterity, strength, level, armor, health, age, birthDate);
            characters.add(character);
        }

        return characters;
    }



    private static int getRandomNumber(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    static void printCharacterInfo(List<Character> player1Characters, List<Character> player2Characters) {
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
            System.out.println("Edad: " + character.getAge());
            System.out.println("Fecha de nacimiento: " + character.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            System.out.println("\u001B[33m-----------\u001B[0m");
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
            System.out.println("Edad: " + character.getAge());
            System.out.println("Fecha de nacimiento: " + character.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("\u001B[30m-----------\u001B[0m");
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

   public void setHealth(int i) {
        if (i >= 0) {
            health = i;
        } else {
            System.out.println("Error: La salud no puede ser un valor negativo.");
        }
    }
}





