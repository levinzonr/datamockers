import com.infoedge.jrandomizer.Generator
import com.tyro.oss.arbitrater.arbitraryInstance
import com.tyro.oss.arbitrater.arbitrater
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import uk.co.jemos.podam.api.PodamFactoryImpl
import java.nio.charset.Charset
import java.time.LocalDate
import kotlin.streams.toList


fun testRandomBeanGenerator() {

    // Random bean generator
    var beanPerson = EnhancedRandom.random(Person::class.java)
    println(beanPerson)

    val enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .charset(Charset.forName("UTF-8"))
            .stringLengthRange(5, 10)
            .randomizationDepth(3)
            .overrideDefaultInitialization(true)
            .seed(11L)
            .dateRange(LocalDate.now(), LocalDate.now().plusMonths(1))
            .build()

    // Using enchancedRandom to maninulate randomizer
    beanPerson = enchancedRandom.nextObject(Person::class.java)
    println(beanPerson)

    //Get a random collection
    val contacts = enchancedRandom.objects(Person::class.java, 5).toList()
    println(contacts)
}


fun testArbitrater() {

    // Will use default values by default
    val arbPerson = Person::class.arbitrater().createInstance()
    println(arbPerson)

    // With nullable fields
    val personWithNulls = Person::class.arbitrater()
            .generateNulls(true)
            .useDefaultValues(false)

            // override value
            .withValue("photo", "another url")
            .createInstance()
    println(personWithNulls)


   val generatedByMe =  Person::class.arbitrater()
           .useDefaultValues(false)
           .apply { registerGenerator { Location::class.arbitrater().useDefaultValues(false).createInstance() } }
           .apply { registerGenerator { "EasyString" } }
           .createInstance()
    println(generatedByMe)




}

fun testJRandomizer() {
    val generator = Generator<AnnotatedPerson>(AnnotatedPerson::class.java)

    val person = generator.generate()
    println(person)

    val list = generator.generate(3)
    println(list)

}


fun testPODAM() {
    val factory = PodamFactoryImpl()
    val person = factory.manufacturePojo(Person::class.java)
    println(person)

    val person2 = factory.manufacturePojoWithFullData(Person::class.java)
    println(person2)

    val person3 = factory.manufacturePojoWithFullData(Person::class.java, Location::class.java)
    println(person3)
}

fun main(args: Array<String>) {

    // Random bean implementation
    testRandomBeanGenerator()
    println("\n=====================/=========================\n")

    // arbitrater implementation
    testArbitrater()
    println("\n=====================/=========================\n")


    // Randomizer implementation using annotations
    testJRandomizer()
    println("\n=====================/=========================\n")


    // PODAM implementation
    testPODAM()

}