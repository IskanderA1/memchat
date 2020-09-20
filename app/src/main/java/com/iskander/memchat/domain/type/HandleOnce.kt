package com.iskander.memchat.domain.type

/**
 * Класс-обертка. Присутствует параметризация T.
 * Предотвращает повторное получение данных.
 *
 * Слушатели LiveData срабатывают при смене конфигурации.
 * В таком случае все данные передаются в UI повторно.
 * Для предотвращения повторного отображения тоста
 * об одной и той же ошибке при повороте устройства
 * мы и используем этот класс.
 */
class HandleOnce<out T> (private val content: T){

    private var hasBeenHandled = false

    /**
     *  возвращает контент только один раз.
     *  В случае если контент уже был возвращен, возвращает null.
     *  Ничего не принимает. Возвращает
     */
    fun getContentIfNotHandled(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
}