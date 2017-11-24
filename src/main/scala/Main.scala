object Main{

  def main(args :Array[String]){
      println("Executando a main");
  val r = scala.util.Random;
  var a=0;
    for( a <- 1 to 10){
      println(r.nextInt(100));

    }




  }

}
