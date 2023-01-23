# POC Keycloak and Spring Security

POC Keycloak and Spring Security to Secure WebApp Gestion Materiel 

# Keycloak Server

##Setting Up a Keycloak Server

**1. Downloading and Installing Keycloak Server**

We'll be using the standalone version.

Download the Keycloak Standalone server distribution from the official source.
https://www.keycloak.org/downloads.html

Install :  official doc
https://www.keycloak.org/getting-started/getting-started-zip
![alt text](https://fouomene.com/images/installeKeycloak.jpg)
![alt text](https://fouomene.com/images/homekeycloak.jpg)
![alt text](https://fouomene.com/images/adminUser.jpg)
![alt text](https://fouomene.com/images/masterrealmkeycloak.jpg)

**2. Creating a Realm call "GestionMaterielSpringKeycloak"**

Navigate to the upper left corner to discover the Add realm button

![alt text](https://fouomene.com/images/gestionmaterielrealmkeycloask.jpg)
![alt text](https://fouomene.com/images/gestionmaterielrealmkeycloask2.jpg)

**3. Creating a Client call "login-webapp-gestionmateriel"**

Navigate to the Clients page
![alt text](https://fouomene.com/images/clientkeycloak1.jpg)
![alt text](https://fouomene.com/images/clientkeycloak2.jpg)

Leave all the defaults except the Valid Redirect URIs field. 

This field should contain webapp Gestion Materiel URL(s) that will use this client for authentication :
![alt text](https://fouomene.com/images/clientkeycloak3.jpg)

**4. Creating a Role call "admin"**
![alt text](https://fouomene.com/images/rolekeycloak1.jpg)
![alt text](https://fouomene.com/images/rolekeycloak2.jpg)

**5. Creating a User call "usertest1"**
![alt text](https://fouomene.com/images/userkeycloak.jpg)
![alt text](https://fouomene.com/images/userkeycloak2.jpg)

**6. Set password "usertest1"**
![alt text](https://fouomene.com/images/userkeycloak3.jpg)

**7. Assign the Role "admin" to our User "usertest1"**
![alt text](https://fouomene.com/images/assignrolekeycloak1.jpg)
![alt text](https://fouomene.com/images/assignrolekeycloak2.jpg)
![alt text](https://fouomene.com/images/assignrolekeycloak3.jpg)

# Spring Boot APP

## Requirements

1. Java - 1.8.x or greater

2. Maven - 3.x.x

## Steps to Setup

**1. Clone the application**

```bash
https://github.com/fouomene/poc-keycloak-spring-with-webapp-gestionmateriel.git
```

**2. Build and run the app using maven**

```bash
mvn package
java -jar target/*.jar

```

Alternatively, you can run the app directly without packaging like this -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8282>.
```bash
http://localhost:8282
```
Account Keycloak Create
```bash
Login : usertest1
Password : usertest1
```

![alt text](https://fouomene.com/images/keycloakgestionmatlogin.jpg)

![alt text](https://fouomene.com/images/keycloakdashboardgestionmat2.jpg)

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.

## License
For open source projects, say how it is licensed.

## Project status
If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely.
Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make
an explicit request for maintainers.
