import java.net._
import java.io._
import scala.io._

object Main extends App {
    val chat = new Chat()
    val server = new ServerSocket(666)

    while (true) {
        try {
            val s = server.accept()
            val in = new BufferedSource(s.getInputStream).getLines()
            val out = new PrintStream(s.getOutputStream)

            in.next match {
                case "list" => {
                    println("Channels requested.")
                    out.println(chat.getChannelNames().mkString(";"))
                }
                case "new" => {
                    println("Create a new channel.")
                    chat.addChannel(new Channel(in.next))
                }
                case "get" => {
                    println("Messages from channel requested.")
                    out.println(chat.getChannel(in.next).getMessagesAfter(in.next.toInt).map(
                        m => m.id + ";" + m.sender + ";" + m.content
                    ).mkString("\n"))
                }
                case "send" => {
                    println("Send a new message")
                    chat.getChannel(in.next).addMessages(new Message(Message.nextId, in.next, in.next) :: Nil)
                }
                case default => {
                    println("Unknown command: " + default)
                }
            }

            out.flush()
            s.close()
        } catch {
            case _: Throwable => println("An error occurred. Never mind.")
        }
    }
}