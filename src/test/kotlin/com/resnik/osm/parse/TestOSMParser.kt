package com.resnik.osm.parse

import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

class TestOSMParser {

    @Test
    fun testParseOSM1(){
        val path = this::class.java.getResource("/test1")?.path ?: throw IllegalStateException()
        val file = File(path)
        val collection = OSMParser().parse(FileInputStream(file))
        println(collection)
    }


}