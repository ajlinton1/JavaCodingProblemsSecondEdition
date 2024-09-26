package chapter1;

import java.util.random.RandomGenerator;
import java.util.Random;

public class LegacyRandomAdapter extends Random {
    private final RandomGenerator randomGenerator;

    public LegacyRandomAdapter(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    protected int next(int bits) {
        return randomGenerator.nextInt() >>> (32 - bits);
    }

    @Override
    public int nextInt() {
        return randomGenerator.nextInt();
    }

    @Override
    public long nextLong() {
        return randomGenerator.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return randomGenerator.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return randomGenerator.nextFloat();
    }

    @Override
    public double nextDouble() {
        return randomGenerator.nextDouble();
    }

    @Override
    public void nextBytes(byte[] bytes) {
        randomGenerator.nextBytes(bytes);
    }
}
