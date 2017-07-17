package cat.joanlopez.jsonapi.controllers

import cat.joanlopez.jsonapi.services.RepositoryService
import org.springframework.web.bind.annotation.*

@RestController
class CollectionController {

    private val repository = RepositoryService()

    @GetMapping("/{collection}")
    fun getEntireCollection(@PathVariable(value = "collection") collection: String) =
        repository.all(collection).items

    @GetMapping("/{collection}/{id}")
    fun getModelById(@PathVariable(value = "collection") collection: String,
                     @PathVariable(value = "id") id: String) =
            repository.getById(collection, id)

    @PostMapping("/{collection}")
    fun postModel(@PathVariable(value = "collection") collection: String,
                  @RequestBody payload: String) =
            repository.create(collection, payload)

    @DeleteMapping("/{collection}/{id}")
    fun deleteModelById(@PathVariable(value = "collection") collection: String,
                     @PathVariable(value = "id") id: String) =
            repository.deleteById(collection, id)
}