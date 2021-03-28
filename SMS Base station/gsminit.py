import time
import serial
serialPort = serial.Serial(port = "COM17", baudrate=9600,
                           bytesize=8, timeout=2, stopbits=serial.STOPBITS_ONE)

serialPort.write(b'AT\r')
serialPort.flush()
time.sleep(1)
while(serialPort.in_waiting > 0):

        # Read data out of the buffer until a carraige return / new line is found
    serialString = serialPort.readline()

        # Print the contents of the serial data
    print(serialString.decode('Ascii'))
    