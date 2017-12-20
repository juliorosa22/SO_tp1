import scala.collection.mutable.Queue
//import java.util.concurrent.Executors


object Main{
    def main(args :Array[String]){
	//Instanciando objeto Menu
      var Mymenu = new Menu
      //Opções do Menu

      Mymenu.fos
      Mymenu.tiposistema
      Mymenu.recursosistema
      Mymenu.processamento
      Mymenu.algoritmos
      Mymenu.nprocessos

      var pm : ProcessManager=null
      var cpus : Array[Cpu] = null
      var threads : Array[Thread] = null
      var hd:HardDisk=null
      var printer:Printer=null
      var busSignal = new SignalBus()

      var t1 : Thread =null
      var t2 : Thread =null
      //Se o sistema for preemptivo.
      if(Mymenu.c==1){
        Mymenu.e match {
            case 1=> pm = new ProcessManager(new Dispatcher(),new RoundRobin(),Mymenu.f)
            case 2=> pm = new ProcessManager(new Dispatcher(),new ShortestProcessRemaining(),Mymenu.f)
            case 3=> pm = new ProcessManager(new Dispatcher(),new MultiplesQueues(),Mymenu.f)
        }
      }
      //Se o sistema for cooperativo(não premptivo).
      if(Mymenu.c ==2 || Mymenu.b==1){
        Mymenu.e match {
            case 1=> pm = new ProcessManager(new Dispatcher(),new FIFO(),Mymenu.f)
            case 2=> pm = new ProcessManager(new Dispatcher(),new ShortestJobFirst(),Mymenu.f)
            case 3=> pm = new ProcessManager(new Dispatcher(),new Priorities(),Mymenu.f)
        }
      }
      pm.verifySettings
      printer = new Printer(7,busSignal)
      hd = new HardDisk(10,busSignal)
      cpus = new Array[Cpu](Mymenu.d)
      threads = new Array[Thread](Mymenu.d+2)

      for(i<-1 to Mymenu.d)
          cpus(i-1) = new Cpu(i,pm,busSignal)
      for(i<-1 to (Mymenu.d))
          threads(i-1) = new Thread(cpus(i-1))
      threads(Mymenu.d) = new Thread(hd)
      threads(Mymenu.d+1) = new Thread(printer)


      threads.foreach(t=>{t.start})
      threads.foreach(t=>{t.join})
      Results.showResults




    ///Relatorio de testes
/*
      Results.testFlag = true
      var busSignal: SignalBus=null

      var threads = new Array[Thread](3)
      var schudelers =new Array[Schudeler](6)
      var q = ProcessFactory.buildProcessQueue(100)//cria uma fila de processos para teste
      schudelers(0) = new FIFO()
      schudelers(1) = new ShortestJobFirst()
      schudelers(2) = new Priorities()
      schudelers(3) = new RoundRobin()
      schudelers(4) = new ShortestProcessRemaining()
      schudelers(5) = new MultiplesQueues()
      Process.showProcessQueue(q)
      for(j<-1 to schudelers.length){

          busSignal = new SignalBus()
          threads(0) = new Thread(new Cpu(j,new ProcessManager(new Dispatcher(),schudelers(j-1),ProcessFactory.cloneProcessQueue(q)),busSignal))
          threads(1) = new Thread(new HardDisk(j+10,busSignal))
          threads(2) = new Thread(new Printer(j+11,busSignal))
          threads.foreach(t=>{t.start})
          threads.foreach(t=>{t.join})
          Results.showResults
          Results.cleanQueue

        }
*/
      }
}
