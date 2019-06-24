package jdbc

import base.repository.IOContext
import scalikejdbc.DBSession

case class IOContextOnJDBC(session: DBSession) extends IOContext
