
import scala.collection.mutable.Queue

class ShortestProcessRemaining(val preemptiveFlag: Boolean= true) extends Schudeler{

  override def runScheduling(pQueue: Queue[Process]): Queue[Process]={

      pQueue.foreach(e=>{e.receivedQuantum_=(10)})
      var newQueue= pQueue.sortWith(_.myQuantum < _.myQuantum)//ordena a fila de processos na ordem crescente dos myQuantum
      newQueue

  }
  override def showSchudelerConfig():Unit={

      println("ShortestProcessRemaining|")


  }




}
