package agh.ics.oop.model;

import java.util.Arrays;
import java.util.Random;

public class Genome {
    private final int[] genes;

    private final int MAX_GENE_VALUE = 7;

    public Genome(int[] genes) {
//        this.genes = new int[MAX_GENE_VALUE];
//        for (int i = 0; i < settings.getGenomeLength(); i++) {
//            Random rand = new Random();
//            int newGene = rand.nextInt(MAX_GENE_VALUE + 1);
//            genes[i] = newGene;
//        }
        this.genes = genes;
    }

    public int[] getGenes() {
        return genes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genome genome = (Genome) o;
        return Arrays.equals(genes, genome.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
