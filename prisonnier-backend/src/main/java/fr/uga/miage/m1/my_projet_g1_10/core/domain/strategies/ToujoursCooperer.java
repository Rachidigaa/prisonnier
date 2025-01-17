package com.example.demo.core.domain.strategies;

import com.example.demo.core.domain.enums.Decision;

import java.util.List;
public class ToujoursCooperer implements IStrategie {
    @Override
    public Decision decider(List<Decision> opponent) {
        return Decision.COOPERER;
    }
}
