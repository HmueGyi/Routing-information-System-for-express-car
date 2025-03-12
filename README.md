# Routing Information for Express Car (RMI Project)

## Overview
This project is designed to provide routing information for express cars using RMI (Remote Method Invocation) in Java. The system consists of both a server-side component and client-side components.

## Prerequisites
Before running the project, ensure that you have the following software installed:

1. **Java Development Kit (JDK)**:
   - To download and install OpenJDK for Kali Linux, you can use the following link:
     - [OpenJDK 11 for Kali Linux](https://packages.debian.org/buster/openjdk-11-jdk)
   - Alternatively, for Oracle JDK:
     - [Oracle JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

2. **XAMPP Server**: (sometime you need VPN to install XAMPP package)
   - To download and install XAMPP for Kali Linux, visit the official Apache Friends website:
     - [XAMPP for Linux](https://www.apachefriends.org/index.html)

## Installation Steps

### Run XAMPP Server on Kali Linux (Server Side Only)
1. Open a terminal window and gain root access:
   ```bash
   sudo su
   ```

2. Make the installer file executable:
   ```bash
   sudo chmod 755 xampp-linux-x64-8.0.30-0-installer.run
   ```

3. Run the installer to install XAMPP:
   ```bash
   sudo ./xampp-linux-x64-8.0.30-0-installer.run
   ```

4. Start the XAMPP server:
   ```bash
   sudo /opt/lampp/lampp start
   ```

### Stop XAMPP Server
To stop the XAMPP server, run:
```bash
sudo /opt/lampp/lampp stop
```

### Run Server
To start the server-side component, execute the following command:
```bash
java -Djava.rmi.server.hostname=192.168.100.1 -cp mysql-connector-java-8.0.26.jar:jcalendar-1.4.jar:. BusRouteServer
```

### Run Client
To start the client-side components, run the following commands:

- For the admin client:
  ```bash
  java -Djava.rmi.server.hostname=192.168.100.2 AdminClient
  ```

- For the user client:
  ```bash
  java -Djava.rmi.server.hostname=192.168.100.2 UserClient
  ```

## References
- **JDK Installation**: [OpenJDK 11 for Kali Linux](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-debian-11)
- **XAMPP Installation**: [XAMPP for Linux](https://www.apachefriends.org/index.html)

## Project Details
This project is a Routing Information system that provides express car routing using RMI technology in Java.
```
