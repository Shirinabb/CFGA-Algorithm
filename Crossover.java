package Srbiau.ac.CFGA;




import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public abstract class Crossover {

    protected int SELECTIONS_PER_GENERATION = 10;
    protected double CROSS_PROBABILITY = 0.5;
    protected double MUTATION_PROBABILITY = 0.01;

    protected int nPopulation;
    protected int nGenesPerChromosome;
    protected List<Chromosome> chromosomes;

    protected Chromosome bestChromosome;

    private String saveFile;
    protected void crossPopulation(List<Chromosome> selectedChromosomes, List<Chromosome> crossedChromosomes) {
        while (crossedChromosomes.size() < nPopulation) {
            Random rand = new Random();

            int randomElementIndex = rand.nextInt(selectedChromosomes.size());
            Chromosome firstCrossChromosome = selectedChromosomes.get(randomElementIndex);

            if (crossedChromosomes.size() == nPopulation - 1) {
                crossedChromosomes.add(firstCrossChromosome);
            } else {
                randomElementIndex = rand.nextInt(selectedChromosomes.size());
                Chromosome secondCrossChromosome = selectedChromosomes.get(randomElementIndex);

                crossChromosomesUC(crossedChromosomes, firstCrossChromosome, secondCrossChromosome);
            }
        }
    }


    // Uniform Crossover
    protected void crossChromosomesUC(List<Chromosome> crossedChromosomes, Chromosome firstCrossChromosome, Chromosome secondCrossChromosome) 
    {
        Random rand = new Random();

        for (int i = 0; i < nGenesPerChromosome; ++i) {
            if (Integer.compare(firstCrossChromosome.getGenes().get(i), secondCrossChromosome.getGenes().get(i)) != 0) {
                if (rand.nextDouble() < CROSS_PROBABILITY) {
                    firstCrossChromosome.flipGene(i);
                    secondCrossChromosome.flipGene(i);
                }
            }
        }

        crossedChromosomes.add(firstCrossChromosome);
        crossedChromosomes.add(secondCrossChromosome);
    }

   

}
