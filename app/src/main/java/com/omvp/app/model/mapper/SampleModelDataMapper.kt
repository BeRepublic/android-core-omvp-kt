package com.omvp.app.model.mapper

import android.content.Context

import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.model.SampleModel
import com.omvp.commons.DataMapper
import com.omvp.domain.SampleDomain

import javax.inject.Inject

@PerFragment
class SampleModelDataMapper
@Inject
internal constructor(context: Context) : DataMapper<SampleDomain, SampleModel>(context) {

    override fun transform(source: SampleDomain): SampleModel = SampleModel()

    override fun inverseTransform(source: SampleModel): SampleDomain = SampleDomain()

    override fun equals(source: SampleDomain, destination: SampleModel) = false

}
