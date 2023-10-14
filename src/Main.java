import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String city = "Bahia Blanca, AR";

        try {
            String weatherData = WeatherApp.getWeatherData(city);
            String temperature = String.valueOf(WeatherApp.parseTemperature(weatherData));
            String description =WeatherApp.parseDescription(weatherData);

            System.out.println("Clima en " + city + ":");
            System.out.println("Temperatura: " + temperature + "°C");
            System.out.println("Descripción: " + description);
        } catch (IOException e) {
            System.out.println("Error al obtener los datos del clima: " + e.getMessage());
        }

        Game game = new Game();
        game.start();
    }
}

