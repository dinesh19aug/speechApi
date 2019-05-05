# speechApi
Sppech API POC

# How to run
- checkout the project
- Go to your google console project where you have subscribe to cloud speec to text service.
- Get the key as json file.
- Set the environment variable GOOGLE_APPLICATION_CREDENTIALS to the file path of the JSON file that contains your service account key.
- export GOOGLE_APPLICATION_CREDENTIALS="<Your file full path>.json"
  
- Create a voice recording with this command in your terminal(I am using ubuntu) - arecord -f S16_LE -D plughw:1,0 -d 20 -r 16000 MyFile.wav
- Clone the project
- cd speechApi
- Run `mvn celan install`
- start the application. I am running on tomcat and using IntelliJ. Use the same tools or use an IDE of your choice or after the war file is created under the target folder, just deploy in app server of your choice.
- Record a statement - "What is my account balance?" OR "Transfer $2000 in my saving account" OR "What is nearest ATM location near me?"
- The API is set to catch phrase - "balance" or "transfer" or "ATM/location".
- You will see a file saved on your desktop as MyFile.wav. You can change the basepath and file name in Constants.java file.
- After the app starts open a Postman or SOAP UI or use curl and hit HTTP POST - http://localhost:8081/smartApi/bye . Note port 8081 is beig set by application. Do not change even if your tomcat is running on 8080 port.
- You will see a reponse in postman and also a audo file would be created on as `reponse_tts.mp3` on your desktop. You can change the baseath and file name by updating the values in `Constants.java`.

