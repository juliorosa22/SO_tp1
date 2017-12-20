import scala.collection.mutable.Queue

class ProcessManager(private var _myDis: Dispatcher, private var _mySch: Schudeler,private var _mainQueue:Queue[Process],private var _returnQueue:Queue[Process],private var _numberFinishedProcess:Int=0){


    def this(myDis: Dispatcher,mySch : Schudeler, numProcess: Int){
        this(myDis,mySch, ProcessFactory.buildProcessQueue(numProcess),new Queue[Process](),0 )//Array(0) fila do cpu,Array(1) armazena os processos que ja foram executados pelo hd e impressora
      }
    def this(myDis: Dispatcher,mySch : Schudeler, q: Queue[Process]){
          this(myDis,mySch, q,new Queue[Process](),0 )//Array(0) fila do cpu,Array(1) armazena os processos que ja foram executados pelo hd e impressora
        }


    //getters
    def myDis = _myDis
    def mySch = _mySch
    def mainQueue = _mainQueue
    def mainQueue_=(q:Queue[Process])={
      _mainQueue = q
    }
    def returnQueue = _returnQueue

    def mySch_=(sch:Schudeler){
          this._mySch = sch
    }

    def insertMainQueue(p: Process){
        mainQueue+=p
    }


    def insertReturnQueue(p: Process){
        returnQueue+=p
    }

    def headerMsg():String ={
        "|[ProcessManager]|>"

    }

  private  def executeScheduler():Unit={// funcao que aciona o algoritmo de agendamento de processos
       _mainQueue=this.mySch.runScheduling(mainQueue)
    }

  def verifySettings():Unit={
        print("\n"+this.headerMsg +" Algoritmo de Agendamento ")
        this.mySch.showSchudelerConfig
        if(!Results.testFlag){
          println(this.headerMsg+ "Fila de processos")
          Process.showProcessQueue(mainQueue)

        }

    }


  private def getProcessFromResources(cpu: Cpu):Unit={

          cpu.mySignalBus.getCpuQueue(returnQueue)//pega os processos que finalizaram I/O
          returnQueue.foreach(e=>{
            e.state_=(Process.READY_STATE)
          })

          while(!returnQueue.isEmpty){
              this.insertMainQueue(returnQueue.dequeue)//insere o processo de volta na fila princial de processos do cpu
          }

      }

  private def runInPreemptiveMode(cpu: Cpu): Unit={
        var resultProcess : Process = null
        if(!ProcessManager._firstExecuteScheduling && Results.testFlag){
            this.verifySettings
            ProcessManager._firstExecuteScheduling = true
        }
          if(cpu.mySignalBus.getSignal)
              this.getProcessFromResources(cpu)
          if(!mainQueue.isEmpty){//ocorre a troca de processos, caso na haja mais processos na fila principal do cpu, sinaliza ao cpu para encerrar
                executeScheduler //executa o algoritmo de agendamento
                resultProcess = mainQueue.dequeue
                if(resultProcess.remainingQuantum==resultProcess.myQuantum)
                  resultProcess.firstTime = cpu.getTotalClock
            }
                this.myDis.dispatchProcess(cpu,resultProcess)//coloca outro processo para executar na cpu
    }

  private def runInNotPreemptiveMode(cpu: Cpu): Unit={
        var resultProcess : Process = null
        if(!ProcessManager._firstExecuteScheduling){//executa somente uma vez o algoritmo de agendamento em sistemas batch
            executeScheduler
            if(Results.testFlag)
                this.verifySettings

            ProcessManager._firstExecuteScheduling = true
        }
              if(cpu.curProcess!=null && cpu.curProcess.state == Process.BLOCKED_STATE)//insere denovo o mesmo processo no cpu
                resultProcess = cpu.curProcess
              else{
                  if(!mainQueue.isEmpty){
                        resultProcess = mainQueue.dequeue
                        if(resultProcess.remainingQuantum==resultProcess.myQuantum)
                            resultProcess.firstTime = cpu.getTotalClock
                  }

              }
              this.myDis.dispatchProcess(cpu,resultProcess)//indica que o processo atual do cpu solicitou recurso e o cpu deve esperar até q ele consiga acessar o recurso

        }


  def serveToCpu(cpu: Cpu): Boolean ={//rotina que trata do gerenciamento do cpu
      this.synchronized{

          if(ProcessManager.numberOfProcess==(-1) ){ProcessManager.numberOfProcess = mainQueue.length}
              var prevProcess = cpu.curProcess
              cpu.tickIdleClock(1)
                if(prevProcess!=null){//caso haja um processo q estava anteriormente executando na cpu
                      if(prevProcess.remainingQuantum>0){//o processo q estava no cpu teve seu quantum expirado ou solicitou o  hd ou impressora
                            if(!prevProcess.hasSignalInThisQuantum){//caso o processo nao tenha solicitado a cpu ou hd neste indice de quantum
                              prevProcess.state_=(Process.READY_STATE)
                              this.insertMainQueue(prevProcess)//coloca o processo novamente na fila de processos da cpu
                            }else{//processo solicitou o hd ou impressora
                                    prevProcess.setQuantumSignal_=(0)
                                    prevProcess.state_=(Process.BLOCKED_STATE)
                                    if(mySch.preemptiveFlag){//modo preemptivo apenas insere o processo na fila do recurso que o processo desejar
                                      if(prevProcess.hdQuantum > 0)
                                          cpu.mySignalBus.insertHDQueue(prevProcess)
                                      else
                                        cpu.mySignalBus.insertPrinterQueue(prevProcess)//processo vai para fila da impressora no barramento
                                    }
                                }
                      }
                      else{//o processo termina
                            _numberFinishedProcess +=1
                            cpu.averageReturnTime += (cpu.getTotalClock-prevProcess.firstTime).toDouble
                            if(!Results.testFlag)
                                println("\n"+"|[ProcessManager]__>:Processo:ID["+prevProcess.ID+"]Terminou|")
                              cpu.curProcess_=(null)
                              prevProcess = null
                      }
              }

              if(mySch.preemptiveFlag)
                  runInPreemptiveMode(cpu)
              else
                runInNotPreemptiveMode(cpu)

              if(_numberFinishedProcess == ProcessManager.numberOfProcess){
                    cpu.averageReturnTime = (cpu.averageReturnTime/ProcessManager.numberOfProcess.toDouble)
                    cpu.mySignalBus.setSignalToContinue_=(false)
                    ProcessManager._firstExecuteScheduling = false
                    false
                }else
                    true

        }


    }



    object ProcessManager{
        var _firstExecuteScheduling: Boolean = false
        var numberOfProcess: Int=(-1)
    }

}
