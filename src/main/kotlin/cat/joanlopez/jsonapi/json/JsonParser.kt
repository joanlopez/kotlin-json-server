package cat.joanlopez.jsonapi.json

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

class JsonParser {

    private var parser: Parser = Parser()

    fun parseStorageFileContent(content: String): JsonArray<JsonObject> {
        val parsedContent = parser.parse(StringBuilder(content))
        if(parsedContent is JsonArray<*> && parsedContent[0] is JsonObject) {
            return parsedContent as JsonArray<JsonObject> // Really checked
        }
        return JsonArray()
    }

    fun parseRequestPayloadContent(content: String): JsonObject {
        val parsedContent = parser.parse(StringBuilder(content))
        if(parsedContent is JsonObject) {
            return parsedContent
        }
        return JsonObject()
    }

}
