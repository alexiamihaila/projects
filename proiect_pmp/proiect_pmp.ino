#include <Servo.h>
#include <LiquidCrystal.h>
//#include <LiquidCrystal_I2C.h>

LiquidCrystal lcd(7, 6, 5, 4, 3, 2);
//LiquidCrystal_I2C lcd( 0x3D, 16, 2);

const int trigPin = 9;
const int echoPin = 10;

const int trigPin1 = 11;
const int echoPin1 = 12;

Servo servo;
int servoPin = 8;
int pos = 0;

float duration, distance, duration1, distance1;

float height = 24.5 ;

void setup() {
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);

  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);

  Serial.begin(9600);

  lcd.begin(16, 2);

  //lcd.backlight();

  lcd.clear();
  
  servo.attach(servoPin);
  servo.write(0); 
}

void loop() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  distance = (duration*.0343)/2;
  delay(100);


  // senzor pt cat de plin e cosul

  digitalWrite(trigPin1, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin1, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin1, LOW);


  duration1 = pulseIn(echoPin1, HIGH);
  distance1 = (duration1*.0343)/2;
  Serial.print("Distance: ");
  float distance1_percentage = 100 - (distance1 * 100 / height);
  Serial.print(distance1_percentage);
  Serial.println("%");

  lcd.setCursor(0, 0);
  lcd.print("Full: ");
  lcd.setCursor(0, 1);
  lcd.print(distance1_percentage);
  lcd.print("%");

  delay(100);

  if ( distance < 20 ) {
    for (pos = 90; pos <= 180; pos += 1) { // goes from 0 degrees to 310 degrees
      // in steps of 1 degree
      servo.write(pos);              // tell servo to go to position in variable 'pos'
      delay(2);                       // waits 15ms for the servo to reach the position
    }

    delay(5000);
    for (pos = 180; pos >=90; pos -= 1) { // goes from 310 degrees to 0 degrees
    servo.write(pos);              // tell servo to go to position in variable 'p
      delay(2);                       // waits 15ms for the servo to reach the position
    }
  }

  
}