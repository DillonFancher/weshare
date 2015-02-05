package com.weshare.servicer.schema.wesharehier.table

import java.sql.Timestamp
import com.weshare.servicer.schema.wesharehier.tablerow.UserRow
import org.joda.time.DateTime
import scala.slick.driver.H2Driver.simple._

class UsersTable(tag: Tag) extends Table[UserRow](tag, "Users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username", O.NotNull)
  def adventureStatus = column[String]("adventure_status", O.NotNull)
  def createdAt = column[Timestamp]("created_at", O.NotNull)
  def * = (id, username, adventureStatus, createdAt) <> (UserRow.tupled, UserRow.unapply)
}

