package tests

import kotlinx.coroutines.test.runTest
import org.example.api.Retrofit
import org.example.models.User
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.random.Random
import kotlin.test.assertNotNull

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTests {

    private val api = Retrofit.userApi
    private var userId: Long = 0
    private var userName = ""
    private var userPass = "12345"
    private var userMail = "mail@gmail.com"

    @BeforeAll
    fun setup() {
        userId = Random.nextLong(100, 100000)
        userName = "User_$userId"
    }

    @Test
    @Order(1)
    fun `POST - Create user`() = runTest {
        val user = User(
            id = userId,
            username = userName,
            firstName = "Ivan",
            lastName = "Ivanov",
            email = userMail,
            password = userPass,
            phone = "123-45-67",
            userStatus = 1
        )

        val response = api.createUser(user)
        assertEquals(200, response.code())
    }

    @Test
    @Order(2)
    fun `GET - Login user`() = runTest {
        val response = api.login(userName, userPass)

        assertEquals(200, response.code())
        val message = response.body()?.string()
        assertNotNull(message)
        println("Login response: $message")
    }

    @Test
    @Order(3)
    fun `GET - Get user by username`() = runTest {
        val response = api.getUser(userName)
        assertEquals(200, response.code())
        assertEquals(userName, response.body()?.username)
        assertEquals(userMail, response.body()?.email)
    }

    @Test
    @Order(4)
    fun `PUT - Update user`() = runTest {
        val updatedUser = User(
            id = userId,
            username = userName,
            firstName = "Ivan",
            lastName = "Updated",
            email = "newmail@gmail.com",
            password = userPass,
            phone = "000-00-00",
            userStatus = 1
        )

        val response = api.updateUser(userName, updatedUser)
        assertEquals(200, response.code())
    }

    @Test
    @Order(5)
    fun `GET - Logout User`() = runTest {
        val response = api.logout()
        assertEquals(200, response.code())
    }

    @Test
    @Order(6)
    fun `DELETE - Delete user`() = runTest {
        val response = api.deleteUser(userName)
        assertEquals(200, response.code())
    }

    @Test
    @Order(7)
    fun `POST - Create users with list`() = runTest {
        val userList = listOf(generateUser(), generateUser(), generateUser())

        val response = api.createUsersWithList(userList)

        assertEquals(200, response.code())

        val message = response.body()?.string()
        println("CreateWithList Response: $message")
    }

    @Test
    @Order(8)
    fun `POST - Create users with array`() = runTest {
        val userList = listOf(generateUser(), generateUser())

        val response = api.createUsersWithArray(userList)

        assertEquals(200, response.code())

        val message = response.body()?.string()
        println("CreateWithArray Response: $message")
    }

    private fun generateUser(): User {
        val userId = Random.nextLong(10000, 99999)
        return User(
            id = userId,
            username = "RandomUser_$userId",
            firstName = "Random",
            lastName = "User",
            email = "randommail@gmail.com",
            password = "12345",
            phone = "111-222",
            userStatus = 1
        )
    }
}