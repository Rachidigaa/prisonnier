package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class PacificateurNaif implements IStrategie {
   
    private final Random random = new SecureRandom();
    private double probabiliteDeTrahir = 0.1;

    public void setProbabiliteDeTrahir(Double probabilite) {
        this.probabiliteDeTrahir = probabilite;
    }

    @Override
    public Decision decider(List<Decision> opponent) {
       
        if (random.nextDouble() < probabiliteDeTrahir) {
            return Decision.TRAHIR;
        }
        return Decision.COOPERER;
    }
}
