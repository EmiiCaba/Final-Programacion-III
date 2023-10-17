import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String city;
        boolean ciudadValida = false;

        do {
            System.out.print("\u001B[1m Por favor, ingresa la ciudad desde la que quieres jugar. La ciudad no puede ser ficticia: ");
            city = scanner.nextLine();

            try {
                String weatherData = WeatherApp.getWeatherData(city);
                double temperature = WeatherApp.parseTemperature(weatherData);
                String formattedTemperature = String.format("%.2f", temperature);
                String description = WeatherApp.parseDescription(weatherData);

                System.out.println("\u001B[36m \u001B[1m Clima en " + city + ":");
                System.out.println(" La temperatura actual es: " + formattedTemperature + "°C");
                System.out.println(" El cielo está: " + description);

                Game game = new Game();
                game.start();
                ciudadValida = true; // La ciudad esta correcta.
            } catch (IOException e) {
                System.out.println("Error al obtener los datos del clima: " + e.getMessage());
                System.out.println("Por favor, ingresa una ciudad válida.");
            }
        } while (!ciudadValida || city.isEmpty() || city.isBlank());
    }


}

