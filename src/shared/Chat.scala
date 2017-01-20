class Chat {
    var channels: List[Channel] = List()

    def addChannel(channel: Channel) {
        if (channels.exists(c => c.name == channel.name))
            return
        channels = channel :: channels
    }

    def getChannel(name: String): Channel = {
        val channel = channels.filter(_.name == name)
        if (channel != Nil) {
            return channel.head
        }
        new Channel("Not found really")
    }

    def getChannelNames() = channels.map(c => c.name)
}