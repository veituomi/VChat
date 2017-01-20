import java.net._
import java.io._
import scala.io._

object Main extends App {
    while (true) {
        val command = StdIn.readLine("> ")
        var lines = command :: Nil

        val args = command match {
            case "list" => 0
            case "new" => 1
            case "get" => 2
            case "send" => 3
            case default => 0
        }

        var x = 0
        while (x < args) {
            val line = StdIn.readLine("> ")
            lines = line :: lines
            x += 1
        }

        println("Sending...")
        val s = new Socket(InetAddress.getByName("localhost"), 666)
        lazy val in = new BufferedSource(s.getInputStream).getLines()
        val out = new PrintStream(s.getOutputStream)

        out.println(lines.reverse.mkString("\n"))
        out.flush()

        println("Response:")
        while (in.hasNext) {
            println(in.next)
        }

        s.close()
    }
}