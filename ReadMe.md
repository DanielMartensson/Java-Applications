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
