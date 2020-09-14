package ru.spbstu.nikita.zadanie2

import java.io.File
import java.lang.Exception
import java.util.*

fun normalSize(fileSize: Long): String {
    val giga = 1024 * 1024 * 1024.0
    val mega = 1024 * 1024.0
    val kilo = 1024.0
    if (fileSize >= giga)
        return "${fileSize / giga} ГБ"
    if (fileSize >= mega)
        return "${fileSize / mega} МБ"
    if (fileSize >= kilo)
        return "${fileSize / kilo} КБ"
    return "$fileSize Б"
}

fun longOrLongHuman(fileOrDir: File, longF: Boolean, humanF: Boolean): String = when {
    longF && humanF -> longHumanFormat(fileOrDir)
    longF -> longFormat(fileOrDir)
    else -> fileOrDir.name
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
            } + "     " +
            Date(fileOrDir.lastModified()) + "     " + normalSize(fileOrDir.length())
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
    var outputName = String()
    require(dir.exists())
    var flagNumber = 0
    while (flagNumber<args.size-1) {
        when (args[flagNumber]) {
            "-l" -> flagL = true
            "-h" -> flagH = true
            "-r" -> flagR = true
            "-o" -> {
                flagO = true
                outputName = args[flagNumber + 1]
                flagNumber++
            }
            else -> throw Exception("Invalid Argument")
        }
        flagNumber++
    }
    if (!itsFile) {
        for (item in dir.listFiles()!!) output.add(longOrLongHuman(item, flagL, flagH))
        if (flagR) output.sortDescending() else output.sort()
    } else output.add(longOrLongHuman(dir, flagL, flagH))

    if (!flagO) /*Вывод ответа в консоль*/ {
        for (item in output) println(item)
    } else /*Вывод ответа в файл*/
        File(outputName).bufferedWriter().use { out ->
            output.forEach {
                out.write(it)
                out.newLine()
            }
        }
}