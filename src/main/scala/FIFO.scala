import scala.collection.mutable.Queue
//algoritmo de escalonamento first in first out
class FIFO  (val preemptiveFlag:Boolean = false ) extends Schudeler{


  override def runScheduling(pQueue: Queue[Process]): Queue[Process]={
    //println("Executando o Agendamento: FIFO")
    pQueue.foreach((e)=>{e.receivedQuantum_=(e.myQuantum)})//configura para os quantuns recebidos serem iguais ao necessario p finalizar a tarefa
      pQueue
  }

override  def showSchudelerConfig():Unit={
        println("FIFO|")
  }



}
