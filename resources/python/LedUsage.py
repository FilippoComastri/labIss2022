##############################################################
# TCP-Send a msg to Led on 8010
##############################################################
import socket
import time

hostAdress     = '169.254.162.64'
port           = 8010
ledOn          = "on"
ledOff         = "off"
msg            = ''

sock          = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def connect(port) :
    server_address = (hostAdress, port)
    sock.connect(server_address)    
    print("CONNECTED WITH ", server_address)

def terminate() :
    sock.close()    #qak infrastr receives a msg null
    print("BYE")

def forward( message ) :
    print("forward ", message)
    msg = message + "\n"
    byt=msg.encode()    #required in Python3
    sock.send(byt)

def receive() :
    print("receive")
    byte=sock.recv(1024)
    print(byte.decode())

def console() :  
    print("console  STARTS :"   )
    cmd =  str( input() )
    print("console  D= :" , cmd  )
    while( cmd != "q"  ) :
        msg = cmd
        print( msg )
        forward( msg )
        if(msg=="getState"):
            receive()
        cmd =  str(input())
     
##################################################
print("STARTING ... ")
connect(port)
console()