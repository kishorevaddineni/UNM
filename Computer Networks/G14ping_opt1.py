#Authors: Kishore Kumar Vaddineni and Anil Kumar Kulla; Group Name: G14
#from socket import *
from __future__ import division
import socket
import os
import sys
import struct
import time
import select
import binascii

ICMP_ECHO_REQUEST = 8

def checksum(str):
 csum = 0
 countTo = (len(str) / 2) * 2
 
 count = 0
 while count < countTo:
     thisVal = ord(str[count+1]) * 256 + ord(str[count])
     csum = csum + thisVal
     csum = csum & 0xffffffffL
     count = count + 2
 if countTo < len(str):
    csum = csum + ord(str[len(str) - 1])
    csum = csum & 0xffffffffL 
 
 csum = (csum >> 16) + (csum & 0xffff)
 csum = csum + (csum >> 16)
 answer = ~csum
 answer = answer & 0xffff 
 answer = answer >> 8 | (answer << 8 & 0xff00) 
 return answer

def receiveOnePing(mySocket, ID, timeout, destAddr):
    timeLeft = timeout
    while 1:
        startedSelect = time.time()
        whatReady = select.select([mySocket], [], [], timeLeft)
        howLongInSelect = (time.time() - startedSelect)
        if whatReady[0] == []: # Timeout
            return "Request timed out."
        timeReceived = time.time()
        recPacket, addr = mySocket.recvfrom(1024)
 
 #Fill in start
 
 #Fetch the ICMP header from the IP packet
        icmpHeader = recPacket[20:28]
        typ, code, checksum, packetID, sequence = struct.unpack ("bbHHh",icmpHeader)
        if packetID == ID:
             bytesInDouble = struct.calcsize("d")
             timeSent = struct.unpack("d", recPacket[28:28 +bytesInDouble])[0]
             return timeReceived - timeSent
 #Fill in end
        timeLeft = timeLeft - howLongInSelect
        if timeLeft <= 0:
            return "Request timed out." 

def sendOnePing(mySocket, destAddr, ID):
 # Header is type (8), code (8), checksum (16), id (16), sequence (16)
 myChecksum = 0
 # Make a dummy header with a 0 checksum.
 # struct -- Interpret strings as packed binary data
 header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, ID, 1) 
 data = struct.pack("d", time.time()) 
 # Calculate the checksum on the data and the dummy header.
 myChecksum = checksum(header + data)
 
 # Get the right checksum, and put in the header
 if sys.platform == 'darwin':
     myChecksum = socket.htons(myChecksum) & 0xffff 
 #Convert 16-bit integers from host to network byte order.
 else:
     myChecksum = socket.htons(myChecksum) 
 
 header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, ID, 1)
 packet = header + data
 mySocket.sendto(packet, (destAddr, 1)) # AF_INET address must be tuple, not str
 #Both LISTS and TUPLES consist of a number of objects 
 #which can be referenced by their position number within the object

def doOnePing(destAddr, timeout): 
 icmp = socket.getprotobyname("icmp") #SOCK_RAW is a powerful socket type. For more details see: http://sock-raw.org/papers/sock_raw
 #Fill in start
 #Create Socket here
 mySocket = socket.socket(socket.AF_INET,socket.SOCK_RAW,icmp)
 #Fill in end
 myID = os.getpid() & 0xFFFF #Return the current process i
 sendOnePing(mySocket, destAddr, myID)
 delay = receiveOnePing(mySocket, myID, timeout, destAddr) 
 
 mySocket.close() 
 return delay

def ping(host, timeout=1):
 #timeout=1 means: If one second goes by without a reply from the server,
#the client assumes that either the clients ping or the servers pong is lost
 dest = socket.gethostbyname(host)
 print "Pinging " +host+ " (" +dest + ") using Python:"
 print ""
 #Send ping requests to a server separated by approximately one second
 totaldelay=0.00000
 mindelay=0.00000
 maxdelay=0.00000
 lost=0 #packets lost count
 pings=4 #no. of pings
 for i in range(0, pings) :
     delay = doOnePing(dest, timeout)
     if delay == "Request timed out.":
         lost=lost+1
     else:
        totaldelay=totaldelay+delay
        if i==0 | i==lost:
            mindelay=delay
            maxdelay=delay
        else:
         if delay>maxdelay:
            maxdelay=delay
         if delay<mindelay:
            mindelay=delay
     print delay
     time.sleep(1)# one second
 spings=pings-lost #successful pings
 print "Maximum Delay = %.5f" %maxdelay
 if spings>0:
  print "Average Delay = %.5f" %(totaldelay/(spings))
 else:
     print "Average Delay = 0"
 print "Minimum Delay = %.5f" %mindelay
 print "Packets Sent = %d" %pings
 print "Packets Received = %d" %spings
 print "Packets Lost = %d" %lost
 print "Packet Loss Percentage = %d %%" %(((lost/pings)*100))
 return delay
     

ping("www.yahoo.com")