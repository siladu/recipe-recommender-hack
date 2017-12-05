package com.github.gnoely.model.filereader

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object FileReader {

    fun readFile(fileName: String) : List<String> {
        val collect = Files.readAllLines(Paths.get(fileName))
                .parallelStream()
                .skip(1)
                .collect(Collectors.toList())
        return collect;
    }
}