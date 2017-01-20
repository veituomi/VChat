class Channel(var name: String, var messages: List[Message] = Nil) {
    def addMessages(newMessages: List[Message]) {
        messages = messages ::: newMessages
    }

    def getMessagesAfter(id: Int): List[Message] = messages.filter(_.id > id)
}