#!/bin/bash

ssh-add

echo
echo "Iniciando docker"
echo "-------------------------------------------------------"
sudo systemctl start docker
sudo docker start meli_db_1
