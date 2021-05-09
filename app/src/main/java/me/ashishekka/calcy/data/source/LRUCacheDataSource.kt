package me.ashishekka.calcy.data.source

import me.ashishekka.calcy.data.Calculation
import java.security.InvalidKeyException

class LRUCacheDataSource(private val capacity: Int = 10) : CalculationDataSource {
    private val map = HashMap<String, Node>(capacity)

    //These values will not be saved in the map
    private var head: Node = Node("", Calculation("", emptyList(), 0))
    private var tail: Node = Node("", Calculation("", emptyList(), 0))

    init {
        head.next = tail
        tail.prev = head
    }


    fun get(key: String, default: Calculation? = null): Calculation {
        val node = map[key]
        return node?.let {
            remove(it)
            add(it)
            it.value
        } ?: default ?: throw InvalidKeyException("No entry found for key $key")
    }

    fun put(key: String, value: Calculation) {
        val node = map[key]

        node?.let {
            remove(it)
            it.value = value
            add(it)
        } ?: run {
            if (map.size == capacity) {
                tail.prev?.let {
                    map.remove(it.key)
                    remove(it)
                }
            }

            val newNode = Node(key, value)
            map[key] = newNode
            add(newNode)
        }
    }

    private fun add(node: Node) {
        val headNext = head.next
        head.next = node
        node.next = headNext
        node.prev = head
        headNext?.prev = node
    }

    private fun remove(node: Node) {
        val prev = node.prev
        val next = node.next

        prev?.next = next
        next?.prev = prev
    }

    override fun getCalculations(): List<Calculation> {
        val list = mutableListOf<Calculation>()
        var temp = head.next
        while (temp != null && temp !== tail) {
            list.add(temp.value)
            temp = temp.next
        }
        return list
    }

    override fun getCalculation(id: String): Calculation = get(id)

    override fun setCalculation(id: String, calculation: Calculation) = put(id, calculation)

    override fun addCalculation(calculation: Calculation) = put(calculation.id, calculation)

    inner class Node(var key: String, var value: Calculation, var prev: Node? = null, var next: Node? = null)
}