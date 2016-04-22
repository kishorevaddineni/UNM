#import socket module

from socket import * #Importing the socket module
serverSocket = socket(AF_INET, SOCK_STREAM) #creating the client socket with vriable name clientSocket; AF_INET intdicates network is IPv4; 2nd parameter indicates socket is of type SOCK_STREAM(i.e TCP socket)
#Prepare a sever socket
#Fill in start
serverSocket.bind(('',12014)) # Associating(binding) the server port number with the socket
serverSocket.listen(1) # This line has the server listen for TCP connection requests from client. parameter represents the maximum number of queued connections, here it is 1
print('The server is ready to receive') # printing the message on the screen
#Fill in end
while True: # infinite while loop so that server keeps on running to process the requests
 	#Establish the connection
 	print 'Ready to serve...' # printing the message on the screen
 	connectionSocket, addr = serverSocket.accept() #Fill in start #Fill in end # After getting the client request, the program invokes the accept() method for serverSocket which creates a new socket in the server called connectionSocket
 	try: # keeping the remaining code in the try block to handle the exceptions that may raise
	    message = connectionSocket.recv(1024) #Fill in start #Fill in end # Receiving the response from the server
	    filename = message.split()[1] # Splitting the received message i.e request to get the file name(here /HelloWorld.html)
	    f = open(filename[1:]) # Opening the file name specified by looking it into the folder where server code is located and assigning it to the variable f to handle the file further
	    outputdata = f.read() #Fill in start #Fill in end # Reading the file contents and assigning the content to outputdata variable
 	    #Send one HTTP header line into socket
	    #Fill in start
	    connectionSocket.send('HTTP/1.1 200 OK\r\n\r\n') # Sending the HTTP header response into the TCP connection through server socket(connectionSocket)
	    #Fill in end 
	    #Send the content of the requested file to the client
	    for i in range(0, len(outputdata)): 
                connectionSocket.send(outputdata[i]) # Sending the content of the requested file to the client throught TCP connection using connectionSocket
            connectionSocket.close()
 	except IOError: #To handle the IOError's(exceptions) raised while accessing the requested file
 	    #Send response message for file not found
 	    #Fill in start
            connectionSocket.send('404: Page not found') #Sending the Error Message saying file not found
            #Fill in end
	    #Close client socket
	    #Fill in start
            connectionSocket.close() # Closing the connection socket(i.e.,client socket)
	    #Fill in end 
serverSocket.close() # Closing the server socket
