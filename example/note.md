*If build with Maven.*  

Because confluent dependency maven repo is not the normal one, there need to add the multi repo to pom.

Like:
```xml
    <repositories>
        <repository>
            <id>maven_repo</id>
            <name>maven_repo_center</name>
            <url>https://mvnrepository.com/</url>
        </repository>
        <repository>
            <id>confluent_repo</id>
            <name>confluent_repo_mvn</name>
            <url>http://packages.confluent.io/maven/</url>
        </repository>
    </repositories>
    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka -->
        <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka_2.12</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>io.confluent</groupId>
            <artifactId>kafka-avro-serializer</artifactId>
            <version>4.0.0</version>
        </dependency>
    </dependencies>
```
