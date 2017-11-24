class Process(private var _ID: Int, private var _priority:Int, private var _state:Int, private var _myQuantum:Int, var _receivedQuantum:Int){

//myQuantum é o tempo q o processo vai precisar ao todo para terminar
//receivedQuantum é o quantum q o escalonador forneceu ao processo
  def this(_ID: Int, _state: Int, myQuantum: Int){//construtor secundario com apenas 3 argumentos, usar este ao criar processos
    this(_ID,0,_state,myQuantum,0);//
  }

//setters e getters dos atributos da classe
  def ID = _ID
  def ID_= (newId: Int)= this._ID = newId

  def priority = _priority
  def priority_= (newPriority: Int)= this._priority = newPriority

  def state = _state
  def setState_=(newState: Int)= this._state = newState

  def myQuantum = _myQuantum
  def myQuantum_= (newQ: Int)= this._myQuantum = newQ

  def receivedQuantum = _receivedQuantum
  def receivedQuantum_= (newReceivedQuantum: Int)= this._receivedQuantum = newReceivedQuantum


  def showProcess={
    println("ID process:"+this._ID+"/"+this._state)
  }


}

object  Process{
    val BLOCKED_STATE = 0
    val RUNNING_STATE=1
    val READY_STATE=2

}
