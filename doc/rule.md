
``` json
[
  {
    "request": {
      "url": "https://abc/",
      "header": {
        "title":"value"
      }
    },
    "payloads": {},
    "checks": [
      {
        "place": "status|body|",
        "rel": "EQ|GT|LT|GE|LE|BE|REG",      
        "desired": ""
      }
    ],
    "responses": [
      {
        "type": "status" 
      }
    ]
  }
]
```