import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.system.exitProcess

class Main : Application() {
    val valid = "This is a valid card number!"
    val notvalid = "This is NOT a valid card number!"

    override fun start(stage: Stage) {
        val cardField = TextField()
            cardField.promptText = "Enter card number here..."
        val result = Label()
        val button = Button("Check")
            button.isDefaultButton = true
        val subPane = HBox(cardField, button)
        val root = VBox(subPane, result)

        val scene = Scene(root, 225.0, 50.0)
        stage.scene = scene
        stage.title = "Credit Cred Checker"
        stage.show()

        button.setOnAction { e ->
            result.text = checkCardNumber(cardField.text)
            cardField.clear()
        }

        cardField.setOnKeyReleased { e ->
            if (e.code == KeyCode.ENTER) {
                button.fire()
            } else {
                result.text = ""
            }
        }

        scene.setOnKeyReleased { e ->
            if (e.code == KeyCode.ESCAPE) {
                exitProcess(0)
            }
        }
    }

    fun checkCardNumber(input: String): String {
        try {
            val cardnumber: Long = input.toLong()
            if (CreditNumCheck().isValid(cardnumber)) {
                return valid
            } else {
                return notvalid
            }
        } catch (nfe: NumberFormatException) {
            return notvalid
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(Main::class.java)
        }
    }
}
