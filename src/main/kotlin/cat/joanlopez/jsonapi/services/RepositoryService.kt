package cat.joanlopez.jsonapi.services

import cat.joanlopez.jsonapi.domain.Model
import cat.joanlopez.jsonapi.domain.ModelCollection
import cat.joanlopez.jsonapi.json.JsonParser
import cat.joanlopez.jsonapi.storage.StorageParser
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.string

class RepositoryService {

    private val storage = StorageParser()

    private val parser = JsonParser()

    fun all(collection: String): ModelCollection {
        val items = storage.retrieve(collection)
        return jsonArrayToCollection(items)
    }

    fun getById(collection: String, id: String): Model {
        val items = storage.retrieve(collection)
        val filtered = items.filter { it.string("id") == id }
        if(filtered.size > 0) return jsonObjectToModel(filtered[0]) else return Model()
    }

    fun create(collection: String, payload: String): Model {
        val items = storage.retrieve(collection)
        val maxId = if(items.size > 0) items.string("id").map { it!!.toInt()}.toIntArray().max() else 0
        val jsonObject = parser.parseRequestPayloadContent(payload)
        jsonObject.put("id", maxId?.plus(1).toString())
        items.add(jsonObject)
        storage.save(collection, items)
        return jsonObjectToModel(jsonObject)
    }

    fun deleteById(collection: String, id: String): Model {
        val items = storage.retrieve(collection)
        val deleted = items.filter { it.string("id") == id }
        if(deleted.size < 1) return Model()
        val filtered = JsonArray<JsonObject>()
        items.forEach { if(it.string("id") != id) filtered.add(it) }
        storage.save(collection, filtered)
        return jsonObjectToModel(deleted[0])
    }

    private fun jsonArrayToCollection(items: JsonArray<JsonObject>): ModelCollection {
        val collection = ModelCollection()
        items.forEach {
            val model = jsonObjectToModel(it)
            collection.items.add(model)
        }
        return collection
    }

    private fun jsonObjectToModel(jsonObject: JsonObject) : Model {
        val model = Model(jsonObject.string("id") as String)
        jsonObject.entries.forEach {
            if(it.key != "id") {
                model.attributes.put(it.key, it.value as String)
            }
        }
        return model
    }
}