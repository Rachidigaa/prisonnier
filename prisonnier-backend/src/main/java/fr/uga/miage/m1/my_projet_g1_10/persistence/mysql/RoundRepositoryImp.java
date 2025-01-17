package fr.uga.miage.m1.my_projet_g1_10.persistence.mysql;



import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Round;
import fr.uga.miage.m1.my_projet_g1_10.core.repository.RoundRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public class RoundRepositoryImp implements RoundRepository {

    private final RoundJpaRepository roundJpaRepository;

    public RoundRepositoryImp(RoundJpaRepository roundJpaRepository)
    {
        this.roundJpaRepository=roundJpaRepository;
    }
    @Override
    public Round save(Round round) {
        return roundJpaRepository.save(round);
    }

    @Override
    public Optional<Round> findById(Long roundId) {
        return roundJpaRepository.findById(roundId);
    }
}
