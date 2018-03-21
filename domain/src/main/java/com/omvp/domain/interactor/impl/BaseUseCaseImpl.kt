package com.omvp.domain.interactor.impl

import com.omvp.domain.repository.Repository

abstract class BaseUseCaseImpl<out T : Repository>(val repository: T)
