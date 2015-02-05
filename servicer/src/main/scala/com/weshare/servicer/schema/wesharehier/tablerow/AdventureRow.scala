package com.weshare.servicer.schema.wesharehier.tablerow

import java.security.Timestamp

case class AdventureRow(id: Int, token: String, userId: Int, createdAtStmp: Timestamp) extends BaseRow
