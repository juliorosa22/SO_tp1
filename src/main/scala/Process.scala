class Process(private var _ID: Int, private var _state:Int){
  //var description:String
  //var especifiedQuantum:Int
  //var remainingQuantum:Int
  //var maxQuantum
  def ID = _ID
  def ID_= (newId: Int): Unit = {
        _ID = newId
    }

  def state = _state
  def state_=( newState:Int):Unit={_state = newState}

  def showProcess={
    println("ID process:"+this._ID+"/"+this._state)
  }


}
