import sys # Importing the sys module. Used to get command line arguments in this program
from socket import * #Importing the socket module
#for arg in sys.argv
serverName = sys.argv[1] # Getting the 1st Command line argument which is SERVERNAME
#print serverName
serverPort = sys.argv[2] # Getting the 2nd Command line argument which is SERVER PORT NUMBER
#print serverPort
filename=sys.argv[3]# Getting the 3rd Command line argument which is HTML FILENAME in the server
#print filename
clientSocket = socket(AF_INET, SOCK_STREAM) #creating the client socket with vriable name clientSocket; AF_INET intdicates network is IPv4; 2nd parameter indicates socket is of type SOCK_STREAM(i.e TCP socket)
clientSocket.connect((serverName,int(serverPort))) # Initiates TCP connection between client nd server; Paramters of connect method are servername and server port number
sentence = "GET "+filename+" HTTP/1.0\n\n" # HTTP GET request to be sent is assigne to variable sentence
#print sentence
clientSocket.send(sentence) # Sending the HTTP GET Request i.e. the string sentence through the client's socket and into the TCP connection
print 'From Server: '
while True:
    modifiedSentence = clientSocket.recv(1024) # Receiving the response from the server
    if modifiedSentence=="": break # If the response empty i.e. end of the response loop will be exited
    print modifiedSentence # printing the response text in the output screen
clientSocket.close() # Closing the socket connection
