package com.weshare.servicer.DAL.wesharehier

import scala.slick.lifted.TableQuery
import scala.util.Try
import com.weshare.servicer.schema.wesharehier.tablerow.UserRow
import com.weshare.servicer.schema.wesharehier.table.UsersTable
import scala.slick.driver.H2Driver.simple._

trait UsersDAL {
  val SUCCESSFUL_QUERY_INT: Int = 1

  def table = TableQuery[UsersTable]
  
  def get(id: Int) = Try {
    table.filter(_.id === id).list
  }

  def get(username: String) = Try {
    table.filter(_.username === username).list
  }

  def delete(username: String) = getQuery(username).map(_.delete)

  def delete(id: Int) = getQuery(id).map(_.delete)

  def update(username: String, newInfo: UserRow) = getQuery(username).map(_.update(newInfo))

  def update(id: Int, newInfo: UserRow) = getQuery(id).map(_.update(newInfo))
  
  protected def getQuery(id: Int) = Try { table.filter(_.id === id) }

  protected def getQuery(username: String) = Try { table.filter(_.username === username) }
}
