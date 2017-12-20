import scala.collection.mutable.Queue
class HardDisk(private var _ID:Int, private var _myQueue:Queue[Process], private var signalBus:SignalBus) extends Runnable{

    def this(id: Int,bus: SignalBus){
      this(id,new Queue[Process](),bus)
    }

  def ID_=(id:Int):Unit={_ID = id}
  def ID = _ID
  def getMyBusSignal = signalBus//pega a referencia do barramento de sinal

  def myQueue = _myQueue

  def insertHDQueue(p:Process):Unit={
    myQueue+=p
  }


  def transferDoneProcess(p:Process):Unit ={
    getMyBusSignal.insertCpuQueue(p)
  }

  def runCurrentHdProcess():Process ={
      var  p = myQueue.dequeue
      var i = p.hdQuantum
        while(p.hdQuantum > 0){
            //println("|HD("+this.ID+")|__>"+"Executando o quantum:"+(p.hdQuantum - i))
            //i = i-1
            p.hdQuantum_=(p.hdQuantum-1)
        }
        if(!Results.testFlag)
        println("|HD("+this.ID+")|__>: Processo ("+p.ID+")Finalizou tarefa")
    //p.hdQuantum_=(0)
    p
  }

  def runHDResource():Unit ={

      while(getMyBusSignal.getSignalToContinue){
        if(this.getMyBusSignal.hasProcessInHDQueue)
            getMyBusSignal.getHDQueue(myQueue)//pega a fila de processos do barramento e armazena em myQueue

            while(!myQueue.isEmpty)//executa a fila de processos que anteriormente estava no barramento
              transferDoneProcess(runCurrentHdProcess())
      }

  }

  def run(){


      this.runHDResource
      //println("HD encerrou")

  }

}
