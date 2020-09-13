package ru.spbstu.nikita.zadanie2

import java.io.File

fun normalSize(fileSize: Long): String {
    val giga = 1024 * 1024 * 1024
    val mega = 1024 * 1024
    val kilo = 1024
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
    var output = mutableListOf<String>()
    var outputName = String()
    require(dir.exists())
    var validArgs = setOf("-l", "-h", "-r", "-o", "ls")

    for (flagNumber in 0..args.size - 2) {
        when (args[flagNumber]) {
            "-l" -> flagL = true
            "-h" -> flagH = true
            "-r" -> flagR = true
            "-o" -> {
                flagO = true
                outputName = args[flagNumber + 1]
                require(File(outputName).exists())
                validArgs += outputName
            }
            else -> require(args[flagNumber] in validArgs) //проверка на лишние аргументы
        }
    }
    if (!itsFile) {
        for (item in dir.listFiles()!!) output.add(longOrLongHuman(item, flagL, flagH))
        if (flagR) output.reverse() else output.sort()
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