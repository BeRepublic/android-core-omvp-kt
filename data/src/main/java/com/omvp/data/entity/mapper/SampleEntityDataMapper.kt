package com.omvp.data.entity.mapper

import android.content.Context

import com.omvp.commons.DataMapper
import com.omvp.data.entity.SampleEntity
import com.omvp.domain.SampleDomain

import org.modelmapper.ModelMapper

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleEntityDataMapper
@Inject
constructor(context: Context, modelMapper: ModelMapper) :
        DataMapper<SampleEntity, SampleDomain>(context, modelMapper) {

    override fun transform(source: SampleEntity): SampleDomain {
        return modelMapper.map(source, SampleDomain::class.java)
    }

    override fun inverseTransform(source: SampleDomain): SampleEntity {
        return modelMapper.map(source, SampleEntity::class.java)
    }

    override fun equals(source: SampleEntity, destination: SampleDomain): Boolean {
        return false
    }

}
