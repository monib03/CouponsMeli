# CouponsMeli

## Descripción Repositorio
Este repositoprio contiene el servicio de coupons de prueba para Meli

## Prerequisitos

Para ejecutar correctamente el proyecto es necesario tener las siguientes herramientas instaladas en el ambiente donde
se desea ejecutar

* Java JRE versión 11 o superior
* SBT 
* Docker

## Inicio

* Clonar el repositorio localmente.
        
      git clone git@github.com:monib03/CouponsMeli.git

* Crear localmente un contenedor de docker con postgres
  * Es necesario en ciertos sistemas operativos tener permisos de root para los siguientes comandos
  * Se debe reemplazar el valor de <password> con una contraseña segura que usted pueda recordar

        docker pull postgres
        docker run --name postgres-db -e POSTGRES_PASSWORD=<password> -d postgres 
  
* Obtener la ip del docker.

         docker inspect postgres-db | grep IPAddress

* Conectarse a la base de datos y crear una base de datos con el nombre coupon:

         psql -h IPAddress -p 5432 -U postgres
         create database coupon;
         create database prueba_coupon;

* Importar en un IDE de su preferencia el proyecto, tener en cuenta que se usa SBT
 
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

#### POST
##### Compra items coupon
* /coupon/

