# KafkaToElasticToKibana
Kafka message to Elastic search and check with Kibana  

*It is a step_by_step note of building the data stream work.*

OS: CentOS 7 x86_64  
Java: openjdk version "1.8.0_141"  
Kafka: confluent-oss-4.0.0-2.11.tar.gz  
Elastic: elasticsearch-6.1.1.tar.gz  
Kibana: kibana-6.1.1-linux-x86_64.tar.gz  

## Step 0. Precondition Java  
[how-to-install-java-sdk-on-centos](https://stackoverflow.com/questions/5104817/how-to-install-java-sdk-on-centos)
```
$ yum search java | grep 'java-'
$ sudo yum install java-1.8.0-openjdk*
$ java -version
  openjdk version "1.8.0_141"
  OpenJDK Runtime Environment (build 1.8.0_141-b16)
  OpenJDK 64-Bit Server VM (build 25.141-b16, mixed mode)
```

## Step 1. Install Confluent
[confluent quickstart](https://docs.confluent.io/current/quickstart.html)
```
$ wget http://packages.confluent.io/archive/4.0/confluent-oss-4.0.0-2.11.tar.gz
$ tar -xzvf confluent-oss-4.0.0-2.11.tar.gz
$ cd confluent-4.0.0/bin
```
start confluent and outputs
```
$ ./confluent start
Starting zookeeper
zookeeper is [UP]
Starting kafka
kafka is [UP]
Starting schema-registry
schema-registry is [UP]
Starting kafka-rest
kafka-rest is [UP]
Starting connect
connect is [UP]
```

## Step 2. Install Elastic
[elasticsearch installation](https://www.elastic.co/guide/en/elasticsearch/reference/current/_installation.html)
```
$ wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.1.1.tar.gz
$ tar -xzvf elasticsearch-6.1.1.tar.gz
$ cd elasticsearch-6.1.1/bin
```
start elastic and outputs
```
./elasticsearch
```
or in daemon mode
```
./elasticsearch -d
```

## Step 3. Install Kibana
[kibana start](https://www.elastic.co/webinars/getting-started-kibana?elektra=home&storm=sub2)
```
$ wget https://artifacts.elastic.co/downloads/kibana/kibana-6.1.1-linux-x86_64.tar.gz
$ tar -xzvf kibana-6.1.1-linux-x86_64.tar.gz
$ cd kibana-6.1.1-linux-x86_64/bin
$ ./kibana
```
if you want to access out of local, please add '-H 0.0.0.0'
```
$ ./kibana -H 0.0.0.0
```
then open http://<hostname_or_ip>:5601, kibana UI will load

## Step 4. Kafka producer to send message
[example code in java](https://github.com/liyancode/KafkaToElasticToKibana/blob/master/example/ProducerClient.java)   

## Step 5. Start kafka-sink-elastic connector
[connect ref](https://docs.confluent.io/current/connect/managing.html)  

create connector config file named 'es_kfkconntest' under '~/confluent-4.0.0/etc/kafka-connect-elasticsearch/'
```
$ cd ~/confluent-4.0.0/etc/kafka-connect-elasticsearch/
$ vi es_kfkconntest.properties
```
content of es_kfkconntest.properties:
```
name=es_kfkconntest
connector.class=io.confluent.connect.elasticsearch.ElasticsearchSinkConnector
tasks.max=1
topics=kfkconntest
key.ignore=true
connection.url=http://localhost:9200
type.name=kafka-connect
schema.ignore=true # pay attention to this prop
```
then
```
$ cd ~/confluent-4.0.0/bin
$ ./confluent load es_kfkconntest -d ../etc/kafka-connect-elasticsearch/es_kfkconntest.properties
```
check the connector status and outputs
```
$ curl localhost:8083/connectors/es_kfkconntest/status
{"name":"es_kfkconntest","connector":{"state":"RUNNING","worker_id":"<your_ip>:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"<your_ip>:8083"}],"type":"sink"}
```

## Step 6. Craete index in Kibana UI
![img](https://github.com/liyancode/KafkaToElasticToKibana/blob/master/Screen%20Shot%202017-12-21%20at%203.26.57%20PM.png)
![img](https://github.com/liyancode/KafkaToElasticToKibana/blob/master/Screen%20Shot%202017-12-21%20at%203.27.06%20PM.png)
then data
![img](https://github.com/liyancode/KafkaToElasticToKibana/blob/master/Screen%20Shot%202017-12-21%20at%203.27.26%20PM.png)


##Notes
*if confluent is dead, all connectors disappered(not sure why),but kafka topics are still there and after restarted kafka they are normal. So I recreated elastic connectors like what I do upon and check they are in good status, back to nomal.*
