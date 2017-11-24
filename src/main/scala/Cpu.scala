class Cpu( private var _coreId: Int, private var _myPManager: ProcessManager , private var _curProcess: Process) {

  def this(_coreId: Int){
      this(_coreId,null,null)
  }

  def this(_coreId: Int,myPManager: ProcessManager){
      this(_coreId,myPManager,null)
  }

  def coreId = _coreId//getter do id
  def myPManager = _myPManager
  def myPManager_= (recPManager: ProcessManager): Unit={this._myPManager = recPManager}//setter do gerenciador de processos desta cpu

  def curProcess = _curProcess//getter do processo atualmente sendo executado
  def curProcess_= (newCProcess: Process): Unit = {this._curProcess = newCProcess}// setter de um novo processo , vai ser usado no Dispatcher




  def processManagerRequest():Unit={
      this.myPManager.serveToCpu(this)


  }

  //escrever funcao para executar o processo


}
