package ru.spbstu.nikita.zadanie2

import java.io.File
import kotlin.math.pow

fun normalSize(fileSize: Long): String {
    val giga = 2.0.pow(30).toLong()
    val mega = 2.0.pow(20).toLong()
    val kilo = 2.0.pow(10).toLong()
    if (fileSize >= giga)
        return "${fileSize / giga} гигабайт"
    if (fileSize >= mega)
        return "${fileSize / mega} мегабайт"
    if (fileSize >= kilo)
        return "${fileSize / kilo} килобайт"
    return "$fileSize байт"
}

fun main(args: Array<String>) {
    require(args[0]=="ls")
    val dir = File(args[args.size - 1])
    var flagL = false
    var flagH = false
    var flagR = false
    var flagO = false
    val itsFile = dir.isFile
    val output = mutableListOf<String>()
    require(dir.exists())
    for (flagNumber in 1..args.size - 2) {
        when (args[flagNumber]) {
            "-l" -> flagL = true
            "-h" -> flagH = true
            "-r" -> flagR = true
            "-o" -> flagO = true
        }
    }
    if (!itsFile) /*folder*/ {
        if (flagL) {
            if (flagH) /*l and h*/ {
                for (item in dir.listFiles()!!) {
                    output.add(
                        item.name + ' ' + if (item.canExecute()) "x" else {""} +
                                if (item.canRead()) "r" else {""} +
                                        if (item.canWrite()) "w" else {""} + ' ' +
                                                item.lastModified() + ' ' + normalSize(item.length())
                    )
                }
            } else /*only l*/ {
                for (item in dir.listFiles()!!) {
                    output.add(
                        item.name + ' ' + if (item.canExecute()) "1" else {"0"} +
                                if (item.canRead()) "1" else {"0"} +
                                        if (item.canWrite()) "1" else {"0"} + ' ' +
                                                item.lastModified() + ' ' + item.length()
                    )
                }
            }
        } else /*short format*/ {
            for (item in dir.listFiles()!!) {
                output.add(item.name)
            }
        }
        if (flagR) output.reverse()
    } else {
        if (flagL) {
            if (flagH) /*l and h*/ {
                output.add(
                    dir.name + ' ' + if (dir.canExecute()) "x" else {""} +
                            if (dir.canRead()) "r" else {""} +
                                    if (dir.canWrite()) "w" else {""} + ' ' +
                                            dir.lastModified() + ' ' + normalSize(dir.length())
                )
            } else /*only l*/ {
                output.add(
                    dir.name + ' ' + if (dir.canExecute()) "1" else {"0"} +
                            if (dir.canRead()) "1" else {"0"} +
                                    if (dir.canWrite()) "1" else {"0"} + ' ' +
                                            dir.lastModified() + ' ' + dir.length()
                )
            }
        }
    }
    if (!flagO) /*Вывод ответа в консоль*/ {
        for (item in output) println(item)
    } else /*Вывод ответа в файл*/ {
        val writer = File(args[args.size-2]).bufferedWriter()
        for (item in output) {
            writer.write(item)
            writer.newLine()
        }
        writer.close()
    }
}