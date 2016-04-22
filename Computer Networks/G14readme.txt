Team: G14, Kishore Kumar Vaddineni, Anil Kumar Kulla
How to execute:
---------------
Enter/modify the machine ipaddress/url in the ping("ip address/url") function in the code
type the following command to execute the file
>>sudo python filename (our file names are G14ping.py, G14ping_opt1.py(optional exercise 1), G14ping_opt2.py(optional exercise 2))

Tests made on our code:
------------------------
For Python Code for the ICMP Pinger:(G14ping.py)
-------------------------------------

Executed in "b146-16.cs.unm.edu"
--------------------------------
1. a machine inside the lab, one of the B146-nn (in the original statement we asked for it to ping a virtual machine of another group, but unfortunately, due to the changes in Virtual Box versions, we did not connect those virtual machines)
Ans: Our machine name is "b146-16.cs.unm.edu"

ping("b146-34.cs.unm.edu")
>>sudo python G14ping.py

Result of first 4 pings:
Pinging 64.106.21.163 using Python:

0.000699043273926
0.000493049621582
0.000437021255493
0.000190019607544

2. google.com
Ans:
ping("www.google.com")
>>sudo python G14ping.py

Result of first 4 pings:
Pinging 74.125.201.103 using Python:

Request timed out.
Request timed out.
Request timed out.
Request timed out.

3. unm.edu
ping("unm.edu")
>>sudo python G14ping.py

Result of first 4 pings:
Pinging 129.24.168.32 using Python:

0.00126218795776
0.00227403640747
0.00146102905273
0.00167608261108

-----End of results: Executed in "b146-16.cs.unm.edu"----------

Executed in machine not in the unm.edu domain(windows machine)
-------------------------------------------------------------

1. www.google.com
ping("www.google.com")
>>python G14ping.py

Result of first 4 pings:
Pinging 74.125.225.210 using Python:

0.0320000648499
0.125
0.0309998989105
0.0309998989105

*******The difference of running it from the unm domain is that from the unm domain we are unable to ping www.google.com where as from outside unm network we are able to ping it this is because " The Host administratively prohibited the access" i.e the system (or a router on route to the destination) is indicating that your system(unm machine) is not allowed to access the system(www.google.com) 

2.Machine in Japan: www.google.co.jp
ping("www.google.co.jp")
>>python G14ping.py

Result of first 4 pings:
Pinging 74.125.225.215 using Python:

0.0460000038147
0.0309998989105
0.0309998989105
0.0319998264313

3.Machine in Europe: www.google.co.uk
ping("www.google.co.uk")
>>python G14ping.py

Result of first 4 pings:
Pinging 74.125.225.175 using Python:

0.0319998264313
0.0310001373291
0.0309998989105
0.0320000648499

4.Machine in South America: www.google.com.ar
ping("www.google.com.ar")
>>python G14ping.py

Result of first 4 pings:
Pinging 74.125.225.191 using Python:

0.0310001373291
0.0310001373291
0.0309998989105
0.0320000648499



For optional exercise 1 of ICMP Pinger:(G14ping_opt1.py)
-------------------------------------

Executed in "b146-16.cs.unm.edu"
--------------------------------
1. unm.edu

ping("unm.edu")
>>python G14ping_opt1.py

Pinging unm.edu (129.24.168.32) using Python:

Ping 1 delay is 0.00361
Ping 2 delay is 0.00166
Ping 3 delay is 0.00205
Ping 4 delay is 0.00138

Ping Statistics:
Delay: Maximum = 0.00361, Average = 0.00218, Minimum = 0.00138
Packets: Sent = 4, Received = 4, Lost = 0 (0% loss)

2. www.google.com

ping("www.google.com")
>>python G14ping_opt1.py

Pinging www.google.com (74.125.201.99) using Python:

Requested timed out.
Requested timed out.
Requested timed out.
Requested timed out.

Ping Statistics:
Delay: Maximum = 0.00000, Average = 0.00000, Minimum = 0.00000
Packets: Sent = 4, Received = 0, Lost = 4 (100% loss)


---------------End of results For optional exercise 1 of ICMP Pinger:(G14ping_opt1.py)------


For optional exercise 2 of ICMP Pinger:(G14ping_opt2.py)
-------------------------------------

Executed in "b146-16.cs.unm.edu"
--------------------------------
1. unm.edu

ping("unm.edu")
>>python G14ping_opt1.py

Pinging unm.edu (129.24.168.32) using Python:

0.0028920173645
0.00129294395447
0.001708984375
0.00137495994568

Ping Statistics:
Delay: Maximum = 0.00289, Average = 0.00182, Minimum = 0.00129
Packets: Sent = 4, Received = 4, Lost = 0 (0% loss)

2. www.google.com

ping("www.google.com")
>>python G14ping_opt1.py

Pinging www.google.com (74.125.201.106) using Python:

Host administratively prohibited
Host administratively prohibited
Host administratively prohibited
Host administratively prohibited

Ping Statistics:
Delay: Maximum = 0.00000, Average = 0.00000, Minimum = 0.00000
Packets: Sent = 4, Received = 0, Lost = 4 (100% loss)


---------------End of results For optional exercise 2 of ICMP Pinger:(G14ping_opt2.py)------