# KafkaToElasticToKibana
Kafka message to Elastic search and check with Kibana  

**It is a step_by_step note of building the data stream work.**

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
