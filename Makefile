run:
	mvn -q clean compile package exec:java

dev:
	mvn -q -e clean compile package exec:java

up:
	docker-compose up -d