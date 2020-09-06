#!/usr/bin/env bash
curl -X GET \
  http://localhost:8443/event-manager/event/127.0.34.4

curl -X GET \
  http://localhost:8443/event-manager/event/127.0.34.5

curl -X GET \
  http://localhost:8443/event-manager/event/198.0.34.4

curl -X GET \
  http://localhost:8443/event-manager/event/198.0.34.5

curl -X GET \
  http://localhost:8443/event-manager/event/198.0.88.4