import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.random.Random
fun generateRandomCategory(id: Int): Category {
    val names = listOf("Electronics", "Books", "Clothing", "Home Appliances", "Toys")
    val name = names.random()
    return Category(id, name, listOf())
}

fun generateRandomProduct(id: Int, category: Category): Product {
    val names = listOf("Smartphone", "Laptop", "Novel", "T-shirt", "Refrigerator", "Toy Car")
    val name = names.random()
    val price = Random.nextDouble(10.0, 1000.0)
    val stock = Random.nextInt(1, 100)
    return Product(id, name, category, price, stock)
}

fun generateRandomUser(id: Int): User {
    val names = listOf("John Doe", "Jane Smith", "Alice Johnson", "Bob Brown", "Charlie Davis")
    val emails = listOf("example.com", "test.com", "email.com")
    val name = names.random()
    val email = "${name.split(" ").joinToString(".").lowercase()}@${emails.random()}"
    return User(id, name, email, listOf())
}

fun generateRandomOrder(id: Int, user: User, products: List<Product>): Order {
    val orderItems = products.shuffled().take(Random.nextInt(1, products.size)).map { product ->
        val quantity = Random.nextInt(1, 5)
        OrderItem(product, quantity, product.price * quantity)
    }
    val totalAmount = orderItems.sumOf { it.price }
    return Order(id, user, orderItems, totalAmount)
}
fun generateData(numCategories: Int, numProducts: Int, numUsers: Int, numOrders: Int) {
    val categories = (1..numCategories).map { generateRandomCategory(it) }
    val products = categories.flatMap { category ->
        (1..numProducts / numCategories).map { generateRandomProduct(it, category) }
    }
    val users = (1..numUsers).map { generateRandomUser(it) }
    val orders = users.flatMap { user ->
        (1..numOrders / numUsers).map { generateRandomOrder(it, user, products) }
    }

    val usersWithOrders = users.mapIndexed { index, user ->
        user.copy(orders = orders.filter { it.user == user })
    }

    serializeData(usersWithOrders, "generateUsers.json")
    println("Data generated and saved to generateUsers.json")
}

fun main() {
    generateData(numCategories = 5, numProducts = 25, numUsers = 10, numOrders = 30)
}
