package com.devkazonovic.projects.thenews.util

import okhttp3.internal.io.FileSystem
import okio.buffer
import java.io.File

/**Singleton that provide some IO Operations for Tests*/
object IOUtil {

    fun findFiles(name: String): File {
        return File(ClassLoader.getSystemResource(name).path)
    }

    fun readAll(path: File): String {
        val stringBuffer = StringBuilder()
        FileSystem.SYSTEM.source(path).use { fileSource ->
            fileSource.buffer().use { bufferedFileSource ->
                while (true) {
                    val line = bufferedFileSource.readUtf8Line() ?: break
                    stringBuffer.append(line)
                }
            }
        }
        return stringBuffer.toString()
    }

}