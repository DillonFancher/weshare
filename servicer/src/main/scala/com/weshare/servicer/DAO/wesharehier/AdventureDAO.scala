package com.weshare.servicer.DAO.wesharehier

import scala.util.Try
import scala.concurrent.Future
import com.weshare.servicer.DAL.wesharehier.{DALCommon, AdventuresDAL}
import com.weshare.servicer.schema.wesharehier.tablerow.AdventureRow

class AdventureDAO extends AdventuresDAL {
  
  override def get(id: Int): Future[Try[AdventureRow]] = Future { super.get(id).map(_.head) }

  override def get(token: String): Future[Try[AdventureRow]] = Future { super.get(token).map(_.head) }

  override def getForUser(userId: Int): Future[Try[AdventureRow]] = Future { super.getForUser(userId).map(_.head) }

  override def delete(id: Int): Future[Try[Boolean]] = Future {
    super.delete(id).map(_ == DALCommon.SUCCESSFUL_DELETE_QUERY_CODE)
  }

  override def delete(token: String): Future[Try[Boolean]] = Future {
    super.delete(token).map(_ == DALCommon.SUCCESSFUL_DELETE_QUERY_CODE)
  }

  override def update(id: Int, newData: AdventureRow): Future[Try[Boolean]] = Future {
    super.update(id, newData).map(_ == DALCommon.SUCCESSFUL_UPDATE_QUERY_CODE)
  }

  override def update(token: String, newData: AdventureRow): Future[Try[Boolean]] = Future {
    super.update(token, newData).map(_ == DALCommon.SUCCESSFUL_UPDATE_QUERY_CODE)
  }
  
}
