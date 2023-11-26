package com.resnik.osm.parse

import org.dom4j.Node as XMLNode
import org.dom4j.io.SAXReader
import org.dom4j.tree.DefaultElement
import java.io.InputStream

class OSMParser {


    fun parse(stream: InputStream): OSMCollection {
        val saxReader = SAXReader()
        val document = saxReader.read(stream)
        val xmlNodes : List<XMLNode> = document.selectNodes("/osm")
        val rootElement = document.rootElement
        val nodes = mutableMapOf<Long, Node>()
        val ways = mutableMapOf<Long, Way>()
        val relations = mutableMapOf<Long, Relation>()
        rootElement.content().forEach { xmlNode ->
            when(xmlNode.name) {
                "node" -> {
                    val defaultElement = xmlNode as? DefaultElement
                    val id = defaultElement?.attribute("id")?.value?.toLongOrNull()
                    val lat = defaultElement?.attribute("lat")?.value?.toDoubleOrNull()
                    val lon = defaultElement?.attribute("lon")?.value?.toDoubleOrNull()
                    val tags = mutableMapOf<String, String>()
                    if (id != null) {
                        defaultElement.content()?.forEach { child ->
                            when(child.name) {
                                "tag" -> {
                                    val defaultChild = child as? DefaultElement
                                    val k = defaultChild?.attribute("k")?.value
                                    val v = defaultChild?.attribute("v")?.value
                                    if (k != null && v != null) tags[k] = v
                                }
                            }
                        }
                    }
                    if (id != null && lat != null && lon != null) nodes[id] = Node(id,lat, lon, tags)
                }
                "way" ->  {
                    val defaultElement = xmlNode as? DefaultElement
                    val id = defaultElement?.attribute("id")?.value?.toLongOrNull()
                    val children = mutableListOf<Node>()
                    val tags = mutableMapOf<String, String>()
                    if (id != null){
                        defaultElement.content()?.forEach { child ->
                            when (child.name) {
                                "nd" -> {
                                    val defaultChild = child as? DefaultElement
                                    val ref = defaultChild?.attribute("ref")?.value?.toLongOrNull()
                                    val refNode = nodes[ref]
                                    if (refNode != null) children.add(refNode)
                                }
                                "tag" -> {
                                    val defaultChild = child as? DefaultElement
                                    val k = defaultChild?.attribute("k")?.value
                                    val v = defaultChild?.attribute("v")?.value
                                    if (k != null && v != null) tags[k] = v
                                }
                            }
                        }
                        ways[id] = Way(id, children, tags)
                    }
                }
                "relation" -> {
                    val defaultElement = xmlNode as? DefaultElement
                    val id = defaultElement?.attribute("id")?.value?.toLongOrNull()
                    val members = mutableListOf<Member>()
                    val tags = mutableMapOf<String, String>()
                    if (id != null) {
                        defaultElement.content()?.forEach { child ->
                            when(child.name) {
                                "member" -> {
                                    val defaultChild = child as? DefaultElement
                                    val ref = defaultChild?.attribute("ref")?.value?.toLongOrNull()
                                    val role = defaultChild?.attribute("role")?.value
                                    val type = defaultChild?.attribute("type")?.value
                                    if (ref != null && role != null && type != null) members.add(Member(type, ref, role))
                                }
                                "tag" -> {
                                    val defaultChild = child as? DefaultElement
                                    val k = defaultChild?.attribute("k")?.value
                                    val v = defaultChild?.attribute("v")?.value
                                    if (k != null && v != null) tags[k] = v
                                }
                            }
                        }
                        relations[id] = Relation(id, members, tags)
                    }
                }
            }
        }
        return OSMCollection(nodes, ways, relations)
    }



}