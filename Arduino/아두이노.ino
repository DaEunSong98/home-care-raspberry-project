#include <ESP8266WiFi.h>
#include <DHT.h>
#define DHTpin 2
DHT dht(DHTpin, DHT11);

const char* ssid = "JK";
const char* password = "kim980429";


int red_led=0; 
int blue_led = 12;
int big_led = 4;
int fire_sensor=13;
int fire_state=0; // 화재감지 (화재 발생 안함 =100 , 화재 발생 =1)
int move_state=0; //움직임 없음=1 움직임 있음 =2
int move_sensor=2;
int sound=5;
WiFiServer server(80);
 
void setup() {
  //dht.begin();
  Serial.begin(115200);
  delay(10);
  pinMode(red_led, OUTPUT);
  pinMode(blue_led, OUTPUT);
  pinMode(big_led, OUTPUT);
  pinMode(move_sensor, INPUT);
  digitalWrite(red_led, LOW);
  digitalWrite(blue_led, LOW);
  digitalWrite(big_led, LOW);
  pinMode(fire_sensor, INPUT);


  // 와이파이 연결 
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
 
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
 
  // 서버 시작 
  server.begin();
  Serial.println("Server started");
 
  // Print the IP address
  Serial.print("Use this URL to connect: ");
  Serial.print("http://");
  Serial.print(WiFi.localIP());
  Serial.println("/");
 
}
 
void loop() {
  int fire_state = fire_state;
  int move_state =move_state;

  WiFiClient client = server.available();
  if (!client) {
    return;
  }
 
  Serial.println("new client");
  while(!client.available()){
    delay(1);
  }

  Serial.print("move_state : ");
  Serial.print(move_state);
  Serial.print(" fire_state : ");
  Serial.print(fire_state);


  String request = client.readStringUntil('\r');
  Serial.println(request);
  client.flush();

  
  int fire=digitalRead(fire_sensor);// 1 : 화재 발생,  100: 화재발생 안함 
  if(fire==LOW){ //화재발생 안함 
    digitalWrite(red_led, LOW);
    digitalWrite(blue_led,HIGH);
    delay(50);
    fire_state=100;
    tone(sound,300);
   }
   else if(fire==HIGH){// 화재 발생 
    digitalWrite(red_led, HIGH);
    digitalWrite(blue_led,LOW);
    fire_state=1;
    noTone(sound);
   }
   int val2=digitalRead(move_sensor);
   if(val2==HIGH){//인체 감지가 되면
    Serial.println("Motion detected");
    digitalWrite(big_led, LOW);
    move_state=2;
      }
    else if(val2==LOW){//인체 감지가 없으면
        unsigned cur_time=millis();
        if(cur_time>=36000000){ 
          Serial.print("움직임이 없습니다.");
          Serial.println("Good Bye");
          Serial.println(val2);
          digitalWrite(big_led, HIGH);
          move_state=1;
        }
    }

  client.print("HTTP/1.1 200 OK");
  client.print("Content-Type: text/html\r\n\r\n");
  
  client.print("<!DOCTYPE HTML>");
  client.print("<html>");
  client.print("<head>");
  client.print("<meta charset=\"UTF-8\" http-equiv=\"refresh\" content=\"2\">");
  client.print("<style>");
  client.print("body{background-color:#000;}");
  client.print("</style>");
  client.print("</head>");
  client.print("<body>");
  client.print("<p style='font-size:30px; color:white'>");
  client.print("MOVE_STATE: ");
  client.print(move_state);
  client.print("</p>");
  client.print("<br>");
  client.print("<p style='font-size:30px; color:white'>");
  client.print("FIRE_STATE: ");
  client.print(fire_state);
  client.print("</p>");
  client.print("</body>");  
  client.print("</html>");
 
  delay(1);
  Serial.println("Client disonnected");
  Serial.println("");
}
