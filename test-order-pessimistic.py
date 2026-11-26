#!/usr/bin/env python3
"""Simulate many customers ordering the same product concurrently (pessimistic locking).

Every request calls POST /products/{id}/order. The row is locked with
SELECT ... FOR UPDATE, so the requests are served one after another:
exactly `stock` orders succeed (200) and the rest get 409 Conflict (out of stock).
No order fails because of a lock conflict and the stock never goes below 0.
"""
import os
from concurrent.futures import ThreadPoolExecutor

import requests

BASE_URL = os.getenv("BASE_URL", "http://localhost:8080")
PRODUCT_ID = int(os.getenv("PRODUCT_ID", "2"))  # Orange, stock = 4
CUSTOMERS = int(os.getenv("CUSTOMERS", "8"))

print("Before:", requests.get(f"{BASE_URL}/products/{PRODUCT_ID}").json())


def order(customer):
    response = requests.post(f"{BASE_URL}/products/{PRODUCT_ID}/order", params={"quantity": 1})
    return customer, response.status_code


with ThreadPoolExecutor(max_workers=CUSTOMERS) as executor:
    for customer, status in executor.map(order, range(1, CUSTOMERS + 1)):
        print(f"Customer {customer} order: {status}")

print("After:", requests.get(f"{BASE_URL}/products/{PRODUCT_ID}").json())
