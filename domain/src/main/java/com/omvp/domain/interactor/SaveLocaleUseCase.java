package com.omvp.domain.interactor;

import java.util.Locale;

import io.reactivex.Completable;

/**
 * Created by Angel on 05/09/2017.
 */

public interface SaveLocaleUseCase {

    Completable execute(Locale locale);

}
