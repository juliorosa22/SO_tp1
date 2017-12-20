import scala.collection.mutable.Queue

trait Schudeler{
  val preemptiveFlag: Boolean
  def runScheduling(pQueue: Queue[Process]): Queue[Process]
  def showSchudelerConfig():Unit

}
