import java.io.File
import java.io.FileInputStream
import java.util.*

object Secrets {

    private val PEXELS_API_KEY = "PEXELS_API_KEY"
    val pexelsApiKey = apiKeysProperties().getProperty(PEXELS_API_KEY)

    private fun apiKeysProperties(): Properties {
        val filename = "app.properties"
        val file = File(filename)
        if(!file.exists()) {
            throw Error("You need to prepare a file called $filename in the project root directory")
        }
        val properties = Properties()
        properties.load(FileInputStream(file))
        return properties
    }
}