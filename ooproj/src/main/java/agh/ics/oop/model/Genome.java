package agh.ics.oop.model;

import java.util.Random;

public class Genome {
    private int[] genes;

    private final int MAX_GENE_VALUE = 7;

    public Genome(Settings settings) {
        this.genes = new int[MAX_GENE_VALUE];
        for (int i = 0; i < settings.getGenomeLength(); i++) {
            Random rand = new Random();
            int newGene = rand.nextInt(MAX_GENE_VALUE + 1);
            genes[i] = newGene;
        }
    }

    public int[] getGenes() {
        return genes;
    }
}
