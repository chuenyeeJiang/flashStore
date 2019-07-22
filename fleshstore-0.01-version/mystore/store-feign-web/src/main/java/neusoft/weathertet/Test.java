package neusoft.weathertet;

import java.util.Scanner;

public class Test {
    public static void main(String[] arg){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String cityname = scanner.nextLine();
            String weather = WeatherUntil.getWeather(cityname);
            System.out.println(weather);
        }
    }
}
