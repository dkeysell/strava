id=`docker container ls | grep postgres | cut -d " " -f 1`

docker exec -it $id psql -U postgres -c "create database kom_hunter;"

docker cp create_tables.sql $id:/create_tables.sql
docker exec -it $id psql -d kom_hunter -f create_tables_sequences.sql -U postgres

docker cp insert_data.sql $id:/insert_data.sql
docker exec -it $id psql -d kom_hunter -f insert_data.sql -U postgres
