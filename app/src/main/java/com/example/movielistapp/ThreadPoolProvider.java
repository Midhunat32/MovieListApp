package com.example.movielistapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolProvider {
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CORES);


}
