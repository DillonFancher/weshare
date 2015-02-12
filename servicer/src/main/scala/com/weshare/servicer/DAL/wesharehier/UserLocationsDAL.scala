package com.weshare.servicer.DAL.wesharehier

import scala.util.Try
import scala.slick.lifted.TableQuery
import scala.slick.driver.H2Driver.simple._
import com.weshare.servicer.schema.wesharehier.table.UserLocationsTable

trait UserLocationsDAL {

  def tableQuery = TableQuery[UserLocationsTable]

  def all = Try{ tableQuery.list }

  def get(id: Int) = Try{ getQuery(id).map(_.list) }

  def delete(id: Int): Unit = getQuery(id).map(_.delete)

  def getQuery(id: Int) = Try{ tableQuery.filter(_.id === id) }
  
}
