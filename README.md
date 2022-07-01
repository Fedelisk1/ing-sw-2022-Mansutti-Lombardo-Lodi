# ing-sw-2022-Mansutti-Lombardo-Lodi

## Run the game
You can download the server and client jars. They can run on linux, macos and windows and include all the needed dependecies for all platforms.

The application was tested and developed with Oracle JDK 18

You can run the server with the following command: 
```
java - jar eriantys-server.jar
```
while the client can be run with the command:
```
java - jar eriantys-client.jar
```

optional parameter ```-v``` can be used choose the interface before executing the application itself. Allowed values are ```cli``` and ```gui```:
```
java - jar eriantys-client.jar -v cli
```
```
java - jar eriantys-client.jar -v gui
```

If the parameter is not provided, you will be asked 

## Technologies
* Java SE 18
* Maven
* JavaFX
* JUnit
* mockito
* Intellij IDEA

## Implemented features
* Complete rules
* Socket connection
* Command Line Interface
* Graphical User Interface with JavaFX

### Advanced features
* 12 Character Cards implementation
* Multiple games

## Authors
[Pietro Lodi Rizzini](https://github.com/PietroLodiRizzini)

[Federico Lombardo](https://github.com/federicolombardo)

[Federico Mansutti](https://github.com/FedericoMansutti)