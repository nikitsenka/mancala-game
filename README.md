# Rest API for Mancala game
[About mancala game](https://en.wikipedia.org/wiki/Mancala)
Spring WEB and Spring Data based http REST API.
DB: PostgreSql

### Run Using Docker

Build

```
./gradlew build
```

Simple build command will run next checks:
1. [Checkstyle](http://checkstyle.sourceforge.net/) 
2. [Spotbugs](https://spotbugs.github.io/)

Run Integration tests

```
docker-compose up -d database
./gradlew integrationTest
```

Run
 
```
docker-compose up -d --build
```



