import Queue
import threading
from socket import * #Importing the socket module
num_workers = 10
queue = Queue.Queue()

serverSocket = socket(AF_INET, SOCK_STREAM) #creating the client socket with vriable name clientSocket; AF_INET intdicates network is IPv4; 2nd parameter indicates socket is of type SOCK_STREAM(i.e TCP socket)
#Prepare a sever socket
#Fill in start
serverSocket.bind(('',12014)) # Associating(binding) the server port number with the socket
serverSocket.listen(5) # This line has the server listen for TCP connection requests from client. parameter represents the maximum number of queued connections, here it is 1
print('The server is ready to receive') # printing the message on the screen
def worker(queue):
    while True:
        connectionSocket = queue.get()
        if connectionSocket is None:
            break

        try:
            message = connectionSocket.recv(1024)
            filename = message.split()[1]
            f = open(filename[1:])
            outputdata = f.read()
            connectionSocket.send('HTTP/1.0 200 OK\r\n\r\n')
            connectionSocket.send(outputdata)
        except IOError:
            connectionSocket.send('404 Not Found')
        finally:
            connectionSocket.close()

workers = []
for i in range(num_workers):
    t = threading.Thread(target=worker, args=(queue,))
    t.start()
    workers.append(t)

while True:
    #Establish the connection
    print 'Ready to serve...'
    connectionSocket, addr = serverSocket.accept()
    print 'Required connection', addr
    queue.put(connectionSocket)
