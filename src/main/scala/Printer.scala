import scala.collection.mutable.Queue
class Printer(private var _ID:Int, private var _myQueue:Queue[Process], private var signalBus:SignalBus) extends Runnable{

    def this(id: Int,bus: SignalBus){
      this(id,new Queue[Process](),bus)
    }

  def ID_=(id:Int):Unit={_ID = id}
  def ID = _ID
  def getMyBusSignal = signalBus//pega a referencia do barramento de sinal

  def myQueue = _myQueue

  def insertPrinterQueue(p:Process):Unit={
    myQueue+=p
  }


  def transferDoneProcess(p:Process):Unit ={
    getMyBusSignal.insertCpuQueue(p)
  }

  def runCurrentPrinterProcess():Process ={
      var  p = myQueue.dequeue
      var i = p.printerQuantum
        while(p.printerQuantum > 0){
            //println("|HD("+this.ID+")|__>"+"Executando o quantum:"+(p.printerQuantum - i))
            //i = i-1
            p.printerQuantum_=(p.printerQuantum-1)
        }
        if(!Results.testFlag)
        println("|Impressora["+this.ID+"]|__>: Processo:ID("+p.ID+")Encerrou")
    //p.printerQuantum_=(0)
    p
  }

  def runPrinterResource():Unit ={

      while(getMyBusSignal.getSignalToContinue){
        if(this.getMyBusSignal.hasProcessInPrinterQueue)
            getMyBusSignal.getPrinterQueue(myQueue)//pega a fila de processos do barramento e armazena em myQueue

            while(!myQueue.isEmpty)//executa a fila de processos que anteriormente estava no barramento
              transferDoneProcess(runCurrentPrinterProcess())
      }

  }

  def run(){


      this.runPrinterResource
    //println("impressora encerrou")

  }

}
