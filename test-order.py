#!/usr/bin/python
import requests

# Request A (Susan) GET
response = requests.get("http://localhost:8080/products/1")
request_1 = response.json()
print "User A get:", request_1

# Request B (Manda) GET
response = requests.get("http://localhost:8080/products/1")
request_2 = response.json()
print "User B get:", request_2

# Request B (Manda) Update
request_2["stock"] = 4
response = requests.put("http://localhost:8080/products", json=request_2)
print "User B save:", response.json()

# Request A (Susan) Update (Blows up on a lock exception)
request_1["stock"] = 3
response = requests.put("http://localhost:8080/products", json=request_1)
print "User A save:", response.json()
