package com.devkazonovic.projects.thenews.common.util

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object IOUtil {

    fun getAssetFileData(assets: AssetManager, name: String): String {
        val sb = StringBuilder()
        val input = assets.open(name)
        val br = BufferedReader(InputStreamReader(input, StandardCharsets.UTF_8))
        var str: String?
        while (br.readLine().also { str = it } != null) {
            sb.append(str)
        }
        br.close()

        return sb.toString()
    }
}