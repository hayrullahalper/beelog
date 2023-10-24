run:
	mvn -q clean compile package exec:java

dev:
	mvn -q -e clean compile package exec:java

check:
	mvn -q -e checkstyle:checkstyle

up:
	docker-compose up -d
