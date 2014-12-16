package com.weshare.commoner.publisher

import java.util.Properties
import com.typesafe.config.Config
import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig

class KafkaProducer(topic: String, partitionKey: String, config: Config) {

  val props = new Properties()
  props.put("metadata.broker.list", "broker1:9092,broker2:9092")
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  props.put("partitioner.class", "example.producer.SimplePartitioner")
  props.put("request.required.acks", "1")

  val kafkaConfig= new ProducerConfig(props)
  val producer = new Producer[String, String](kafkaConfig)
  val keyedMessage = new KeyedMessage[String, String](topic, partitionKey, _: String)

  def write(data: String) = {
    val msg = mkMessage[String](data)
    producer.send(msg)
  }

  def mkMessage[B](data: B): KeyedMessage[String, B] = {
    new KeyedMessage[String, B](topic, partitionKey, data)
  }
}
