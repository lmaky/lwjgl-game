#!/bin/bash
mvn clean package
java -jar -Djava.library.path="target/natives" target/lwjgl-game-1.0-SNAPSHOT.jar 