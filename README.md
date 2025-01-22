## Required Environment
- Java: Version need to be equal to or greater then **Java 17**
- Node.js (https://nodejs.org/en/download)


## Build and Run Backend - Option 1: With IDE
This project use **Gradle**. Just build and run this project depends on your IDE. 

![image](https://github.com/user-attachments/assets/8c42b6a1-c888-4f63-8a65-cfae5297e62e)

 This page might be helpful: https://docs.gradle.org/current/userguide/gradle_ides.html

## Build and Run Backend - Option 2: Without IDE
### Pre-setting: Check `java` and `gradlew.bat` command is available
Please make sure you can run `java` and `gradlew.bat` command using a terminal or command prompt.

```bash
java -version
```

![image](https://github.com/user-attachments/assets/cb8a576e-8fa3-4c71-a84e-d6cb79bc48f0)

```bash
gradlew.bat -version
```

![image](https://github.com/user-attachments/assets/e0fee34e-612d-408e-ae4a-891c98d4f596)

- If you cannot use `java -version` command, please make sure to add JDK to PATH, like this.
    ```cmd
    setx PATH "%PATH%;C:\Users\<<path to your jdk folder>>\.jdks\corretto-17.0.13\bin" /M
    ``` 

- If you cannot use `gradlew.bat` command, please make sure to set value `JAVA_HOME`, like this:
    ```cmd
    setx JAVA_HOME "C:\Users\<<path to your jdk folder>>\.jdks\corretto-17.0.13" /M
    ```

---

### Steps to Build and Run the Spring Boot Backend
1. Navigate to the project folder

    ![image](https://github.com/user-attachments/assets/c1020bcf-9e9c-4200-8f62-192eb70b7183)

2. Build
    ```bash
    ./gradlew build    # Linux/Mac
    gradlew.bat build  # Windows
    ```

   ![image](https://github.com/user-attachments/assets/baebf25e-1e4d-467d-b328-6862731ca98a)

3. Run: Spring Boot server running on port `8081`
    - To run
      ```bash
      ./gradlew bootRun  # Linux/Mac
      gradlew.bat bootRun # Windows
      ```
  
      ![image](https://github.com/user-attachments/assets/bc3ad68c-b51c-4f86-814e-d6c5bb44b02f)

    - Or, after once building, you can run the JAR file:
      ```bash
      java -jar build/libs/your-app-name.jar
      ```



## Build and Run Frontend
### Pre-setting: Install Node.js
This project using React. React requires `Node.js` to run. If you have not installed it yet, you can get it from:
https://nodejs.org/en/download

Please make sure you can use npm command.
```bash
npm -version
```

![image](https://github.com/user-attachments/assets/3d58f68f-ad25-4b84-b415-19a7f6eca854)

---

### Steps to Build and Run the React Frontend using terminal or command prompt
1. Navigate to the `frontend` directory just under the project root.
    ```bash
    cd frontend
    ```
    
    ![image](https://github.com/user-attachments/assets/def4a41d-7c7d-41b7-aeb3-bb29bc4d6b24)

2. Run the Development Server
   ```bash
   npm start
   ```

   This will open the application in your default web browser. If you want to access it manually, the url is `http://localhost:3000`.
   
   ![image](https://github.com/user-attachments/assets/a23d002a-068c-487f-abe1-fcc2c6969a7e)



## 3. Display Image
![image](https://github.com/user-attachments/assets/981639bb-d885-4dc0-a005-b4d203fdaaf0)

