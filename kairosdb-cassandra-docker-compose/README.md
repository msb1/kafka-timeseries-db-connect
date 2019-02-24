### kairosdb-cassandra-docker-compose

<h4> General Info </h4>
<ol>
  <li> 
    This is a docker-compose setup for KairosDB with Cassandra in Alpine Linux 
  </li>
  <li> 
    Grafana and Portainer are also included in the docker-compose for data and container management. 
  </li>
  <li>
    This setup is a combination of dockerfiles and docker-compose.yml files from the following 
    <ul>
      <li> https://github.com/wangdrew/docker-kairosdb </li>
      <li> https://github.com/Madhu1512/scylla-kairosdb-cql </li>
      <li> https://mannekentech.com/2017/01/28/playing-with-kairos-db/ </li>
      <li> https://github.com/elastisys/kairosdb-docker </li>
    </ul>
  </li>
  <li>
    Kudos to Peter Gardfjall from Elastisys for the python script to configure/connect Kairosdb to Cassandra. This was enabling and most helpful. 
  </li>
  <li> 
    The docker-compose.yml file can be readily extended to a Cassandra Cluster by subsituting the cassandra cluster yaml from the file cassandra-cluster.yml  in the current docker-compose.yml file
  </li>
</ol>



