package org.cham.customercore.handler;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

/**
 * @author skobbekaduawa on 2023-05-18
 */
// Example of plugging in a custom handler that in this case will print a statement before and after all observations take place
@Component
class LogHandler implements ObservationHandler<Observation.Context> {

    private static final Logger log = LoggerFactory.getLogger(LogHandler.class);

    @Override
    public void onStart(Observation.Context context) {
        log.info("Before running the observation for context [{}], userType [{}]", context.getName(), getUserTypeFromContext(context));
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("After running the observation for context [{}], userType [{}]", context.getName(), getUserTypeFromContext(context));
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }

    private String getUserTypeFromContext(Observation.Context context) {
//        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(), false)
//                .filter(keyValue -> "userType".equals(keyValue.getKey()))
//                .map(keyValue::getValue)
//                .findFirst()
//                .orElse("UNKNOWN");
        return "Customer";
    }
}
