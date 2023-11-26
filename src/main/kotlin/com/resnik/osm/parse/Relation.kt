package com.resnik.osm.parse

class Relation(val id: Long, val members: List<Member>, val tags: Map<String, String> = mutableMapOf()) {
}