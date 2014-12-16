package com.weshare.commoner

import com.typesafe.config.{ConfigFactory, Config}

class AppConfiguration(config: Config) {

  // validate vs. reference.conf
  config.checkValid(ConfigFactory.defaultReference(), "simple-lib")

  // non-lazy fields, we want all exceptions at construct time
  val foo = config.getString("simple-lib.foo")
  val bar = config.getInt("simple-lib.bar")
}

/**
 *
 * props.put("metadata.broker.list", "broker1:9092,broker2:9092")
 * props.put("serializer.class", "kafka.serializer.StringEncoder")
 * props.put("partitioner.class", "example.producer.SimplePartitioner")
 * props.put("request.required.acks", "1")
 *
 */

trait AppConfigration {

}