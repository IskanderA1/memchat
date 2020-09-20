package com.iskander.memchat.domain.type

/**
 * Представляет значение одного из двух возможных типов (непересекающееся объединение).
 * Экземпляры [Either] являются экземплярами [Left] или [Right].
 * Соглашение FP гласит, что [Left] используется для «отказа»
 * и [Right] используется для "успеха".
 */
sealed class Either<out L, out R>{
    /** * Представляет левую часть класса [Either], который по соглашению является «Failure». */
    data class Left<out L>(val a: L) : Either<L, Nothing>()
    /** * Представляет правую часть класса [Either], который по соглашению является «Успехом». */
    data class Right<out R>(val b: R): Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    /**
     * выполняет одну из двух ф-ций высшего порядка, переданных в параметрах.
     * Принимает две ф-ции высшего порядка(для
     * параметризированных типов L(Left) и R(Right)):
     * functionLeft(принимает L, возвращает Any) и
     * functionRight(принимает R и возвращает Any). Возвращает Any.
     */
    fun either(functionLeft: (L) -> Any, functionRight: (R) -> Any): Any =
        when (this){
            is Left -> functionLeft(a)
            is Right -> functionRight(b)
        }
}

fun <A,B,C>((A)->B).compose(f: (B)->C): (A)-> C ={
        f(this(it))
}

/**
 * выполняет преобразование. если объект Either является
 * типом L – возвращает его без изменений; если объект Either является
 * типом R – с помощью переданной ф-ции высшего порядка(fn: (R) -> Either<L, T>)
 * подменяет исходный Either другим Either,
 * преобразовывая его содержимое (R).
 * Принимает ф-цию высшего порядка fn(принимает R,
 * возвращает Eihter<L, T>). Возвращает Either<L,T>,
 * где T – преобразованный R.
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R)-> Either<L, T>): Either<L, T> {
    return when(this){
        is Either.Left -> Either.Left(
            a
        )
        is Either.Right -> fn(b)
    }
}

/**
 * выполнят преобразование. если объект Either является
 * типом L – возвращает его без изменений; если объект Either является
 * типом R – возвращает преобразованный с помощью переданной
 * ф-ции высшего порядка(fn: (R) -> (T) объект типа R. Принимает ф-цию
 * высшего порядка fn(принимает R, возвращает T).
 * Возвращает Either<L, T>, где T – преобразованный R.
 */
fun <T, L, R> Either<L, R>.map(fn: (R)->(T)): Either<L, T> {
    return this.flatMap(fn.compose(::right))
}

/**
 *выполнят функцию. Если объект Either является
 * типом L – возвращает его без изменений; если объект Either является
 * типом R – возвращает его без изменений.
 * Выполняет ф-цию высшего порядка(fn: (R) -> Unit).
 * Принимает ф-цию высшего порядка fn(принимает R,ничего не возвращает).
 * Возвращает Either<L, R>
 */
fun <L, R> Either<L, R>.onNext(fn: (R)->Unit): Either<L, R> {
    this.flatMap(fn.compose(::right))
    return this
}
