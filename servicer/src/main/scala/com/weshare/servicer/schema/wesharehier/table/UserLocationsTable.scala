package com.weshare.servicer.schema.wesharehier.table

import java.sql.Timestamp
import com.weshare.servicer.schema.wesharehier.tablerow.UserLocationRow
import org.joda.time.DateTime
import scala.slick.driver.H2Driver.simple._

class UserLocationsTable(tag: Tag) extends Table[UserLocationRow](tag, "UserLocations") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Int]("user_id", O.NotNull)
  def lat = column[Float]("lat", O.NotNull)
  def lon = column[Float]("lon", O.NotNull)
  def createdAt = column[Timestamp]("created_at", O.NotNull)
  def * = (id, lat,lon, createdAt) <> (UserLocationRow.tupled, UserLocationRow.unapply)
}