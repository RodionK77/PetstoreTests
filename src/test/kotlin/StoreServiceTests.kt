package tests

import kotlinx.coroutines.test.runTest
import org.example.api.Retrofit
import org.example.models.OrderStatus
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.random.Random

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreServiceTests {

    private val api = Retrofit.storeApi
    private var orderId: Long = 0
    private var petId: Long = 0

    @BeforeAll
    fun setup() {
        orderId = Random.nextLong(100, 100000)
        petId = Random.nextLong(100, 100000)
    }

    @Test
    @Order(1)
    fun `GET - Inventory`() = runTest {
        val response = api.getInventory()
        assertEquals(200, response.code())

        val inventory = response.body()
        assertTrue(!inventory.isNullOrEmpty())
        println("Inventory keys: ${inventory?.keys}")
    }

    @Test
    @Order(2)
    fun `POST - Place an order`() = runTest {
        val order = org.example.models.Order(
            id = orderId,
            petId = petId,
            quantity = 1,
            shipDate = "2026-01-01T12:00:00.000+0000",
            status = OrderStatus.PLACED,
            complete = true
        )

        val response = api.placeOrder(order)
        assertEquals(200, response.code())

        val createdOrder = response.body()
        assertEquals(orderId, createdOrder?.id)
        assertEquals(OrderStatus.PLACED, createdOrder?.status)
    }

    @Test
    @Order(3)
    fun `GET - Find order by ID`() = runTest {
        val response = api.getOrder(orderId)
        assertEquals(200, response.code())
        assertEquals(orderId, response.body()?.id)
    }

    @Test
    @Order(4)
    fun `DELETE - Delete order`() = runTest {
        val response = api.deleteOrder(orderId)
        assertEquals(200, response.code())
    }
}