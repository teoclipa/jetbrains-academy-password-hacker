# Password Hacker

## Overview
This project, "Password Hacker," is a Java-based application designed to demonstrate a basic understanding of network communication, security principles, and ethical hacking techniques. It simulates a scenario where the user attempts to gain unauthorized access to a system using a time-based vulnerability. The project is purely for educational purposes to showcase skills in socket programming, JSON handling, and algorithm implementation in Java.

## Features
- **Socket Communication**: Establishes a network connection to a predefined server using sockets.
- **Login Discovery**: Iteratively tests a list of potential usernames (from `logins.txt`) to find a valid login using server response analysis.
- **Password Brute Forcing**: Employs a timing attack to guess the password character by character. It detects slight differences in response times to determine correct password parts.
- **JSON Messaging**: Communicates with the server using JSON-formatted messages for sending login and password attempts and interpreting server responses.

## How It Works
1. **Finding the Correct Login**: Reads from a list of common usernames and sends each to the server until it finds one that yields a "Wrong password!" response, indicating a valid username.
2. **Password Cracking**: Once a valid username is found, the program attempts to guess the password. It does this by appending characters to the current password guess and measuring the server's response time to each attempt. A longer response time indicates that the current guess is a substring of the actual password.

## Usage
To run the Password Hacker, you need a server to which the application can connect. The server should respond in a specific manner as outlined in the project description. Once you have the server set up:

1. **Compile the Java Program**: Use your preferred Java compiler or IDE to compile the program.
2. **Run the Program**: Execute the program with the server's IP address and port as command-line arguments.
   ```
   java hacker.Main <IP address> <port>
   ```
3. **Observe the Output**: The program will output the discovered valid login and password in JSON format.

## Disclaimer
This project is designed for educational purposes only. Unauthorized hacking, i.e., attempting to access a computer network without authorization or beyond one's access level, is illegal and unethical. Users should only run this program in a controlled, legal environment.
