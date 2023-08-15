import java.util.*
import kotlin.system.exitProcess

class CoffeeMachine {

    private var water: Int = 0
    private var milk: Int = 0
    private var beans: Int = 0
    private var scanner = Scanner (System.`in`)

    enum class Text (val text: String) {
        ENTER_COMMAND_MESSAGE_1("Введите команду"),
        MESSAGE_IN_CASE_OF_ERROR_1("Ошибка. Введите корректную команду"),
        ENTER_COMMAND_MESSAGE_2("Введите название напитка или \"назад\" для возврата в главное меню"),
        MESSAGE_IN_CASE_OF_ERROR_2("Рецепт не найден!");
    }

    private val commandMainMenu = listOf(
        "выключить",
        "наполнить",
        "статус",
        "кофе"
    )
    private val commandCoffeeMenu = listOf(
        "эспрессо",
        "американо",
        "капучино",
        "латте",
        "назад"
    )


    enum class Coffee(
        val coffeeName: String,
        val waterForPortion: Int,
        val milkForPortion: Int,
        val beansForPortion: Int
    ) {
        ESPRESSO("Эспрессо", 60, 0, 10),
        AMERICANO("Американо", 120, 0, 10),
        CAPPUCCINO("Капучино", 120, 60, 20),
        LATTE("Латте", 240, 120, 20);

        override fun toString(): String {
            return coffeeName
        }
    }

    private fun getCommand(
        enterCommandMessage: Text,
        messageInCaseOfError: Text,
        commandMenu: List<String>
    ): String {
        var command = " "
        while (command !in commandMenu) {
                println(enterCommandMessage.text) //запрос ввода
                scanner = Scanner(System.`in`) //считываем ввод
                if (scanner.hasNextLine()) { //если это строка
                    command = (scanner.next()).lowercase() //присваиваем ее значение переменной
                    if (command !in commandMenu) { //проверяем, есть ли такая команда
                    println(messageInCaseOfError.text) //если нет - сообщение об ошибке и продолжаем цикл
                    } //если есть, выходим из цикла while
                } else { //если не строка - выводим ошибку и продолжаем цикл
                    println(messageInCaseOfError.text)
                }
        }
        return command //возвращаем команду
    }

    private fun goToMainMenu() {
        mainMenu(getCommand(Text.ENTER_COMMAND_MESSAGE_1, Text.MESSAGE_IN_CASE_OF_ERROR_1, commandMainMenu))
    }

    private fun goToCoffeeMenu() {
        val command = getCommand(Text.ENTER_COMMAND_MESSAGE_2, Text.MESSAGE_IN_CASE_OF_ERROR_2, commandCoffeeMenu)
        if (command == "назад") {
            goToMainMenu()
        } else {
            makeCoffee(command)
        }
    }

    private fun mainMenu(command: String) {
        when (command) {
            "выключить" -> {
                println("До свидания!")
                exitProcess(0)
            }

            "наполнить" -> {
                water = 2000
                milk = 1000
                beans = 500
                println("Ингредиенты пополнены")
                goToMainMenu()
            }

            "статус" -> {
                println("Ингредиентов осталось: \n$water мл воды\n$milk мл молока\n$beans гр кофе")
                goToMainMenu()
            }

            "кофе" -> goToCoffeeMenu()
        }
    }

    private fun makeCoffee(command: String) {
        val newPortion = when (command) {
            "эспрессо" -> Coffee.ESPRESSO
            "американо" -> Coffee.AMERICANO
            "капучино" -> Coffee.CAPPUCCINO
            else -> Coffee.LATTE
        }
        if (newPortion.waterForPortion > water) {
            println("Недостаточно воды!")
            goToMainMenu()
        }
        if (newPortion.milkForPortion > milk) {
            println("Недостаточно молока!")
            goToMainMenu()
        }
        if (newPortion.beansForPortion > beans) {
            println("Недостаточно кофе!")
            goToMainMenu()
        }
            water -= newPortion.waterForPortion
            milk -= newPortion.milkForPortion
            beans -= newPortion.beansForPortion
            println("${newPortion.coffeeName} готов")
            goToMainMenu()
    }

    fun start() {
        println("Кофемашина готова к работе")
        goToMainMenu()
    }
}

fun main() {
    val coffeeMachine = CoffeeMachine()
    coffeeMachine.start()
}
