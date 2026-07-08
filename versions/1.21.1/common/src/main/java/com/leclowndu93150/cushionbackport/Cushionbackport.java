package com.leclowndu93150.cushionbackport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cushionbackport {
    public static final String MOD_ID = "cushionbackport";
    public static final String MOD_NAME = "Cushion Backport";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("{} initializing!", MOD_NAME);
    }
}
