class Message(val id: Int, val sender: String, val content: String) {

}

object Message {
    private var id = 0
    def nextId = {
        id += 1
        id
    }
}