package ru.artkorchagin.rxtraining.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import ru.artkorchagin.rxtraining.exceptions.ExpectedException;
import ru.artkorchagin.rxtraining.exceptions.NotImplementedException;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 13.11.18
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class RxCreatingTraining {

    /* Тренировочные методы */

    /**
     * Эммит одного элемента
     *
     * @param value - Произвольное число
     * @return {@link Observable}, который эммитит только значение {@code value}
     */
    public Observable<Integer> valueToObservable(int value) {
        return Observable.just(value);
    }

    /**
     * Эммит элементов массива в {@link Observable}
     *
     * @param array - Массив произвольных строк
     * @return {@link Observable}, который эммитит по порядку все строки из заданного массива
     */
    public Observable<String> arrayToObservable(String[] array) {
        return Observable.fromArray(array);
    }

    /**
     * Выполнение метода с длительными вычислениями: {@link #expensiveMethod()}. Необходимо, чтобы метод
     * вызывался только при подписке на Observable
     *
     * @return {@link Observable} - который эммитит результат выполнения метода
     * {@link #expensiveMethod()}
     */
    public Observable<Integer> expensiveMethodResult() {
        return Observable.<Integer>create((emitter) -> {
            emitter.onNext(expensiveMethod());
            emitter.onComplete();
        });
    }

    /**
     * Возрастающая последовательность, начинающаяся с нуля с первоначальной задержкой и заданным
     * интервалом
     *
     * @return {@link Observable} - который эммитит возрастающую последовательность значений,
     * начиная с 0L, пока не произойдёт отписка.
     * Значения начинают эммититься с задержкой {@code initialDelay} миллисекунд и каждый
     * последующий с интервалом {@code period} миллисекунд.
     * {@code onError} или {@code onComplete} не должны вызваться.
     */
    public Observable<Long> increasingSequenceWithDelays(long initialDelay, long period) {
        return Observable.<Long>create(emitter -> {
            long count = 0L;
            for (;;) {
                emitter.onNext(count++);
            }
        }).concatMap(value -> Observable.just(value).delay(period, TimeUnit.MILLISECONDS))
                .delay(initialDelay, TimeUnit.MILLISECONDS);
    }

    /**
     * Возращение значения 0L с заданной задержкой
     *
     * @param delay - Задержка
     * @return Observable который эммитит только одно значение 0L с указанной
     * задержкой {@code delay}
     */
    public Observable<Long> delayedZero(long delay) {
        return Observable.just(0L).delay(delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Последовательный вызов нескольких методов с длительными вычислениями.
     *
     * @param unstableCondition - условие, которое необходимо передавать в {@code unstableMethod}
     * @return {@link Observable} который последовательно эммитит результаты выполнения методов, в
     * следующем порядке:
     * 1. {@link #expensiveMethod()}
     * 2. {@link #alternativeExpensiveMethod()}
     * 3. {@link #unstableMethod(boolean)}
     */
    public Observable<Integer> combinationExpensiveMethods(final boolean unstableCondition) {
        return Observable.create(emitter -> {
            emitter.onNext(expensiveMethod());
            emitter.onNext(alternativeExpensiveMethod());
            emitter.onNext(unstableMethod(unstableCondition));
            emitter.onComplete();
        });
    }

    /**
     * Без каких либо событий
     *
     * @return {@link Observable} который не эммитит ни одного элемента и не вызывает
     * {@code onComplete} или {@code onError}
     */
    public Observable<Integer> withoutAnyEvents() {
        return Observable.create(emitter -> {
        });
    }

    /**
     * Пустая последовательность
     *
     * @return {@link Observable} который не эммитит значения, вызывается только {@code onComplete}
     */
    public Observable<Integer> onlyComplete() {
        return Observable.create(Emitter::onComplete);
    }

    /**
     * Только одна ошибка
     *
     * @return {@link Observable} который не эммитит значения, только в {@code onError} приходит
     * ошибка {@link ExpectedException}
     */
    public Observable<Integer> onlyError() {
        return Observable.create((emitter) -> {
            emitter.onError(new ExpectedException());
        });
    }

    /* Вспомогательные методы */

    /**
     * Длительные вычисления. (Вспомогательный метод! Не изменять!)
     *
     * @return Результат вычислений
     */
    int expensiveMethod() {
        // Some Expensive Calculations
        return Integer.MAX_VALUE;
    }

    /**
     * Длительные вычисления. (Вспомогательный метод! Не изменять!)
     *
     * @return Результат вычислений
     */
    int alternativeExpensiveMethod() {
        // Some Expensive Calculations
        return Integer.MAX_VALUE;
    }

    /**
     * Метод, генерирующий ошибку при unstableCondition=true
     * (Вспомогательный метод! Не изменять!)
     *
     * @return Результат вычислений
     */
    int unstableMethod(boolean unstableCondition) {
        if (unstableCondition) {
            throw new ExpectedException();
        }
        return Integer.MAX_VALUE;
    }

}
