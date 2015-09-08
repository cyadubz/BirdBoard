package com.cya.birdboard.core.model;

import java.util.Date;

public interface Tweet {
    Date getCreatedAt();

    long getId();

    String getText();

    String getUsername();
}
