import scala.collection.mutable.Queue

trait Schudeler{

  def runScheduling(pQueue: Queue[Process]): Process

}
