class Dispatcher{
  def dispatchProcess(cpuRec : Cpu,p: Process): Unit={
          if(p!=null)
            p.state_=(Process.RUNNING_STATE)

          cpuRec.curProcess_=(p);
          //println("Processo dentro do cpu")

          //println(cpuRec.curProcess.showProcess())

      }

  }
