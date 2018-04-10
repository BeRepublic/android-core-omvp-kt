package com.omvp.app.base.ui


/**
 * A simple abstract class that implements Comparable, extend your model class with BaseModel if
 * you want to use the DiffUtilsCallback to improve recyclerView updates.
 */
abstract class BaseModel : Comparable<Any>
