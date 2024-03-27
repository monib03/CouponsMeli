# CouponsMeli

## Descripción Repositorio
Este repositoprio contiene el servicio de coupons de prueba para Meli

## Prerequisitos

Para ejecutar correctamente el proyecto es necesario tener las siguientes herramientas instaladas en el ambiente donde
se desea ejecutar

* Java JRE versión 11 o superior
* SBT 
* Docker
* Docker-compose
* Sql

## Inicio

* Clonar el repositorio localmente.
        
      git clone git@github.com:monib03/CouponsMeli.git

* Crear localmente un contenedor de docker con postgres
  * Es necesario en ciertos sistemas operativos tener permisos de root para los siguientes comandos
  * Se debe reemplazar el valor de <password> con una contraseña segura que usted pueda recordar

        bash documentacion/inicio.sh  

* Importar en un IDE de su preferencia el proyecto, tener en cuenta que se usa SBT
 
* Levantar ambiente para su ejecución

        bash documentacion/subirAmbienteLocal.sh

* Para ejecutar el proyecto basta con el siguiente comando

      sbt run

* Las pruebas del proyecto se pueden correr con el siguiente comando

      sbt review

## Servicios
#### GET
##### Version
* /coupon/version
##### Top 5 items favoritos
  * /coupon/stats
    * Esta funcionalidad no se encuentra conectada a los servicios de Meli, para hacer pruebas en ambiente se deben 
    registrar items, usuarios y bookmarks en BD, se pueden usar los que se encuentran en el archivo documentacion/inserts.sql

          curl --location 'http://localhost:9000/coupon/stats'

    * Response example
          
          [{"id":"MLA1","quantity":15},{"id":"MLA2","quantity":10}]

#### POST
##### Compra items coupon
* /coupon/
        
        curl --location 'http://localhost:9000/coupon' \ --header 'Content-Type: application/json' \ --data '{"item_ids": ["MLA1523624734","MLA1523624720","MLA1523624746","MLA1523624728","MLA1523624750","MLA1523624760"],"amount": 17000 }'

    * Response example

             {"items_ids": ["MLA1523624750","MLA1523624720","MLA1523624760","MLA1523624728"],"total": 16976.0 }
## Documentación

![Alt text](documentacion/Diagrams.png?raw=true "Diagrama")
