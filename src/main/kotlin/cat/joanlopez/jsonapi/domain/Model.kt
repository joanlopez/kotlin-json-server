package cat.joanlopez.jsonapi.domain

data class Model(val id: String = "", val attributes: MutableMap<String, String> = mutableMapOf<String, String>())