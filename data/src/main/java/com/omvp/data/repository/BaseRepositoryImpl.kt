package com.omvp.data.repository

import com.omvp.commons.DataMapper

/**
 * Created by Angel on 15/02/2018.
 */

abstract class BaseRepositoryImpl<out T : DataMapper<*, *>>(val dataMapper: T)
