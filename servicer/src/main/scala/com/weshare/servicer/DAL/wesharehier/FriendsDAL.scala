package com.weshare.servicer.DAL.wesharehier

import com.weshare.servicer.schema.wesharehier.tablerow.FriendRow

import scala.util.Try
import scala.slick.lifted.TableQuery
import scala.slick.driver.H2Driver.simple._
import com.weshare.servicer.schema.wesharehier.table.FriendsTable

trait FriendsDAL {

  def all = Try{ tableQuery.list }

  def get(id: Int) = getQuery(id).map(_.list )

  def get(userId: Int, friendId: Int) = getQuery(userId, friendId).map(_.list)

  def getFriends(userId: Int) = getFriendsQuery(userId).map(_.list)

  def delete(id: Int) = getQuery(id).map(_.delete)

  def delete(userId: Int, friendId: Int) = getQuery(userId, friendId).map(_.delete)
  
  def update(id: Int, newData: FriendRow) = getQuery(id).map(_.update(newData))

  def update(userId: Int, friendId: Int, newData: FriendRow) = getQuery(userId, friendId).map(_.update(newData))

  private def tableQuery = TableQuery[FriendsTable]

  protected def getQuery(id: Int) = Try{ tableQuery.filter(_.id === id) }
  
  protected def getQuery(userId: Int, friendId: Int) = Try{
    tableQuery.filter(f =>
      (f.userId   === userId && f.friendId === friendId) ||
      (f.friendId === userId && f.userId   === friendId)
    )
  }

  protected def getFriendsQuery(userId: Int) = Try{
    tableQuery.filter(f => f.userId === userId || f.friendId === userId)
  }
  
}
