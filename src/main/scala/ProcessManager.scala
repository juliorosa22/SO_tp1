import scala.collection.mutable.Queue

class ProcessManager(private var _myDis: Dispatcher, private var _mySch: Schudeler,private var _queues: Array[Queue[Process]]){

    def this(myDis: Dispatcher,mySch : Schudeler){
        this(myDis,mySch,new Array[Queue[Process]](3))//Array(0) fila do cpu
                                                    //Array(1) e Array(2) :HD e impressora
    }

    //getters
    def myDis = _myDis
    def mySch = _mySch
    def queues = _queues


    def serveToCpu(cpu: Cpu): Unit ={



    }

    def serveToHd(hd: HardDisk):Unit={



    }

    def serveToPrinter(imp: Printer):Unit={



    }

    def runDispatcher( cpu: Cpu): Unit ={//vai chamar o dispatcher para tirar um processo da fila


    }


}
