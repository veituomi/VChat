import scala.swing._
import scala.swing.event._
import javax.swing.JComboBox
import javax.swing.JTextArea
import javax.swing.JScrollPane

class Window extends MainFrame {
    val service = new Service("localhost")

    title = "VChat version 0.0"
    preferredSize = new Dimension(666, 333)

    var createChannelText = new TextField("")
    var createChannelButton = new Button("Add channel")

    var channels = new JComboBox(Array("default"))
    var messages = new JTextArea("Nothing new here. Go away.", 10, 50)
    var messagesScrollPane = new JScrollPane(messages)
    
    var sendMessageNameText = new TextField("Nick")
    var sendMessageContentText = new TextField("")
    var sendMessageButton = new Button("Send")

    contents = new BoxPanel(Orientation.Vertical) {
        contents += new BoxPanel(Orientation.Horizontal) {
            contents += createChannelText
            contents += createChannelButton
        }
        contents += Component.wrap(channels)
        contents += Component.wrap(messagesScrollPane)
        contents += new BoxPanel(Orientation.Horizontal) {
            contents += sendMessageNameText
            contents += sendMessageContentText
            contents += sendMessageButton
        }
    }

    listenTo(createChannelButton)
    listenTo(sendMessageButton)

    reactions += {
        case ButtonClicked(s) => {
            s.text match {
                case "Add channel" => {
                    service.addChannel(createChannelText.text)
                }
                case "Send" => {
                    val chosenChannel = String.valueOf(channels.getSelectedItem())
                    service.sendMessage(chosenChannel, sendMessageNameText.text, sendMessageContentText.text)
                }
            }
        }
    }
    
    Timer(500) { update() }

    def update() {
        val chosenChannel = String.valueOf(channels.getSelectedItem())

        service.getChannelNames()
        service.getMessages()
        val channelNames = service.chat.getChannelNames()
        if (channels.getItemCount != channelNames.length) {
            channels.removeAllItems()
            channelNames.foreach { channels.addItem }
        }
        val channel = service.chat.getChannel(chosenChannel)
        if (channel != null) {
            messages.setText(channel.getMessagesAfter(0).map(m => m.sender + ": " + m.content).reverse.mkString("\n"))
        }
    }
}