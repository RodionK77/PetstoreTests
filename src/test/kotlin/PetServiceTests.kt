package tests

import io.qameta.allure.*
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import org.example.models.Category
import org.example.models.Pet
import org.example.api.Retrofit
import org.example.models.Tag
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random

@Epic("Petstore API")
@Feature("Pet Management")
@DisplayName("Тестирование API Питомцев")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PetServiceTests {

    private val api = Retrofit.petApi
    private var petId: Long = 0
    private var petName: String = ""

    @BeforeAll
    fun setup() {
        petId = Random.nextLong(100, 100000)
        petName = "PetName_${Random.nextInt(1000)}"
    }

    @Test
    @Order(1)
    @Story("Как пользователь, я хочу создать питомца")
    @DisplayName("Успешное создание нового питомца")
    @Description("Проверяет успешное создание нового питомца")
    @Severity(SeverityLevel.BLOCKER)
    fun `POST - Create pet`() = runTest {
        val category = Category(id = 10, name = "Cats")
        val tag = Tag(id = 1, name = "cute-cat")

        val newPet = Pet(
            id = petId,
            name = petName,
            status = "available",
            category = category,
            tags = listOf(tag),
            photoUrls = listOf("url1", "url2")
        )

        val response = api.createPet(newPet)

        assertEquals(200, response.code())
        val body = response.body()

        assertNotNull(body, "Тело ответа не должно быть пустым")
        assertEquals(petId, body?.id)
        assertEquals(petName, body?.name)

        assertEquals(category.name, body?.category?.name)
        assertEquals(tag.name, body?.tags?.first()?.name)
    }

    @Test
    @Order(2)
    @Story("Как пользователь, я хочу найти питомца")
    @DisplayName("Получение питомца по его ID")
    @Severity(SeverityLevel.CRITICAL)
    fun `GET - Find pet by ID`() = runTest {
        val response = api.getPet(petId)

        assertEquals(200, response.code())
        val body = response.body()

        assertEquals(petName, body?.name)
        assertEquals(petId, body?.id)
    }

    @Test
    @Order(3)
    fun `PUT - Update pet status`() = runTest {
        val newStatus = "pending"

        val updatedPet = Pet(
            id = petId,
            name = petName,
            status = newStatus
        )

        val response = api.updatePet(updatedPet)

        assertEquals(200, response.code())
        assertEquals(newStatus, response.body()?.status)
    }

    @Test
    @Order(4)
    fun `POST - Update pet from Form Data`() = runTest {
        val newFormName = "FormUpdated_$petName"
        val newFormStatus = "sold"

        val response = api.updatePetWithForm(
            id = petId,
            name = newFormName,
            status = newFormStatus
        )

        assertEquals(200, response.code())

        val checkResponse = api.getPet(petId)
        assertEquals(newFormName, checkResponse.body()?.name)
        assertEquals(newFormStatus, checkResponse.body()?.status)
    }

    @Test
    @Order(5)
    fun `POST - Upload image`() = runTest {
        val fileContent = "fake pet image".toByteArray()
        val requestBody = okhttp3.RequestBody.create(
            okhttp3.MediaType.parse("image/jpeg"),
            fileContent
        )

        val part = MultipartBody.Part.createFormData("file", "pet-image.jpg", requestBody)

        val response = api.uploadImage(petId, part)

        assertEquals(200, response.code())
    }

    @Test
    @Order(6)
    fun `GET - Find pets by status`() = runTest {
        val response = api.findPetsByStatus("sold")

        assertEquals(200, response.code())
        val petsList = response.body()

        assertNotNull(petsList)
        assertTrue(petsList!!.isNotEmpty(), "Список не должен быть пустым")

        val myPetFound = petsList.find { it.id == petId }
        assertNotNull(myPetFound, "Питомец должен быть продан")
    }

    @Test
    @Order(7)
    fun `DELETE - Remove pet`() = runTest {
        val response = api.deletePet(petId)

        assertEquals(200, response.code())
    }

    @Test
    @Order(8)
    fun `GET - Check pet is deleted`() = runTest {
        val response = api.getPet(petId)

        assertEquals(404, response.code())
    }
}