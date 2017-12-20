import scala.collection.mutable.Queue

class RoundRobin(val preemptiveFlag: Boolean= true) extends Schudeler{

  override def runScheduling(pQueue: Queue[Process]): Queue[Process]={

      pQueue.foreach(e=>{e.receivedQuantum_=(10)})
      pQueue

  }
  override def showSchudelerConfig():Unit={

      println("Round Robin|")


  }



}
