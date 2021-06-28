# Yew Ken, Chai
# Simple script to test /login page using default (admin) credentials.

import http.client
import json

conn = http.client.HTTPConnection('localhost', 8080)

headers = {'Content-type': 'application/json'}

foo = {'userId': 'Bob', 'password': 'Bon123', 'emailAddress': 'bob@domain.com', 'userAgent': 'Android 2.2'}
json_data = json.dumps(foo)

conn.request('POST', "/register", json_data, headers)

response = conn.getresponse()
print(response.read().decode())
