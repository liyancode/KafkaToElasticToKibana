import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class ProducerClient {
    public static void main(String args[]){

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); //kafka server localhost or your server
        props.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put("schema.registry.url", "http://localhost:8081");// registery server
        props.put("value.schema", "{\"type\":\"record\",\"name\":\"kafkaconnecttestrecord\",\"fields\":[{\"name\":\"sname\",\"type\":\"string\"},{\"name\":\"sage\",\"type\":\"int\"}]}");

        KafkaProducer producer = new KafkaProducer(props);

        String topic = "kfkconntest";

        String userSchema = "{\"type\":\"record\",\"name\":\"kafkaconnecttestrecord\",\"fields\":[{\"name\":\"sname\",\"type\":\"string\"},{\"name\":\"sage\",\"type\":\"int\"}]}";

        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);
        GenericRecord avroRecord = new GenericData.Record(schema);


        ProducerRecord<Object, Object> record;

        Random rnd=new Random();

        for(int i=0;i<15;i++){
            try {
                avroRecord.put("sname", "Tom");
                avroRecord.put("sage", (Math.abs(rnd.nextInt())%25+15));
                record = new ProducerRecord(topic, "kfkconntest_key", avroRecord);
                producer.send(record).get();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
