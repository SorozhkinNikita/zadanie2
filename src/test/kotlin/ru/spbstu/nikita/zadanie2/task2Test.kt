package ru.spbstu.nikita.zadanie2

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.assertEquals


class task2Test {
    //тесты для проверки входных данных
//    @Test
//    fun illegalArgs() {
//    //assertThrows(IllegalArgumentException::class.java){task2}
//        assertThrows<IllegalArgumentException> { main(arrayOf("0", "1", "2", "3", "4", "5", "6", "7"))}
//    }
    //перевод размера в человекочитаемый формат
    @Test
    fun normalSizeTest(){
        assertEquals("512 Б", normalSize(512))
        assertEquals("1.0 КБ", normalSize(1024))
        assertEquals("1.0 МБ", normalSize(1024*1024))
        assertEquals("1.0 ГБ", normalSize(1024*1024*1024))
    }
    //длинная запись в человекочитаемом формате
    @Test
    fun longHumanFormatTest(){
        assertEquals("first.txt xrw     Mon Sep 14 15:42:06 MSK 2020     3.25 КБ",
            longHumanFormat(File("C:\\Users\\soroz\\IdeaProjects\\zadanie2\\forTests\\first.txt")))
        assertEquals("second.txt xr     Mon Sep 14 15:19:48 MSK 2020     0 Б",
            longHumanFormat(File("C:\\Users\\soroz\\IdeaProjects\\zadanie2\\forTests\\first.txt")))
        assertEquals("third.txt xrw     Mon Sep 14 15:20:08 MSK 2020     0 Б",
            longHumanFormat(File("C:\\Users\\soroz\\IdeaProjects\\zadanie2\\forTests\\first.txt")))
    }
    //длинная запись
    @Test
    fun longFormatTest(){
        assertEquals("first.txt 111 1600087326470 3328",
            longHumanFormat(File("C:\\Users\\soroz\\IdeaProjects\\zadanie2\\forTests\\first.txt")))
        assertEquals("second.txt 110 1600085988079 0",
            longHumanFormat(File("C:\\Users\\soroz\\IdeaProjects\\zadanie2\\forTests\\first.txt")))
        assertEquals("third.txt 111 1600086008043 0",
            longHumanFormat(File("C:\\Users\\soroz\\IdeaProjects\\zadanie2\\forTests\\first.txt")))
    }
}
