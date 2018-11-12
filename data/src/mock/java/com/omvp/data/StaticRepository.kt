package com.omvp.data

import android.net.Uri
import com.omvp.domain.SampleDomain
import org.threeten.bp.LocalDateTime
import java.util.*

object StaticRepository {

    private const val NUM_ITEMS = 20

    var sampleDomainList: MutableMap<String, SampleDomain> = LinkedHashMap(NUM_ITEMS)

    fun init() {
        if (sampleDomainList.isEmpty()) {
            for (i in 1..NUM_ITEMS) {
                val id = UUID.randomUUID().toString()
                sampleDomainList[id] = initSampleDomain(id, i)
            }
        }
    }

    private fun initSampleDomain(id: String, position: Int): SampleDomain {
        return SampleDomain(
                id,
                "item $position",
                Uri.parse("https://www.google.com"),
                LocalDateTime.now(),
                R.mipmap.ic_launcher_round
        )
    }

}
