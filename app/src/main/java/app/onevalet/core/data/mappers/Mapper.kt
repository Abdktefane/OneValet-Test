package app.onevalet.core.data.mappers

// Contract to transform Network Model to UI Model
fun interface Mapper<F, T> {
    suspend fun map(from: F): T
}

fun interface IndexedMapper<F, T> {
    suspend fun map(index: Int, from: F): T
}

internal inline fun <F, T> Mapper<F, T>.forLists(): suspend (List<F>) -> List<T> {
    return { list -> list.map { item -> map(item) } }
}


internal inline fun <F, T> IndexedMapper<F, T>.forLists(): suspend (List<F>) -> List<T> {
    return { list -> list.mapIndexed { index, item -> map(index, item) } }
}