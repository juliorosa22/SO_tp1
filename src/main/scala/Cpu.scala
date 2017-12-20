class Cpu( private var _coreId: Int, private var _myPManager: ProcessManager ,private var _mySignalBus: SignalBus, private var _curProcess: Process,private var _occupiedClock: Int,private var _idleClock:Int,var averageReturnTime:Double=0.0) extends Runnable {


  def this(_coreId: Int,myPManager: ProcessManager,bus: SignalBus){
      this(_coreId,myPManager,bus,null,0,0,0)
  }

  def coreId = _coreId//getter do id
  def myPManager = _myPManager
  def myPManager_= (recPManager: ProcessManager): Unit={this._myPManager = recPManager}//setter do gerenciador de processos desta cpu

  def mySignalBus = _mySignalBus
  def mySignalBus_=(bus: SignalBus): Unit={_mySignalBus = bus}

  def curProcess = _curProcess//getter do processo atualmente sendo executado
  def curProcess_= (newCProcess: Process): Unit = {this._curProcess = newCProcess}// setter de um novo processo , vai ser usado no Dispatcher

  def occupiedClock = _occupiedClock
  def tickOccupiedClock(numClocks: Int):Unit ={this._occupiedClock+=numClocks  }

  def idleClock = _idleClock
  def tickIdleClock(numClocks: Int):Unit ={this._idleClock+=numClocks}
  def getTotalClock():Int={ idleClock+occupiedClock}

  def msgHeaderCpu():String={
    "|[CPU]_(CoreId:"+this.coreId+")|>"
  }

  private def processManagerRequest():Boolean={
      this.myPManager.serveToCpu(this)
  }


private  def checkPManager():Boolean={
      if(this.myPManager!=null){
          //this.myPManager.verifySettings()
          true
      }else{
          println(this.msgHeaderCpu+":Erro CPU sem ProcessManager|")
          false
      }
  }




  private def runCurrentProcess():Unit={// @TODO melhorar a maneira como os quantuns sao analizados durante a execucao
      if(this.curProcess!= null){
        if(this.curProcess.state!=Process.BLOCKED_STATE){
          if(!Results.testFlag)
            println("\n"+this.msgHeaderCpu +"Iniciando Processo(ID:" +this.curProcess.ID+")|")
          while(this.curProcess.receivedQuantum>0 && this.curProcess.remainingQuantum >0 && !this.curProcess.hasSignalInThisQuantum){
                tickOccupiedClock(1)//incrementa uma unidade na quantidade de clock ocupado
              if(!Results.testFlag)  println(this.msgHeaderCpu+"\t"+this.curProcess.showProcessRunning())
                curProcess.remainingQuantum_=(curProcess.remainingQuantum -1)//diminui um quantum da quantidade restante para acabar a tarefa
                curProcess.receivedQuantum_=(curProcess.receivedQuantum-1)
          }
          if(this.curProcess.hasSignalInThisQuantum){
              if(!Results.testFlag)println(this.msgHeaderCpu+"Processo Solicitou["+curProcess.ID +"]"+{if(curProcess.hdQuantum>0) "Disco Rigido" else "Impressora"})
            }
        }else{//processo solicitou algum recurso(impressora ou hd)
                while(mySignalBus.getCoreTurn!=SignalBus.RESOURCE_FREE){
                }
                if(curProcess.hdQuantum>0){
                    tickIdleClock(curProcess.hdQuantum)
                      mySignalBus.insertHDQueue(curProcess,this.coreId)
                }else{
                    tickIdleClock(curProcess.printerQuantum)
                    mySignalBus.insertPrinterQueue(curProcess,this.coreId)
                }

                while(!mySignalBus.notPreemptiveTurn){

                }
                curProcess_=(mySignalBus.getOneProcess)
                curProcess.state_=(Process.RUNNING_STATE)
                runCurrentProcess//chamada recursiva para voltar a executar o processo quando volta do recurso
        }
      }else tickIdleClock(1)
    }



  private def runCpu(): Unit={
    if(checkPManager && this.mySignalBus!=null){

      while(this.processManagerRequest()){
            runCurrentProcess
      }
      mySignalBus.setSignalToContinue_=(false)
      Results.insertResults(this)
    }else println("Erro nas configurações")

  }

  def run(){
      this.runCpu

  }

}
