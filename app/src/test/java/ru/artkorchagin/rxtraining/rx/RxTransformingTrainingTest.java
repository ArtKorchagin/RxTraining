package ru.artkorchagin.rxtraining.rx;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.TestObserver;
import ru.artkorchagin.rxtraining.entity.Entity;
import ru.artkorchagin.rxtraining.rx.entity.Pair;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.reset;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 13.11.18
 */
public class RxTransformingTrainingTest {

    private RxTransformingTraining mRxTransformingTraining = Mockito.spy(new RxTransformingTraining());

    @Before
    public void setUp() {
        reset(mRxTransformingTraining);
    }

    @Test
    public void transformIntToString() {
        Integer[] testIntValues = {0, 1, 2, 3};
        String[] testStringValues = {"0", "1", "2", "3"};

        TestObserver<String> testObservable = mRxTransformingTraining
                .transformIntToString(Observable.fromArray(testIntValues))
                .test();

        testObservable.assertComplete();
        testObservable.assertNoErrors();
        testObservable.assertValues(testStringValues);
    }

    @Test
    public void requestEntityById() {
        Integer[] testIdsValues = {0, 1, 2, 3};
        Entity[] testEntitiesValues = {new Entity(0), new Entity(1), new Entity(2), new Entity(3)};

        TestObserver<Entity> testObservable = mRxTransformingTraining
                .requestEntityById(Observable.fromArray(testIdsValues))
                .test();

        testObservable.assertComplete();
        testObservable.assertNoErrors();
        testObservable.assertValues(testEntitiesValues);
    }

    @Test
    public void distributeNamesByFirstLetter() {
        String[] testNamesValues = {"00", "11", "11123", "22", "33", "34", "35"};
        List<Pair<Character, List<String>>> testPairsValues = Arrays.asList(
                Pair.create('0', singletonList("00")),
                Pair.create('1', asList("11", "11123")),
                Pair.create('2', singletonList("22")),
                Pair.create('3', asList("33", "34", "35"))
        );

        TestObserver<Pair<Character, List<String>>> testObservable = mRxTransformingTraining
                .distributeNamesByFirstLetter(Observable.fromArray(testNamesValues))
                .flatMap(new Function<GroupedObservable<Character, String>, ObservableSource<Pair<Character, List<String>>>>() {
                    @Override
                    public ObservableSource<Pair<Character, List<String>>> apply(final GroupedObservable<Character, String> characterStringGroupedObservable) {
                        return characterStringGroupedObservable
                                .toList()
                                .map(new Function<List<String>, Pair<Character, List<String>>>() {
                                    @Override
                                    public Pair<Character, List<String>> apply(List<String> strings) {
                                        return Pair.create(characterStringGroupedObservable.getKey(), strings);
                                    }
                                })
                                .toObservable();
                    }
                })
                .test();

        testObservable.assertComplete();
        testObservable.assertNoErrors();
        testObservable.assertValueSequence(testPairsValues);
    }

    @Test
    public void collectsIntsToLists() {
        Integer[] testIntsValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<List<Integer>> resultIntsValues = asList(
                asList(0, 1, 2),
                asList(3, 4, 5),
                asList(6, 7, 8),
                asList(9, 10));

        TestObserver<List<Integer>> testObservable = mRxTransformingTraining
                .collectsIntsToLists(3, Observable.fromArray(testIntsValues))
                .test();

        testObservable.assertComplete();
        testObservable.assertNoErrors();
        testObservable.assertValueSequence(resultIntsValues);
    }

}