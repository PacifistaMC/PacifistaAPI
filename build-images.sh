#!/bin/bash
set -e

PLATFORMS="linux/amd64,linux/arm64"

build_and_push() {
  SERVICE_NAME=$1
  SERVICE_DIR=$2
  IMAGE="ghcr.io/pacifistamc/pacifista-api-${SERVICE_NAME}:${IMAGE_TAG}"

  echo "➡️ Building $SERVICE_NAME from $SERVICE_DIR"

  docker buildx build \
    --platform $PLATFORMS \
    --build-arg service_name=$SERVICE_NAME \
    --build-arg service_base_dir=$SERVICE_DIR \
    --tag $IMAGE \
    --push \
    .
}

# Liste de tes microservices
build_and_push "server-box" "server/box"
build_and_push "server-guilds" "server/guilds"
build_and_push "server-permissions" "server/permissions"
build_and_push "server-players-sync" "server/players/sync"
build_and_push "server-players-data" "server/players/data"
build_and_push "server-sanctions" "server/sanctions"
build_and_push "server-warps" "server/warps"
build_and_push "server-jobs" "server/jobs"
build_and_push "server-claim" "server/claim"
build_and_push "server-essentials" "server/essentials"
build_and_push "server-shop" "server/shop"

build_and_push "support-tickets" "support/tickets"

build_and_push "web-news" "web/news"
build_and_push "web-shop" "web/shop"
build_and_push "web-user" "web/user"
build_and_push "web-vote" "web/vote"
