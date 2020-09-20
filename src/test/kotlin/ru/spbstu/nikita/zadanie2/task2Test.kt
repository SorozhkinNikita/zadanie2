package ru.spbstu.nikita.zadanie2

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.assertEquals


class task2Test {
    @Test
    fun normalSizeTest() {
        assertEquals("512 Б", normalSize(512))
        assertEquals("1.0 КБ", normalSize(1024))
        assertEquals("1.0 МБ", normalSize(1024 * 1024))
        assertEquals("1.0 ГБ", normalSize(1024 * 1024 * 1024))
    }

    //длинная запись в человекочитаемом формате
    @Test
    fun longHumanFormatTest() {
        assertEquals(
            "first.txt xrw     Mon Sep 14 15:42:06 MSK 2020     3.25 КБ",
            longHumanFormat(File("forTests/first.txt"))
        )
        assertEquals(
            "second.txt xr     Mon Sep 14 15:19:48 MSK 2020     0 Б",
            longHumanFormat(File("forTests/second.txt"))
        )
        assertEquals(
            "third.txt xrw     Mon Sep 14 15:20:08 MSK 2020     0 Б",
            longHumanFormat(File("forTests/third.txt"))
        )
    }

    //длинная запись
    @Test
    fun longFormatTest() {
        assertEquals(
            "first.txt 111 1600087326470 3328",
            longFormat(File("forTests/first.txt"))
        )
        assertEquals(
            "second.txt 110 1600085988079 0",
            longFormat(File("forTests/second.txt"))
        )
        assertEquals(
            "third.txt 111 1600086008043 0",
            longFormat(File("forTests/third.txt"))
        )
    }

    //формирование списка
    @Test
    fun listFormationTest() {
        assertEquals(
            listOf(
                "first.txt 111 1600087326470 3328",
                "second.txt 110 1600085988079 0",
                "third.txt 111 1600086008043 0"
            ), listFormation(
                flagR = false, flagL = true, flagH = false, itsFile = false,
                directory = File("forTests")
            )
        )
        assertEquals(
            listOf(
                "third.txt 111 1600086008043 0",
                "second.txt 110 1600085988079 0",
                "first.txt 111 1600087326470 3328"
            ), listFormation(
                flagR = true, flagL = true, flagH = false, itsFile = false,
                directory = File("forTests")
            )
        )
        assertEquals(
            listOf(
                "first.txt xrw     Mon Sep 14 15:42:06 MSK 2020     3.25 КБ",
                "second.txt xr     Mon Sep 14 15:19:48 MSK 2020     0 Б",
                "third.txt xrw     Mon Sep 14 15:20:08 MSK 2020     0 Б"
            ), listFormation(
                flagR = false, flagL = true, flagH = true, itsFile = false,
                directory = File("forTests")
            )
        )
        assertEquals(
            listOf(
                "first.txt",
                "second.txt",
                "third.txt"
            ), listFormation(
                flagR = false, flagL = false, flagH = false, itsFile = false,
                directory = File("forTests")
            )
        )
        assertEquals(
            listOf(
                "first.txt xrw     Mon Sep 14 15:42:06 MSK 2020     3.25 КБ"
            ), listFormation(
                flagR = false, flagL = true, flagH = true, itsFile = true,
                directory = File("forTests/first.txt")
            )
        )
    }
}
