package com.weshare.servicer.schema.wesharehier.tablerow

import java.sql.Timestamp

case class UserLocationRow(id: Int, lat: Float, lon: Float, createdAtStmp: Timestamp) extends BaseRow
