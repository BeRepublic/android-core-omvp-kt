package com.omvp.app.model.mapper

import android.content.Context

import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.model.SampleModel
import com.omvp.commons.DataMapper
import com.omvp.domain.SampleDomain

import org.modelmapper.ModelMapper

import javax.inject.Inject

@PerFragment
class SampleModelDataMapper
@Inject
internal constructor(context: Context, modelMapper: ModelMapper) : DataMapper<SampleDomain, SampleModel>(context, modelMapper) {

    override fun transform(source: SampleDomain): SampleModel {
        return modelMapper.map(source, SampleModel::class.java)
    }

    override fun inverseTransform(source: SampleModel): SampleDomain {
        return modelMapper.map(source, SampleDomain::class.java)
    }

    override fun equals(source: SampleDomain, destination: SampleModel): Boolean {
        return false
    }

}
