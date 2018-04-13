package com.omvp.data;

import android.net.Uri;

import com.omvp.domain.SampleDomain;

import org.threeten.bp.LocalDateTime;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import timber.log.Timber;

public class StaticRepository {

    private static final int NUM_ITEMS = 20;

    public static Map<String, SampleDomain> sampleDomainList = new LinkedHashMap<>(NUM_ITEMS);

    public static void init() {
        if (sampleDomainList.isEmpty()) {
            for (int i = 1; i <= NUM_ITEMS; i++) {
                String id = UUID.randomUUID().toString();
                sampleDomainList.put(id, initSampleDomain(id, i));
            }
        }
    }

    private static SampleDomain initSampleDomain(String id, int position) {
        return new SampleDomain(
                id,
                "item " + position,
                Uri.parse("https://www.google.com"),
                LocalDateTime.now(),
                R.mipmap.ic_launcher_round
        );
    }

}
