############################# DOCKER #############################
docker_up:
	docker compose up grafana --build --remove-orphans --force-recreate

docker_down:
	docker compose down grafana --remove-orphans --force-recreate


############################# APP #############################
start_app:
	docker compose up app --build --remove-orphans --force-recreate


PHONY: docker_up docker_down start_app