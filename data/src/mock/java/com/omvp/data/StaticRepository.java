package com.omvp.data;

import android.net.Uri;

import com.omvp.domain.SampleDomain;

import org.threeten.bp.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

public class StaticRepository {

    public static Map<Long, SampleDomain> sampleDomainList = new HashMap<>();

    public static void init() {
        if (sampleDomainList.isEmpty()) {
            sampleDomainList.put((long) 1, initSampleDomain(1));
            sampleDomainList.put((long) 2, initSampleDomain(2));
            sampleDomainList.put((long) 3, initSampleDomain(3));
            sampleDomainList.put((long) 4, initSampleDomain(4));
            sampleDomainList.put((long) 5, initSampleDomain(5));
        }
    }

    private static SampleDomain initSampleDomain(long id) {
        return new SampleDomain(
                id,
                "title",
                Uri.parse("https://www.google.com"),
                LocalDateTime.now()
        );
    }

}
