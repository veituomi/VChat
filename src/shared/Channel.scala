class Channel(var name: String, var messages: List[Message] = Nil) {
    def addMessages(newMessages: List[Message]) {
        messages = newMessages ::: messages
    }

    def addMessage(mdata: String) {
        if (mdata.length > 3) {
            messages = new Message(mdata) :: messages
        }
        messages = messages.sortWith((x, y) => x.id > y.id)
    }

    def lastId: Int = {
        if (messages.length == 0) {
            return 0
        }
        messages.head.id
    } 

    def getMessagesAfter(id: Int): List[Message] = messages.filter(_.id > id)
}