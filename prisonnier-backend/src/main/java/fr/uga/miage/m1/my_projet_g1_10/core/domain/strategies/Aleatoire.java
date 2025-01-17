package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
public class Aleatoire implements IStrategie {

   
    private final Random random = new SecureRandom();

    @Override
    public Decision decider(List<Decision> opponent) {
        int randomNumber = random.nextInt(2);
        if (randomNumber == 0)
            return Decision.TRAHIR;
        else
            return Decision.COOPERER;
    }
}