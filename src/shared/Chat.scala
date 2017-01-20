class Chat {
    var channels: List[Channel] = List()

    def addChannel(channel: Channel) {
        if (channels.exists(c => c.name == channel.name))
            return
        channels = channel :: channels
    }

    def getChannel(name: String) = channels.filter(_.name == name).head

    def getChannelNames() = channels.map(c => c.name)
}