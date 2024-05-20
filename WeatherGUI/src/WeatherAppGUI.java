package src;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame {

    private JSONObject weatherData;

    public WeatherAppGUI() {
        // setup gui and title
        super("WeatherApp");
        // configure gui to end program once closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // set the size of gui(pixels)
        setSize(450, 800);
        // load our gui at center of the screen
        setLocationRelativeTo(null);
        // layout manager nul to manually position our components within the gui
        setLayout(null);
        //prevent any resize of gui
        setResizable(false);

        addGuiCompenents();
    }

    public void addGuiCompenents() {
        JTextField searchTextField = new JTextField();

        // set the location
        searchTextField.setBounds(15,15,351,45);
        // change the font
        searchTextField.setFont(new Font("Dialog", Font.PLAIN,24));

        add(searchTextField);


        // weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/sunny.jpg"));
        weatherConditionImage.setBounds(10,120,430,440);
        add(weatherConditionImage);

        //Temperature Text`
        JLabel tempratureText = new JLabel("100 C");
        tempratureText.setBounds(0,600,450,54);
        tempratureText.setFont(new Font("Dialog", Font.BOLD, 40));




        //center the text
        tempratureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(tempratureText);

        //weather condition description
        JLabel weatherConditionDesc = new JLabel("Sunny");
        weatherConditionDesc.setBounds(0,560,450,36);
        weatherConditionDesc.setFont(new Font("Dialog",Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        //humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,670,74,81);
        add(humidityImage);

        // humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(100, 675, 85, 60);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // windspeed image
        JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windspeedImage.setBounds(210, 680, 114, 66);
        add(windspeedImage);

        // windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(330, 690, 105, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        // readrress search buttom
        JButton searchButton = new JButton(loadImage("src/assets/images.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        searchButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                // validate input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update gui

                // update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                // depending on the condition, we will update the weather image that corresponds with the condition
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/sunny.jpg"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.jpg"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rainy.jpg"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.jpg"));
                        break;
                    case "Windy":
                        weatherConditionImage.setIcon(loadImage("src/assets/windy.jpg"));
                        break;
                    case "Dry":
                        weatherConditionImage.setIcon(loadImage("src/assets/dry.jpg"));
                        break;
                    case "None":
                        weatherConditionImage.setIcon(loadImage("src/assets/bruh.jpg"));
                }

                //update temperature text
                if(weatherCondition == "None") {
                    tempratureText.setText("This Place no Exist");
                    humidityText.setText("<html><b>Humidity</b> \n0.0%");
                    windspeedText.setText("<html><b>WindSpeed</b> \n 00km/h");
                }else {
                    double temperature = (double) weatherData.get("temperature");
                    tempratureText.setText(temperature + "C");

                    //update weather condition text
                    weatherConditionDesc.setText(weatherCondition);

                    //update humidity text
                    long humidity = (long) weatherData.get("humidity");
                    humidityText.setText("<html><b>Humidity</b> \n" + humidity + "%");

                    //update windspeed text
                    double windSpeed = (double) weatherData.get("windSpeed");
                    windspeedText.setText("<html><b>WindSpeed</b> \n" + windSpeed + "km/h");


                }
            }
        });
        add(searchButton);


    }


    private ImageIcon loadImage(String resourcePath){
        try{
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("nope");
        return null;
    }
}

