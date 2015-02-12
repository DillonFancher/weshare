package com.weshare.servicer.DAL.wesharehier

import com.weshare.servicer.schema.wesharehier.table.AdventuresTable
import com.weshare.servicer.schema.wesharehier.tablerow.AdventureRow

import scala.util.Try
import scala.slick.lifted.TableQuery
import scala.slick.driver.H2Driver.simple._

trait AdventuresDAL {

  def get(id: Int) = getQuery(id).map(_.list)
  
  def get(token: String) = getQuery(token).map(_.list)
  
  def getForUser(userId: Int) = getForUserQuery(userId).map(_.list)
  
  def delete(id: Int) = getQuery(id).map(_.delete)
  
  def delete(token: String) = getQuery(token).map(_.delete)
  
  def update(id: Int, newData: AdventureRow) = getQuery(id).map(_.update(newData))
  
  def update(token: String, newData: AdventureRow) = getQuery(token).map(_.update(newData))

  protected def tableQuery = TableQuery[AdventuresTable]
  
  protected def getQuery(id: Int) = Try{ tableQuery.filter(_.id === id) }
  
  protected def getQuery(token: String) = Try{ tableQuery.filter(_.token === token) }

  protected def getForUserQuery(userId: Int) = Try{ tableQuery.filter(_.userId === userId) }
  
}
