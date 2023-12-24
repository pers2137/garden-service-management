# SWAGGER

`http://localhost:8082/swagger-ui/index.html#`


# RABBITMQ - INSTALLATION

- `docker pull rabbitmq:3.12.7-management`
- `docker run --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:3.12.7-management`
- `http://localhost:15672/` `guest / guest`

---

# MOSQUITO - INSTALLATION

- `docker compose start`

---

# EXAMPLE DATA 

### REGISTER QUEUE
`{"ip":285255872,"mac":3477672765554641344,"sv":0}`

### DATA QUEUE

    {
    "mac": 3477672765554641344,
    "wilgotnosc_gleby": [
        {"input_line": 1,"value": 2},
        {"input_line": 2,"value": 2}
    ],
    "sun": [
    {"input_line": 1,"value": 1},
    {"input_line": 2,"value": 2}
    ],
	"dht11": [
		{"input_line": 1,"value_temp": 1,"value_humidity": 1},
		{"input_line": 2,"value_temp": 2,"value_humidity": 2}
	],
	"ds18b20": [
		{"rom_code": 1,"value": 1},
		{"rom_code": 2,"value": 2}
	],
	"timestamp": 123123123
	}



---

# MOSQUITO

## REGISTER MOSQUITO REQUEST

    mosquitto_pub -h localhost -p 1883 -t register -m "{"ip":285255872,"mac":"222222","sv":0}"


## DATA MOSQUITO REQUEST


    {
    "mac":"333333",
    "timestamp":1702335515,
    "analog":[1500,1961,1900,1400,1300,1250,1111,1999,2020,2200,2500,2600,2800,2900,2333,2444,3050,3333,3400,3500,3600,3700,3800,3900,100,300,400,500,4100,4200,4300,4400],"dht11":[{"line":2,"temp":23.4,"hum":44},{"line":5,"temp":17,"hum":40}],"ds18b20":[{"address":123412341,"temp":23.3}]
    }
