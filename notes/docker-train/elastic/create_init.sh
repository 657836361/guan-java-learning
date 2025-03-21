#! /bin/bash

docker run -d --name es01 \
  --net elastic \
  -p 9200:9200 \
  -it \
  -m 1GB \
  -v /home/guan/elastic/elasticsearch/data:/usr/share/elasticsearch/data \
  -v /home/guan/elastic/elasticsearch/logs:/usr/share/elasticsearch/logs \
  -v /home/guan/elastic/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
  docker.elastic.co/elasticsearch/elasticsearch:8.17.3;

docker run -d --name kib01 \
  --net elastic \
  -p 5601:5601 \
  -v /home/guan/elastic/kibana/data:/usr/share/kibana/data \
  docker.elastic.co/kibana/kibana:8.17.3;