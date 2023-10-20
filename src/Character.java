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

    private static int enterStat(Scanner scanner, String statName, int minValue, int maxValue) {
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

    static List<Character> generateRandomCharacters() {
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
            AgeAndBirthDate ageAndBirthDate = AgeAndBirthDate.getRandomAge( random, race);
            int age = AgeAndBirthDate.getRandomAge(random, race).getAge();
            LocalDate birthDate = ageAndBirthDate.getBirthDate();


            Character character = new Character(name, race, nickname, velocity, dexterity, strength, level, armor, health, age,  birthDate);
            characters.add(character);

        }

        return characters;
    }

    private static String getRandomRace(Random random){
        Race[] races = Race.values();
        int randomIndex = random.nextInt(races.length);
        return races[randomIndex].name();
    }

    private static String getRandomNickname(Random random){
        NicknameElf[] nicknames = NicknameElf.values();
        int randomIndex = random.nextInt(nicknames.length);
        return nicknames[randomIndex].name();
    }

    private static String getRandomName(Random random){
        Names[] names = Names.values();
        int randomIndex = random.nextInt(names.length);
        return names[randomIndex].name();
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
            System.out.println("Edad: " + character.getAge());
            System.out.println("Fecha de nacimiento: " + character.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("-----------");
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





