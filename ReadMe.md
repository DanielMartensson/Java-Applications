# Java Applications

This is a collection of my applications/software who are made of Java.

# Newton's Cradle
Download the .zip file and open it. Run the executable MyGame.jar file and use WASD keys and mouse to nagivage. Press R or L to lift the rigth ball or the left ball.

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/Newton-s%20Cradle/Markering_072.png)

# Crane
Download the .zip file and open it. Run the executable MyGame.jar file and use WASD keys and mouse to nagivage. Hold R to use the claw, hold T to rotate the whole crane, hold Y to rotate the first arm, hold U to rotate the second arm, hold I to rotate the third arm and hold O to rotate the pink block. Hold the key and then scroll with the mouse to move/rotate its geometries. 

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/Crane/Markering_071.png)

# Database Reader
This software uses JDBC driver to connect to MySQL server. This software can also tell the MySQL server to print out a CSV file.
The key thing with this database reader is that the software saves every edit the user do, to the MySQL server. So data can never be lost!

- View database
- Change the cells online
- Save into a .csv file

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/DatabaseReader/Markering_074.png)

MySQL Workbench:

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/DatabaseReader/Markering_075.png)

Notice that you need to configure your "secure_file_priv" save location for your MySQL server before you can save SQL the database into a e.g CSV file. I'm a Linux user so this is my configuration:

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/DatabaseReader/Markering_076.png)

# Plot Database
This software can connect MySQL and SQLite databases and much more and the user can chose the columns to display in different plot styles. Also the user can download the complete database in a .csv spreadsheet if the user want. Once the user has connected a database, the Plot Database is going to remember which the database the user has connected before. 

- Plot the database with different types of plot styles
- Save the database into a .csv spreadsheet, and the functionality to determine which delimiter should be used into the .csv file.
- Remember the databases who has been connected before which means faster connections
- Single and multi plot is avaiable to use
- Compiled with JDK 10.0.2

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/PlotDatabase/Markering_082.png)


# CRUD - Create Read Update Delete
This is a CRUD software made with Java EE 8 and OpenJDK 11. This software is made for adminstrating different database. All what are needed is a username/password and a MySQL database to change the data onto.

- Web application
- Administrate the database
- Using MySQL
- Multiple users
- Create, Read, Update Delete rows
- Java Server Faces 
- Prime Faces UI
- Dynamical columns

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/CRUD/Markering_005.png)
![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/CRUD/Markering_006.png)

# BOOK - A booking system for business
This is a booking system software for companies who don't want to, or can't share their offical booking system with other companies, so the company itself need to create a separate booking system for special customers.

- Web application
- Using MySQL
- Multiple users
- Java Server Faces
- Prime Faces
- Ajax

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/BOOK/Markering_017.png)

# Java Octave Example - Communicating between Java and GNU Octave by using sockets
This is an example where I demonstrates how to set up an communication between Java and GNU Octave by using the Socket Package from GNU Octave Forge. All you need to do is to run the Java file Main.java and then run the GNU Octave file start.m
The code have try and catch if there be some interupts or the connection breaks. The java program is the server socket and the GNU Octave program is the client socket. If the connections breaks, then the client and server try to re-connecting to find each other. 

Two working examples:

* Sending uint8 from java to octave and back to java
* Sending uint8 array from java and send uint8 double back to java

GNU Octave 

![a](https://github.com/DanielMartensson/Java-Applications/blob/master/JavaOctave/Markering_013.png)

Java 

![a](https://github.com/DanielMartensson/Java-Applications/blob/master/JavaOctave/Markering_014.png)

# Embedded Systems with Java 

What is this? This is a collection of embedded systems by using Java programming language using www.Pi4J.com library and Java Server Faces. Combining Pi4J with web applications is a really IoT-thing. Those examples here are easy to use and very basic. So you can download all of them and play around with them to learn how to IoT every day!

# Blink with LED and counter - Raspberry Pi
This is a web application who are installed on Raspberry Pi. This web application can turn on a LED lamp by pressing on a button
on the web application and also the web application have a counter, connected to a real physical button.

What you need to do is to connet a LED to GPIO_01 and GND and connect a button between GPIO_16 and GND. Then install Tomcat 8 on
the Raspberry Pi and OpenJDK 8. Download the BlinkLED.zip file and insert the BlinkLED.war file from the zip-file to Tomcat's webapps
folder. Start Tomcat and then press the buttons.

This web application has been done with:
* Java Server Faces 2.2 (Integrated in the project)
* Primefaces 6.2 (Integrated in the project)
* Tomcat 8 (Need to be installed on Raspberry Pi)
* OpenJDK 8 (Need to be installed on Raspberry Pi)
* Eclipse IDE SimRel (Need to be installed on a better computer than Raspberry Pi)
* Pi4J (Integrated in the project)

![](https://github.com/DanielMartensson/Java-Applications/blob/master/BlinkLED/Selection_001.png)

# Control analog to digital reader MCP3008
This is another web application who using MCP3008 and MCP9700A temperature sensor. Connect MCP9700A to channel 0 at MCP3008 and CS0 to MCP3008 CS input. 

![](https://github.com/DanielMartensson/Java-Applications/blob/master/TemperatureReader/Selection_002.png)


# Control digital potentiometer MCP41010
This project can control the ligth of a LED lamp. Also this has a shutdown function too. Drag the slider from 0 to 255. 255 = Full LED power, 0 = no power for the LED.
Connect the following:
MCP41010 - Raspberry Pi
* VDD = 3.3 VDC
* PB0 = Ground together with LED's ground pin
* PW0 = LED's positive pin
* PA0 = 3.3 VDC 
* CS = GPIO 10
* SCK = GPIO 14
* SI = GPIO 12
* Vss = Ground

![](https://github.com/DanielMartensson/Java-Applications/blob/master/FadingLED/Selection_004.png)

# Button and LED for MCP23017 with I2C communication
This project is a smal test project for the MCP23017 IO expander IC. Connect it with your Raspberry Pi's i2c connection pins and then run the Run MCP23017.jar file. It's a console application. 

![](https://github.com/DanielMartensson/Java-Applications/blob/master/Test%20MCP23017/image.png)

# Joystick and MCP3008 and MCP23017 and 16x2 LCD
Take a LCD 16x2 and connect D7->D0 from the LCD to the MCP23017 GPIOA7->GPIOA0.
Then connect the RS, R/W and E to GPIOB0, GPIOB1 AND GPIOB2.
Also connect the MCP3008 and a joystick to channel 0 and channel 1. Done! The SPI and i2c connection is like it should be.

Internet:

![](https://github.com/DanielMartensson/Java-Applications/blob/master/LCD_MCP23017/Selection_005.png)

LCD:

![](https://github.com/DanielMartensson/Java-Applications/blob/master/LCD_MCP23017/realPicture.jpg)

# Joystick and MCP3008 and MCP23017 and 16x2 LCD with steper motor driver ULN2003
This is an extension of the past project. This project does not use Java Server Faces and Primefaces. This project is a executable jar file named JoystickStepperLCD.jar. Very simple code so it will be easy for you to play around with it.

![](https://github.com/DanielMartensson/Java-Applications/blob/master/JoystickStepperLCD/realPicture.jpg)

# Temperature logger online
This is a bussnies project where a customer order a IoT solution. The temperature logger can logg values in different sampling rate, send values to database and also other people(with access) can also view the graf in real time. It's connected to 5 temperature sensors. 

Everything can be fitted into your pocket.

My mobile phone:

![](https://github.com/DanielMartensson/Java-Applications/blob/master/TemperatureLoggerOnline/received_541593202992519.png?raw=true)

The unit:

![](https://github.com/DanielMartensson/Java-Applications/blob/master/TemperatureLoggerOnline/received_354894848396581.jpeg?raw=true)

This unit can be purchased at https://www.spektrakon.se/


# JSUBPlotter

This project is a Java and a C project that I have made. The Java project is an JavaFX GUI application that can log 6 analog values in real time with the 16 bit resolution. The C project is an project folder that contains C code for an STM32F401RE Nucleo board.

This project is named JUSBPlotter. J for Java, USB for communication between PC and STM32 and Plotter for the graph.

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/JUSBPlotter/Image.png)

This is the Nucleo board. Picture from the Help menu -> About JUSBPlotter, inside JUSBPlotter software.

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/JUSBPlotter/JUSBPlotter/src/main/resources/org/openjfx/pictures/stm32.jpg)


# How can I use it? 
First you need to install OpenJDK 11 and OpenJFX. It can be done in Ubuntu Linux very easy.

```
sudo apt-get install openjdk-11-jdk openjfx
```

Then you need to download the JUSBPlotter folder. Once you got it, you need to run the run.sh file or run.bat inside folder "ExecutableJar". That file will run the JUSBPlotter.jar file with the VM arguments 

```
--module-path="/usr/share/openjfx/lib" --add-modules=javafx.controls,javafx.fxml
```
Or for Windows

```
--module-path="C:\Program Files\openjfx\lib" --add-modules=javafx.controls,javafx.fxml
```
So make sure that openjfx is correct installed!

When you can run the software, you need to burn in the C-code onto a STM32F401RE Nucleo board. Just download the folder "STM32F401RE - UART ADC". It's a project created in Atollic TrueSTUDIO. You need to download Atollic TrueSTUDIO and import the project and just burn over the C-code onto your STM32F401RE Nucleo board. 

Once that is done, just connect your Nucleo board you your USB port and connect the Nucleo board inside JUSBPlotter. Then start logging values. After you are done with logging values, you just can import the values into GNU Octave.

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/JUSBPlotter/load.png)

# I don't have a Nucelo board. Can I use my Arduino instead?
Yes. You can use any microcontroller that have UART-communication. Just send 14 bytes array with data. I post C pseudo code below so you can see the protocol.

![a](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/master/JUSBPlotter/JUSBPlotter/src/main/resources/org/openjfx/pictures/protocol.jpeg)

# I want to work on this project. How can I compile it and run it?
This is a maven project. You need to use Eclipse IDE and then import the JUSBPlotter folder.

To compile:

```
mvn clean compile assembly:single
```

To run:
```
mvn javafx:run
```
