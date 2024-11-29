## Pasos para levantar
### Empaquetar y crear la imagen
```
mvn clean package
docker build -t charligsol/api-gateway .
```
### Levantar el contenedor
```
mvn clean package
docker run -p 8090:8090 charligsol/api-gateway 
```

https://github.com/piotrholda/spring-webflux-jwt/tree/main