import scala.collection.mutable.Queue

class ShortestJobFirst( val preemptiveFlag:Boolean = false) extends Schudeler{


  def runScheduling(pQueue: Queue[Process]): Queue[Process]={
      //println("Executando o Agendamento: ShortestJobFirst")
      pQueue.foreach((e)=>{e.receivedQuantum_=(e.myQuantum)})
      var newQueue= pQueue.sortWith(_.myQuantum < _.myQuantum)//ordena a fila de processos na ordem crescente dos myQuantum
      //Process.showProcessQueue(newQueue)
      newQueue

  }
  def showSchudelerConfig():Unit={
    println(" Shortest Job First")
  }

}
