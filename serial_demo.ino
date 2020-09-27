#include <Adafruit_PCD8544.h>
Adafruit_PCD8544 display = Adafruit_PCD8544(CLK,DIN,D/C,CE,RST);
//Define pins according to your LCD screen connection

void setup() {
  // put your setup code here, to run once:
  Serial.begin(19200);
  Serial.setTimeout(50);
  display.begin();
  display.setContrast(50);
  display.setTextSize(1);
  display.clearDisplay();
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available()>0){
    display.clearDisplay();
   String data = Serial.readString();
  String date = data.substring(0, 13);
  String t = data.substring(14, data.length());
    display.println(date);
    display.println();
    display.println(t);
    display.display(); 
    
  }
  
}
