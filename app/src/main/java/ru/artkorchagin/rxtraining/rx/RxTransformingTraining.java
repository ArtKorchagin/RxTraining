package ru.artkorchagin.rxtraining.rx;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import ru.artkorchagin.rxtraining.entity.Entity;
import ru.artkorchagin.rxtraining.exceptions.NotImplementedException;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 13.11.18
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class RxTransformingTraining {

    /* Training Methods */

    /**
     * Преобразование чисел в строки
     *
     * @param intObservable - источник
     * @return {@link Observable<String>} - который эммитит строки,
     * преобразованные из чисел в {@code intObservable}
     */
    public Observable<String> transformIntToString(Observable<Integer> intObservable) {
        throw new NotImplementedException();
    }

    /**
     * Преобразование {@link Observable<Integer>} эммитящих идентификаторы сущностей в сами
     * сущности, которые должны быть получены с помощью метода {@code requestApiEntity}
     *
     * @param idObservable - идентификаторы сущностей
     * @return {@link Observable<Entity>} эммитит сущности, соответствующие идентификаторам из
     * {@code idObservable}
     */
    public Observable<Entity> requestEntityById(Observable<Integer> idObservable) {
        throw new NotImplementedException();
    }

    /**
     * Распределение имён из {@code namesObservable} по первой букве имени, в отдельные
     * {@link GroupedObservable}
     *
     * @param namesObservable - {@link Observable<String>} с именами
     * @return {@link Observable} который эммитит {@link GroupedObservable} - сгруппированный
     * поток имён объединённых первой буквой в имени
     */
    public Observable<GroupedObservable<Character, String>> distributeNamesByFirstLetter(Observable<String> namesObservable) {
        throw new NotImplementedException();
    }

    /**
     * Объединить элементы, полученные из {@code intObservable} в списки {@link List} с максимальным
     * размером {@code listsSize}
     *
     * @param listsSize     - максимальный размер списка элементов
     * @param intObservable - {@link Observable} с произвольным количеством рандомных чисел
     * @return {@code Observable} который эммитит списки чисел из {@code intObservable}
     */
    public Observable<List<Integer>> collectsIntsToLists(int listsSize, Observable<Integer> intObservable) {
        throw new NotImplementedException();
    }

    /* Internal Dummy Methods */

    /**
     * !Вспомогательный метод! !Не изменять!
     * <p>
     * Выполнение HTTP запроса и эммит полученной сущности, соответствующей заданному идентификатору
     *
     * @param id - Идентификатор сущности {@link Entity}
     * @return {@link Observable<Entity>} который эммитит полученную сущность
     */
    Observable<Entity> requestApiEntity(int id) {
        // Выполнение запроса и эммит сущности
        return Observable.just(new Entity(id));
    }

}
