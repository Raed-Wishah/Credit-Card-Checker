package com.adlerd.ccc

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlin.system.exitProcess

class CreditCardCheckerGUI : Application() {
    private val dragDelta = Delta()
    private val root = AnchorPane()
    private val cardField = TextField()
    private val close = TitleButton(12.0, 12.0, 4.0, 20.0, "close")
    private val min = TitleButton(12.0, 12.0, 20.0, 4.0, "min")

    // Text field colors
    private val normal = Background(BackgroundFill(Color.LIGHTGREY, CornerRadii(8.0, false), Insets.EMPTY))
    private val selected = Background(BackgroundFill(Color.GREY, CornerRadii(8.0, false), Insets.EMPTY))
    private val good = Background(BackgroundFill(Color.LIGHTGREEN, CornerRadii(8.0, false), Insets.EMPTY))
    private val bad = Background(BackgroundFill(Color.INDIANRED, CornerRadii(8.0, false), Insets.EMPTY))

    override fun start(stage: Stage) {

        val title = Label("Credit Card Checker")
        title.alignment = Pos.TOP_CENTER

        cardField.layoutX = 50.0
        cardField.layoutY = 175.0
        cardField.prefWidth = 200.0
        cardField.promptText = "Enter card number here..."
        cardField.background = selected
        root.children.addAll(cardField, title, close, min)
        root.snapPositionX(10.0)
        root.snapPositionY(10.0)

        val scene = Scene(root, 480.0, 320.0)
        scene.stylesheets.add(CreditCardCheckerGUI::class.java.getResource("/css/style.css").toExternalForm())
        scene.fill = Color.TRANSPARENT
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.isResizable = false
        stage.title = title.text
        stage.scene = scene
        stage.show()

        cardField.setOnKeyReleased { e ->
            if ((e.code.isDigitKey || e.code == KeyCode.BACK_SPACE || e.code == KeyCode.DELETE) || e.code == KeyCode.ENTER) {
                // Check for 16 digits before checking for validity
                if (cardField.text.length == 16) {
                    if (checkCardNumber(cardField.text))
                        cardField.background = good
                    else
                        cardField.background = bad
                } else
                    cardField.background = selected
            }
        }

        cardField.setOnMouseClicked {
            if (cardField.text.length != 16)
                cardField.background = selected
        }

        // Scene-wide shortcuts
        scene.setOnKeyReleased { e ->
            if (e.code == KeyCode.ESCAPE) {
                exitProcess(0)
            }
        }

        close.setOnMouseClicked { e ->
            if (e.button == MouseButton.PRIMARY) {
                exitProcess(0)
            }
        }

        min.setOnMouseClicked { e ->
            if (e.button == MouseButton.PRIMARY) {
                stage.isIconified = true
            }
        }

        root.setOnMouseClicked {
            cardField.background = normal
        }

        // Logic for scene dragging
        root.setOnMousePressed { e ->
            dragDelta.x = stage.x - e.screenX
            dragDelta.y = stage.y - e.screenY
        }
        root.setOnMouseDragged { e ->
            stage.x = (e.screenX + dragDelta.x)
            stage.y = (e.screenY + dragDelta.y)
        }
    }

    private fun checkCardNumber(input: String): Boolean {
        return try {
            CreditCardChecker().isValid(input.toLong())
        } catch (nfe: NumberFormatException) {
            false
        }
    }

    class Delta {
        var x: Double = 0.0
        var y: Double = 0.0
    }

    class TitleButton(width: Double, height: Double, x: Double, y: Double, id: String) : Button() {

        init {
            this.minWidth = width
            this.prefWidth = width
            this.maxWidth = width
            this.minHeight = height
            this.prefHeight = height
            this.maxHeight = height
            this.layoutX = x
            this.layoutY = y
            this.id = id
        }
    }
}
