package cat.joanlopez.jsonapi.json

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.string
import org.testng.annotations.Test

import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Test
class TestJsonParser {

    fun testParseStorageFileContent() {
        val parser = JsonParser()
        val value = parser.parseStorageFileContent("[{\"name\": \"John\", \"surname\":\"McEnroe\"}]")
        val obj = value[0]
        assertTrue { value is JsonArray<JsonObject> }
        assertEquals(1, value.size)
        assertEquals("John", obj.string("name"))
        assertEquals("McEnroe", obj.string("surname"))
    }

    fun testParseRequestPayloadContent() {
        val parser = JsonParser()
        val value = parser.parseRequestPayloadContent("{\"name\": \"John\", \"surname\":\"McEnroe\"}")
        assertTrue { value is JsonObject }
        assertEquals("John", value.string("name"))
        assertEquals("McEnroe", value.string("surname"))
    }

}