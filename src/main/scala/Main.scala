object Main extends App {

  def main(args :Array[String]){
      println("Executando a main")

      var process = new Process(10,1)
      var printer = new Printer("Printer Generic usb");
      process.showProcess
      printer.showPrinter
      

  }

}
