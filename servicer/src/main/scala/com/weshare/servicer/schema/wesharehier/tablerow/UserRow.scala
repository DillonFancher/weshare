package com.weshare.servicer.schema.wesharehier.tablerow

import java.sql.Timestamp

case class UserRow(id: Int, username: String, adventureStatus: String, createdAtStmp: Timestamp) extends BaseRow
