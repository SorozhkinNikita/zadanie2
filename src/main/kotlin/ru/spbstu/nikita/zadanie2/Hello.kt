package ru.spbstu.nikita.zadanie2

import java.io.File

fun normalSize(fileSize: Long): String {
    val giga = 1073741824 //2^30
    val mega = 1048576 //2^20
    val kilo = 1024 //2^10
    if (fileSize >= giga)
        return "${fileSize / giga} ГБ"
    if (fileSize >= mega)
        return "${fileSize / mega} МБ"
    if (fileSize >= kilo)
        return "${fileSize / kilo} КБ"
    return "$fileSize Б"
}

fun main(args: Array<String>) {
    require(args.size in 1..7)
    val dir = File(args[args.size - 1])
    var flagL = false
    var flagH = false
    var flagR = false
    var flagO = false
    val itsFile = dir.isFile
    val output = mutableListOf<String>()
    var outputDirectory = String()
    require(dir.exists())
    for (flagNumber in 1..args.size - 2) {
        when (args[flagNumber]) {
            "-l" -> flagL = true
            "-h" -> flagH = true
            "-r" -> flagR = true
            "-o" -> {
                flagO = true
                outputDirectory = args[flagNumber + 1]
            }
            else -> require(File(args[flagNumber]).isDirectory)
        }
    }

    if (!itsFile) /*folder*/ {
        if (flagL) {
            if (flagH) /*l and h*/ {
                for (item in dir.listFiles()!!) {
                    output.add(
                        item.name + ' ' + if (item.canExecute()) "x" else {
                            ""
                        } +
                                if (item.canRead()) "r" else {
                                    ""
                                } +
                                if (item.canWrite()) "w" else {
                                    ""
                                } + ' ' +
                                item.lastModified() + ' ' + normalSize(item.length())
                    )
                }
            } else /*only l*/ {
                for (item in dir.listFiles()!!) {
                    output.add(
                        item.name + ' ' + if (item.canExecute()) "1" else {
                            "0"
                        } +
                                if (item.canRead()) "1" else {
                                    "0"
                                } +
                                if (item.canWrite()) "1" else {
                                    "0"
                                } + ' ' +
                                item.lastModified() + ' ' + item.length()
                    )
                }
            }
        } else /*short format*/ {
            for (item in dir.listFiles()!!) {
                output.add(item.name)
            }
        }
        output.sorted()
        if (flagR) output.reverse()
    } else {
        if (flagL) {
            if (flagH) /*l and h*/ {
                output.add(
                    dir.name + ' ' + if (dir.canExecute()) "x" else {
                        ""
                    } +
                            if (dir.canRead()) "r" else {
                                ""
                            } +
                            if (dir.canWrite()) "w" else {
                                ""
                            } + ' ' +
                            dir.lastModified() + ' ' + normalSize(dir.length())
                )
            } else /*only l*/ {
                output.add(
                    dir.name + ' ' + if (dir.canExecute()) "1" else {
                        "0"
                    } +
                            if (dir.canRead()) "1" else {
                                "0"
                            } +
                            if (dir.canWrite()) "1" else {
                                "0"
                            } + ' ' +
                            dir.lastModified() + ' ' + dir.length()
                )
            }
        } else /*short format*/ {
            for (item in dir.listFiles()!!) {
                output.add(dir.name)
            }
        }
    }

    if (!flagO) /*Вывод ответа в консоль*/ {
        for (item in output) println(item)
    } else /*Вывод ответа в файл*/ {
        val writer = File(outputDirectory).bufferedWriter()
        for (item in output) {
            writer.write(item)
            writer.newLine()
        }
        writer.close()
    }
}