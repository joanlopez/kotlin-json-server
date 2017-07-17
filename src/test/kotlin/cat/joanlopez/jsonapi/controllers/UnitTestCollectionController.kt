import cat.joanlopez.jsonapi.controllers.CollectionController
import cat.joanlopez.jsonapi.domain.Model
import cat.joanlopez.jsonapi.storage.StorageParser
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.runners.MockitoJUnitRunner
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class UnitTestCollectionController {

    @InjectMocks
    lateinit var collectionController: CollectionController

    @Before
    fun setUp() {
        val originalPath = "/storage/users.json"
        val originalFilePath = UnitTestCollectionController::class.java.getResource(originalPath).toString()
        val backupFilePath = StorageParser::class.java.getResource("/storage").toString() + "/users.json.backup"
        File(originalFilePath.substring(6)).copyTo(File(backupFilePath.substring(6)))
    }

    @After
    fun tearDown() {
        val originalPath = "/storage/users.json"
        val backupPath = "/storage/users.json.backup"
        val originalFilePath = UnitTestCollectionController::class.java.getResource(originalPath).toString()
        val backupFilePath = UnitTestCollectionController::class.java.getResource(backupPath).toString()
        File(originalFilePath.substring(6)).deleteRecursively()
        File(backupFilePath.substring(6)).copyTo(File(originalFilePath.substring(6)))
        File(backupFilePath.substring(6)).deleteRecursively()
    }

    @Test
    fun testGetEntireCollection()
    {
        val result = collectionController.getEntireCollection("users")
        assertNotNull(result)
        assertTrue(result is MutableList<Model>)
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("John", result[0].attributes["name"])
        assertEquals("McEnroe", result[0].attributes["surname"])
        assertEquals("2", result[1].id)
        assertEquals("Roger", result[1].attributes["name"])
        assertEquals("Federer", result[1].attributes["surname"])
    }

    @Test
    fun testGetModelByIdWithExistingId()
    {
        val result = collectionController.getModelById("users", "1")
        assertNotNull(result)
        assertTrue(result is Model)
        assertEquals("1", result.id)
        assertEquals("John", result.attributes["name"])
        assertEquals("McEnroe", result.attributes["surname"])
    }

    @Test
    fun testGetModelByIdWithNonExistingId()
    {
        val result = collectionController.getModelById("users", "3")
        assertNotNull(result)
        assertTrue(result is Model)
        assertEquals("", result.id)
        assertEquals(0, result.attributes.size)
    }

    @Test
    fun testDeleteModelByIdWithExistingId()
    {
        val result = collectionController.deleteModelById("users", "1")
        val result2 = collectionController.getEntireCollection("users")
        assertNotNull(result)
        assertTrue(result is Model)
        assertEquals("1", result.id)
        assertEquals("John", result.attributes["name"])
        assertEquals("McEnroe", result.attributes["surname"])
        assertEquals(1, result2.size)
    }

    @Test
    fun testDeleteModelByIdWithNonExistingId()
    {
        val result = collectionController.deleteModelById("users", "3")
        val result2 = collectionController.getEntireCollection("users")
        assertNotNull(result)
        assertTrue(result is Model)
        assertEquals("", result.id)
        assertEquals(0, result.attributes.size)
        assertEquals(2, result2.size)
    }

    @Test
    fun testCreateModel()
    {
        val result = collectionController.postModel("users", "{\"name\":\"Rafa\",\"surname\":\"Nadal\"}")
        val result2 = collectionController.getEntireCollection("users")
        assertNotNull(result)
        assertTrue(result is Model)
        assertEquals("3", result.id)
        assertEquals("Rafa", result.attributes["name"])
        assertEquals("Nadal", result.attributes["surname"])
        assertEquals(3, result2.size)
    }
}