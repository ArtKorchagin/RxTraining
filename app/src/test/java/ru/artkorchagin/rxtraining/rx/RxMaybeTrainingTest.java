package ru.artkorchagin.rxtraining.rx;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import ru.artkorchagin.rxtraining.exceptions.ExpectedException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.reset;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 20.11.18
 */
public class RxMaybeTrainingTest {


    private RxMaybeTraining mRxMaybeTraining = Mockito.spy(new RxMaybeTraining());
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        reset(mRxMaybeTraining);
        mTestScheduler = new TestScheduler();
        RxJavaPlugins.setComputationSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) {
                return mTestScheduler;
            }
        });
    }

    @Test
    public void positiveOrEmpty_positiveValue() {
        TestObserver<Integer> testObserver = mRxMaybeTraining
                .positiveOrEmpty(1)
                .test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(1);
    }

    @Test
    public void positiveOrEmpty_negativeValue() {
        TestObserver<Integer> testObserver = mRxMaybeTraining
                .positiveOrEmpty(-1)
                .test();

        testObserver.assertNoValues();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void positiveOrEmpty_positiveSingle() {
        TestObserver<Integer> testObserver = mRxMaybeTraining
                .positiveOrEmpty(Single.just(1))
                .test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(1);
    }

    @Test
    public void positiveOrEmpty_negativeSingle() {
        TestObserver<Integer> testObserver = mRxMaybeTraining
                .positiveOrEmpty(Single.just(-1))
                .test();

        testObserver.assertNoValues();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void calculateSumOfValues_hasValues() {
        TestObserver<Integer> testObserver = mRxMaybeTraining
                .calculateSumOfValues(Observable.fromArray(1,2,3))
                .test();

        testObserver.assertValues(6);
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void calculateSumOfValues_noValues() {
        TestObserver<Integer> testObserver = mRxMaybeTraining
                .calculateSumOfValues(Observable.<Integer>empty())
                .test();

        testObserver.assertNoValues();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void leastOneElement_hasValues() {
        TestObserver<Integer> testObserver = mRxMaybeTraining
                .leastOneElement(Maybe.just(1), 2)
                .test();

        testObserver.assertValues(1);
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void leastOneElement_noValues() {
        TestObserver<Integer> testObserver = mRxMaybeTraining
                .leastOneElement(Maybe.<Integer>empty(), 2)
                .test();

        testObserver.assertValues(2);
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }
}