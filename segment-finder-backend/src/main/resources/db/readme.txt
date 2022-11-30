docker create --name postgres -e POSTGRES_PASSWORD=Welcome -p 5432:5432 postgres:11.5-alpine
docker start postgres

create_db.sh