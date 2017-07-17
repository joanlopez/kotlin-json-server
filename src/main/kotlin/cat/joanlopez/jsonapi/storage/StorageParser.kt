package cat.joanlopez.jsonapi.storage

import cat.joanlopez.jsonapi.json.JsonParser
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import java.io.File

class StorageParser {

    private val STORAGE_DIR = "/storage"
    private val FORMAT_EXT = ".json"

    private val parser = JsonParser()

    fun retrieve(collection: String): JsonArray<JsonObject> {
        val path = "$STORAGE_DIR/$collection$FORMAT_EXT"
        val resource = StorageParser::class.java.getResource(path) ?: return JsonArray()
        val fileContent = resource.readText()
        return parser.parseStorageFileContent(fileContent)
    }

    fun save(collection: String, items: JsonArray<JsonObject>) {
        val filePath = StorageParser::class.java.getResource("/storage").toString() + "/$collection$FORMAT_EXT"
        File(filePath.substring(6)).printWriter().use { out ->
            out.write(items.toJsonString(true))
        }
    }

}