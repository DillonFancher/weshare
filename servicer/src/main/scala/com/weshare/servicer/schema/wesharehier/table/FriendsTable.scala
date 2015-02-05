package com.weshare.servicer.schema.wesharehier.table

import java.sql.Timestamp
import com.weshare.servicer.schema.wesharehier.tablerow.FriendRow
import org.joda.time.DateTime
import scala.slick.driver.H2Driver.simple._

class FriendsTable(tag: Tag) extends Table[FriendRow](tag, "Friends") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Int]("user_id", O.NotNull)
  def friendId = column[Int]("friend_id", O.NotNull)
  def createdAt = column[Timestamp]("created_at", O.NotNull)
  def * = (id, userId, friendId, createdAt) <> (FriendRow.tupled, FriendRow.unapply)
}

