package pierwszy.projekt.models

/**
 * Small model used for photo positions. Automatically checks if the position can be changed
 * (doesn't violate the upper and lower bounds). The lower bound is always 0.
 */
class PositionModel(var position: Int, max: Int) {
    private var upperLimit = max - 1
    private val lowerLimit = 0

    /**
     * Move forward one position if the upper bound is not violated.
     */
    fun forward(): PositionModel {
        if (position < upperLimit) position++
        return this
    }

    /**
     * Move back one position if the lower bound is not violated.
     */
    fun back(): PositionModel {
        if (position > lowerLimit) position--
        return this
    }

    val isBeginning: Boolean
        get() = position == lowerLimit

    val isEnd: Boolean
        get() = position == upperLimit
}