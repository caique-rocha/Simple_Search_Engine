package search

import java.io.File


fun main(args: Array<String>) {

    val fileName = args[1]
    val file = File(fileName)
    val inputs = file.readLines()
    val invertedIndex = invertedIndex(inputs)


    while (true) {

        when (menu()) {

            0 -> {

                println("\nBye!")
                break
            }
            1 -> { search(inputs, invertedIndex) }
            2 -> { printAll(inputs)}
            else -> { println("Incorrect option! Try again.") }
        }
    }
}

fun menu(): Int {

    println("=== Menu ===\n" +
            "1. Find a person\n" +
            "2. Print all people\n" +
            "0. Exit")

    return readLine()!!.toInt()
}

fun search(inputs: List<String>, invertedIndex: MutableMap<String, ArrayList<Int>>) {

    val finders = arrayListOf<Int>()

    print("Select a matching strategy: ALL, ANY, NONE")
    val strategy = readLine()!!
    println("Enter a name or email to search all suitable people.")
    val find = readLine()!!.split(" ")

    when (strategy) {

        "ALL" -> {

            find.forEach { itAll ->
                if (!invertedIndex.containsKey(itAll.toLowerCase())) {

                    return
                } else {
                    invertedIndex[itAll.toLowerCase()]?.let { finders.addAll(it) }
                }
            }
        }

        "ANY", "NONE" -> {

            val notRepeatAux = arrayListOf<String>()
            find.forEach { itAll ->
                if (invertedIndex.containsKey(itAll.toLowerCase()) &&
                            !notRepeatAux.contains(itAll)) {

                    invertedIndex[itAll.toLowerCase()]?.let {
                        finders.addAll(it)
                    }
                }
            }
        }
    }

    println()

    if (finders.isEmpty()) {

        println("No matching people found.")
    } else {

        if (strategy == "NONE") {

            println("${inputs.size - finders.size} persons found:")
            for (i in inputs.indices) {

                if (!finders.contains(i)) println(inputs[i])
            }

        } else {

            println("${finders.size} persons found:")
            finders.forEach { println(inputs[it]) }
        }
    }
}

fun printAll(inputs: List<String>) {

    println("=== List of people ===")
    inputs.forEach { println(it) }
}

fun invertedIndex(inputs: List<String>): MutableMap<String, ArrayList<Int>> {

    val invertedIndex = mutableMapOf<String, ArrayList<Int>>()

    for (i in inputs.indices) {

        val words = inputs[i].split(" ")

        words.forEach {

            if (invertedIndex.containsKey(it.toLowerCase())) {

                invertedIndex[it.toLowerCase()]?.add(i)
            } else {

                invertedIndex[it.toLowerCase()] = arrayListOf(i)
            }
        }
    }

    return invertedIndex
}