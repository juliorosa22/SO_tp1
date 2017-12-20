import scala.collection.mutable.Queue
object Results{
  var results:Queue[Information] = new Queue[Information]//Fila que contem os resultados de uso dos cpu´s
  var testFlag = false
  def insertResults(cpu: Cpu){
    this.synchronized{

        var inf = new Information(cpu.coreId,cpu.occupiedClock,cpu.idleClock,100.00d*(cpu.occupiedClock.toDouble/(cpu.occupiedClock+cpu.idleClock).toDouble),cpu.averageReturnTime)
        results+=inf
    }

  }

  def showResults(){
    var r:Double=0d

    results.foreach(i=>{
      println("___________________")
        r = r+i.percUsage
        i.showInformation

    })
    println("___________________")
    r = (r.toDouble/results.length.toDouble).toDouble
    println("Porcentagem de uso médio do(s) "+results.length+f" Processador(es):$r%2.2f%%" )

  }

  def cleanQueue(){
      while(!results.isEmpty)
          results.dequeue

  }

}
