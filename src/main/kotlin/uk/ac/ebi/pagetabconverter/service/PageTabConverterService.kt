package uk.ac.ebi.pagetabconverter.service

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import uk.ac.ebi.pagetabconverter.model.PageTabFile
import java.io.File
import java.io.InputStreamReader

class PageTabConverterService {
    fun convert() {
        val json = File(this::class.java.classLoader.getResource("TestData.json").toURI()).inputStream()
        val output = File("/home/jhoan/EBI/projects/pagetab-converter/src/main/resources/output.tsv")
        val reader = JsonReader(InputStreamReader(json, "UTF-8"))
        val gson = GsonBuilder().create()

        output.delete()
        output.createNewFile()
        reader.beginArray()

        val header = gson.fromJson<PageTabFile>(reader, PageTabFile::class.java)
        output.appendText("Files")
        header.attributes.forEach { output.appendText("\t${it.name}") }
        output.appendText("\n")
        writeFile(header, output)

        while (reader.hasNext()) {
            val pageTabFile = gson.fromJson<PageTabFile>(reader, PageTabFile::class.java)
            writeFile(pageTabFile, output)
        }
    }

    private fun writeFile(file: PageTabFile, output: File) {
        output.appendText(file.path)
        file.attributes.forEach { output.appendText("\t${it.value}") }
        output.appendText("\n")
    }
}
