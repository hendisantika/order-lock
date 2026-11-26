#!/usr/bin/env python3
"""Simulate two customers (Susan & Manda) ordering the same product concurrently.

Both read the same product version, Manda saves first, then Susan's save is
rejected by JPA optimistic locking (@Version) because her copy is stale.
"""
import os

import requests

BASE_URL = os.getenv("BASE_URL", "http://localhost:8080")

# Request A (Susan) GET
request_1 = requests.get(f"{BASE_URL}/products/1").json()
print("User A get:", request_1)

# Request B (Manda) GET
request_2 = requests.get(f"{BASE_URL}/products/1").json()
print("User B get:", request_2)

# Request B (Manda) Update
request_2["stock"] = 4
response = requests.put(f"{BASE_URL}/products", json=request_2)
print("User B save:", response.status_code, response.json())

# Request A (Susan) Update (blows up on an optimistic lock exception)
request_1["stock"] = 3
response = requests.put(f"{BASE_URL}/products", json=request_1)
print("User A save:", response.status_code, response.json())
