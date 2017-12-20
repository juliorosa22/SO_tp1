import scala.collection.mutable.Queue

class SignalBus(private var coreTurn:Int,private var _signal:Boolean , private var signalToContinue:Boolean , private var _cpuQueue:Queue[Process],private var _hdQueue:Queue[Process] , private var _printerQueue: Queue[Process] ){

    def this(){
      this(-1,false,true,new Queue[Process](),new Queue[Process](),new Queue[Process]())
    }

    def setSignalToContinue_=(newSignal: Boolean): Unit={this.synchronized{signalToContinue = newSignal}}

    def getSignalToContinue(): Boolean={this.synchronized{signalToContinue}}

    def setSignal_=(s: Boolean): Unit={this.synchronized{_signal = s}}
    def getSignal():Boolean={this.synchronized{_signal}}

    def setCoreTurn_=(id:Int): Unit={this.synchronized{coreTurn = id}}
    def getCoreTurn():Int={this.synchronized{coreTurn}}


    def cpuQueue = _cpuQueue
    def hdQueue = _hdQueue
    def printerQueue = _printerQueue

    def notPreemptiveTurn():Boolean={this.synchronized{ (_signal && !cpuQueue.isEmpty)} }

    //Funcoes que o cpu(executando o S.O) vai utilizar no barramento de sinal
    def getCpuQueue(recQ: Queue[Process]): Queue[Process]={
      this.synchronized{
          while(!cpuQueue.isEmpty){
              recQ+=cpuQueue.dequeue
          }
        coreTurn = SignalBus.RESOURCE_FREE//usado para indicar em sistemas nao preemptivos que
        setSignal_=(false)
        recQ
      }
    }

//versoes nao preemptivas
    def insertHDQueue(p: Process,coreTurn: Int): Unit={//metodo que vai ser utilizado pelo cpu para inserir na fila do HD
        this.synchronized{
            this.coreTurn = coreTurn
                _hdQueue+=p
        }
    }

    def insertPrinterQueue(p: Process,coreTurn: Int): Unit={//funcao que vai ser utilizada pelo cpu para inserir na fila da impressora
        this.synchronized{
            this.coreTurn= coreTurn
            _printerQueue+=p
        }
    }
    def getOneProcess():Process={//metodo usado pelo cpu no modo batch
      this.synchronized{
          this.coreTurn = SignalBus.RESOURCE_FREE
          cpuQueue.dequeue
      }
    }
//versoes preemptivas
    def insertHDQueue(p: Process): Unit={//metodo que vai ser utilizado pelo cpu para inserir na fila do HD
        this.synchronized{
                _hdQueue+=p
        }
    }

    def insertPrinterQueue(p: Process): Unit={//funcao que vai ser utilizada pelo cpu para inserir na fila da impressora
        this.synchronized{
            _printerQueue+=p
        }
    }


  //funcoes que o hd e a impressora vao usar no barramento


    def hasProcessInHDQueue():Boolean={this.synchronized{(!_hdQueue.isEmpty)}}
    def hasProcessInPrinterQueue():Boolean={this.synchronized{(!_printerQueue.isEmpty)}}


    def insertCpuQueue(p: Process): Unit={//metodo utilizado pela impressora e Disco Rigido para devolver processos ja executados pelos mesmos ao S.O
        this.synchronized{
                  _cpuQueue+=p
                  if(!_signal) _signal = true
        }
    }

    def getHDQueue(q: Queue[Process]):Queue[Process]={//metodo utilizado pelo Disco Rigido para pegar do barramento seus  processos pendentes

      this.synchronized{
        while(!hdQueue.isEmpty){
            q+=hdQueue.dequeue
        }
        q
      }
    }

    def getPrinterQueue(q: Queue[Process]): Queue[Process]={//metodo utilizado pela impressora para pegar do barramento seus  processos pendentes

      this.synchronized{
        while(!printerQueue.isEmpty){
            q+=printerQueue.dequeue
        }
          q
      }
    }

}

object SignalBus{
  val RESOURCE_FREE =(-1)

}
