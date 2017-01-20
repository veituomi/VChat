import java.net._
import java.io._
import scala.io._

class Service(val host: String, val port: Int = 666) {
    val chat = new Chat()

    def addChannel(name: String) {
        callServer("new\n" + name)
    }

    def getChannelNames() {
        callServer("list")(0).split(";").foreach { name => chat.addChannel(new Channel(name)) }
    }

    def getMessages() {
        chat.channels.foreach { channel => getNewMessages(channel) }
    }

    def getNewMessages(channel: Channel) {
        val chatChannel = chat.getChannel(channel.name)
        callServer("get\n" + channel.name + "\n" + channel.lastId).foreach {
            messagedata => chatChannel.addMessage(messagedata)
        }
    }
    
    def sendMessage(channel: String, nick: String, content: String) {
        callServer("send\n" + channel + "\n" + nick + "\n" + content)
    }

    def callServer(data: String) = {
        val s = new Socket(InetAddress.getByName(host), port)
        lazy val in = new BufferedSource(s.getInputStream).getLines()
        val out = new PrintStream(s.getOutputStream)
        out.println(data)
        out.flush()

        var response: List[String] = Nil
        while (in.hasNext) {
            response = in.next :: response
        }
        s.close()
        response
    }
}