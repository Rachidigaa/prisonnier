package com.example.demo.core.domain.strategies;


import com.example.demo.core.domain.enums.Decision;

import java.util.List;

public interface IStrategie {
    public Decision decider(List<Decision> opponent);
}
