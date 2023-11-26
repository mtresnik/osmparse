package com.resnik.osm.parse

class OSMCollection(val nodes: Map<Long, Node> = mutableMapOf(), val ways: Map<Long, Way> = mutableMapOf(), val relations: Map<Long, Relation> = mutableMapOf()) {

    override fun toString(): String {
        return "OSMCollection(nodes=${nodes.size}, ways=${ways.size}, relations=${relations.size})"
    }
}