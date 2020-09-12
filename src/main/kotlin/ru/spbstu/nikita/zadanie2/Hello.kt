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

fun longHumanFormat(fileOrDir: File): String {

    return fileOrDir.name + ' ' + if (fileOrDir.canExecute()) "x" else {
        ""
    } +
            if (fileOrDir.canRead()) "r" else {
                ""
            } +
            if (fileOrDir.canWrite()) "w" else {
                ""
            } + ' ' +
            fileOrDir.lastModified() + ' ' + normalSize(fileOrDir.length())
}

fun longFormat(fileOrDir: File): String {
    return fileOrDir.name + ' ' + if (fileOrDir.canExecute()) "1" else {
        "0"
    } +
            if (fileOrDir.canRead()) "1" else {
                "0"
            } +
            if (fileOrDir.canWrite()) "1" else {
                "0"
            } + ' ' +
            fileOrDir.lastModified() + ' ' + fileOrDir.length()
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
            else -> require(File(args[flagNumber]).isDirectory || File(args[flagNumber]).isFile)
        }
    }

    if (!itsFile) /*folder*/ {
        if (flagL) {
            if (flagH) /*l and h*/ {
                for (item in dir.listFiles()!!) {
                    output.add(longHumanFormat(item))
                }
            } else /*only l*/ {
                for (item in dir.listFiles()!!) {
                    output.add(longFormat(item))
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
                output.add(longHumanFormat(dir))
            } else /*only l*/ {
                output.add(longFormat(dir))
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