class Menu{

  var a=0;
  var b=0;
  var c=2;
  var d=0;
  var e=0;
  var f=0;

  def get_a = a

  //Funções com as opções do menu.
  def fos{
      println("Sistema Operacional Fictício");
  }

  def tiposistema{
      println("Informe o tipo de sistema a ser simulado:");
      println("1 - Sistemas Batch");
      println("2 - Time Sharing");
      a = scala.io.StdIn.readInt();
      if(a!=1 && a!=2){
          erro
          tiposistema
      }
  }

  def recursosistema{
      println("Informe os recursos do sistema a ser simulado:");
      println("1 - Monoprogramado");
      println("2 - Multiprogramado");
      b = scala.io.StdIn.readInt();
      if(b==2){  //Se o sistema escolhido for multiprogramado, mostra a opçao prempção.
          prempcao
      }
      else if(b!=1 && b!=2){
          erro
          recursosistema
      }
  }

  def prempcao{
      println("Informe uma opção:");
      println("1 - Preemptivo");
      println("2 - Cooperativo(não preemptivo)");
      c = scala.io.StdIn.readInt();
      if(c!=1 && c!=2){
          erro
          prempcao
      }
  }

  def processamento{
      println("Informe a quantidade de processadores:");
      println("1 - 1 processador");
      println("2 - 2 processadores");
      d = scala.io.StdIn.readInt();
      if(d!=1 && d!=2){
          erro
          processamento
      }
  }

  def algoritmos{
      println("Informe o algoritmo de escalonamento a ser utilizado:");
      if(c==1){  //Opções para sistemas premptivo.
          println("1 - Round Robin");
          println("2 - Shortest Process Remaining Time");
          println("3 - Multiples Queues");
      }
      if(c==2 || b==1){  //Opções para sistemas não premptivo(cooperativo).
          println("1 - First-In First-Out (FIFO)");
          println("2 - Shortest Job First");
          println("3 - Priorities");
      }
      e = scala.io.StdIn.readInt();
      if(e!=1 && e!=2 && e!= 3){
          erro
          algoritmos
      }
  }

  def nprocessos{
      println("Informe a quantidade de processos:");
      f = scala.io.StdIn.readInt();
  }

  def erro{
      println("Opção inválida!");
  }

}
