# JSUBPlotter

This project is a Java and a C project that I have made. The Java project is an JavaFX GUI application that can log 6 analog values in real time with the 16 bit resolution. The C project is an project folder that contains C code for an STM32F401RE Nucleo board.

This project is named JUSBPlotter. J for Java, USB for communication between PC and STM32 and Plotter for the graph.

![](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/JUSBPlotter/master/Image.png)

This is the Nucleo board. Picture from the Help menu -> About JUSBPlotter, inside JUSBPlotter software.

![](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/JUSBPlotter/master/JUSBPlotter/src/main/resources/org/openjfx/pictures/stm32.jpg)


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

![](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/JUSBPlotter/master/load.png)

# I don't have a Nucelo board. Can I use my Arduino instead?
Yes. You can use any microcontroller that have UART-communication. Just send 14 bytes array with data. I post C pseudo code below so you can see the protocol.

![](https://raw.githubusercontent.com/DanielMartensson/Java-Applications/JUSBPlotter/master/JUSBPlotter/src/main/resources/org/openjfx/pictures/protocol.jpeg)

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
