import scala.collection.mutable.Queue

class MultiplesQueues( val preemptiveFlag:Boolean = true) extends Schudeler{

  def runScheduling(pQueue: Queue[Process]): Queue[Process]={
      var newQueue= pQueue.sortWith(_.priority > _.priority)//ordena a fila de processos na ordem decrescente de prioridade
      newQueue.foreach(p=>{
              if(p.priority<=3){//1,2,3
                if(p.priority!=1)
                  p.priority_=(p.priority-1)
                  p.receivedQuantum_=(15)
                }
              if(p.priority>3 && p.priority<=7 ){//4,5,6,7
                  p.receivedQuantum_=(10)
                  p.priority_=(p.priority-1)
                }
              else{
                    p.priority_=(p.priority-1)
                    p.receivedQuantum_=(5)
              }
      })
        newQueue
  }
  def showSchudelerConfig():Unit={
    println(" Filas Múltiplas")
  }

}
