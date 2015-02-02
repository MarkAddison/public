package net.addison.java8.benchmark;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import org.apache.log4j.Logger;

public class BenchmarkLongAdder {
    static final private Logger logger = Logger.getLogger(BenchmarkLongAdder.class.getName());
    static final private DecimalFormat decimalFormat = new DecimalFormat("00.00000000000000000000");

    LongAdder longAdder = new LongAdder();
    AtomicLong atomicLong = new AtomicLong();
    long primitiveLong;
    Thread[] threads;
    CyclicBarrier barrier;
    int addCount = 1000000;
    int threadCount;
    @SuppressWarnings("rawtypes")
    static HashMap<Class, Vector<Double>> instrumentationMap;

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws Exception {
	// initialise, ensure all classes are loaded and HotSpot is 'warmed up'
	initInstrumentation();
	BenchmarkLongAdder tester = new BenchmarkLongAdder();
	tester.run(10);

	logger.info("Number of cores available: " + Runtime.getRuntime().availableProcessors());
	initInstrumentation();
	for (int j = 1; j <= 50; j++) {
	    tester = new BenchmarkLongAdder();
	    tester.run(j);
	}

	int maxLen = 0;
	for (Class cls : instrumentationMap.keySet()) {
	    int len = cls.getSimpleName().length();
	    if (len > maxLen)
		maxLen = len;
	}

	for (Class cls : instrumentationMap.keySet()) {
	    StringBuilder text = new StringBuilder();
	    String strfmt = "%" + maxLen + "s:";
	    text.append(String.format(strfmt, cls.getSimpleName()));
	    boolean appendComma = false;
	    for (Double duration : instrumentationMap.get(cls)) {
		if (appendComma)
		    text.append(", ");
		else
		    appendComma = true;
		text.append(decimalFormat.format(duration));
	    }
	    logger.info(text.toString());
	}
    }

    @SuppressWarnings("rawtypes")
    static void initInstrumentation() {
	instrumentationMap = new HashMap<Class, Vector<Double>>();
	instrumentationMap.put(SynchronizedLongThread.class, new Vector<Double>());
	instrumentationMap.put(LongAdderThread.class, new Vector<Double>());
	instrumentationMap.put(AtomicLongThread.class, new Vector<Double>());
	instrumentationMap.put(NoOpThread.class, new Vector<Double>());
    }

    @SuppressWarnings("rawtypes")
    static void addMeasurement(Class cls, double duration) {
	Vector<Double> durations = instrumentationMap.get(cls);
	durations.add(duration);
    }

    public void run(int threadCount) throws Exception {
	barrier = new CyclicBarrier(threadCount + 1);
	measureThreads(SynchronizedLongThread.class, threadCount);
	measureThreads(AtomicLongThread.class, threadCount);
	measureThreads(LongAdderThread.class, threadCount);
	measureThreads(NoOpThread.class, threadCount);
    }

    private <T extends SimulatedThread> void measureThreads(Class<T> cls, int threadCount) throws Exception {
	createAndStartThreads(cls, threadCount);
	long startTime = System.nanoTime();
	waitForBarrier();
	waitForBarrier();
	long endTime = System.nanoTime();
	join();
	double duration = (endTime - startTime) / 1000000.0 / threadCount;
	addMeasurement(cls, duration);
    }

    private <T extends SimulatedThread> void createAndStartThreads(Class<T> cls, int threadCount) throws Exception {
	threads = new Thread[threadCount];
	for (int i = 0; i < threadCount; i++) {
	    SimulatedThread thread = cls.newInstance();
	    thread.setBenchmark(this);
	    threads[i] = new Thread(thread);
	    threads[i].start();
	}
    }

    protected void waitForBarrier() {
	try {
	    barrier.await();
	} catch (InterruptedException | BrokenBarrierException ex) {
	    logger.info(ex.getMessage());
	}
    }

    public void join() {
	for (int i = 0; i < threads.length; i++) {
	    try {
		threads[i].join();
	    } catch (Exception ex) {
		logger.info(ex.getMessage());
	    }
	}
    }

    static abstract class SimulatedThread implements Runnable {
	protected BenchmarkLongAdder benchmark;

	@Override
	public void run() {
	    benchmark.waitForBarrier();
	    for (int i = 0; i < benchmark.addCount; i++) {
		runMethod();
	    }
	    benchmark.waitForBarrier();
	}

	public void setBenchmark(BenchmarkLongAdder benchmarkLongAdder) {
	    benchmark = benchmarkLongAdder;
	}

	public abstract void runMethod();
    }

    static class SynchronizedLongThread extends SimulatedThread {
	@Override
	public void runMethod() {
	    synchronized (benchmark) {
		benchmark.primitiveLong++;
	    }
	}
    }

    static class LongAdderThread extends SimulatedThread {
	@Override
	public void runMethod() {
	    benchmark.longAdder.increment();
	}
    }

    static class AtomicLongThread extends SimulatedThread {
	@Override
	public void runMethod() {
	    benchmark.atomicLong.getAndIncrement();
	}
    }

    static class NoOpThread extends SimulatedThread {
	@Override
	public void runMethod() {
	    // NO-OP
	}
    }
}