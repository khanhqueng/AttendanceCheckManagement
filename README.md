## How to use
Create network 
```
docker network create -d bridge attendance-network
```
Pull docker image
```
docker pull quangkhanh288/attendance-server
```
Run mysql
```
docker compose -f docker-compose.yml up -d mysql
```
Run server
```
docker compose -f docker-compose.yml up -d codebase
```
