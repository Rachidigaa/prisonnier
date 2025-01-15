package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.g2_12.enums.TribeAction;
import fr.uga.miage.g2_12.strategies.Strategy;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies.StrategieAdaptateur;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StrategieAdaptateurTest {

    @Test
    void testAdaptateur() {
        Strategy fakeStrategy = new Strategy() {
            @Override
            public TribeAction calculateAction() {
                return TribeAction.COOPERATE;
            }
        };

        StrategieAdaptateur adaptateur = new StrategieAdaptateur(fakeStrategy);

        Decision decision = adaptateur.decider(List.of(Decision.COOPERER, Decision.TRAHIR));
        assertEquals(Decision.COOPERER, decision);
    }
}
