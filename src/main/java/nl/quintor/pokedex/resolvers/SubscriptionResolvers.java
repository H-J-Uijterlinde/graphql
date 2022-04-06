package nl.quintor.pokedex.resolvers;

import graphql.schema.DataFetcher;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.observables.ConnectableObservable;
import lombok.extern.slf4j.Slf4j;
import nl.quintor.pokedex.model.Species;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscriptionResolvers {
    private final Flowable<Species> createSpeciesFlowable;

    public SubscriptionResolvers(MutationResolvers mutationResolvers) {
        ConnectableObservable<Species> connectableObservable = mutationResolvers.getCreateSpeciesPublishSubject().share().publish();
        connectableObservable.connect();
        createSpeciesFlowable = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

    public DataFetcher<Flowable<Species>> speciesCreated() {
        return dataFetchingEnvironment -> createSpeciesFlowable;
    }
}
