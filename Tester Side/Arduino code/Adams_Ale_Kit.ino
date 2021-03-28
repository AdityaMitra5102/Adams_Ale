#define orpPin          A7    
#define pHPin           A5    
#define turbidityPin    A0    
#define voltage         5.00  

#define redLED          2     
#define blueLED         5     

double orpSense;
double orpVolt;
double orpValue;

double pHSense;
double pHVolt;
double pHValue;
const float c = 21.34;
const float m = -5.70;

double turbiditySense;
double turbidityVolt;
double turbidityValue;

int samples = 50;
unsigned long int Timer, printTime, ledTime;

void setup(void)
{
  Serial.begin(9600);
  pinMode(orpPin,INPUT);
  pinMode(pHPin,INPUT);
  pinMode(turbidityPin,INPUT);
  pinMode(redLED,OUTPUT);
  pinMode(blueLED,OUTPUT);
}

void loop(void)
{  
  if(millis()-Timer>=20)    
  {
    Timer = millis();
    orpSense = 0;
    pHSense = 0;
    turbiditySense = 0;
    for (int i = 0; i < samples; i++)
    {
      orpSense += analogRead(orpPin);
      pHSense += analogRead(pHPin);
      turbiditySense += analogRead(turbidityPin);
      delay(10);
    }
    orpVolt = (75*orpSense*voltage*1000/1023)/samples;
    orpValue = (((30*(double)voltage*1000)-orpVolt)/75);
    pHVolt = (pHSense*voltage/1023)/samples;
    pHValue = m*pHVolt+c;
    turbidityVolt = turbiditySense/samples;
    turbidityValue = map(turbidityVolt, 0, 730, 100, 0);
  }
  
  if(millis()-printTime>=1000)   
  {
    printTime = millis();
    Serial.print("ORP Value: ");
    Serial.print(orpValue,0);
    Serial.println("mV");
    Serial.print("pH Value: ");
    Serial.println(pHValue,0);
    Serial.print("Turbidity Value: ");
    Serial.println(turbidityValue,0);
    LED();
  }
}

void LED () 
{
  if(millis()-ledTime<=30000)
  {
    printTime = millis();
    digitalWrite(redLED,LOW);  
    digitalWrite(blueLED,HIGH);
    delay(500);
    digitalWrite(blueLED,LOW);  
    digitalWrite(redLED,HIGH); 
  }
  else
  {
  if (orpValue > 150 && orpValue < 450)
    {
      if (pHValue > 6.5 && pHValue < 8.5)
      {
        if (turbidityValue > -5 && turbidityValue < 5)
        {
          digitalWrite(redLED,LOW);
          digitalWrite(blueLED,HIGH); 
        }
        else
        {
          digitalWrite(redLED,HIGH);
          digitalWrite(blueLED,LOW); 
        }
      }
      else
      {
        digitalWrite(redLED,HIGH);
        digitalWrite(blueLED,LOW); 
      }
    }
    else
    {
      digitalWrite(redLED,HIGH);
      digitalWrite(blueLED,LOW); 
    }
  }
}
