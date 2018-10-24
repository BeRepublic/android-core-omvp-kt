package com.omvp.data.entity.mapper

import android.content.Context
import android.net.Uri
import com.omvp.commons.DataMapper
import com.omvp.data.entity.SampleEntity
import com.omvp.domain.SampleDomain
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleEntityDataMapper
@Inject
constructor(context: Context) : DataMapper<SampleEntity, SampleDomain>(context) {

    override fun transform(source: SampleEntity): SampleDomain {
        return SampleDomain(
                source.id,
                source.title,
                Uri.parse(source.link),
                LocalDateTime.now(),
                0
        )
    }

    override fun inverseTransform(source: SampleDomain): SampleEntity {
        return SampleEntity(
                source.id,
                source.title,
                source.link.toString(),
                source.pubdate.toInstant(ZoneOffset.UTC).epochSecond
        )
    }

    override fun equals(source: SampleEntity, destination: SampleDomain): Boolean {
        return false
    }

}
