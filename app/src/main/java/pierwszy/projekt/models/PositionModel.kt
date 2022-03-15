package pierwszy.projekt.models

class PositionModel(var position: Int, max: Int) {
    var upperLimit = max - 1

    fun forward(): Int {
        if (position < upperLimit) position++
        return position
    }

    fun back(): Int {
        if (position > 0) position--
        return position
    }

    val isBeginning: Boolean
        get() = position == 0

    val isEnd: Boolean
        get() = position == upperLimit
}