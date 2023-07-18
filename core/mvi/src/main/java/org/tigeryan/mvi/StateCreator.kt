package org.tigeryan.mvi

/**
 * Interface to be implemented by companion objects of state classes that require state creation for load, failure, and success cases
 * @param S state class type
 * @param T the type of data the state receives on success
 */
interface StateCreator<S, T> {

    fun success(data: T): S

    fun failure(exception: Exception?): S

    fun loading(): S
}