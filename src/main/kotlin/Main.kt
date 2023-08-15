import java.util.*
import kotlin.system.exitProcess

class CoffeeMachine {

    private var water: Int = 0
    private var milk: Int = 0
    private var beans: Int = 0
    private var scanner = Scanner(System.`in`)
    private var command = " "

    private fun mainMenu() {
        while (true) {
            command = " "
            println("Введите команду")
            command = scanner.nextLine()

            when(command.lowercase()) {
                "выключить" -> {
                    println("До свидания!")
                    exitProcess(0)
                }
                "наполнить" -> {
                    water = 2000
                    milk = 1000
                    beans = 500
                    println("Ингредиенты пополнены")
                }
                "статус" -> println("Ингредиентов осталось: \n$water мл воды\n$milk мл молока\n$beans гр кофе")
                "кофе" -> {
                    makeCoffee()
                }
                else -> println("Ошибка. Введите корректную команду")
            }
        }
    }

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

    private fun makeCoffee() {
        command = " "
        println("Введите название напитка или \"назад\" для возврата в главное меню")
        command = scanner.nextLine().lowercase()
        val newPortion = when (command) {
            "эспрессо" -> Coffee.ESPRESSO
            "американо" -> Coffee.AMERICANO
            "капучино" -> Coffee.CAPPUCCINO
            "латте" -> Coffee.LATTE
            else -> null
        }
        if (newPortion == null) {
            println("Рецепт не найден!")
            mainMenu()
        }
        if (newPortion!!.waterForPortion > water) {
            println("Недостаточно воды!")
            mainMenu()
        }
        if (newPortion.milkForPortion > milk) {
            println("Недостаточно молока!")
            mainMenu()
        }
        if (newPortion.beansForPortion > beans) {
            println("Недостаточно кофе!")
            mainMenu()
        }
        water -= newPortion.waterForPortion
        milk -= newPortion.milkForPortion
        beans -= newPortion.beansForPortion
        println("${newPortion.coffeeName} готов")
        mainMenu()
    }


    fun start() {
        println("Кофемашина готова к работе")
        mainMenu()
    }
}

fun main() {
    val coffeeMachine = CoffeeMachine()
    coffeeMachine.start()
}
