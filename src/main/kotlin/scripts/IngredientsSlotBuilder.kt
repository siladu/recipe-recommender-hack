package scripts

import com.github.gnoely.messageconverter.TwitterMessageConverter
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.nio.charset.Charset

import java.nio.file.Files
import java.nio.file.Paths
import com.google.gson.stream.JsonReader
import java.io.InputStreamReader
import java.io.FileInputStream
import java.util.function.Consumer


class Value(var value: String) {
}

class SlotTypes {
    var enumerationValues : MutableList<Value> = mutableListOf()
    var name: String = "Ingredients"
    var description: String = "Yummly ingredients"
    var valueSelectionStrategy: String = "TOP_RESOLUTION"

}
fun main(args: Array<String>) {

    val path = Paths.get(TwitterMessageConverter::class.java.classLoader
        .getResource("yumly-incredients.txt")!!.toURI())

    val istr = FileInputStream("/Users/andy.may/repos/ballmerpeakyblinders/src/main/resources/rawIngredients.json")
    val isr = InputStreamReader(istr)
    val str = isr.readLines().joinToString()

    val jsonArray = JsonParser().parse(str).asJsonArray
    var slots = SlotTypes()

    var count = 0

    jsonArray.forEach({it -> if (count++ < 9999) slots.enumerationValues.add(Value(it.asJsonObject.get("term").asString))})






    val outPath = Paths.get("/Users/andy.may/repos/ballmerpeakyblinders/src/main/resources/aws-ingredient-slots.json")

    println(outPath)
    Files.write(outPath, Gson().toJson(slots).toByteArray(Charsets.UTF_8))

}
