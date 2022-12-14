package pairmatching.presentation

abstract class View {

    internal fun printEnter() = println()

    internal fun printError(exception: Exception) = println(exception.message)
    internal fun printError(throwable: Throwable) = println(throwable.message)
}