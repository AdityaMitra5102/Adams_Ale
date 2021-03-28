import serial
import os

def phoneNumber(str):
 for i in range(len(str)):
  if(str[i]=='+' and str[i-1]=='"'):
    return (str[i:i+13])
    break

serialPort = serial.Serial(port = "COM17", baudrate=9600,
                           bytesize=8, timeout=2, stopbits=serial.STOPBITS_ONE)
serialString = ""                           # Used to hold data coming over UART
k=1
txt=""
phn=""
msg=""
while(k):

    # Wait until there is data waiting in the serial buffer
    if(serialPort.in_waiting > 0):
        serialString=""
        txt=""
        # Read data out of the buffer until a carraige return / new line is found
        serialString = serialPort.readline()
        try:
        # Print the contents of the serial data
            txt=serialString.decode('Ascii')
            print(txt)
            if('+CMT' in txt):
                phn=phoneNumber(txt)
            if('getLoc' in txt):
                msg=txt
                print("Phone ")
                print(phn)
                print("Text")
                print(msg)
                msg='"'+msg+'"'
                k=0
                cmd='java AdamsAleSMSClient '+phn+' '+msg
                serialPort.close()
                os.system(cmd)
                serialPort.open()
                k=1
                txt=""
                phn=""
                msg=""
        except:
            print()
        # Tell the device connected over the serial port that we recevied the data!
        # The b at the beginning is used to indicate bytes!
        #serialPort.write(b"Thank you for sending data \r\n")