package MinecraftDSL

import java.io.File
import kotlin.text.Regex

class Parser {

    var index = 0

    fun parse(file: String): DSLNode {
        var s = File(file).bufferedReader().readLines().joinToString(" ").replace("\t", " ").replace("\n", " ").replace("\\s+", " ")
        
        var split = s.split(" ").filter{ it.strip() != "" }

        var nodes = ArrayList<DSLNode>()
        while(index < split.size) {
            nodes.add(parseNode(split))
            index += 1
        }

        return DSLRoot(nodes.toTypedArray())
    }

    fun parseNode(split: List<String>): DSLNode {
        var word = split[index]
        println(word)
        when (word) {
            "join" -> {
                var nodes = ArrayList<DSLNode>()

                index += 1 // {
                index += 1
                
                while(split[index] != "}") {
                    nodes.add(parseNode(split))
                }

                return JoinNode(nodes.toTypedArray())
            }
            "leave" -> {
                var nodes = ArrayList<DSLNode>()

                index += 1 // {
                index += 1
                
                while(split[index] != "}") {
                    nodes.add(parseNode(split))
                }

                return LeaveNode(nodes.toTypedArray())
            }
            "give" -> {
                var items = ArrayList<String>()

                index += 1
                index += 1

                while(split[index] != "}") {
                    items.add(split[index])
                    index += 1
                }

                index += 1

                return GiveNode(items.toTypedArray())
            }
            "message" -> {
                var message = ""

                index += 1
                index += 1

                while(split[index] != "}") {
                    message = message + " " + split[index]
                    index += 1
                }

                index += 1

                return MessageNode(message)
            }
        }

        index += 1

        return MessageNode("failed")
    }

}

abstract class DSLNode {

}

data class DSLRoot(var nodes: Array<DSLNode>) : DSLNode() {

}

data class JoinNode(var nodes: Array<DSLNode>) : DSLNode() {

}

data class LeaveNode(var nodes: Array<DSLNode>) : DSLNode() {

}

data class GiveNode(var items: Array<String>) : DSLNode() {

}

data class MessageNode(var message: String) : DSLNode() {

}