#!/bin/bash
echo "---------------------------------------------"
echo " Se inicia la creación de contenedores: "
echo "---------------------------------------------"
docker-compose -p meli up -d
sleep 6s

echo "---------------------------------------------"
echo " Se crea las bases de datos"
echo "---------------------------------------------"
PGPASSWORD=Prueba123* psql -h localhost -U postgres --file=crearBD.sql