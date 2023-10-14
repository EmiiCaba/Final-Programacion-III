import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {

        private static final String API_KEY = "5734abe7d9ee7fc9fc31535dd865c05a";
        private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

        public static String getWeatherData(String city) throws IOException {
            String apiUrl = String.format(API_URL, city, API_KEY);
            StringBuilder response = new StringBuilder();

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        }


    /*public static String parseTemperature(String weatherData) {
        JSONObject jsonObject = new JSONObject(weatherData);
        JSONObject mainObject = jsonObject.getJSONObject("main");
        double temperature = mainObject.getDouble("temp");
        return String.valueOf(temperature);
        }*/
    public static double parseTemperature(String weatherData) {
        JSONObject jsonObject = new JSONObject(weatherData);
        JSONObject mainObject = jsonObject.getJSONObject("main");
        double temperatureKelvin = mainObject.getDouble("temp");
        double temperatureCelsius = temperatureKelvin - 273.15;
        return temperatureCelsius;
    }
    public static String parseDescription(String weatherData) {
        JSONObject jsonObject = new JSONObject(weatherData);
        JSONArray weatherArray = jsonObject.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        String description = weatherObject.getString("description");
        return description;
    }
}
