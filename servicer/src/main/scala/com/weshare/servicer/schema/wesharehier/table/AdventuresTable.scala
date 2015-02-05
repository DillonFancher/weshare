package com.weshare.servicer.schema.wesharehier.table

import java.sql.Timestamp
import com.weshare.servicer.schema.wesharehier.tablerow.AdventureRow
import scala.slick.driver.H2Driver.simple._

class AdventuresTable(tag: Tag) extends Table[AdventureRow](tag, "Adventures") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def token = column[String]("token", O.NotNull)
  def userId = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
  def createdAt = column[Timestamp]("created_at", O.NotNull)
  def * = (id, token, userId, createdAt) <> (AdventureRow.tupled, AdventureRow.unapply)
}

