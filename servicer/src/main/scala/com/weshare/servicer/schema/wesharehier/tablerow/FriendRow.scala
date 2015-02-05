package com.weshare.servicer.schema.wesharehier.tablerow

import java.sql.Timestamp

case class FriendRow(id: Int, userId: Int, friendId: Int, createdAtStmp: Timestamp) extends BaseRow
