package com.resnik.osm.parse

class Way(val id: Long, val children: List<Node>, val tags: Map<String, String>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Way

        if (id != other.id) return false
        if (this.from() != other.from()) return false
        if (this.to() != other.to()) return false
        if (children != other.children) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }


    fun from(): Node? {
        return children.firstOrNull()
    }

    fun to(): Node? {
        return children.lastOrNull()
    }

}