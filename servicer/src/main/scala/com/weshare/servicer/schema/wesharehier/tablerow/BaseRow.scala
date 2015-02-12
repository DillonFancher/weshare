package com.weshare.servicer.schema.wesharehier.tablerow

import java.sql.Timestamp
import org.joda.time.DateTime

trait BaseRow {

  val createdAtStmp: Timestamp
  def createdAt: DateTime = new DateTime(createdAtStmp)
  val id: Int
  
}
