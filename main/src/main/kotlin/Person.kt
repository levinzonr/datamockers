import com.infoedge.jrandomizer.annotations.*
import com.infoedge.jrandomizer.annotations.Number
import uk.co.jemos.podam.common.PodamIntValue
import uk.co.jemos.podam.common.PodamStrategyValue
import java.time.LocalDate

data class Person(
        val name: String?,
        val lastName: String?,

        @PodamIntValue(minValue = 1, maxValue = 100)
        val age: Int,
        val id: Long?,
        val photo: String = "defaulturl",
        val lastOnline: LocalDate,

        val place: Location? = Location()
)

@ReferencedRecord
data class Location(
        @Number
        val lat: Int = 1,

        @Number
        val lon: Int = 2,

        @City
        val name: String = "Some place"
)

data class AnnotatedPerson(
        @FirstName
        val firstName: String = "John",

        @LastName
        val lastName: String? = "Dow",

        @Number(min = 1, max = 100)
        val age: Int = 0,

        @Phone
        val phone: String? = "",

        @ReferenceRecord(clazz = Location::class)
        val location: Location = Location()
)
