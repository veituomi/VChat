class Message(val id: Int, val sender: String, val content: String) {
    def this(mdata: String) = {
        this(mdata.split(";")(0).toInt, mdata.split(";")(1), mdata.split(";")(2))
    }
}

object Message {
    private var id = 0
    def nextId = {
        id += 1
        id
    }
}