package Srbiau.ac.GA;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.ListPopulation;
import org.apache.commons.math3.genetics.SelectionPolicy;


public class TournamentSelection implements SelectionPolicy {

    /** number of chromosomes included in the tournament selections */
    private int arity;

    
    public TournamentSelection(final int arity) {
        this.arity = arity;
    }

    /**
     * Select two chromosomes from the population. Each of the two selected
     * chromosomes is selected based on n-ary tournament -- this is done by
     * drawing {@link #arity} random chromosomes without replacement from the
     * population, and then selecting the fittest chromosome among them.
     *
     * @param population the population from which the chromosomes are chosen.
     * @return the selected chromosomes.
     * @throws MathIllegalArgumentException if the tournament arity is bigger than the population size
     */
    public ChromosomePair select(final Population population) throws MathIllegalArgumentException {
        return new ChromosomePair(tournament((ListPopulation) population),
                                  tournament((ListPopulation) population));
    }

    /**
     * Helper for {@link #select(Population)}. Draw {@link #arity} random chromosomes without replacement from the
     * population, and then select the fittest chromosome among them.
     *
     * @param population the population from which the chromosomes are chosen.
     * @return the selected chromosome.
     * @throws MathIllegalArgumentException if the tournament arity is bigger than the population size
     */
    private Chromosome tournament(final ListPopulation population) throws MathIllegalArgumentException {
        if (population.getPopulationSize() < this.arity) {
            throw new MathIllegalArgumentException(LocalizedFormats.TOO_LARGE_TOURNAMENT_ARITY,
                                                   arity, population.getPopulationSize());
        }
        // auxiliary population
        ListPopulation tournamentPopulation = new ListPopulation(this.arity) {
            /** {@inheritDoc} */
            public Population nextGeneration() {
                // not useful here
                return null;
            }
        };

        // create a copy of the chromosome list
        List<Chromosome> chromosomes = new ArrayList<Chromosome> (population.getChromosomes());
        for (int i=0; i<this.arity; i++) {
            // select a random individual and add it to the tournament
            int rind = GeneticAlgorithm.getRandomGenerator().nextInt(chromosomes.size());
            tournamentPopulation.addChromosome(chromosomes.get(rind));
            // do not select it again
            chromosomes.remove(rind);
        }
        // the winner takes it all
        return tournamentPopulation.getFittestChromosome();
    }

    /**
     * Gets the arity (number of chromosomes drawn to the tournament).
     *
     * @return arity of the tournament
     */
    public int getArity() {
        return arity;
    }

    /**
     * Sets the arity (number of chromosomes drawn to the tournament).
     *
     * @param arity arity of the tournament
     */
    public void setArity(final int arity) {
        this.arity = arity;
    }

}
