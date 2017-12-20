import scala.collection.mutable.Queue

class Priorities( val preemptiveFlag:Boolean = false) extends Schudeler{

  def runScheduling(pQueue: Queue[Process]): Queue[Process]={
      pQueue.foreach((e)=>{e.receivedQuantum_=(e.myQuantum)})
      var newQueue= pQueue.sortWith(_.priority > _.priority)//ordena a fila de processos na ordem decrescente de prioridade
      newQueue

  }
  def showSchudelerConfig():Unit={
    println(" Fila de Prioridades")
  }

}
