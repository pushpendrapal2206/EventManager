#!/usr/bin/env bash
curl -X POST \
  http://localhost:8443/event-manager/event \
  -H 'content-type: application/json' \
  -d '{
	"clientIpAddress": "127.0.34.4"
}'

curl -X POST \
  http://localhost:8443/event-manager/event \
  -H 'content-type: application/json' \
  -d '{
	"clientIpAddress": "127.0.34.5"
}'

curl -X POST \
  http://localhost:8443/event-manager/event \
  -H 'content-type: application/json' \
  -d '{
	"clientIpAddress": "198.0.34.4"
}'

curl -X POST \
  http://localhost:8443/event-manager/event \
  -H 'content-type: application/json' \
  -d '{
	"clientIpAddress": "198.0.34.5"
}'

curl -X POST \
  http://localhost:8443/event-manager/event \
  -H 'content-type: application/json' \
  -d '{
	"clientIpAddress": "198.0.35.5"
}'

curl -X POST \
  http://localhost:8443/event-manager/event \
  -H 'content-type: application/json' \
  -d '{
	"clientIpAddress": "198.0.88.4"
}'

curl -X POST \
  http://localhost:8443/event-manager/event \
  -H 'content-type: application/json' \
  -d '{
	"clientIpAddress": "198.0.88.4"
}'