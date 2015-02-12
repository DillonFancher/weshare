package com.weshare.servicer.DAO.wesharehier

import scala.util.Try
import scala.concurrent.Future
import com.weshare.servicer.schema.wesharehier.tablerow.FriendRow
import com.weshare.servicer.DAL.wesharehier.{DALCommon, FriendsDAL}

class FriendDAO extends FriendsDAL {
  
  override def get(id: Int): Future[Try[FriendRow]] = Future { super.get(id).map(_.head) }

  override def get(userId: Int, friendId: Int): Future[Try[FriendRow]] = Future {
    super.get(userId, friendId).map(_.head)
  }

  override def getFriends(userId: Int): Future[Try[List[FriendRow]]] = Future { super.getFriends(userId) }

  override def delete(id: Int): Future[Try[Boolean]] = Future {
    super.delete(id).map(_ == DALCommon.SUCCESSFUL_DELETE_QUERY_CODE)
  }

  override def delete(userId: Int, friendId: Int): Future[Try[Boolean]] = Future {
    super.delete(userId, friendId).map(_ == DALCommon.SUCCESSFUL_DELETE_QUERY_CODE)
  }

  override def update(id: Int, newData: FriendRow): Future[Try[Boolean]] = Future {
    super.update(id, newData).map(_ == DALCommon.SUCCESSFUL_UPDATE_QUERY_CODE)
  }

  override def update(userId: Int, friendId: Int, newData: FriendRow): Future[Try[Boolean]] = Future {
    super.update(userId, friendId, newData).map(_ == DALCommon.SUCCESSFUL_UPDATE_QUERY_CODE)
  }
  
}
