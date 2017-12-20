class Information(var cpuId: Int, var nOccupiedClocks:Int,var nIdleClocks: Int, var percUsage: Double , var returnTime: Double){

  def this(){
        this(0,0,0,0,0)
  }

  def showInformation(){
      println("[Cpu ID:"+cpuId+"]")
      println("---Numero de Clocks Ocupados:"+nOccupiedClocks)
      println("---Numero de Clocks Ociosos:"+nIdleClocks)
      println(f"---Porcentagem de Uso do CPU:$percUsage%2.2f%%")
      println(f"---Tempo Medio de retorno:$returnTime%2.2f Ciclos de cpu")
  }


}
