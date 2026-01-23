package org.study.bootcamp.task_52.youtube_view_counter.v1.application.service;

import java.util.HashMap;
import java.util.Map;

public class VideoManager {

    private final Object lock = new Object();
    private final Map<String, Integer> viewsMap = new HashMap<>();

    public void addView(String videoId) {
        if (videoId == null || videoId.isBlank()) {
            throw new IllegalArgumentException("videoId не должен быть пустым");
        }

        synchronized (lock) {
            Integer currentViews = viewsMap.get(videoId);
            int newViews = (currentViews == null) ? 1 : (currentViews + 1);
            viewsMap.put(videoId, newViews);
        }
    }

    public int getViewCount(String videoId) {
        if (videoId == null || videoId.isBlank()) {
            throw new IllegalArgumentException("videoId не должен быть пустым");
        }

        synchronized (lock) {
            Integer currentViews = viewsMap.get(videoId);
            return (currentViews == null) ? 0 : currentViews;
        }
    }
}
