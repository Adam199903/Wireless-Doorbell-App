#include <WiFi.h>
#include <FirebaseESP32.h>
 
#define FIREBASE_HOST ""
#define FIREBASE_AUTH ""
#define WIFI_SSID ""
#define WIFI_PASSWORD ""

//Define FirebaseESP32 data object
FirebaseData firebaseData;
FirebaseJson json;


int PIRinput = 27;
int PIRstate = LOW;
int led = 32;
int val = 0;
int Firebase_val = 0;



void setup() {
  Serial.begin(115200);
  pinMode(PIRinput, INPUT);
  pinMode(led, OUTPUT);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();
 
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
 
  //Set database read timeout to 1 minute (max 15 minutes)
  Firebase.setReadTimeout(firebaseData, 1000 * 60);
  //tiny, small, medium, large and unlimited.
  //Size and its write timeout e.g. tiny (1s), small (10s), medium (30s) and large (60s).
  Firebase.setwriteSizeLimit(firebaseData, "tiny");

  Serial.println("------------------------------------");
  Serial.println("Connected...");
  

}

void loop() {

  val = digitalRead(PIRinput);
  if (val == HIGH){
    digitalWrite(led, HIGH);
    if(PIRstate == LOW){
      Serial.println("motion detected");
      Firebase_val = 1;
      PIRstate = HIGH;
      json.set("/motion_detector", Firebase_val);
      Firebase.updateNode(firebaseData,"/users/John1",json);
      delay(3000);
    }
  }
  else{
    digitalWrite(led, LOW);
    if(PIRstate == HIGH){
      Serial.println("motion ended");
      Firebase_val = 0;
      PIRstate = LOW;
      json.set("/motion_detector", Firebase_val);
      Firebase.updateNode(firebaseData,"/users/John1",json);
    }
  }

}
