############################# DOCKER #############################
docker_up:
	docker compose up alertmanager -d --build --remove-orphans --force-recreate
	docker compose up prometheus -d --build --remove-orphans --force-recreate
	docker compose up grafana -d --build --remove-orphans --force-recreate

docker_down:
	docker compose down grafana --remove-orphans --force-recreate


############################# APP #############################
start_app:
	docker compose up app --build --remove-orphans --force-recreate


PHONY: docker_up docker_down start_app