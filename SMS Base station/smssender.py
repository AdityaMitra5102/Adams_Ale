import sys
import serial
import time
# total arguments
n = len(sys.argv)
print("Total arguments passed:", n)
 
# Arguments passed
print("\nName of Python script:", sys.argv[0])
 
print("\nArguments passed:")
phn=sys.argv[1]
route=sys.argv[2]
print(phn)
print(route)
serialPort = serial.Serial(port = "COM17", baudrate=9600,
                           bytesize=8, timeout=2, stopbits=serial.STOPBITS_ONE)

serialPort.write(str.encode('AT+CMGF=1\r'))
serialPort.flush()
time.sleep(1)
serialPort.write(str.encode('AT+CMGS="'+phn+'"\r'))
time.sleep(1)
serialPort.write(str.encode(route))
serialPort.write(str.encode(chr(26)))
serialPort.flush()
time.sleep(3)