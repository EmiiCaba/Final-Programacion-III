import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class AgeAndBirthDate {
    private int age;
    private LocalDate birthDate;

    public AgeAndBirthDate(int age, String birthDateString) {
        this.age = age;
        this.birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public int getAge() {
        return age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    //Calcular edad Random
    public static AgeAndBirthDate getRandomAge(Random random, String race) {
        int minAge = 0;
        int maxAge = 0;

        if (race.equalsIgnoreCase("ORCO")) {
            minAge = 20;
            maxAge = 80;
        } else if (race.equalsIgnoreCase("HUMAN")) {
            minAge = 15;
            maxAge = 95;
        } else if (race.equalsIgnoreCase("ELFO")) {
            minAge = 100;
            maxAge = 250;
        }

        // Asegurarse de que la edad generada esté dentro del rango específico
        int randomAge = getRandomNumber(random, minAge, maxAge);

        // Ajustar la edad para asegurarse de que esté en el rango de 01/01/0001 al 31/12/1000
        randomAge = Math.max(randomAge, 1);
        randomAge = Math.min(randomAge, 1000);


        // Generar un año de nacimiento aleatorio dentro del rango
        int birthYear = random.nextInt(1000) + 1; // Año aleatorio entre 1 y 1000

        // Generar un día y mes aleatorio
        int birthMonth = random.nextInt(12) + 1; // Mes aleatorio entre 1 y 12
        int maxDay = YearMonth.of(birthYear, birthMonth).lengthOfMonth(); // Obtener el número máximo de días para el mes y año dados
        int birthDay = random.nextInt(maxDay) + 1; // Día aleatorio entre 1 y el número máximo de días

        // Crear la fecha de nacimiento
        String birthDateString = String.format("%02d/%02d/%04d", birthDay, birthMonth, birthYear);
        AgeAndBirthDate ageAndBirthDate = new AgeAndBirthDate(randomAge, birthDateString);

        return ageAndBirthDate;
    }

    private static int getRandomNumber(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    //Calcular edad Manual

    static LocalDate enterBirthDateManually(Scanner scanner, Race race) {
        int minAge = 15;
        int maxAge = 85;

        if (race == Race.ELFO) {
            minAge = 100;
            maxAge = 250; // Límite máximo de edad para Elfos
        } else if (race == Race.ORCO) {
            minAge = 20;
            maxAge = 80; // Límite máximo de edad para Orcos
        } else if (race == Race.HUMAN) {
            minAge = 15;
            maxAge = 95; // Límite máximo de edad para Humanos
        }

        while (true) {
            System.out.print("Ingresa la edad (" + minAge + "-" + maxAge + "): ");
            String ageStr = scanner.nextLine();

            try {
                int age = Integer.parseInt(ageStr);
                if (age >= minAge && age <= maxAge) {
                    int birthYear = generateRandomBirthYear();
                    int birthMonth = generateRandomBirthMonth();
                    int birthDay = generateRandomBirthDay(birthYear, birthMonth);

                    // Crear la fecha de nacimiento
                    LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
                    System.out.println("Fecha de nacimiento: " + birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    return birthDate;
                } else {
                    System.out.println("Por favor, ingresa una edad válida entre " + minAge + " y " + maxAge + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa una edad válida.");
            }
        }
    }

    static int generateRandomBirthYear() {
        Random random = new Random();
        int minBirthYear = 1;
        int maxBirthYear = 1000;
        return random.nextInt(maxBirthYear - minBirthYear + 1) + minBirthYear;
    }
    static int generateRandomBirthMonth() {
        Random random = new Random();
        return random.nextInt(12) + 1; // Mes aleatorio entre 1 y 12
    }
    static int generateRandomBirthDay(int birthYear, int birthMonth) {
        Random random = new Random();
        int maxDay = YearMonth.of(birthYear, birthMonth).lengthOfMonth();
        return random.nextInt(maxDay) + 1; // Día aleatorio entre 1 y el número máximo de días
    }
    static int calculateAge(String race, LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        if (race.equalsIgnoreCase("ELFO")) {
            return Math.min(age, 200); // Máximo de 200 años para elfos
        } else if (race.equalsIgnoreCase("HUMANO")) {
            return Math.min(age, 85); // Máximo de 85 años para humanos
        } else if (race.equalsIgnoreCase("ORCO")) {
            return Math.min(age, 95); // Máximo de 95 años para orcos
        }

        return 0;
    }

}
