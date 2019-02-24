### kafka-db-timeseries-connect

<h4> General Info </h4>
<ol>
  <li> 
    Sink connectors are included for KairosDB, InfluxDB and TimescaleDB (Postgres)
  </li>
  <li> 
    The Kafka Connect SinkTask class is not used here for these connectors as it is generally believed that well-tested, released SinkTask connectors are robust and reliable but that it is difficult to debug and test new connectors based on SinkTask's (and SourceTask's).
  </li>
  <li>
    A Producer/Consumer design pattern is used where a Kafka Consumer is actually the producer in the design pattern. A blockingqueue is implemented and a thread is started to take records from the queue and send to the three databases (in manners consistent with their documentation).
  </li>
  <li> 
    The Spring Boot code is written in a manner that readily allows the addition of multiple Kafka Consumers (Spring Kafka Listeners), multiple queues and multiple message consuming threads. This will facilitate any flexibility needed in testing and exercising these databases.
  </li>
  <li>
    A docker-compose.yml is available for each of the three time series DB's. For TimescaleDB and InfluxDB, this is located in a docker folder within the respective Spring Boot projects. For KairosDB, this docker build is significantly more complex so it is located within a separate folder on the main trunk - all files in this folder are needed for the KairosDB Docker build with Cassandra. A working, containerized time series database can be started with docker-compose up (and any appropriate flags).
  </li>
</ol>



