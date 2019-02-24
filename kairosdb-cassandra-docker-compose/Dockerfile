FROM openjdk:8-jre-alpine
MAINTAINER barnwaldo

# install bash shell and python in alpine linux
RUN apk add --no-cache bash python py-pip \
    && pip install 'cassandra-driver' \
    && apk del py-pip \
    && rm -rf /var/cache/apk

## Install KairosDB
RUN set -e \
    && mkdir -p /opt \
    && wget https://github.com/kairosdb/kairosdb/releases/download/v1.2.2/kairosdb-1.2.2-1.tar.gz -O /opt/kairosdb.tar.gz \
    && tar xzf /opt/kairosdb.tar.gz -C /opt/ \
    && rm /opt/kairosdb.tar.gz

COPY conf/kairosdb.properties.template /opt/kairosdb/conf/kairosdb.properties.template
COPY conf/logging/logback.xml /opt/kairosdb/conf/logging/logback.xml
COPY entrypoint.py /opt/entrypoint.py

EXPOSE 4242 8080
CMD ["/usr/bin/python", "/opt/entrypoint.py"]
