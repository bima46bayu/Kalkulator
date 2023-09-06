import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.R

class MainActivity : AppCompatActivity() {

    private lateinit var inputField: EditText
    private lateinit var operatorText: TextView
    private lateinit var resultText: TextView

    private var currentInput = ""
    private var currentOperator = ""
    private var currentResult = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputField = findViewById(R.id.text_input)
        operatorText = findViewById(R.id.operator)
        resultText = findViewById(R.id.text_hasil)

        val numberButtons = arrayOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9
        )

        val operatorButtons = arrayOf(
            R.id.button_plus, R.id.button_minus, R.id.button_times, R.id.button_per
        )

        // Set OnClickListener for number buttons
        for (buttonId in numberButtons) {
            findViewById<Button>(buttonId).setOnClickListener { appendNumber((it as Button).text.toString()) }
        }

        // Set OnClickListener for operator buttons
        for (buttonId in operatorButtons) {
            findViewById<Button>(buttonId).setOnClickListener { setOperator((it as Button).text.toString()) }
        }

        // Equal button
        findViewById<Button>(R.id.button_equals).setOnClickListener { calculateResult() }

        // Clear button
        findViewById<Button>(R.id.clear).setOnClickListener { clearInput() }

        // Delete button
        findViewById<Button>(R.id.del).setOnClickListener { deleteLastChar() }
    }

    private fun appendNumber(number: String) {
        currentInput += number
        inputField.setText(currentInput)
    }

    private fun setOperator(operator: String) {
        currentOperator = operator
        operatorText.text = operator
        inputField.setText("")
    }

    private fun calculateResult() {
        val secondOperand = inputField.text.toString().toDoubleOrNull() ?: 0.0

        when (currentOperator) {
            "+" -> currentResult += secondOperand
            "-" -> currentResult -= secondOperand
            "x" -> currentResult *= secondOperand
            "/" -> {
                if (secondOperand != 0.0) {
                    currentResult /= secondOperand
                } else {
                    currentResult = Double.NaN // Handle division by zero
                }
            }
            "%" -> currentResult = currentResult * secondOperand / 100
        }

        resultText.text = currentResult.toString()
        inputField.setText("")
        currentInput = ""
        currentOperator = ""
    }

    private fun clearInput() {
        inputField.setText("")
        operatorText.text = ""
        resultText.text = ""
        currentInput = ""
        currentOperator = ""
        currentResult = 0.0
    }

    private fun deleteLastChar() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            inputField.setText(currentInput)
        }
    }
}
