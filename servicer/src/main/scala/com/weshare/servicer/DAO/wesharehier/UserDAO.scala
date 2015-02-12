package com.weshare.servicer.DAO.wesharehier

import com.weshare.servicer.DAL.wesharehier.{DALCommon, UsersDAL}
import com.weshare.servicer.schema.wesharehier.tablerow.UserRow
import scala.concurrent.Future
import scala.util.Try

class UserDAO extends UsersDAL {

  override def get(id: Int): Future[Try[UserRow]] = Future {
    super.get(id).map(_.head)
  }
  
  override def get(username: String): Future[Try[UserRow]] = Future {
    super.get(username).map(_.head)
  }
  
  override def delete(id: Int): Future[Try[Boolean]] = Future {
    super.delete(id).map(_ == DALCommon.SUCCESSFUL_DELETE_QUERY_CODE)
  }
  
  override def delete(username: String): Future[Try[Boolean]] = Future {
    super.delete(username).map(_ == DALCommon.SUCCESSFUL_DELETE_QUERY_CODE)
  }

  override def update(username: String, newInfo: UserRow): Future[Try[Boolean]] = Future {
    super.update(username, newInfo).map(_ == DALCommon.SUCCESSFUL_UPDATE_QUERY_CODE)
  }

  override def update(id: Int, newInfo: UserRow): Future[Try[Boolean]] = Future {
    super.update(id, newInfo).map(_ == DALCommon.SUCCESSFUL_UPDATE_QUERY_CODE)
  }
  
}
