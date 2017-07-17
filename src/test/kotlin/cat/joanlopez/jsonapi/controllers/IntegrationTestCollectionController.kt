package cat.joanlopez.jsonapi.controllers

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTestCollectionController {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun testGetEntireCollection()
    {
        val result = testRestTemplate.getForEntity("/users", String::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(
                "[{\"id\":\"1\",\"attributes\":{\"name\":\"John\",\"surname\":\"McEnroe\",\"email\":\"john.mcenroe@atp.com\",\"age\":\"58\"}},{\"id\":\"2\",\"attributes\":{\"name\":\"Roger\",\"surname\":\"Federer\",\"email\":\"roger.federer@atp.com\",\"age\":\"35\"}}]",
                result.body
        )
    }
}